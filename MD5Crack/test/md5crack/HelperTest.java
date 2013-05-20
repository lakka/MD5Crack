

package md5crack;

import helpers.CommonHelper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Lauri Kangassalo / lauri.kangassalo@helsinki.fi
 */
public class HelperTest {
    CommonHelper helper;

    public HelperTest() {
    }

    @Before
    public void setUp() {
        helper = new CommonHelper();
    }

    
    @Test
    public void bytesToStringReturnsCorrectLength() {
        String charset = "abc";
        byte[] charBytes = charset.getBytes();
        String byteString = helper.bytesToString(charBytes, charset);
        assertEquals(charset.length(), byteString.length());
        
    }
    
    @Test
    public void bytesToStringRespectsCharset() {
        String charset = "abc";
        byte[] charBytes = charset.getBytes();
        String byteString = helper.bytesToString(charBytes, charset);
        for (int i = 0; i < charBytes.length; i++) {
            assertTrue(charset.contains(""+byteString.charAt(i)));
        }
        
    }
    
    @Test
    public void equalBytesReturnFalseOnArraysOfDifferentLength() {
        byte[] bytes1 = {1,2,3};
        byte[] bytes2 = {1,2};
        assertFalse(helper.equalBytes(bytes1, bytes2));
    }
    
    @Test
    public void equalBytesReturnsTrue() {
        byte[] bytes1 = {1,2,3};
        byte[] bytes2 = {1,2,3};
        assertTrue(helper.equalBytes(bytes1, bytes2));
    }
    
    @Test
    public void equalBytesReturnsFalse() {
        byte[] bytes1 = {1,2,3};
        byte[] bytes2 = {2,2,3};
        assertFalse(helper.equalBytes(bytes1, bytes2));
    }
    


}