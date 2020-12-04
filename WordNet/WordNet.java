import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;
import java.util.ArrayDeque;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class WordNet {

    private final Map<String, Set<Integer>> dict; // map of the format 'word : list of ids of synsets containing word'
    private final Digraph G; // digragh charting hypernyms - vertex number = synset id
    private final int numOfWords;

    // constructor takes the name of two CSV input files
    // (see full spec for required file format)
    public WordNet(String synsets, String hypernyms) {
        dict = createSynsetDict(synsets);
        numOfWords = dict.size();
        G = createGraph(hypernyms);
    }

    private Map<String, Set<Integer>> createSynsetDict(String synsets) {
        In in = new In(synsets);
        Map<String, Set<Integer>> synMap = new HashMap<String, Set<Integer>>();
        while (!in.isEmpty()) {
            String[] tokens = parseLine(in);
            Integer id = new Integer(tokens[0]);
            String synset = tokens[1];
            for (String word : synset.split(" ")) {
                addToDict(synMap, word, id);
            }
        }
        return synMap;
    }

    // adds word to dict (synMap), if it's not already present, then adds id to idSet,
    // creating a new set (a HashSet<Integer>) if necessary
    private void addToDict(Map<String, Set<Integer>> synMap, String word, Integer id) {
        if (!synMap.containsKey(word)) {
            synMap.put(word, new HashSet<Integer>());
        }

        Set<Integer> idSet = synMap.get(word);
        idSet.add(id);
    }


    // returns next line in CSV filestream as array of tokens
    private String[] parseLine(In in) {
        String line = in.readLine();
        return line.split(",");
    }

    private Digraph createGraph(String hypernyms) {
        In in = new In(hypernyms);
        Digraph dg = new Digraph(numOfWords);
        while (!in.isEmpty()) {
            String[] tokens = parseLine(in);
            int len = tokens.length;
            if (len > 1) {
                addEdges(dg, tokens, len);
            }
        }
        return dg;
    }

    // len = tokens.length
    private void addEdges(Digraph dg, String[] tokens, int len) {
        assert len > 1;
        int v = Integer.parseInt(tokens[0]);
        for (int i = 1; i < len; i++) {
            int w = Integer.parseInt(tokens[i]);
            dg.addEdge(v, w);
        }
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        Queue<String> q = new ArrayDeque<String>(numOfWords);
        for (String word : dict.keySet()) {
            q.add(word);
        }
        return q;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return dict.containsKey(word);
    }

    /*// distance between nounA and nounB
    public int distance(String nounA, String nounB) {
    }

    // a synset (second field of synsets.txt) that is the 
    // common ancestor of nounA and nounB in a shortest
    // ancestral path (see spec)
    public String sap(String nounA, String nounB) {
    }
    */

    // prints to stdout
    // for testing
    private void print() {
    }

    // unit testing
    public static void main(String[] args) {
        WordNet wn = new WordNet(args[0], args[1]);
        
        for (String noun : wn.nouns()) {
            assert wn.isNoun(noun);
            StdOut.printf(noun);
        }
        StdOut.println();
    }

}
