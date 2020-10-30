package sat;

/*
This algorithm is a randomized approach to solve the SAT problem,

 */

import java.util.*;

public class randomized {
    public List<List<Integer>> clauses;
    public int numOfVars;
    public Map<Integer, Boolean> formula = new HashMap<Integer, Boolean>(); // for use in solving

    randomized() {

    }

    randomized(List<List<Integer>> clauses, int numOfVars) {
        this.clauses = clauses;
        this.numOfVars = numOfVars;
    }

    private boolean evalClause(List<Integer> clause) {
        return evalLiteral(clause.get(0)) || evalLiteral(clause.get(1));
    }

    private boolean evalLiteral(int a) {
        return ((a > 0) ? formula.get(a) : !formula.get(-a));
    }

    public boolean solve() {
        Random rand = new Random();
        // initialize the formulas
        for(int i = 1; i <= numOfVars; i++) {
            formula.put(i, false);
        }
        // try to solve
        for(int i = 0; i < (int) Math.pow(numOfVars, 2)*2000; i++) {
            // look for an unsatisfied clause
            List<Integer> clause = null;
            for(List<Integer> clauseFor : clauses) {
                if(!evalClause(clauseFor)) {
                    // found an unsatisfied one
                    clause = clauseFor;
                    break;
                }
            }

            // check if it found one or just ran out of clauses
            if (clause == null) {
                return true;
            }

            // flip the coin
            int index;
            if (rand.nextInt() <= 0) {
                index = 0;
            } else {
                index = 1;
            }
            int var = Math.abs(clause.get(index));
            // flip the value of the var
            Boolean value = formula.get(var);
            formula.put(var, !value);
        }
        return false;
    }

    public static void main(String[] args) {
        String path = args[0];
        randomized p = Loader.load_rand(path);

        if(p.solve()) {
            System.out.println("FORMULA SATISFIABLE");
            // print solution
            for(int i = 1; i <= p.formula.size(); i++) {
                Boolean value = p.formula.get(i);
                if(value) {
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

    }
}
