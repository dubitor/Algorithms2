import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.TreeSet;

public class SAP {

    private static final int INFINITY = Integer.MAX_VALUE;
    private final Digraph G;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        this.G = G;
    }

    // length of shortest ancestral path between v and w
    // returns -1 if no such path
    public int length(int v, int w) {
        return sap(v, w)[0];
    }

    // a common ancestor of v and w that participates in
    // a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        return sap(v, w)[1];
    }

    private int[] sap(int v, int w) {

        BreadthFirstDirectedPaths vbf = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths wbf = new BreadthFirstDirectedPaths(G, w);
        return sap(vbf, wbf);
    }

    // returns { shortestAncestralPathLength, shortestCommonAncestor } int array.
    private int[] sap(BreadthFirstDirectedPaths vbf, BreadthFirstDirectedPaths wbf) {
        

        int champLength = INFINITY;
        int ancestor = -1;
        for (int i = 0; i < G.V(); i++) {
            if (wbf.hasPathTo(i) && vbf.hasPathTo(i)) {
                int currentLength = wbf.distTo(i) + vbf.distTo(i);
                if (currentLength < champLength) {
                    champLength = currentLength;
                    ancestor = i;
                }
            }
        }
        if (champLength == INFINITY) {
            champLength = -1;
        }

        return new int[] { champLength, ancestor };
    }

    // length of shortest ancestral path between any vertex
    // in v and any vertex in w
    public int length(Iterable<Integer> v, Iterable<Integer> w) {

        BreadthFirstDirectedPaths vbf = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths wbf = new BreadthFirstDirectedPaths(G, w);
        
        return sap(vbf, wbf)[0];

    }

    // a common ancestor that participates in shortest
    // ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {

        BreadthFirstDirectedPaths vbf = new BreadthFirstDirectedPaths(G, v);
        BreadthFirstDirectedPaths wbf = new BreadthFirstDirectedPaths(G, w);
        
        return sap(vbf, wbf)[1];

    }

    // unit testing
    // builds graph and intitalises SAP
    // tests random pairs of points
    // tests random sets of points
	public static void main(String[] args) {

		In in = new In(args[0]); // build graph
		Digraph G = new Digraph(in);
		SAP sap = new SAP(G);

        int V = G.V(); // number of vertices

        int n = StdRandom.uniform(V / 3, V / 2); // number of pairs to test
        for (int i = 0; i < n; i++) {
            int v = StdRandom.uniform(V);
            int w = StdRandom.uniform(V);
			int length   = sap.length(v, w);
			int ancestor = sap.ancestor(v, w);
			StdOut.printf("for v = %d, w = %d:\tlength = %d, ancestor = %d\n", v, w, length, ancestor);
		}

        // create two sets of points, each of size < sqrt(V) / 2 
        // => chance of collision roughly 0.5
        TreeSet<Integer> x = new TreeSet<Integer>();
        TreeSet<Integer> y = new TreeSet<Integer>();
        double halfRootV = Math.sqrt(V) / 2;
        for (int i = 0; i < halfRootV; i++) {
            int v = StdRandom.uniform(V);
            x.add(new Integer(v));
            int w = StdRandom.uniform(V);
            y.add(new Integer(w));
        }
        // print and test the subsets
        StdOut.println("subset x: ");
        for (Integer i : x) {
            StdOut.print(i + " ");
        }
        StdOut.println("\nsubset y: ");
        for (Integer i : y) {
            StdOut.print(i + " ");
        }
        int length   = sap.length(x, y);
        int ancestor = sap.ancestor(x, y);
        StdOut.printf("\nfor x and y:\tlength = %d, ancestor = %d\n", length, ancestor);

    }

}
