package md5crack;

import HashTable.Bytes;
import helpers.CommonHelper;
import helpers.FileHelper;
import helpers.Reductor;
import helpers.UIHelper;
import java.io.DataInputStream;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.HashSet;

/**
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

    public MD5Crack(String charset, int minPwLength, int maxPwLength, int chainsPerTable, int chainLength, String filename) {
        this.charset = charset;
        this.minPwLength = minPwLength;
        this.maxPwLength = maxPwLength;
        this.chainsPerTable = chainsPerTable;
        this.chainLength = chainLength;
        this.filename = filename;

        uihelper = new UIHelper();
    }

    public boolean crackHash(String hashString) {
        FileHelper file = new FileHelper();
        CommonHelper helper = new CommonHelper();
        Reductor reductor = new Reductor(charset, minPwLength, maxPwLength);
        MessageDigest md = helper.getMD5digester();

        HashSet<Bytes> foundEndpoints = new HashSet<>();

        uihelper.startFileRead();
        DataInputStream dis = file.openFile(filename);
        HashMap<Bytes, Bytes> table = file.readTable(dis, minPwLength, maxPwLength);
        uihelper.done();
        
        System.out.println("Table size: " + table.size());


//        for (Bytes bs : table.keySet()) {
//            System.out.println(helper.bytesToString(bs.getBytes(), charset) + "   " + helper.bytesToString(table.get(bs).getBytes(),charset));
//        }



        byte[] hash = hexStringToByteArray(hashString);
        byte[] reducedEndpoint = null;
        // reduce the hash until a known endpoint is found
        for (int i = chainLength - 1; i >= 0; i--) {
            byte[] possibleEndpoint = hash;
            for (int j = i; j < chainLength; j++) {
                reducedEndpoint = reductor.reduce(possibleEndpoint, j);
                possibleEndpoint = md.digest(reducedEndpoint);
            }

            Bytes bytes = new Bytes(reducedEndpoint);

            // add the endpoint to a hashset for further analysis
            if (table.containsKey(bytes)) {
                foundEndpoints.add(bytes);
            }

        }
        uihelper.printEndpointCount(foundEndpoints.size());

        // Print original hash
//        for (int j = 0; j < hash.length; j++) {
//            System.out.print(hash[j] + " ");
//        }
//        System.out.println(hash.length);

        // loop through matching endpoints to eliminate false alarms
        for (Bytes endpoint : foundEndpoints) {
            Bytes currentPlaintext = table.get(endpoint);
//            System.out.println(helper.bytesToString(currentPlaintext.getBytes(), charset));
            byte[] currentHash;
            for (int i = 0; i < chainLength; i++) {
                currentHash = md.digest(helper.bytesToString(currentPlaintext.getBytes(), charset).getBytes());

                // Print hash
//                for (int j = 0; j < currentHash.length; j++) {
//                    System.out.print(Integer.toString((currentHash[j] & 0xff) + 0x100, 16).substring(1));
//                }
//                System.out.println(" "+currentHash.length);

                if (helper.equalBytes(hash, currentHash)) {
                    // found a matching plaintext for hash
                    uihelper.hashCracked(helper.bytesToString(currentPlaintext.getBytes(), charset));
                    return true;
                }
                currentPlaintext = new Bytes(reductor.reduce(currentHash, i));
            }
        }
        return false;
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}
