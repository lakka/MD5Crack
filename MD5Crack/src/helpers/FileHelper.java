package helpers;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;

/**
 * File helper methods.
 *
 * @author Lauri Kangassalo / lauri.kangassalo@helsinki.fi
 */
public class FileHelper {

    /**
     * Creates a new table file
     *
     * @return null, if table creation failed
     */
    public DataOutputStream createTableFile(int charsetLength, int minPwLength, int maxPwLength, int chainsPerTable, int chainLength) {

        File file = new File(charsetLength+"_"+minPwLength+"-"+maxPwLength+"_"+chainsPerTable+"_"+chainLength+"_"+ (""+System.currentTimeMillis()).substring(4) + ".tbl");
        DataOutputStream dos;
        try {
            dos = new DataOutputStream(new FileOutputStream(file));
        } catch (Exception e) {
            System.out.println("Could not create a table file.");
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
            System.out.println("Could not write to table file.");
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
            System.out.println("Could not close table file.");
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
            System.out.println("Could not open the table file.");
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
    public HashMap<byte[], byte[]> readTable(DataInputStream dis, int pwLength) {

        HashMap<byte[], byte[]> table = new HashMap<byte[], byte[]>();

        int i = 0;
        while (true) {
            byte[] startingPoint = new byte[pwLength];
            byte[] endpoint = new byte[pwLength];
            try {
                dis.readFully(startingPoint);
                dis.readFully(endpoint);
                table.put(endpoint, startingPoint);

            } catch (EOFException e) {
                return table;
            } catch (Exception e) {
                System.out.println("Error reading table file.");
                return null;
            }

        }

        //return table;


    }

}
