

package helpers;

import java.util.Random;

/**
 * User interface helper class. Mainly handles printing of stats.
 * 
 * @author Lauri Kangassalo / lauri.kangassalo@helsinki.fi
 */
public class UIHelper {
    
    /**
     * What to print when starting table generation.
     */
    public void printTableGenerationStartStats() {
        System.out.println("Starting table generation.");
    }

    /**
     * Prints the progress of table generation.
     * @param i current row
     * @param chainsPerTable total of rows
     */
    public void printTableGenerationProgress(int i, int chainsPerTable) {
        System.out.println(i + "/" + chainsPerTable);
    }
    
    public void startFileRead() {
        System.out.print("Reading table file... ");
    }
    
    public void endFileRead(int lineCount) {
        System.out.println("read " + lineCount + " lines.");
    }
    
    public void done() {
        System.out.println("done.");
    }
    
    public void hashCracked(String plaintext) {
        System.out.println("Hash cracked: "+ plaintext);
    }
    
    public void crackFailed() {
        System.out.print("Failed to crack the hash :( ");
        int random = new Random().nextInt(5);
        String consolidation = "";
        switch(random) {
            case 0:
                consolidation = "Maybe go eat a banana?";
                break;
            case 1:
                consolidation = "Maybe go smoke a cigarette?";
                break;
            case 2:
                consolidation = "Maybe get some sleep?";
                break;
            case 3:
                consolidation = "Your mom still loves you, though.";
                break;
            case 4:
                consolidation = "Please put the gun down... please.";
                break;
        }
        System.out.println(consolidation);
    }
    
    public void printEndpointCount(int endpoints) {
        System.out.println("Possible endpoints: "+endpoints);
    }
    
    // table file error messages
    public void writeError() {
        System.out.println("Could not write to table file.");
    }
    
    public void readError() {
        System.out.println("Could not read table file.");
    }
    
    public void closeError() {
        System.out.println("Could not close table file.");
    }

}
