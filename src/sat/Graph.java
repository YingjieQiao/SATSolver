package sat;

import java.util.*;


public class Graph {
    HashMap<Node, HashSet<Node>> adjacencyLists;
    HashMap<Integer, Node> nodes;
    public int index;  // index used in Tarjan's algorithm
    public Stack<Node> stack = new Stack<>();
    public List<List<Node>> SCCs = new ArrayList<>();


    Graph(int n_v, ArrayList<ArrayList<Integer>> edges) {
        adjacencyLists = new HashMap<>();
        nodes = new HashMap<>();

        for (int id = 1; id <= n_v; id++) {
            Node vertex = new Node();
            vertex.id = id;
            adjacencyLists.put(vertex, new HashSet<>());
            nodes.put(id, vertex);
        }
        for (int id = -1; id >= -n_v; id--){
            Node vertex = new Node();
            vertex.id = id;
            adjacencyLists.put(vertex, new HashSet<>());
            nodes.put(id, vertex);
        }

        // add all edges
        for(List<Integer> edge: edges) {
            Node from = nodes.get(edge.get(0));
            Node to = nodes.get(edge.get(1));

            Set<Node> set = adjacencyLists.get(from);
            set.add(to);
        }
    }


    @Override
    public String toString() {
        return adjacencyLists.toString();
    }

}
