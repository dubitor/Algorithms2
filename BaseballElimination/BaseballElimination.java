import edu.princeton.cs.StdOut;
import edu.princeton.cs.In;
import edu.princeton.cs.FlowNetwork;
import edu.princeton.cs.FlowEdge;
import edu.princeton.cs.FordFulkerson;
import java.util.Map;
import java.util.HashMap;

public class BaseballElimination {
    private final int n; // number of teams
    private final int v; // number of vertices in the flow network
    private final Map<String, int[]> teams; // int[] format: { id, wins, losses, rem }
    private final int[][] toPlay; // toPlay[i][j] = num of games remaining between i & j
    private final int INFINITY = Integer.MAX_VALUE;
    // consider Map<String, int> team name: team id -- not bidirectional
    // and int[] wins, losses, rem

	public BaseballElimination(String filename) {                   // create a baseball division from given filename in format specified in spec

        In in = new In(filename);
        n = in.readInt();
        v = numOfVertices(n);
        teams = new HashMap<String, int[]>(n);
        toPlay = new int[n][n];

        for (int i = 0; i < n; i++) {

            String name = in.readString();
            int[] arr = new int[4];
            arr[0] = i;
            for (int j = 1; j < arr.length(); j++) {
                arr[j] = in.readInt();
            }
            teams.put(name, arr);

            for (int j = 0; j < n; j++) {
                if (j == i) {
                    continue;
                }
                else {
                    toPlay[i][j] = in.readInt();
                }
            }
        }
    }

    private int numOfVertices(int n) {
        int nChoose2 = n * (n - 1) / 2; // num of game vertices
        return nChoose2 + n + 2;
    }


	public int numberOfTeams() {                       // number of teams
        return n;
    }

	public Iterable<String> teams() {                                // all teams
        return teams.keySet();
    }

	public int wins(String team) {        // number of wins for given team
        checkTeam(team);
        int[] arr = teams.get(team);
        return arr[1];
    }
	public int losses(String team) {                    // number of losses for given team
        checkTeam(team);
        int[] arr = teams.get(team);
        return arr[2];
    }
	public int remaining(String team) {                 // number of remaining games for given team
        checkTeam(team);
        int[] arr = teams.get(team);
        return arr[3];
    }
	public int against(String team1, String team2) {    // number of remaining games between team1 and team2
        checkTeam(team1);
        checkTeam(team2);
        return toPlay[getId(team1), getId(team2);
    }
        
	public boolean isEliminated(String team)              // is given team eliminated?

	public Iterable<String> certificateOfElimination(String team)  // subset R of teams that eliminates given team; null if not eliminated

    private void checkTeam(String team) {
        if (!teams.containsKey(team)) {
            throw new IllegalArgumentException("no such team");
        }
    }

    private void getId(String team) {
        int[] arr = teams.get(team);
        return arr[0];
    }

    private void elimination(String team) {

        int id = getId(team);
        int source = 0; target = v - 1;

        FlowNetwork fn = new FlowNetwork(v);

        // add source --> game edges, excluding games involving current team
        int currentVertex = 1;
        for (int i = 0; i < n; i++) {
            if (i == id) {
                continue;
            }
            for (int j = i + 1; j < n; j++) {
                if (j == id) {
                    continue;
                }
                FlowEdge edge = new FlowEdge(source, currentVertex++, toPlay[i][j]);
                fn.addEdge(edge);
            }
        } 
        
        // add game-team edges
        currentVertex = 1;
        for (int i = 0; i < n; i++) {
            if (i == id) {
                continue;
            }
            for (int j = i + 1; j < n; j++) {
                if (j == id) {
                    continue;
                }
                FlowEdge edge = new FlowEdge(currentVertex++, i, INFINITY);
                fn.addEdge(edge);
            }
        }

        // add team-t edges
        assert currentVertex = v - 1;
        currentVertex = currentVertex - (n - 1) // go back to first team vertex
        for (int i = 0; i < n - 2; i++) {
            FlowEdge edge = new FlowEdge(--currentVertex, target


    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}
