package md5crack;

import helpers.CommonHelper;
import helpers.Reductor;
import java.util.Random;

/**
 *
 * @author Torso
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        UI ui = new UI();
        ui.start();
//        TableCreator tc = new TableCreator("kalt", 4,6, 12800, 1000);
//        tc.createTable();
        
//        MD5Crack cracker = new MD5Crack("kalt", 5, 1280, 1000, "1369072346056.tbl");
//        cracker.crackHash("e920fe90c96a5588df35ec7604b93641");
        
//        CommonHelper helper = new CommonHelper();
////        
//        // reductor testing
//        Reductor rf = new Reductor("abcd0123",3,4);
//        byte[] hash = "qwertyuiopasdfghjklzxc0123456789".getBytes();
//        for (int i = 0; i < 31; i++) {
//            // change one byte in hash
//            hash[i] ^= hash[i+1];
//            System.out.println(helper.bytesToString(rf.reduce(hash,i),"abcd0123"));
//        }
    }
}
