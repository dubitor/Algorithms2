import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FordFulkerson;
import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.ArrayList;

public class BaseballElimination {
    private final int n; // number of teams
    private final int nChoose2;
    private final int v; // number of vertices in the flow network
    private final Map<String, int[]> teams; // int[] format: { id, wins, losses, rem }
    private final String[] teamNames; // team names indexed by team id
    private final Map<String, ArrayList<String>> certificates; // team : certificate of elimination
    private final int[][] toPlay; // toPlay[i][j] = num of games remaining between i & j
    private final double INFINITY = Double.POSITIVE_INFINITY;
    // consider Map<String, int> team name: team id -- not bidirectional
    // and int[] wins, losses, rem

	public BaseballElimination(String filename) {                   // create a baseball division from given filename in format specified in spec

        In in = new In(filename);
        n = in.readInt();
        nChoose2 = n * (n - 1) / 2;
        v = nChoose2 + n + 2; // each game + each team + source and sink
        teams = new HashMap<String, int[]>(n);
        certificates = new TreeMap<String, ArrayList<String>>();
        toPlay = new int[n][n];
        teamNames = new String[n];

        for (int i = 0; i < n; i++) {

            String name = in.readString();
            teamNames[i] = name;
            int[] arr = new int[4];
            arr[0] = i;
            for (int j = 1; j < arr.length; j++) {
                arr[j] = in.readInt();
            }
            teams.put(name, arr);

            for (int j = 0; j < n; j++) {
                toPlay[i][j] = in.readInt();
            }
        }
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
        return toPlay[getId(team1)][getId(team2)];
    }
        
	public boolean isEliminated(String team) {              // is given team eliminated?
        checkTeam(team);
        return (certificateOfElimination(team) != null);
    }

	public Iterable<String> certificateOfElimination(String team) {  // subset R of teams that eliminates given team; null if not eliminated

        if (certificates.containsKey(team)) {
            return certificates.get(team);
        }

        certificates.put(team, null); // assume not eliminated

        ArrayList<String> cert = new ArrayList<String>();

        // check trivial elimination
        int maxPossWins = wins(team) + remaining(team);
        for (String name : teamNames) {
            if (wins(name) > maxPossWins) {
                cert.add(name);
            }
        }
        if (cert.size() > 0) {
            certificates.put(team, cert);
            return cert;
        }

        // check non-trivial elimination
        else {
            FlowNetwork fn = createFlowNetwork(team);
            elimination(team, fn);
            
            return certificates.get(team);
        }

    }

    private void checkTeam(String team) {
        if (!teams.containsKey(team)) {
            throw new IllegalArgumentException("no such team");
        }
    }

    private int getId(String team) {
        int[] arr = teams.get(team);
        return arr[0];
    }

    private FlowNetwork createFlowNetwork(String team) {

        int teamId = getId(team);
        int source = 0, target = nChoose2 + n + 1;
        int firstGameVertex = 1, firstTeamVertex = nChoose2 + 1;

        FlowNetwork fn = new FlowNetwork(v);

        for (int id = 0; id < teamNames.length; id++) {
            String currentTeam = teamNames[id];
            if (currentTeam.equals(team)) {
                continue;
            }
            for (int otherId = id + 1; otherId < n; otherId++) {
                if (otherId == teamId) {
                    continue;
                }
                int currentVertex = gameVertex(id, otherId);
                FlowEdge edge = new FlowEdge(source, currentVertex, toPlay[id][otherId]);
                fn.addEdge(edge);
                edge = new FlowEdge(currentVertex, teamVertex(id), INFINITY);
                fn.addEdge(edge);
                edge = new FlowEdge(currentVertex, teamVertex(otherId), INFINITY);
                fn.addEdge(edge);
            }

        }


        // add team-t edges
        int maxPossWins = wins(team) + remaining(team);
        for (int id = 0; id < n; id++) {
            if (id == teamId) {
                continue;
            }
            FlowEdge edge = new FlowEdge(teamVertex(id), target, maxPossWins - wins(id));
            fn.addEdge(edge);
        }

        return fn;
    }


    // the vertex number representing a game between two teams
    private int gameVertex(int id, int otherId) {
        int v = nChoose2; // set v to the last game vertex
        int x = 1;
        while (id < n - 2) { // set v to the last vertex that refers to an id game
            id++;
            v -= x;
            x++;
        }
        int y = n - 1;
        while (y > otherId) { // decrement v until reach id-otherId game
            v--;
            y--;
        }
        return v;
    }

    // adds to certificates team : certificate if eliminated
    private void elimination(String team, FlowNetwork fn) {
        FordFulkerson ff = new FordFulkerson(fn, 0, nChoose2 + n + 1);
        for (FlowEdge e : fn.adj(0)) {
            if (e.flow() != e.capacity()) { // it's been eliminated

                ArrayList<String> cert = new ArrayList<String>();
                for (String name : teamNames) {
                    int vertex = teamVertex(getId(name));
                    if (ff.inCut(vertex)) {
                        cert.add(name);
                    }
                }
                certificates.put(team, cert);
                return;
            }
        }
    }




    // vertex number corresponding to team id
    private int teamVertex(int id) {
        return nChoose2 + 1 + id;
    }
        

    private int wins(int teamId) {
        return wins(teamNames[teamId]);
    }

    private int rem(int teamId) {
        return remaining(teamNames[teamId]);
    }


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
