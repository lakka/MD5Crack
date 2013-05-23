package HashTable;

/**
 * Hash function for hash table.
 * 
 * @author Lauri Kangassalo / lauri.kangassalo@helsinki.fi
 */
public class HashFunction {

    private int firstBytes;
    private int tableSize;

    /**
     * Initializes a hashing function. Password lengths are used in calculating the index.
     * 
     * @param tableSize size of the hash table
     * @param minPwLength minimum password length
     * @param maxPwLength maximum password length
     */
    public HashFunction(int tableSize, int minPwLength, int maxPwLength) {
        firstBytes = minPwLength + (maxPwLength - minPwLength)/2;
        this.tableSize = tableSize;
    }
    
    /**
     * Calculates an index number out of byte array.
     * 
     * @param bytes
     * @return index number to use with hash table
     */
    public int hash(byte[] bytes) {
        int hash = 0;
        for (int i = 1; i < firstBytes; i++) {
            hash += Math.pow(bytes[i-1],i);
        }
        return hash%tableSize;
    }
}
