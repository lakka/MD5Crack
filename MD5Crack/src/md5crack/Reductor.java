
package md5crack;

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
     * Reduces hashes to plaintext according to selected charset and password length.
     * 
     * @param hash hash to translate
     * @param functionNr index of the reduction function
     * @return plaintext generated from hash
     */
    public String reduce(byte[] hash, int functionNr) {
        StringBuilder sb = new StringBuilder(pwLength);
        for (byte b : hash) {
            // unique results for each reduction function
            b ^= functionNr;
            
            sb.append(charset.charAt(Math.abs(b%charset.length())));
        }
        
        return sb.substring(0,pwLength);
        
    }
}
