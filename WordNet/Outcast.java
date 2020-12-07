import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
public class Outcast {

    private final WordNet wn;

    public Outcast(WordNet wordnet) {
        wn = wordnet;
    }

    public String outcast(String[] nouns) {
        String outcast = "";
        int maxDist = 0;
        for (int i = 0; i < nouns.length; i++) {
            int dist = 0;
            for (String noun : wn.nouns()) {
                dist += wn.distance(nouns[i], noun);
            }
            if (dist > maxDist) {
                outcast = nouns[i];
                maxDist = dist;
            }
        }
        return outcast;
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
