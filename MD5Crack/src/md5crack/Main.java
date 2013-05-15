package md5crack;

import helpers.Reductor;
import helpers.CommonHelper;

/**
 *
 * @author Torso
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TableCreator tc = new TableCreator("qwertyuiopasdfghjklzxcvbnm1234567890", 5, 128000, 1000);
        tc.createTable();
        
//        CommonHelper helper = new CommonHelper();
//        
//        // reductor testing
//        Reductor rf = new Reductor("abcd0123",5);
//        byte[] hash = "qwertyuiopasdfghjklzxc0123456789".getBytes();
//        for (int i = 0; i < 31; i++) {
//            // change one byte in hash
//            hash[i] ^= hash[i+1];
//            System.out.println(helper.bytesToString(rf.reduce(hash,1),"abcd0123"));
//        }
    }
}
