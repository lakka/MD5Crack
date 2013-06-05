

package HashSet;

import java.util.Iterator;


/**
 *
 * @author Lauri Kangassalo / lauri.kangassalo@helsinki.fi
 */
    public class HashSetIterator implements Iterator<Bytes> {
        
        private int position;
        private int size;
        private int seen;
        private Bytes currentBytes;
        private Bytes[] bytestable;

        public HashSetIterator(Bytes[] bytestable, int size) {
            this.bytestable = bytestable;
            this.size = size;
        }

        @Override
        public boolean hasNext() {
            return seen < size;
        }

        @Override
        public Bytes next() {
            if (currentBytes == null || currentBytes.next == null) {
                while (bytestable[position] == null) {
                    position++;
                }
                currentBytes = bytestable[position];
            } else {
                currentBytes = currentBytes.next;
            }
            seen++;
            return currentBytes;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    

