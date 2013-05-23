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
     * @param bytes1 first byte array
     * @param bytes2 second byte array
     * @return true if the arrays are equal
     */
    public boolean equalBytes(byte[] bytes1, byte[] bytes2) {
        if(bytes1.length != bytes2.length) return false;
        for (int i = 0; i < bytes1.length; i++) {
            if(bytes1[i] != bytes2[i]) return false;
        }
        return true;
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
     * @param charset
     * @param minPwLength
     * @param maxPwLength
     * @return 
     */
    public long calculateKeyspace(String charset, int minPwLength,int maxPwLength) {
        long keyspace = 0;
        for (int i = minPwLength; i <= maxPwLength; i++) {
            keyspace += (long) Math.pow(charset.length(), i);
        }
        return keyspace;
    }
}
