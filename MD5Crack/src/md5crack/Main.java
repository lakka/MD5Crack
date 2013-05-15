package md5crack;

/**
 *
 * @author Torso
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TableCreator tc = new TableCreator("aklt", 5, 1024, 10);
        tc.createTable();
        
        
//        // reductor testing
//        Reductor rf = new Reductor("abcd0123",5);
//        byte[] hash = "qwertyuiopasdfghjklzxc0123456789".getBytes();
//        for (int i = 0; i < 31; i++) {
//            // change one byte in hash
//            hash[i] ^= hash[i+1];
//            System.out.println(rf.reduce(hash,1));
//        }
    }
}
