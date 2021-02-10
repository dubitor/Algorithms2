import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stopwatch;
/** how many boggle boards can the solver solve in a second?
 *  command line argument: dictionary file
 */
public class BoggleStressTester {

    public BoggleStressTester() {
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        
        int boardsSolved = 0;
        Stopwatch timer = new Stopwatch();
        while (timer.elapsedTime() < 1.0) {
            BoggleBoard board = new BoggleBoard(4, 4);
            solver.getAllValidWords(board);
            boardsSolved++;
        }
        System.out.printf("Solved %d boards in %.5f seconds", boardsSolved, timer.elapsedTime());
        System.out.println();
    }
}
