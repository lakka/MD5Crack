package md5crack;

import HashTable.HashTable;
import helpers.CommonHelper;
import helpers.Reductor;

/**
 *
 * @author Torso
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        UI ui = new UI();
//        ui.start();
//        TableCreator tc = new TableCreator("kalt", 4,6, 12800, 1000);
//        tc.createTable();
        
//        MD5Crack cracker = new MD5Crack("kalt", 5, 1280, 1000, "1369072346056.tbl");
//        cracker.crackHash("e920fe90c96a5588df35ec7604b93641");
        
        CommonHelper helper = new CommonHelper();
        
        // WARNING! Running this will take a long time.
        long time = System.currentTimeMillis();
        HashTable ht = new HashTable(Integer.MAX_VALUE/4,3,4);
        
        // reductor testing
        Reductor rf = new Reductor("abcd0123",3,4);
        byte[] hash = "qwertyuiopasdfghjklzxc0123456789".getBytes();
        for (int i = 0; i < Integer.MAX_VALUE/4; i++) {
            // change one byte in hash
            hash[i%hash.length] ^= hash[(i+1)%hash.length];
//            System.out.println(helper.bytesToString(rf.reduce(hash,i),"abcd0123"));
//            ht.insert(rf.reduce(hash,i));
        }
        System.out.println("Took: "+(System.currentTimeMillis()-time)/1000+ " seconds.");
//        System.out.println(ht);
    }
}
