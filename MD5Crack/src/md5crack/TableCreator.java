package md5crack;

import helpers.FileHelper;
import helpers.Reductor;
import helpers.CommonHelper;
import java.io.DataOutputStream;
import java.security.*;
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
    private long keyspace;

    /**
     * Initializes a class for creating rainbow tables.
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

        for (int i = minPwLength; i <= maxPwLength; i++) {
            this.keyspace += (long) Math.pow(charset.length(), i);
        }
    }

    /**
     * Generates chains for the rainbow table, and saves the starting and
     * endpoints in a file.
     *
     * @return true, if table generation was successful
     */
    public boolean createTable() {
        Random random = new Random(System.currentTimeMillis());
        Reductor rf = new Reductor(charset, minPwLength, maxPwLength);
        FileHelper file = new FileHelper();
        CommonHelper helper = new CommonHelper();

        MessageDigest md = helper.getMD5digester();
        if (md == null) {
            return false;
        }


        DataOutputStream dos = file.createTableFile();
        if (dos == null) {
            return false;
        }


        System.out.println("Starting table creation. Keyspace is " + keyspace + ".");

        byte[] startingPoint = new byte[maxPwLength];
        byte[] endpoint = new byte[maxPwLength];
        for (int i = 0; i < chainsPerTable; i++) {
            createRandomStartingPoint(random, startingPoint);

            byte[] hash;
            // loop each column with different reducing function
            for (int j = 0; j < chainLength; j++) {
                hash = md.digest(startingPoint);
                endpoint = rf.reduce(hash, j);
            }

            file.writeToFile(dos, startingPoint, endpoint);

            // print progress
            if (i != 0 && i % (chainsPerTable / 10) == 0) {
                System.out.println(i + "/" + chainsPerTable);
            }
        }

        System.out.println(chainsPerTable + "/" + chainsPerTable);
        file.closeFile(dos);
        return true;
    }

    /**
     * Creates a random 'password'.
     *
     * @param random
     * @param startingPoint array, which will be overwritten by the 'password'
     */
    private void createRandomStartingPoint(Random random, byte[] startingPoint) {
        random.nextBytes(startingPoint);

        for (int a = 0; a < startingPoint.length; a++) {
            startingPoint[a] = (byte) (Math.abs(startingPoint[a] % charset.length()));
        }
    }
}
