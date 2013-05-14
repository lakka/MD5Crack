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
        Reductor rf = new Reductor("abcd0123",5);
        byte[] hash = "qwertyuiopasdfghjklzxc0123456789".getBytes();
        for (int i = 0; i < 32; i++) {
            hash[i] = ++hash[i];
            System.out.println(rf.reduce(hash,1));
        }
    }
}
