
package helpers;

/**
 * Function for reducing hash values into plaintext.
 * 
 * @author Lauri Kangassalo / lauri.kangassalo@helsinki.fi
 */
public class Reductor {
    private String charset;
    private int pwLength;
    
    /**
     * Initialize reduction function.
     * 
     * @param charset charset for plaintext
     * @param pwLength length of plaintext
     */
    public Reductor(String charset, int pwLength) {
        this.charset = charset;
        this.pwLength = pwLength;
    }
    /**
     * Reduces hashes to charset indexes according to selected charset and password length.
     * 
     * @param hash hash to translate
     * @param functionNr index of the reduction function
     * @return byte array containing charset index numbers
     */
    public byte[] reduce(byte[] hash, int functionNr) {

        byte[] result = new byte[pwLength];
        for (int i = 0; i < pwLength; i++) {
            // unique results for each reduction function
            hash[i] ^= functionNr;
            
            result[i] = (byte) (Math.abs(hash[i]%charset.length()));
            
            // cancel xor
            hash[i] ^= functionNr;
        }
        
        return result;
        
    }
}
