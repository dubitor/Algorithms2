import edu.princeton.cs.algs4.TrieSET;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
public class BoggleSolver {

    private final BoggleTrie dict;

    // Initialises the data structure using the given array as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z)
    public BoggleSolver(String[] dictionary) {
        dict = new BoggleTrie();
        for (String word : dictionary) {
            if (word.length() >= 3) {
                dict.add(word);
            }
        }
    }

    // Returns the set of all valid words in the given Boggle board
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        TrieSET words = new TrieSET();
        boolean[][] visited = new boolean[board.rows()][board.cols()];
        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                getValidWords(i, j, board, visited, new StringBuilder(), words);
            }
        }
        return words;
    }

    private void getValidWords(int row, int col, BoggleBoard board, boolean[][] visited, StringBuilder currentSB, TrieSET words) {
        if (visited[row][col]) {
            return;
        }

        char letter = board.getLetter(row, col);
        currentSB.append(letter);
        if (letter == 'Q') {
            currentSB.append('U');
        }
        String current = currentSB.toString();

        // found a word?
        if (dict.contains(current)) {
            words.add(current);
        }
        // worth visiting any more tiles?
        if (dict.containsPrefix(current)) {
            visited[row][col] = true;
            
            // visit all neighbouring tiles
            if (row != board.rows() - 1) {
                getValidWords(row + 1, col, board, visited, new StringBuilder(current), words); // recurse down
                if (col != board.cols() - 1) {
                    getValidWords(row + 1, col + 1, board, visited, new StringBuilder(current), words); // recurse down-right
                }
            }
            if (row != 0) {
                getValidWords(row - 1, col, board, visited, new StringBuilder(current), words); // recurse up
                if (col != 0) {
                    getValidWords(row - 1, col - 1, board, visited, new StringBuilder(current), words); // recurse up-left
                }
            }
            if (col != board.cols() - 1) {
                getValidWords(row, col + 1, board, visited, new StringBuilder(current), words); // recurse right
                if (row != 0) {
                    getValidWords(row - 1, col + 1, board, visited, new StringBuilder(current), words); // recurse up-right
                }
            }
            if (col != 0) {
                getValidWords(row, col - 1, board, visited, new StringBuilder(current), words); // recurse left
                if (row != board.rows() - 1) {
                    getValidWords(row + 1, col - 1, board, visited, new StringBuilder(current), words); // recurse down-left
                }
            }
        }
        visited[row][col] = false;
        return;
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

    // test client - takes file name of dictionary and file name of boggle board
    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }

}
