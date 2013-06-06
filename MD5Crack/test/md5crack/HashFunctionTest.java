

package md5crack;

import hashtable.Bytes;
import hashtable.HashFunction;
import org.junit.Before;
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
        assertEquals(hf.hash(new Bytes(bytes)),hf.hash(new Bytes(bytes2)));
    }
    
    @Test
    public void differentHashValueForDifferentKeys() {
        byte[] bytes = {123,123,123};
        byte[] bytes2 = {123,123,124};
        assertNotSame(hf.hash(new Bytes(bytes)),hf.hash(new Bytes(bytes2)));
    }



}