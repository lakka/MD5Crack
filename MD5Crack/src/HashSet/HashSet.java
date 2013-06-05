package HashSet;

import helpers.CommonHelper;

/**
 * A hash table implementation.
 *
 * @author Lauri Kangassalo / lauri.kangassalo@helsinki.fi
 */
public class HashSet {

    private Bytes[] bytesTable;
    private HashFunction hf;
    private CommonHelper helper;

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
     * Insert a byte array to the table.
     *
     * @param bytes
     */
    public void insert(byte[] key) {
        Bytes newBytes = new Bytes(key);
        int index = hf.hash(key);
        if (bytesTable[index] == null) {
            bytesTable[index] = newBytes;
        } else {
            Bytes currentBytes = bytesTable[index];
            while(currentBytes.next != null) {
                currentBytes = currentBytes.next;
            }
            currentBytes.next = newBytes;
        }
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
     * @param bytes
     * @return true, if byte array exists
     */
    public boolean contains(byte[] bytes) {
        int index = hf.hash(bytes);
        Bytes otherBytes = bytesTable[index];
        while (otherBytes != null && !helper.equalBytes(bytes, otherBytes.getBytes())) {
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
}
