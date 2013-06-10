package helpers;

import hashtable.Bytes;
import hashtable.HashTable;
import java.io.BufferedInputStream;
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

        File file = new File(charsetLength + "-" + minPwLength + "-" + maxPwLength + "-" + chainsPerTable + "-" + chainLength + ".tbl");
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
    public BufferedInputStream openFile(String filename) {
        File file = new File(filename);
        BufferedInputStream dis;
        try {
            dis = new BufferedInputStream(new FileInputStream(file));
        } catch (Exception e) {
            uihelper.readError();
            return null;
        }

        return dis;
    }

    /**
     * Reads a previously opened table file into a hashmap.
     *
     * @param dis handle for file
     * @param pwLength password length
     * @return rainbow table
     */
    public HashTable readTable(BufferedInputStream dis, int chainsPerTable, int minPwLength, int maxPwLength) {

        HashTable table = new HashTable(chainsPerTable / 5, minPwLength, maxPwLength);
        int i = 0;

        int availableIterations = 0;
        try {
            availableIterations = dis.available();
        } catch (Exception e) {
        };
        uihelper.startFileRead();
        while (true) {
            int pwLength = i % (maxPwLength - minPwLength + 1) + minPwLength;
            byte[] startingPoint = new byte[pwLength];
            byte[] endpoint = new byte[pwLength];

            try {

                // this block is here to prevent continuous polling of available()
                availableIterations -= (pwLength * 2);
                if (availableIterations < 50) {
                    if (dis.available() < startingPoint.length + endpoint.length) {
                        break;
                    }
                }


                dis.read(startingPoint);
                dis.read(endpoint);
//                dis.readFully(startingPoint);
//                dis.readFully(endpoint);
                table.insert(new Bytes(endpoint), new Bytes(startingPoint));
                i++;
            } catch (EOFException e) {
                uihelper.endFileRead(i);
                return table;
            } catch (Exception e) {
                uihelper.readError();
                return null;
            }

        }
        uihelper.endFileRead(i);
        return table;


    }


}
