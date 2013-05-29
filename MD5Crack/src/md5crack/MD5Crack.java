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
    private int chainLength;
    private String filename;
    private UIHelper uihelper;

    public MD5Crack(String charset, int minPwLength, int maxPwLength, int chainLength, String filename) {
        this.charset = charset;
        this.minPwLength = minPwLength;
        this.maxPwLength = maxPwLength;
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

        //        Print rainbow table contents
//        for (Bytes bs : table.keySet()) {
//            System.out.println(helper.bytesToString(bs.getBytes(), charset) + "   " + helper.bytesToString(table.get(bs).getBytes(),charset));
//        }


        byte[] hash = helper.hexStringToByteArray(hashString);
        byte[] reducedEndpoint = null;
        // reduce the hash until a known endpoint is found, try every pw length
        for (int pwLength = minPwLength; pwLength <= maxPwLength; pwLength++) {
            for (int i = chainLength - 1; i >= 0; i--) {
                byte[] possibleEndpoint = hash;
                for (int j = i; j < chainLength; j++) {
                    reducedEndpoint = reductor.reduce(possibleEndpoint, j, (byte)pwLength);
                    possibleEndpoint = md.digest(reducedEndpoint);
                }

                Bytes bytes = new Bytes(reducedEndpoint);

                // add the endpoint to a hashset for further analysis
                if (table.containsKey(bytes)) {
                    foundEndpoints.add(bytes);
                }

            }
        }
        uihelper.printEndpointCount(foundEndpoints.size());



        // loop through matching endpoints to eliminate false alarms
        for (Bytes endpoint : foundEndpoints) {
            Bytes currentPlaintext = table.get(endpoint);
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
