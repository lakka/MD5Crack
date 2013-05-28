package md5crack;

import helpers.CommonHelper;
import java.util.Scanner;

/**
 * User interface class
 * 
 * @author Lauri Kangassalo / lauri.kangassalo@helsinki.fi
 */
public class UI {

    private Scanner scanner = new Scanner(System.in);
    private CommonHelper helper = new CommonHelper();

    /**
     * Ask details from user and either start generating a new table or cracking a hash/hashes.
     */
    public void start() {
        int mode = askMode();
        String filename = null;
        if (mode == 2) {
            while (filename == null) {
                filename = askFilename();
            }
        }
        String charset = null;
        while (charset == null) {
            charset = askCharset();
        }
        int minPw = askInteger("Minimum password length: ");
        int maxPw = askInteger("Maximum password length: ");
        System.out.println();
        System.out.println("Keyspace: "+helper.calculateKeyspace(charset, minPw, maxPw));
        int chainsPerTable = askInteger("Chains per table: ");
        int chainLength = askInteger("Chain length: ");
        System.out.println();

        if (mode == 1) {
            System.out.println("Chains x chain length: "+(long)chainsPerTable*chainLength + " Good luck!");
            TableCreator tc = new TableCreator(charset, minPw, maxPw, chainsPerTable, chainLength);
            tc.createTable();
        } else if (mode == 2) {
            System.out.println("Enter hash to start cracking, or press enter to quit.");
            String hash = askHash();
            MD5Crack cracker = new MD5Crack(charset, minPw, maxPw, chainLength, filename);
            while (hash != null) {
                cracker.crackHash(hash);
                hash = askHash();
            }
        }

    }

    /**
     * Query the user for charsets, a few charsets are set here as a template.
     * @return charset
     */
    private String askCharset() {
        String[] charsets = {"qwertyuiopasdfghjklzxcvbnm0123456789", "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM0123456789",
            "qwertyuiopasdfghjklzxcvbnm", "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM"};

        System.out.println("Select a charset or press enter to use a custom one:");
        System.out.println("1) alpha-numeric (lowercase)");
        System.out.println("2) alpha-numeric (lower- and uppercase)");
        System.out.println("3) letters a-z");
        System.out.println("4) letters a-z and A-Z");
        System.out.print("Selection: ");
        String input = scanner.nextLine();
        if (input.isEmpty()) {
            System.out.print("Type the characters to be included in charset: ");
            input = scanner.nextLine();
            if (input.isEmpty()) {
                System.out.println("Invalid input.\n");
                return null;
            }
            return input;
        }
        int charsetIndex;
        try {
            charsetIndex = Integer.parseInt(input);
        } catch (Exception e) {
            System.out.println("Invalid input.\n");
            return null;
        }
        if (charsetIndex >= charsets.length || charsetIndex < 1) {
            System.out.println("Invalid input.\n");
            return null;
        }
        return charsets[charsetIndex - 1];



    }

    /**
     * Queries the user for an integer and checks that input really is one.
     * 
     * @param message message to print
     * @return an integer
     */
    private int askInteger(String message) {
        int inputInteger = 0;
        while (inputInteger == 0) {
            System.out.print(message);
            String input = scanner.nextLine();
            try {
                inputInteger = Integer.parseInt(input);
            } catch (Exception e) {
                System.out.println("Invalid input.\n");
            }
            if (inputInteger == 0) {
                System.out.println("Invalid input.\n");
            }
        }
        return inputInteger;
    }

    /**
     * Query the user for which mode to run the program in.
     * 
     * @return 1 for generating tables, 2 for cracking hashes
     */
    private int askMode() {
        System.out.println("Select mode:");
        System.out.println("1) Generate a new rainbow table");
        System.out.println("2) Crack an MD5-hash");
        int input = 0;
        while (input < 1 || input > 2) {
            input = askInteger("Selection: ");
            if (input < 1 || input > 2) {
                System.out.println("Invalid input.\n");
            }
        }
        return input;

    }

    private String askFilename() {
        System.out.print("Rainbow table to load: ");
        String input = scanner.nextLine();
        if (input.isEmpty()) {
            return null;
        }
        return input;

    }

    /**
     * Queries user for a hash until user gives a legitimate one.
     * @return MD5-hash
     */
    private String askHash() {
        String hash = null;
        while (hash == null || hash.length() != 32) {
            System.out.print("Hash: ");
            hash = scanner.nextLine();
            if (hash.isEmpty()) {
                return null;
            }
            if (hash.length() != 32) {
                System.out.println("Invalid hash.\n");
            }
        }
        return hash;
    }
}
