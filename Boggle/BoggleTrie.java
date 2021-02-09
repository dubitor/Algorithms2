import java.util.Scanner;
/** Implementation of trie data structure built for dictionary
 *  to be used in a boggle-playing programme
 */
public class BoggleTrie {
    private static final int R = 256; // extended ASCII
    private Node root; // root of trie

    private class Node {
        private Node[] next = new Node[R]; // R-way trie
        private boolean isString;
    }

    public BoggleTrie() {
    }

    public boolean contains(String key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        Node x = get(root, key, 0);
        if (x == null) {
            return false;
        }
        return x.isString;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) {
            return null;
        }
        if (d == key.length()) {
            return x;
        }
        char c = key.charAt(d);
        return get(x.next[c], key, d + 1);
    }

    // Are there any words in the dictionary that begin with 
    // a given prefix?
    public boolean containsPrefix(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException();
        }
        Node x = get(root, prefix, 0);
        return x != null;
    }

    public void add(String key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        root = add(root, key, 0);
    }

    private Node add(Node x, String key, int d) {
        if (x == null) {
            x = new Node();
        }
        if (d == key.length()) {
            x.isString = true;
        }
        else {
            char c = key.charAt(d);
            x.next[c] = add(x.next[c], key, d + 1);
        }
        return x;
    }

    public static void main(String[] args) {
        BoggleTrie trie = new BoggleTrie();
        Scanner sc = new Scanner(System.in);
        System.out.print("number of words to enter: ");
        int total = sc.nextInt();
        System.out.println();

        for (int i = 0; i < total; i++) {
            System.out.print("enter next word: ");
            String word = sc.next();
            trie.add(word);
            System.out.println();
        }

        System.out.println("now enter query strings");

        while (sc.hasNext()) {
            String query = sc.next();
            System.out.printf("contains as word: %b", trie.contains(query));
            System.out.println();
            System.out.printf("contains as prefix: %b", trie.containsPrefix(query));
            System.out.println();
        }
    }

        
}
