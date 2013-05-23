

package HashTable;

/**
 * A class representing a byte array in a linked list.
 * 
 * @author Lauri Kangassalo / lauri.kangassalo@helsinki.fi
 */
public class Bytes {
    private byte[] bytes;
    Bytes next;
    public Bytes(byte[] bytes, Bytes next) {
        this.bytes = bytes;
        this.next = next;
    }
    public Bytes(byte[] bytes) {
        this.bytes = bytes;
    }
    
    public byte[] getBytes() {
        return bytes;
    }

}
