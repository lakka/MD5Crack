package md5crack;

import hashtable.Bytes;
import hashtable.HashTable;
import helpers.CommonHelper;
import helpers.FileHelper;
import helpers.Reductor;
import helpers.UIHelper;
import java.io.DataInputStream;
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
    private int chainsPerTable;
    private int chainLength;
    private String filename;
    private UIHelper uihelper;

    /**
     * Initializes a class for cracking MD5 hashes.
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
        this.chainsPerTable = chainsPerTable;
        this.chainLength = chainLength;
        this.filename = filename;

        uihelper = new UIHelper();
    }

    /**
     * Cracks a hash and prints the results.
     * 
     * @param hashString a string representation of the hash to be cracked
     * @return true, if the hash was cracked, false otherwise
     */
    public boolean crackHash(String hashString) {
        FileHelper file = new FileHelper();
        CommonHelper helper = new CommonHelper();
        Reductor reductor = new Reductor(charset, minPwLength, maxPwLength);
        MessageDigest md = helper.getMD5digester();

        

        uihelper.startFileRead();
        DataInputStream dis = file.openFile(filename);
        HashTable table = file.readTable(dis, chainsPerTable, minPwLength, maxPwLength);
        
        System.out.println("Table size: " + table.size());

        HashTable foundEndpoints = new HashTable(table.size()/11,minPwLength,maxPwLength);

        byte[] hash = helper.hexStringToByteArray(hashString);
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
        uihelper.printEndpointCount(foundEndpoints.size());

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
        return false;
    }
}