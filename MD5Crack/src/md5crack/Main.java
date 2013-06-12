package md5crack;

import hashtable.Bytes;
import helpers.CommonHelper;
import helpers.Reductor;
import java.util.Iterator;

/**
 *
 * This class is currently used only for testing/debugging
 * 
 * @author Lauri Kangassalo / lauri.kangassalo@helsinki.fi
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {


        UI ui = new UI();
        ui.start();
//        int minPw = 4;
//        int maxPw = 4;
//        int chains = 420000;
//        int chainl = 2000;
//        String kka = "ec7b078a5b8df149983c1210d4a1b1db";
//        String akk = "b610295fea526d7c32a5ab82d74a9063";
//        String kisa = "1c0d894f6f6ab511099a568f6e876c2f";
//        String tulr = "03b03e55a55bee6e4771f4974050b9cb";
//        String ash50 = "70d5b257eb7227e983588d08fa857b96";
//        
//        String pisa = "9428520f7f19c29574b912796d43fb11";
//        String pis4 = "91e2036552b15f33dc2a2ca278a54cf9";
//        String pis5 = "d99035914880676e004b25609ac56ce0";
//        String viisasd = "c37c8953e433f1a185235e221216239c";
//        String vi5a = "0c5a1637663d32204fbcc7735bea367e";
//        boolean createTable = true;
////        
//////
//        CommonHelper helper = new CommonHelper();
//        System.out.println("Keyspace: " + helper.calculateKeyspace("qwertyuiopasdfghjklzxcvbnm0123456789", minPw, maxPw));
//        
//        if(createTable) {
//        TableCreator tc = new TableCreator("qwertyuiopasdfghjklzxcvbnm0123456789", minPw,maxPw, chains, chainl);
//        tc.createTable();
//        }
//        
//
////        
//        MD5Crack cracker = new MD5Crack("qwertyuiopasdfghjklzxcvbnm0123456789", minPw,maxPw, chains,chainl, "36-4-4-"+chains+"-"+chainl+".tbl");
//        cracker.crackHash(pis4);
        
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

