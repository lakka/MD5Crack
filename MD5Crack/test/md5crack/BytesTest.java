

package md5crack;

import hashtable.Bytes;
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
public class BytesTest {
    Bytes bytes;
    Bytes bytes2;
    Bytes bytes3;

    public BytesTest() {
    }

    @Before
    public void setUp() {
        bytes = new Bytes(new byte[] {122,12,21,112});
        bytes2 = new Bytes(new byte[] {122,12,2,112});
        bytes3 = new Bytes(new byte[] {122,12,21,112});
    }


    @Test
    public void equalsReturnsFalse() {
        assertFalse(bytes.equals(bytes2));
    }
    
    @Test
    public void equalsReturnsTrue() {
        assertTrue(bytes.equals(bytes3));
    }

}