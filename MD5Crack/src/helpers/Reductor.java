package helpers;

import java.util.Random;

/**
 * Function for reducing hash values into plaintext.
 *
 * @author Lauri Kangassalo / lauri.kangassalo@helsinki.fi
 */
public class Reductor {

    private String charset;
    private int maxPwLength;
    private int minPwLength;
    private Random random;

    /**
     * Initialize reduction function.
     *
     * @param charset charset for plaintext
     * @param minPwLength minimum plaintext length
     * @param maxPwLength maximum plaintext length
     */
    public Reductor(String charset, int minPwLength, int maxPwLength) {
        this.charset = charset;
        this.maxPwLength = maxPwLength;
        this.minPwLength = minPwLength;
    }

    /**
     * Reduces hashes to charset indexes according to selected charset and
     * password length.
     *
     * @param hash hash to translate
     * @param functionNr index of the reduction function
     * @return byte array containing charset index numbers
     */
    public byte[] reduce(byte[] hash, int functionNr) {
        // plaintext length from a byte from the hash
        int pwLength = (functionNr) % (maxPwLength - minPwLength + 1) + minPwLength;
        
        return reduce(hash,functionNr,pwLength);
        

    }

    public byte[] reduce(byte[] hash, int functionNr, int pwLength) {
        byte[] result = new byte[pwLength];
        for (int i = 0; i < pwLength; i++) {
            // unique results for each reduction function
            hash[i] ^= functionNr;

            result[i] = (byte)( Math.abs(hash[i]) % charset.length());

            // cancel xor
            hash[i] ^= functionNr;
        }

        return result;
    }
}
