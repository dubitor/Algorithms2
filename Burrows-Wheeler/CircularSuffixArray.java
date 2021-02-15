import java.util.Collections;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
public class CircularSuffixArray {
    private final String s;
    private final List<CircularSuffix> ls;

    // circular suffix array of s
    public CircularSuffixArray(String s) {
        if (s == null) {
            throw new IllegalArgumentException("provide non-null String to constructor");
        }
        this.s = s;
        this.ls = new ArrayList<CircularSuffix>(length());
        for (int i = 0; i < s.length(); i++) {
            ls.add(i, new CircularSuffix(i));
        }
        Collections.sort(ls);
    }

    // length of s
    public int length() {
        return s.length();
    }

    // returns original index of ith sorted suffix
    public int index(int i) {
        if (!(0 <= i && i < length())) {
            throw new IllegalArgumentException("index not in range");
        }
        return ls.get(i).offset;
    }

    private class CircularSuffix implements Comparable<CircularSuffix> {
        private int offset;

        public CircularSuffix(int offset) {
            this.offset = offset;
        }
        
        private char charAt(int i) {
            return s.charAt(i + offset);
        }

        public int compareTo(CircularSuffix that) {
            for (int i = 0; i < length(); i++) {
                if (this.charAt(i) < that.charAt(i)) {
                    return -1;
                }
                else if (this.charAt(i) > that.charAt(i)) {
                    return 1;
                }
            }
            return 0;
        }

    }

    // unit testing
    public static void main(String[] args) {
        String s = "circles";
        CircularSuffixArray csa = new CircularSuffixArray(s);
        System.out.println(s);
        char[] sChars = s.toCharArray();
        Arrays.sort(sChars);
        System.out.println(sChars);
        System.out.println("length = " + csa.length());
        System.out.println("original index of ith sorted suffix:");
        for (int i = 0; i < csa.length(); i++) {
            System.out.printf("i = %d\toriginal index = %d", i, csa.index(i));
            System.out.println();
        }
    }
}
