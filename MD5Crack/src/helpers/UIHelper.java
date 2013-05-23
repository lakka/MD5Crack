

package helpers;

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
        System.out.print("Reading the table file...");
    }
    public void done() {
        System.out.println("done.");
    }
    
    public void hashCracked(String plaintext) {
        System.out.println("Hash cracked: "+ plaintext);
    }
    
    public void printEndpointCount(int endpoints) {
        System.out.println("Endpoints found: "+endpoints);
    }

}
