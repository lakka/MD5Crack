

package md5crack;

import HashSet.HashFunction;
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
public class HashFunctionTest {
    HashFunction hf;

    public HashFunctionTest() {
    }



    @Before
    public void setUp() {
        hf = new HashFunction(300,3,5);
    }

    @Test
    public void sameHashValueForSameKeys() {
        byte[] bytes = {123,123,123};
        byte[] bytes2 = {123,123,123};
        assertEquals(hf.hash(bytes),hf.hash(bytes2));
    }
    
    @Test
    public void differentHashValueForDifferentKeys() {
        byte[] bytes = {123,123,123};
        byte[] bytes2 = {123,123,124};
        assertNotSame(hf.hash(bytes),hf.hash(bytes2));
    }



}