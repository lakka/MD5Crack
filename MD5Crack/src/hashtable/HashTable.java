package hashtable;

import helpers.CommonHelper;
import java.util.Iterator;

/**
 * A stripped HashMap/HashSet implementation.
 *
 * @author Lauri Kangassalo / lauri.kangassalo@helsinki.fi
 */
public class HashTable implements Iterable<Bytes> {

    private Bytes[] bytesTable;
    private HashFunction hf;
    private int size;

    /**
     * Initializes helper classes and calculates the size for the table.
     *
     * @param size maximum size of table
     * @param minPwLength minimum password length
     * @param maxPwLength maximum password length
     */
    public HashTable(int size, int minPwLength, int maxPwLength) {
        int prime = new CommonHelper().calculatePrime(size);
        this.bytesTable = new Bytes[prime];
        this.hf = new HashFunction(prime, minPwLength, maxPwLength);

    }

    /**
     * Insert a byte array to the table. If the table index is taken, add it to
     * a linked list.
     *
     * @param bytes Bytes to add
     */
    public void insert(Bytes key) {
        int index = hf.hash(key);

        if (bytesTable[index] == null) {
            bytesTable[index] = key;
        } else {
            Bytes currentBytes = bytesTable[index];
            if (currentBytes.equals(key)) {
                return;
            }
            while (currentBytes.next != null) {
                if (currentBytes.equals(key)) {
                    return;
                }
                currentBytes = currentBytes.next;
            }
            currentBytes.next = key;
        }
        size++;
    }

    /**
     * Insert operation for keys with values.
     *
     * @param key key to insert
     * @param value value for the key
     */
    public void insert(Bytes key, Bytes value) {
        key.value = value;
        insert(key);
    }

    /**
     * Checks if a byte array is a key in the table.
     *
     * @param key Bytes to check
     * @return true, if byte array exists
     */
    public boolean contains(Bytes key) {
        Bytes found = findKey(key);
        if (found == null) {
            return false;
        }
        return true;
    }

    /**
     * Search a byte array from the table.
     *
     * @param key key to search
     * @return matching value for byte array
     */
    public Bytes search(Bytes key) {
        Bytes found = findKey(key);
        if (found == null) {
            return null;
        }
        return found.value;
    }

    /**
     * Returns the number of keys in the table.
     *
     * @return
     */
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
        return new HashIterator(bytesTable, size);
    }


    private Bytes findKey(Bytes key) {
        int index = hf.hash(key);
        Bytes otherBytes = bytesTable[index];
        while (otherBytes != null && !key.equals(otherBytes)) {
            if (otherBytes.next != null) {
                otherBytes = otherBytes.next;
            } else {
                break;
            }
        }
        return otherBytes;
    }
}
