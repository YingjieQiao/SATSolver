package sat;

/*
This algorithm is a deterministic approach to solve the SAT problem,

based on the analysis of the SCC (Strong Connected Components) in the implication graph.

 */

import java.io.IOException;
import java.util.*;

public class deterministic {

    public static void tarjan(Graph g, Set<Node> nodes) {
        for(Node node: nodes) {
            if(node.index != 0) {
                return;
            } else {
                _tarjan(g, node);
            }
        }
    }


    private static void _tarjan(Graph g, Node node) {
        node.index = g.index;
        node.lowlink = g.index;
        g.index += 1;
        g.stack.push(node);
        node.inStack = true;

        for(Node next: g.adjacencyLists.get(node)) {
            if(next.index == 0) {
                _tarjan(g, next);
                node.lowlink = Math.min(node.lowlink, next.lowlink);
            } else if(node.inStack) {
                node.lowlink = Math.min(node.lowlink, next.index);
            }
        }

        if(node.lowlink == node.index) {
            List<Node> SCC = new ArrayList<Node>();
            Node poppedNode = null;
            while(node != poppedNode) {
                poppedNode = g.stack.pop();
                poppedNode.inStack = false;
                SCC.add(poppedNode);
            }
            g.SCCs.add(SCC);
        }
    }


    public static boolean satisfiable(Graph g) {
        for(List<Node> SCC: g.SCCs) {
            HashMap<Integer, Boolean> variables = new HashMap<>();
            for(Node n: SCC) {
                int varId = Math.abs(n.id);
                if(variables.containsKey(varId)) {
                    return false;
                } else {
                    variables.put(varId, true);
                }
            }
        }
        return true;
    }


    public static Map<Integer, Boolean> solve(Graph g) {
        HashMap<Integer, Boolean> solution = new HashMap<>();

        for(List<Node> SCC: g.SCCs) {
            for(Node n: SCC) {
                if (solution.containsKey(Math.abs(n.id))) continue;
                int id = n.id;
                if(id > 0) {
                    solution.put(id, true);
                } else {
                    solution.put(-id, false);
                }
            }
        }
        return solution;
    }



    public static void main(String[] args) {
        String path = args[0];
        try {
            Graph g = Loader.load(path);
            assert g != null;
            g.index = 1;
            Set<Node> nodes = g.adjacencyLists.keySet();

            // Tarjan's algorithm
            tarjan(g, nodes);

            // check if satisfiable
            if(satisfiable(g)) {
                System.out.println("FORMULA SATISFIABLE");

                // get and print a solution as required
                Map<Integer, Boolean> solution = solve(g);
                for (int i = 1; i <= solution.size(); i++) {
                    Boolean value = solution.get(i);
                    if (value) {
                        System.out.print('1');
                    } else {
                        System.out.print('0');
                    }
                    System.out.print(' ');
                }
                System.out.println("");
            } else {
                System.out.println("FORMULA UNSATISFIABLE");
            }

        } catch (InvalidInputException e) {
            System.out.println("InvalidInputException");
        } catch (IOException e) {
            System.out.println("Unexpected IOException");
        }

    }

}
