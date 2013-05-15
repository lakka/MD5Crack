

package helpers;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;

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
    public DataOutputStream createTableFile() {

        File file = new File(System.currentTimeMillis() + ".tbl");
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
}
