package sat;

import java.util.*;


public class Graph {
    HashMap<Node, HashSet<Node>> adjacencyLists;
    HashMap<Integer, Node> nodes;
    public int index;  // index used in Tarjan's algorithm
    public Stack<Node> stack = new Stack<>();
    public List<List<Node>> SCCs = new ArrayList<>();


    Graph(List<Integer> vertices, List<List<Integer>> edges) {
        adjacencyLists = new HashMap<>();
        nodes = new HashMap<>();

        for (Integer id: vertices) {
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
