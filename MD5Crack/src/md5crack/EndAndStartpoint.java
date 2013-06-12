

package md5crack;

/**
 *
 * @author Lauri Kangassalo / lauri.kangassalo@helsinki.fi
 */
public class EndAndStartpoint {
    private byte[] startingPoint;
    private byte[] endpoint;

    public EndAndStartpoint(byte[] startingPoint, byte[] endpoint) {
        this.startingPoint = startingPoint;
        this.endpoint = endpoint;
    }

    public byte[] getStartingPoint() {
        return startingPoint;
    }

    public byte[] getEndpoint() {
        return endpoint;
    }
    
    

}
