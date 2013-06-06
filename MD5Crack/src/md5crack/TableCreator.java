package md5crack;

import hashtable.Bytes;
import helpers.FileHelper;
import helpers.Reductor;
import helpers.CommonHelper;
import helpers.UIHelper;
import java.io.DataOutputStream;
import java.security.*;
import java.util.HashSet;
import java.util.Random;

/**
 * A class for generating rainbow tables and saving them into a file.
 *
 * @author Lauri Kangassalo / lauri.kangassalo@helsinki.fi
 */
public class TableCreator {

    private String charset;
    private int minPwLength;
    private int maxPwLength;
    private int chainsPerTable;
    private int chainLength;
    private Random random;
    private Reductor rf;
    private CommonHelper helper;
    private UIHelper uihelper;
    private MessageDigest md;
    private FileHelper file;

    /**
     * Initializes helper classes and variables.
     *
     * @param charset character set of the passwords
     * @param maxPwLength maximum password length
     * @param chainsPerTable number of rows per table
     * @param chainLength length of a single chain (row)
     */
    public TableCreator(String charset, int minPwLength, int maxPwLength, int chainsPerTable, int chainLength) {
        this.charset = charset;
        this.minPwLength = minPwLength;
        this.maxPwLength = maxPwLength;
        this.chainsPerTable = chainsPerTable;
        this.chainLength = chainLength;
        
        random = new Random(System.currentTimeMillis());
        rf = new Reductor(charset, minPwLength, maxPwLength);
        file = new FileHelper();
        helper = new CommonHelper();
        uihelper = new UIHelper();
        md = helper.getMD5digester();
        

    }

    /**
     * Generates chains for the rainbow table, and saves the starting and
     * endpoints in a file.
     *
     * @return true, if table generation was successful
     */
    public boolean createTable() {  
        DataOutputStream dos = file.createTableFile(charset.length(), minPwLength, maxPwLength, chainsPerTable, chainLength);
        uihelper.printTableGenerationStartStats();

        int keyspaceID = 0;
        int[] keyspaceRatio = helper.calculateKeyspaceRatios(charset, minPwLength, maxPwLength, chainsPerTable);
        // generate chains
        for (int i = 0; i < chainsPerTable; i++) {
            byte pwLength = (byte)(keyspaceID + minPwLength);
            
            byte[] startingPoint = createRandomStartingPoint(random, pwLength);

            byte[] endpoint = calculateChain(startingPoint, pwLength);
            
            // change keyspace when keyspace size is exceeded
            if(i > keyspaceRatio[keyspaceID] && keyspaceID < keyspaceRatio.length-1) {
                keyspaceID++;
            }

            file.writeToFile(dos, startingPoint, endpoint);

            // print progress
            if (i != 0 && i % (chainsPerTable / 20) == 0) {
                uihelper.printTableGenerationProgress(i, chainsPerTable);
            }
        }

        uihelper.printTableGenerationProgress(chainsPerTable, chainsPerTable);

        file.closeFile(dos);
        return true;
    }

    /**
     * Creates a random 'password'.
     *
     * @param random
     * @param startingPoint array, which will be overwritten by the 'password'
     */
    private byte[] createRandomStartingPoint(Random random, byte pwLength) {
        byte[] startingPoint = new byte[pwLength];
        random.nextBytes(startingPoint);

        for (int a = 0; a < startingPoint.length; a++) {
            startingPoint[a] = (byte) (Math.abs(startingPoint[a] % charset.length()));
        }
        return startingPoint;
    }

    /**
     * Loops each column with different reducing function to produce an endpoint.
     * 
     * @param md
     * @param currentEndpoint
     * @param rf
     * @param pwLength
     * @param hashset
     * @return a byte array containing the endpoint of the chain
     */
    private byte[] calculateChain(byte[] currentEndpoint, byte pwLength) {
        int j;
        byte[] hash;
        // loop each column with different reducing function
        for (j = 0; j < chainLength; j++) {
            hash = md.digest(currentEndpoint);
            currentEndpoint = rf.reduce(hash, j, pwLength);
        }
        return currentEndpoint;
    }
}
