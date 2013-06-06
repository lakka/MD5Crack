

package hashtable;

import java.util.Arrays;

/**
 * A class representing a byte array in a linked list.
 * 
 * @author Lauri Kangassalo / lauri.kangassalo@helsinki.fi
 */
public class Bytes {
    private byte[] bytes;
    // declared public for testing
    public Bytes next;
    public Bytes value;
    
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

    @Override
    public int hashCode() {
        int hash = 0;
        for (byte b : bytes) {
            hash += b;
        }
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Bytes other = (Bytes) obj;
        byte[] bytes1 = this.bytes;
        byte[] bytes2 = other.bytes;
        if (bytes.length != bytes2.length) {
            return false;
        }
        for (int i = 0; i < bytes1.length; i++) {
            if (bytes1[i] != bytes2[i]) {
                return false;
            }
        }
        return true;
    }
    
    public String toString() {
        String ret = "";
        for (int i = 0; i < bytes.length; i++) {
            ret += bytes[i]+", ";
        }
        return ret;
    }
    
    

}
