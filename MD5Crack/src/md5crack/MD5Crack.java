package md5crack;

import hashtable.Bytes;
import hashtable.HashTable;
import helpers.CommonHelper;
import helpers.FileHelper;
import helpers.Reductor;
import helpers.UIHelper;
import java.io.BufferedInputStream;
import java.security.MessageDigest;

/**
 * A class for cracking an MD5 hash. Uses a previously generated rainbow table.
 * 
 * @author Lauri Kangassalo / lauri.kangassalo@helsinki.fi
 */
public class MD5Crack {

    private String charset;
    private int minPwLength;
    private int maxPwLength;
    private int chainLength;
    private UIHelper uihelper;
    private FileHelper file;
    private CommonHelper helper;
    private Reductor reductor;
    private MessageDigest md;
    private HashTable table;

    /**
     * Initializes helper classes and reads the table to memory.
     * 
     * @param charset character set for the passwords
     * @param minPwLength minimum password length
     * @param maxPwLength maximum password length
     * @param chainLength length of chains in the rainbow table
     * @param filename filename of the rainbow table
     */
    public MD5Crack(String charset, int minPwLength, int maxPwLength, int chainsPerTable, int chainLength, String filename) {
        this.charset = charset;
        this.minPwLength = minPwLength;
        this.maxPwLength = maxPwLength;
        this.chainLength = chainLength;

        uihelper = new UIHelper();
        file = new FileHelper();
        helper = new CommonHelper();
        reductor = new Reductor(charset, minPwLength, maxPwLength);
        md = helper.getMD5digester();
        
        // read table to memory
        BufferedInputStream dis = file.openFile(filename);
        table = file.readTable(dis, chainsPerTable, minPwLength, maxPwLength);
        
    }

    /**
     * Cracks a hash and prints the results.
     * 
     * @param hashString a string representation of the hash to be cracked
     * @return true, if the hash was cracked, false otherwise
     */
    public boolean crackHash(String hashString) {   

        final byte[] hash = helper.hexStringToByteArray(hashString);
        
        HashTable foundEndpoints = searchEndpoints(hash, table);
        uihelper.printEndpointCount(foundEndpoints.size());
        
        return eliminateFalseAlarms(foundEndpoints, table, hash);
    }
    
    /**
     * Loops through known password lengths, and tries to reduce the hash with different reduction functions
     * in order to find matching endpoints from the rainbow table.
     * 
     * @param hash
     * @param table
     * @return 
     */
    private HashTable searchEndpoints(byte[] hash, HashTable table) {
        HashTable foundEndpoints = new HashTable(table.size()/11,minPwLength,maxPwLength);

        byte[] reducedEndpoint = null;
        // try every known password length
        for (int pwLength = minPwLength; pwLength <= maxPwLength; pwLength++) {
            // calculate endpoints with all reduction functions
            for (int i = chainLength - 1; i >= 0; i--) {
                byte[] possibleEndpoint = hash;
                for (int j = i; j < chainLength; j++) {
                    reducedEndpoint = reductor.reduce(possibleEndpoint, j, (byte)pwLength);
                    possibleEndpoint = md.digest(reducedEndpoint);
                }

                Bytes bytes = new Bytes(reducedEndpoint);

                // add the endpoint to a hashset for further analysis
                if (table.contains(bytes)) {
                    foundEndpoints.insert(bytes);
                }

            }
        }
        return foundEndpoints;
    }

    /**
     * Eliminates false alarms, false alarms being endpoints that don't point to chains that
     * produce the MD5-hash. This cracks the hash.
     * 
     * @param foundEndpoints
     * @param table
     * @param hash
     * @return true, if the hash was cracked, false otherwise
     */
    private boolean eliminateFalseAlarms(HashTable foundEndpoints, HashTable table, byte[] hash) {
        // loop through matching endpoints to eliminate false alarms
        for (Bytes endpoint : foundEndpoints) {
            
            Bytes currentPlaintext = table.search(endpoint);
            byte pwLength = (byte) endpoint.getBytes().length;
            byte[] currentHash;
            for (int i = 0; i < chainLength; i++) {
                currentHash = md.digest(helper.bytesToString(currentPlaintext.getBytes(), charset).getBytes());

                if (helper.equalBytes(hash, currentHash)) {
                    // found a matching plaintext for hash
                    uihelper.hashCracked(helper.bytesToString(currentPlaintext.getBytes(), charset));
                    return true;
                }
                currentPlaintext = new Bytes(reductor.reduce(currentHash, i, pwLength));
            }
            
        }
        uihelper.crackFailed();
        return false;
    }
}
