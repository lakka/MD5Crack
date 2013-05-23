

package md5crack;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import HashTable.HashTable;
import java.util.Arrays;

/**
 *
 * @author Lauri Kangassalo / lauri.kangassalo@helsinki.fi
 */
public class HashTableTest {
    HashTable ht;


    @Before
    public void setUp() {
        ht = new HashTable(300,3,5);
    }
    
    @Test
    public void insertAddsToTable() {
        byte[] bytes = {122,123,124};
        byte[] bytes2 = {122,123,122};
        ht.insert(bytes,bytes2);
        assertFalse(Arrays.asList(ht.getBytes()).isEmpty());
    }
    
    @Test
    public void searchFindsValue() {
        byte[] bytes = {122,123,124};
        byte[] bytes2 = {122,123,122};
        ht.insert(bytes,bytes2);
        assertEquals(bytes2,ht.search(bytes));
    }
    
    @Test
    public void containsWorks() {
        byte[] bytes = {122,123,124};
        byte[] bytes2 = {122,123,122};
        ht.insert(bytes,bytes2);
        assertTrue(ht.contains(bytes));
    }




}