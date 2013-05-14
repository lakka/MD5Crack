package md5crack;

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
public class ReductorTest {
    private Reductor rf;
    private String charset;
    private int pwLength;
    private byte[] testHash;
    
    public ReductorTest() {
    }
    
    
    @Before
    public void setUp() {
        charset = "abc";
        pwLength = 3;
        testHash = "0123456789qwertyuiopasdfghjklzxc".getBytes();
        rf = new Reductor(charset,pwLength);
    }

    @Test
    public void reduceReturnsCorrectLength() {
        assertEquals(pwLength,rf.reduce(testHash, 1).length());
    }
    
    @Test
    public void reduceReturnsCharsOnlyInCharset() {
        String reduced = rf.reduce(testHash,1);
        for (int i = 0; i < reduced.length(); i++) {
            assertTrue(charset.contains(""+reduced.charAt(i)));
        }
    }
    
    @Test
    public void reduceReturnsDifferentStringForDifferentHash() {
        String reduced = rf.reduce(testHash,1);
        String reduced2 = rf.reduce("1123456789qwertyuiopasdfghjklzxc".getBytes(), pwLength);
        assertFalse(reduced.equals(reduced2));
    }
    
    @Test
    public void reduceReturnsDifferentStringForDifferentHash2() {
        String reduced = rf.reduce(testHash,1);
        String reduced2 = rf.reduce("1123456789qwertyuigpasdfghjklzxd".getBytes(), pwLength);
        assertFalse(reduced.equals(reduced2));
    }
    
    @Test
    public void reduceReturnsDifferentStringForDifferentFunctionNr() {
        String reduced = rf.reduce(testHash,1);
        String reduced2 = rf.reduce(testHash,2);
        assertFalse(reduced.equals(reduced2));
    }
}