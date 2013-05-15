

package md5crack;

/**
 *
 * @author Lauri Kangassalo / lauri.kangassalo@helsinki.fi
 */
public class Helper {
    
    public String bytesToString(byte[] bytes, String charset) {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < bytes.length; j++) {
                sb.append(charset.charAt(bytes[j]%charset.length()));
            }
        return sb.toString();
    }
    

}
