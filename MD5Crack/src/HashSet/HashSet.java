package HashSet;

import helpers.CommonHelper;
import java.util.Iterator;

/**
 * A HashSet implementation.
 *
 * @author Lauri Kangassalo / lauri.kangassalo@helsinki.fi
 */
public class HashSet implements Iterable<Bytes> {

    private Bytes[] bytesTable;
    private HashFunction hf;
    private CommonHelper helper;
    private int size;

    /**
     * Initializes the hash table.
     *
     * @param size maximum size of table
     * @param minPwLength minimum password length
     * @param maxPwLength maximum password length
     */
    public HashSet(int size, int minPwLength, int maxPwLength) {
        helper = new CommonHelper();
        int prime = helper.calculatePrime(size);
        this.bytesTable = new Bytes[prime];
        this.hf = new HashFunction(prime, minPwLength, maxPwLength);
        
        

    }

    /**
     * Insert a byte array to the table. If the table index is taken, add it to a linked list.
     *
     * @param bytes Bytes to add
     */
    public void insert(Bytes newBytes) {
        int index = hf.hash(newBytes);
        if (bytesTable[index] == null) {
            bytesTable[index] = newBytes;
        } else {
            Bytes currentBytes = bytesTable[index];
            if(currentBytes.equals(newBytes)) return;
            while (currentBytes.next != null) {
                if(currentBytes.equals(newBytes)) return;
                currentBytes = currentBytes.next;
            }
            currentBytes.next = newBytes;
        }
        size++;
    }

    /**
     * Search a byte array from the table. Fugly.
     *
     * @param bytes
     * @return matching value for byte array
     */
//    public byte[] search(byte[] bytes) {
//        int index = hf.hash(bytes);
//        Bytes otherBytes = bytesTable[index];
//        while (otherBytes != null && !helper.equalBytes(bytes, otherBytes.getBytes())) {
//            if (otherBytes.next != null) {
//                otherBytes = otherBytes.next;
//            } else {
//                break;
//            }
//        }
//        if (otherBytes == null) {
//            return null;
//        }
//        return otherBytes.getBytes();
//    }
    /**
     * Checks if a byte array is a key in the table.
     *
     * @param bytes Bytes to check
     * @return true, if byte array exists
     */
    public boolean contains(Bytes bytes) {
        int index = hf.hash(bytes);
        Bytes otherBytes = bytesTable[index];
        while (otherBytes != null && !bytes.equals(otherBytes)) {
            if (otherBytes.next != null) {
                otherBytes = otherBytes.next;
            } else {
                break;
            }
        }
        if (otherBytes == null) {
            return false;
        }
        return true;
    }

    public int size() {
        return size;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ ");
        for (int i = 0; i < bytesTable.length; i++) {
            if (i + 1 == bytesTable.length) {
                sb.append(bytesTable[i]);
            } else {
                sb.append(bytesTable[i]);
                sb.append(", ");
            }
        }
        sb.append(" ]");
        return sb.toString();
    }

    public Bytes[] getBytes() {
        return bytesTable;
    }

    @Override
    public Iterator<Bytes> iterator() {
        return new HashSetIterator(bytesTable,size);
    }


}
