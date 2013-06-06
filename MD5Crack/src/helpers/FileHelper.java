package helpers;

import hashtable.Bytes;
import hashtable.HashTable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Need to deal with files? Seek no more, for your savior is here.
 *
 * @author Lauri Kangassalo / lauri.kangassalo@helsinki.fi
 */
public class FileHelper {
    private UIHelper uihelper = new UIHelper();
    
    /**
     * Creates a new table file
     *
     * @return null, if table creation failed
     */
    public DataOutputStream createTableFile(int charsetLength, int minPwLength, int maxPwLength, int chainsPerTable, int chainLength) {

        File file = new File(charsetLength+"-"+minPwLength+"-"+maxPwLength+"-"+chainsPerTable+"-"+chainLength+".tbl");
        DataOutputStream dos;
        try {
            dos = new DataOutputStream(new FileOutputStream(file));
        } catch (Exception e) {
            uihelper.writeError();
            return null;
        }

        return dos;
    }

    /**
     * Writes a chain starting point and endpoint to file.
     *
     * @param dos handle for table file
     * @param startingPoint chain starting point
     * @param endpoint chain endpoint
     */
    public void writeToFile(DataOutputStream dos, byte[] startingPoint, byte[] endpoint) {
        try {
            dos.write(startingPoint);
            dos.write(endpoint);
        } catch (Exception e) {
            uihelper.writeError();
        }
    }

    /**
     * Closes a previously opened file.
     *
     * @param dos
     */
    public void closeFile(DataOutputStream dos) {
        try {
            dos.close();
        } catch (Exception e) {
            uihelper.closeError();
        }
    }

    /**
     * Opens a file for reading.
     * 
     * @param filename file to open
     * @return DataInputStream handle for file
     */
    public DataInputStream openFile(String filename) {
        File file = new File(filename);
        DataInputStream dis;
        try {
            dis = new DataInputStream(new FileInputStream(file));
        } catch (Exception e) {
            uihelper.readError();
            return null;
        }

        return dis;
    }

    /**
     * Reads a previously opened table file into a hashmap.
     * @param dis handle for file
     * @param pwLength password length
     * @return rainbow table
     */
    public HashTable readTable(DataInputStream dis, int chainsPerTable, int minPwLength, int maxPwLength) {

        HashTable table = new HashTable(chainsPerTable/5, minPwLength, maxPwLength);
        int i = 0;
        
        uihelper.startFileRead();
        
        while (true) {
            int pwLength = i % (maxPwLength - minPwLength + 1) + minPwLength;
            byte[] startingPoint = new byte[pwLength];
            byte[] endpoint = new byte[pwLength];
            try {
                
                dis.readFully(startingPoint);
                dis.readFully(endpoint);
                Bytes bytese = new Bytes(endpoint);
                Bytes bytess = new Bytes(startingPoint);
                table.insert(bytese, bytess);
                i++;
            } catch (EOFException e) {
                uihelper.endFileRead(i);
                return table;
            } catch (Exception e) {
                uihelper.readError();
                return null;
            }

        }


    }

}
