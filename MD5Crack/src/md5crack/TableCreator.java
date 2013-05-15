/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package md5crack;

import java.io.File;
import java.security.*;
import java.util.HashMap;
import java.util.Random;

/**
 * A class for generating rainbow tables and saving them into a file.
 *
 * @author Lauri Kangassalo / lauri.kangassalo@helsinki.fi
 */
public class TableCreator {

    private String charset;
    private int pwLength;
    private int chainsPerTable;
    private int chainLength;
    private long keyspace;

    /**
     * Initializes a class for creating rainbow tables.
     *
     * @param charset character set of the passwords
     * @param pwLength password length
     * @param chainsPerTable number of rows per table
     * @param chainLength length of a single chain (row)
     */
    public TableCreator(String charset, int pwLength, int chainsPerTable, int chainLength) {
        this.charset = charset;
        this.pwLength = pwLength;
        this.chainsPerTable = chainsPerTable;
        this.chainLength = chainLength;

        this.keyspace = (long) Math.pow(charset.length(), pwLength);
    }

    public boolean createTable() {
        Reductor rf = new Reductor(charset, pwLength);
        Random random = new Random(System.currentTimeMillis());
        Helper helper = new Helper();

        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("MD5-algorithm not found... this shouldn't be.");
            return false;
        }

//        File file = openFile();
//        if (file == null) {
//            return false;
//        }
        
        
        System.out.println("Starting table creation. Keyspace is " + keyspace+".");
        
        byte[] startingPoint = new byte[pwLength];
        for (int i = 0; i < chainsPerTable; i++) {   
            createRandomStartingPoint(random, startingPoint);
            
            byte[] hash;
            String endpoint;
            for (int j = 0; j < chainLength; j++) {
                hash = md.digest(startingPoint);
                endpoint = rf.reduce(hash, j);     
            }

            // statistics
            if(i != 0 && i%(chainsPerTable/10)==0) {
                System.out.println(i+"/"+chainsPerTable);
            }
        }
        System.out.println(chainsPerTable+"/"+chainsPerTable);
        return true;
    }

    private File openFile() {

        File file;
            file = new File(getCurrDate() + ".tbl");

        
        return file;
    }

    private String getCurrDate() {
        return "20130514-1202";
    }

    private void createRandomStartingPoint(Random random, byte[] startingPoint) {
        random.nextBytes(startingPoint);

        for (int a = 0; a < startingPoint.length; a++) {
            startingPoint[a] = (byte) (Math.abs(startingPoint[a]%charset.length()));
        }
    }
}
