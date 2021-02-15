import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
public class MoveToFront {

    // apply move-to-front encoding, reading from std input & writing to std output
    public static void encode() {
        ASCIILinkedList seq = new ASCIILinkedList();
        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            c = seq.moveToFront(c);
            BinaryStdOut.write(c);
        }
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from std input & writing to std output
    public static void decode() {
        ASCIILinkedList seq = new ASCIILinkedList();
        while (!BinaryStdIn.isEmpty()) {
            int i = BinaryStdIn.readChar();
            char c = seq.moveToFront(i);
            BinaryStdOut.write(c);
        }
        BinaryStdOut.close();
    }

    // if args[0] is "-" apply move to front encoding
    // if args[0] is "+" apply move to front decoding
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            encode();
        }
        else if (args[0].equals("+")) {
            decode();
        }
        else {
            throw new IllegalArgumentException("must provide '+' or '-' to main()");
        }
    }
}
