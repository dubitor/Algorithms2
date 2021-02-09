import edu.cs.princeton.algs4.TrieSET;

public class BoggleSolver {

    private final TrieSET dict;

    // Initialises the data structure using the given array as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z)
    public BoggleSolver(String[] dictionary) {
        dict = new TrieSET();
        for (String word : dictionary) {
            if (word.length() >= 3) {
                dict.add(word);
            }
        }
    }

    // Returns the set of all valid words in the given Boggle board
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        

    }

    // Returns the score of the given word if it is in the dctionary, zero otherwise.
    // (Assume word only contains uppercase A-Z)
    public int scoreOf(String word) {
        if (!dict.contains(word)) {
            return 0;
        }
        int len = word.length();
        if (len == 3 || len == 4) {
            return 1;
        }
        if (len == 5) {
            return 2;
        }
        if (len == 6) {
            return 3;
        }
        if (len == 7) {
            return 5;
        }
        if (len >= 8) {
            return 11;
        }
        return 0;
    }
}
