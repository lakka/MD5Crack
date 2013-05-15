/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package md5crack;

/**
 * A class for generating rainbow tables and saving them into a file.
 * 
 * @author Lauri Kangassalo / lauri.kangassalo@helsinki.fi
 */
public class CreateTables {
    private String charset;
    private int pwLength;
    private int chainsPerTable;
    private int chainLength;
    private long keyspace;
    
    /**
     * Sets variables, counts keyspace.
     * 
     * @param charset character set of the passwords
     * @param pwLength password length
     * @param chainsPerTable number of rows per table
     * @param chainLength length of a single chain (row)
     */
    public CreateTables(String charset, int pwLength, int chainsPerTable, int chainLength) {
        this.charset = charset;
        this.pwLength = pwLength;
        this.chainsPerTable = chainsPerTable;
        this.chainLength = chainLength; 
       
        this.keyspace = (long) Math.pow(charset.length(), pwLength);
    }
    
}
