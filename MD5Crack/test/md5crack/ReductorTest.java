package md5crack;

import helpers.Reductor;
import java.util.Arrays;
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
    private byte pwLength;
    private byte[] testHash;
    
    public ReductorTest() {
    }
    
    
    @Before
    public void setUp() {
        charset = "abc";
        pwLength = 5;
        testHash = "0123456789qwertyuiopasdfghjklzxc".getBytes();
        rf = new Reductor(charset,3,5);
    }

    @Test
    public void reduceReturnsCorrectLength() {
        assertEquals(pwLength,rf.reduce(testHash, 1,pwLength).length);
    }
//    @Test
//    public void reduceReturnsCorrectLength2() {
//        for (int i = 0; i < 100; i++) {
//            assertTrue(minPwLength <= rf.reduce(testHash,i).length && maxPwLength >= rf.reduce(testHash, i).length);
//        }
//    }
    
    @Test
    public void reduceReturnsCharsOnlyInCharset() {
        byte[] reduced = rf.reduce(testHash,1);
        for (int i = 0; i < reduced.length; i++) {
            assertTrue(reduced[i] >= 0 && reduced[i] < charset.length());
        }
    }
    
    @Test
    public void reduceReturnsDifferentStringForDifferentHash() {
        byte[] reduced = rf.reduce(testHash,1);
        byte[] reduced2 = rf.reduce("1123456789qwertyuiopasdfghjklzxc".getBytes(), 1);
        
        assertFalse(Arrays.equals(reduced, reduced2));
    }
    @Test
    public void reduceReturnsSameStringForSameHash() {
        byte[] reduced = rf.reduce(testHash,1);
        byte[] reduced2 = rf.reduce(testHash, 1);
        assertTrue(Arrays.equals(reduced, reduced2));
    }
//    
//    @Test
//    public void reduceReturnsDifferentStringForDifferentHash2() {
//        String reduced = rf.reduce(testHash,1);
//        String reduced2 = rf.reduce("1123456789qwertyuigpasdfghjklzxd".getBytes(), pwLength);
//        assertFalse(reduced.equals(reduced2));
//    }
//    
    @Test
    public void reduceReturnsDifferentStringForDifferentFunctionNr() {
        byte[] reduced = rf.reduce(testHash,1);
        byte[] reduced2 = rf.reduce(testHash,2);
        assertFalse(Arrays.equals(reduced,reduced2));
    }
    
    @Test
    public void originalHashNotChangedInReduction() {
        byte[] testHash = this.testHash;
        byte[] reduced = rf.reduce(testHash,1);
        assertTrue(Arrays.equals(testHash,this.testHash));
    }
    
  
}