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
}
