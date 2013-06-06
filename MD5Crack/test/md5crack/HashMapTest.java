

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


    @Before
    public void setUp() {
        ht = new HashTable(300,3,5);
    }
    
    @Test
    public void insertAddsToTable() {
        byte[] bytes = {122,123,124};
        ht.insert(new Bytes(bytes));
        assertFalse(Arrays.asList(ht.getBytes()).isEmpty());
    }
    
    @Test
    public void sizeIsZero() {
        assertEquals(0,ht.size());
    }
    
    @Test
    public void sizeReturnsCorrectValueWhenTwoDifferentArraysAreInserted() {
        byte[] bytes = {122,123,124};
        byte[] bytes2 = {122,123,122};
        ht.insert(new Bytes(bytes));
        ht.insert(new Bytes(bytes2));
        assertEquals(2,ht.size());
    }
    
        @Test
    public void sizeReturnsCorrectValueWhenTwoSameArraysAreInserted() {
        byte[] bytes = {122,123,122};
        byte[] bytes2 = {122,123,122};
        ht.insert(new Bytes(bytes));
        ht.insert(new Bytes(bytes2));
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
    
//    @Test
//    public void searchFindsValue() {
//        byte[] bytes = {122,123,124};
//        byte[] bytes2 = {122,123,122};
//        ht.insert(bytes);
//        assertEquals(bytes2,ht.search(bytes));
//    }
    
    @Test
    public void containsReturnsTrue() {
        byte[] bytes = {122,123,124};
        ht.insert(new Bytes(bytes));
        assertTrue(ht.contains(new Bytes(bytes)));
    }
    
    @Test
    public void containsReturnsFalse() {
        byte[] bytes = {122,123,124};
        byte[] bytes2 = {122,123,122};
        ht.insert(new Bytes(bytes));
        assertFalse(ht.contains(new Bytes(bytes2)));
    }




}