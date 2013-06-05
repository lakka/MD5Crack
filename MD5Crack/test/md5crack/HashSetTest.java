

package md5crack;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import HashSet.HashSet;
import java.util.Arrays;

/**
 *
 * @author Lauri Kangassalo / lauri.kangassalo@helsinki.fi
 */
public class HashSetTest {
    HashSet ht;


    @Before
    public void setUp() {
        ht = new HashSet(300,3,5);
    }
    
    @Test
    public void insertAddsToTable() {
        byte[] bytes = {122,123,124};
        byte[] bytes2 = {122,123,122};
        ht.insert(bytes);
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
        ht.insert(bytes);
        ht.insert(bytes2);
        assertEquals(2,ht.size());
    }
    
        @Test
    public void sizeReturnsCorrectValueWhenTwoSameArraysAreInserted() {
        byte[] bytes = {122,123,122};
        byte[] bytes2 = {122,123,122};
        ht.insert(bytes);
        ht.insert(bytes2);
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
        ht.insert(bytes);
        assertTrue(ht.contains(bytes));
    }
    
    @Test
    public void containsReturnsFalse() {
        byte[] bytes = {122,123,124};
        byte[] bytes2 = {122,123,122};
        ht.insert(bytes);
        assertFalse(ht.contains(bytes2));
    }




}