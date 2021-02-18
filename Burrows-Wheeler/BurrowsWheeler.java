import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
public class BurrowsWheeler {

    private static final int R = 256; // extended ASCII

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output 
    public static void transform() {
        String s = BinaryStdIn.readString();
        CircularSuffixArray csa = new CircularSuffixArray(s);
        int first = -1;
        int n = csa.length();
        char[] bw = new char[n];
        for (int i = 0; i < n; i++) {
            int index = csa.index(i);
            if (index == 0) {
                first = i;
            }
            bw[i] = s.charAt((index + n - 1) % n);
        }
        BinaryStdOut.write(first);
        for (char c : bw) {
            BinaryStdOut.write(c);
        }
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
    public static void inverseTransform() {
        int first = BinaryStdIn.readInt();
        char[] t = BinaryStdIn.readString().toCharArray();

        int n = t.length;
        int[] next = new int[n];

        int[] count = getFreqCumulates(t);
        char[] firstCol = getSortedChars(t);
            
        // loop int i from 0 -> n
        // char c = t[i]
        // int j = first unused occurence of c in firstCol *** use count[] from key-indexed sort??
        // next[j] = i
        for (int i = 0; i < n; i++) {
            char c = t[i];
            int j = count[c]++;
            next[j] = i;
        }


        BinaryStdOut.write(firstCol[first]);
        for (int current = next[first]; current != first; current = next[current]) {
            BinaryStdOut.write(firstCol[current]);
        }

        BinaryStdOut.close();

    }

    // letter frequency cumulates, offset by 1
    private static int[] getFreqCumulates(char[] a) {
        int[] count = new int[R+1];
        for (int i = 0; i < a.length; i++) {
            count[a[i]+1]++;
        }
        for (int r = 0; r < R; r++) {
            count[r+1] += count[r];
        }
        return count;
    }


    // sort chars using key-indexed counting
    private static char[] getSortedChars(char[] t) {

        int[] count = getFreqCumulates(t);
        int n = t.length;
        char[] aux = new char[n];
        for (int i = 0; i < n; i++) {
            aux[count[t[i]]++] = t[i];
        }
        char[] a = new char[n];
        for (int i = 0; i < n; i++) {
            a[i] = aux[i];
        }
        return a;
    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args) {
        if (args[0].equals("-")) {
            transform();
        }
        else if (args[0].equals("+")) {
            inverseTransform();
        }
        else {
            throw new IllegalArgumentException("first argument must be '-' or '+'");
        }
    }
}
