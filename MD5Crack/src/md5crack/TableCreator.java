package md5crack;

import helpers.FileHelper;
import helpers.Reductor;
import helpers.CommonHelper;
import helpers.UIHelper;
import java.io.BufferedOutputStream;
import java.security.*;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

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
        BufferedOutputStream dos = file.createTableFile(charset.length(), minPwLength, maxPwLength, chainsPerTable, chainLength);
        uihelper.printTableGenerationStartStats();

        int keyspaceID = 0;
        int[] keyspaceRatio = helper.calculateKeyspaceRatios(charset, minPwLength, maxPwLength, chainsPerTable);
        // generate chains
        
        int cores = Runtime.getRuntime().availableProcessors();
        BlockingQueue<EndAndStartpoint> queue = new LinkedBlockingQueue<>();
        int chains = chainsPerTable / cores;
        Chaingenerator[] chain = new Chaingenerator[cores];
        for (int i = 0; i < cores; i++) {
            chain[i] = new Chaingenerator(dos, (byte) minPwLength, chainLength, md, rf, file, chains, new Random(System.currentTimeMillis()), charset, queue, i);
            chain[i].start();
        }

        while (someThreadsAreAlive(chain)) {
            if (queue.isEmpty()) {
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                }
            } else {
                while (!queue.isEmpty()) {
                    EndAndStartpoint eas = queue.poll();
                    file.writeToFile(dos, eas.getStartingPoint(), eas.getEndpoint());
                }
            }
        }



//        for (int i = 0; i < chainsPerTable; i++) {
//            byte pwLength = (byte)(keyspaceID + minPwLength);
//            
//            byte[] startingPoint = createRandomStartingPoint(random, pwLength);
//
//
//            
// 
////            file.writeToFile(dos, startingPoint, endpoint);
//
//            
//            
////            byte[] endpoint = calculateChain(startingPoint, pwLength);
//            
//            // change keyspace when keyspace size is exceeded
//            if(i > keyspaceRatio[keyspaceID] && keyspaceID < keyspaceRatio.length-1) {
//                keyspaceID++;
//            }
//
//            
//
//            // print progress
//            if (i != 0 && i % (chainsPerTable / 20) == 0) {
//                uihelper.printTableGenerationProgress(i, chainsPerTable);
//            }
//        }

        uihelper.done();

        file.closeFile(dos);
        return true;
    }

    private boolean someThreadsAreAlive(Chaingenerator[] chains) {
        for (int i = 0; i < chains.length; i++) {
            if (chains[i].isAlive()) {
                return true;
            }
        }
        return false;
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
     * Loops each column with different reducing function to produce an
     * endpoint.
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
    
    public String getTableFilename() {
        return file.getFilename();
    }
}
