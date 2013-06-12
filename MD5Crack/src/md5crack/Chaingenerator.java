package md5crack;

import helpers.CommonHelper;
import helpers.FileHelper;
import helpers.Reductor;
import java.io.BufferedOutputStream;
import java.security.MessageDigest;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author Lauri Kangassalo / lauri.kangassalo@helsinki.fi
 */
public class Chaingenerator extends Thread {

    private BufferedOutputStream bos;
    private byte pwLength;
    private MessageDigest md;
    private int chainLength;
    private Reductor rf;
    private final FileHelper file;
    private byte[] startingPoint;
    private final int chains;
    private final Random random;
    private final String charset;
    private final BlockingQueue<EndAndStartpoint> queue;
    private int nr;

    public Chaingenerator(BufferedOutputStream bos, byte pwLength, int chainLength, MessageDigest md, Reductor rf, FileHelper file, int chains, Random random, String charset, BlockingQueue<EndAndStartpoint> outputQueue,
            int threadnr) {
        this.bos = bos;
        this.pwLength = pwLength;
        this.md = new CommonHelper().getMD5digester();
        this.chainLength = chainLength;
        this.rf = rf;
        this.file = file;
        this.chains = chains;
        this.random = random;
        this.charset = charset;
        this.queue = outputQueue;
        this.nr = threadnr;
    }

    @Override
    public void run() {
        calculateChain();
    }

    private void calculateChain() {
int a = 0;
        for (int i = 0; i < chains; i++) {

            createRandomStartingPoint(random, pwLength);
            int j;
            byte[] hash;
            byte[] endpoint = startingPoint;
            // loop each column with different reducing function
            
            for (j = 0; j < chainLength; j++) {
                hash = md.digest(endpoint);
                endpoint = rf.reduce(hash, j, pwLength);


            }
            if (i != 0 && i % (chains / 10) == 0) {
                System.out.println("Thread " + nr + " " + (a+=10) + "% ready.");
            }
            queue.add(new EndAndStartpoint(startingPoint, endpoint));
        }
    }

    private synchronized void writeChain(byte[] endpoint) {
        file.writeToFile(bos, startingPoint, endpoint);
    }

    private byte[] createRandomStartingPoint(Random random, byte pwLength) {
        startingPoint = new byte[pwLength];
        random.nextBytes(startingPoint);


        for (int a = 0; a < startingPoint.length; a++) {
            startingPoint[a] = (byte) (Math.abs(startingPoint[a]) % charset.length());
        }
        return startingPoint;
    }
}
