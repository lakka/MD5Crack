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
    public static void main(String[] args) throws Exception {
//        UI ui = new UI();
//        ui.start();
        
        
//
        TableCreator tc = new TableCreator("qwertyuiopasdfghjklzxcvbnm", 4,4, 45000, 350);
        tc.createTable();
        
        CommonHelper helper = new CommonHelper();
        System.out.println("Keyspace: " + helper.calculateKeyspace("qwertyuiopasdfghjklzxcvbnm", 4, 4));
        
        MD5Crack cracker = new MD5Crack("qwertyuiopasdfghjklzxcvbnm", 4,4, 2000, 350, "yksi.tbl");
        cracker.crackHash("bf0937fcb05b460e447a0bea7537218a");
        
//        CommonHelper helper = new CommonHelper();
//        
//        // WARNING! Running this will take a long time.
//        long time = System.currentTimeMillis();
////        HashTable ht = new HashTable(Integer.MAX_VALUE/4,3,4);
//        
//        // reductor testing
//        Reductor rf = new Reductor("abcd0123",3,5);
//        byte[] hash = "qwertyuiopasdfghjklzxc0123456789".getBytes();
//        for (int i = 0; i < 31; i++) {
//            // change one byte in hash
//            hash[i%hash.length] ^= hash[(i+1)%hash.length];
//            int value = 0;
//            for (int j = 0; j < 3; j++) {
//                value = (value << 8) + (hash[i%hash.length] & 0xff);
//            }
//            System.out.println(value);
//            System.out.println(helper.bytesToString(rf.reduce(hash,i),"abcd0123"));
//                        ht.insert(rf.reduce(hash,i));
        }
//        System.out.println("Took: "+(System.currentTimeMillis()-time)/1000+ " seconds.");
////        System.out.println(ht);
    }

