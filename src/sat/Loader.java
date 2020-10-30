package sat;

import java.io.*;
import java.util.*;


public class Loader {
    public static Graph load(String path) throws Exception{
        File file = new File(path);
        Scanner sc = new Scanner(file);
        while (sc.next().charAt(0) == 'c') sc.nextLine();
        sc.next();
        int n_v = sc.nextInt(); //number of variables
        int n_c = sc.nextInt();//number of clause
        ArrayList<ArrayList<Integer>> edges = new ArrayList<>();
        for (int i = 0; i < n_c; i++) {
            int literalNumber = sc.nextInt();
            ArrayList<Integer> clause = new ArrayList<>();
            while (literalNumber != 0 && sc.hasNext()) {
                clause.add(literalNumber);
                literalNumber = sc.nextInt();
            }
            if (clause.size() == 1) {
                ArrayList<Integer> edge = new ArrayList<>();
                edge.add(-clause.get(0));
                edge.add(clause.get(0));
                edges.add(edge);
            } else if (clause.size() == 2){
                ArrayList<Integer> edge1 = new ArrayList<>();
                ArrayList<Integer> edge2 = new ArrayList<>();
                edge1.add(-clause.get(0)); edge1.add(clause.get(1));
                edge2.add(-clause.get(1)); edge2.add(clause.get(0));
                edges.add(edge1); edges.add(edge2);
            } else {
                System.out.println("Number of literals inside a clause exceeds 2!!");
                return null;
            }
        }
        return new Graph(n_v, edges);
    }
    public static randomized load_rand(String path) {
        return new randomized();
    }
}
