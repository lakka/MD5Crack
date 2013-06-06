package helpers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Common helper methods.
 *
 * @author Lauri Kangassalo / lauri.kangassalo@helsinki.fi
 */
public class CommonHelper {

    /**
     * Converts an array of bytes into a string according to charset.
     *
     * @param bytes bytes to be converted
     * @param charset charset, to which bytes will be converted
     * @return plaintext of the byte array
     */
    public String bytesToString(byte[] bytes, String charset) {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < bytes.length; j++) {
            sb.append(charset.charAt(bytes[j] % charset.length()));
        }
        return sb.toString();
    }

    /**
     * Check if two byte-arrays have equal content.
     *
     * @param bytes1 first byte array
     * @param bytes2 second byte array
     * @return true if the arrays are equal
     */
    public boolean equalBytes(byte[] bytes1, byte[] bytes2) {
        if (bytes1.length != bytes2.length) {
            return false;
        }
        for (int i = 0; i < bytes1.length; i++) {
            if (bytes1[i] != bytes2[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Calculates a password length within the limits of min and max.
     *
     * @param chainNr current chain number
     * @param minPwLength minimum password length
     * @param maxPwLength maximum password length
     * @return a number between max and min, depends on chainNr
     */
    public byte calculatePwLength(int chainNr, int minPwLength, int maxPwLength) {
        return (byte) (chainNr % (maxPwLength - minPwLength + 1) + minPwLength);
    }

    /**
     * Get Java's built-in MD5-digester.
     *
     * @return
     */
    public MessageDigest getMD5digester() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("MD5-algorithm not found... this shouldn't be.");
            return null;
        }
        return md;
    }

    /**
     * Calculates the keyspace size.
     *
     * @param charset
     * @param minPwLength
     * @param maxPwLength
     * @return
     */
    public long calculateKeyspace(String charset, int minPwLength, int maxPwLength) {
        long keyspace = 0;
        for (int i = minPwLength; i <= maxPwLength; i++) {
            keyspace += (long) Math.pow(charset.length(), i);
        }
        return keyspace;
    }

    /**
     * Calculates keyspace ratios in relation to chains per table. This is useful
     * when creating a rainbow table. If ratios aren't used, the table
     * will have more chains for shorter passwords than is necessary.
     * 
     * @param charset
     * @param minPwLength
     * @param maxPwLength
     * @param chainsPerTable
     * @return an array containing the ratios. Each index represents a password length.
     */
    public int[] calculateKeyspaceRatios(String charset, int minPwLength, int maxPwLength, int chainsPerTable) {
        long total = calculateKeyspace(charset, minPwLength, maxPwLength);
        int[] ratios = new int[maxPwLength - minPwLength + 1];
        for (int i = 0; i <= maxPwLength - minPwLength; i++) {
            ratios[i] = (int) (chainsPerTable / (total / calculateKeyspace(charset, i, i)));
        }
        return ratios;
    }

    /**
     * Calculates a prime number with a minimum value of size/2.
     *
     * @param size
     * @return a prime number
     */
    public int calculatePrime(int size) {
        int i = size / 2;
        while (true) {
            boolean isPrime = true;
            for (int j = 2; j < i; j++) {
                if (i % j == 0) {
                    isPrime = false;
                    break;
                }
            }
            if (isPrime) {
                return i;
            }
            i++;
        }
    }

    /**
     * Converts a hexadecimal string to a byte array.
     *
     * @param s string to convert
     * @return a corresponding byte array
     */
    public byte[] hexStringToByteArray(String s) {
        int length = s.length();
        byte[] data = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}
