public class WordNet {

    private final Map<String, Set<Integer>> dict; // map of the format 'word : list of ids of synsets containing word'
    private final Digraph G; // digragh charting hypernyms - vertex number = synset id

    // constructor takes the name of two CSV input files
    // (see full spec for required file format)
    public WordNet(String synsets, String hypernyms) {
        dict = createSynsetDict(synsets);
    }

    private Map<String, Set<Integer>> createSynsetDict(String synsets) {
        In in = new In(synsets);
        Map<String, Set<Integer>> synMap = new HashMap<String, Set<Integer>>();
        while (!in.isEmpty()) {
            String line = in.readLine();
            String[] tokens = line.split(",");
            
            Integer id = new Integer(tokens[0]);
            String synset = tokens[1];
            for (String word : synset.split(" ")) {
                addToDict(synMap, word, id)
            }
        }
        return synMap;
    }

    // adds word to dict (synMap), if it's not already present, then adds id to idSet,
    // creating a new set (a HashSet<Integer>) if necessary
    private void addToDict(Map<String, Set<Integer>> synMap, String word, Integer id) {
        if (!synMap.contains(word)) {
            synMap.put(word, new HashSet<Integer>());
        }

        Set<Integer> idSet = synMap.get(word);
        idSet.add(id);
    }
            

    // returns all WordNet nouns
    public Iterable<String> nouns() {
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
    }

    // distance between nounA and nounB
    public int distance(String nounA, String nounB) {
    }

    // a synset (second field of synsets.txt) that is the 
    // common ancestor of nounA and nounB in a shortest
    // ancestral path (see spec)
    public String sap(String nounA, String nounB) {
    }

    // unit testing
    public static void main(String[] args) {
        WordNet wn = new WordNet(args[0], args[1]);

}
