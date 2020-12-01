public class WordNet {

    // constructor takes the name of two CSV input files
    // (see full spec for required file format)
    public WordNet(String synsets, String hypernyms) {
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

}
