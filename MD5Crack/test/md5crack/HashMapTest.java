

package md5crack;

import hashtable.Bytes;
import hashtable.HashTable;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;

/**
 *
 * @author Lauri Kangassalo / lauri.kangassalo@helsinki.fi
 */
public class HashMapTest {
    HashTable ht;
    Bytes bytes;
    Bytes bytes2;


    @Before
    public void setUp() {
        ht = new HashTable(300,3,5);
        bytes = new Bytes(new byte[] {122,123,124});
        bytes2 = new Bytes(new byte[] {122,123,122});
    }
    
    @Test
    public void insertAddsToTable() {
        ht.insert(bytes);
        assertFalse(Arrays.asList(ht.getBytes()).isEmpty());
    }
    
    @Test
    public void insert2AddsToTable() {
        ht.insert(bytes,bytes2);
        assertFalse(Arrays.asList(ht.getBytes()).isEmpty());
    }
    
    @Test
    public void sizeIsZero() {
        assertEquals(0,ht.size());
    }
    
    @Test
    public void sizeReturnsCorrectValueWhenTwoDifferentArraysAreInserted() {
        ht.insert(bytes);
        ht.insert(bytes2);
        assertEquals(2,ht.size());
    }
    
        @Test
    public void sizeReturnsCorrectValueWhenTwoSameArraysAreInserted() {
        ht.insert(bytes);
        ht.insert(bytes);
        assertEquals(1,ht.size());
    }
    
//    @Test
//    public void isEmptyReturnsTrue() {
//        assertTrue(ht.isEmpty());
//    }
//    
//    @Test
//    public void isEmptyReturnsFalse() {
//        byte[] bytes = {122,123,124};
//        ht.insert(bytes);
//        assertFalse(ht.isEmpty());
//    }
    
    @Test
    public void searchFindsValue() {
        ht.insert(bytes,bytes2);
        assertEquals(bytes2,ht.search(bytes));
    }
    
    @Test
    public void searchReturnsNull() {
        ht.insert(bytes);
        assertNull(ht.search(bytes));
    }
    
    @Test
    public void containsReturnsTrue() {
        ht.insert(bytes);
        assertTrue(ht.contains(bytes));
    }
    
    @Test
    public void containsReturnsFalse() {
        ht.insert(bytes);
        assertFalse(ht.contains(bytes2));
    }




}