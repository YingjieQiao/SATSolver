package sat;

import java.util.Iterator;

import immutable.EmptyImList;
import immutable.ImList;
import sat.env.Environment;
import sat.formula.Clause;
import sat.formula.Formula;
import sat.formula.Literal;
import sat.formula.PosLiteral;


/**
 * A simple DPLL SAT solver. See http://en.wikipedia.org/wiki/DPLL_algorithm
 */
public class SATSolver {
    /**
     * Solve the problem using a simple version of DPLL with backtracking and
     * unit propagation. The returned environment binds literals of class
     * bool.Variable rather than the special literals used in clausification of
     * class clausal.Literal, so that clients can more readily use it.
     *
     * @return an environment for which the problem evaluates to Bool.TRUE, or
     *         null if no such environment exists.
     */
    public static Environment solve(Formula formula) {
        ImList<Clause> res = formula.getClauses();
        Environment res_env = new Environment();
        return solve(res, res_env);

    }

    /**
     * Takes a partial assignment of variables to values, and recursively
     * searches for a complete satisfying assignment.
     *
     * @param clauses
     *            formula in conjunctive normal form
     * @param env
     *            assignment of some or all variables in clauses to true or
     *            false values.
     * @return an environment for which all the clauses evaluate to Bool.TRUE,
     *         or null if no such environment exists.
     */
    private static Environment solve(ImList<Clause> clauses, Environment env) {
        if (clauses.isEmpty()){
            return env;
        }

        Iterator<Clause> check_empty = clauses.iterator();
        while (check_empty.hasNext()){
            if (check_empty.next().isEmpty()){
                return null;
            }
        }

        int min = 9999;
        Clause min_x = new Clause();
        for (Clause x : clauses){
            if (x.size() < min){
                min = x.size();
                min_x = x;
            }
        }

        Environment env_true = new Environment();
        Environment res = new Environment();

        if (min_x.isUnit()){
            if (min_x.chooseLiteral() instanceof PosLiteral){
                env_true = env.putTrue(min_x.chooseLiteral().getVariable());
                ImList<Clause> sub = new EmptyImList<Clause>();
                sub = substitute(clauses, min_x.chooseLiteral());
                res = solve(sub, env_true);
            }
            else{
                env_true = env.putFalse(min_x.chooseLiteral().getVariable());
                ImList<Clause> sub = new EmptyImList<Clause>();
                sub = substitute(clauses, min_x.chooseLiteral());
                res = solve(sub, env_true);
            }


        }else{
            Literal lit = min_x.chooseLiteral();

            if (lit instanceof PosLiteral){
                env_true = env.putTrue(lit.getVariable());
                ImList<Clause> sub = new EmptyImList<Clause>();
                sub = substitute(clauses, lit);
                res = solve(sub, env_true);
            }else{
                env_true = env.putFalse(lit.getVariable());
                ImList<Clause> sub = new EmptyImList<Clause>();
                sub = substitute(clauses,lit);
                res = solve(sub, env_true);
            }
            if (res == null){
                Literal new_lit = lit.getNegation();
                if (new_lit instanceof PosLiteral){
                    env_true = env.putTrue(lit.getVariable());
                    ImList<Clause> sub = new EmptyImList<Clause>();
                    sub = substitute(clauses, new_lit);
                    res = solve(sub, env_true);
                }else{
                    env_true = env.putFalse(lit.getVariable());
                    ImList<Clause> sub = new EmptyImList<Clause>();
                    sub = substitute(clauses, new_lit);
                    res = solve(sub, env_true);
                }


            }


        }return res;





    }

    /**
     * given a clause list and literal, produce a new list resulting from
     * setting that literal to true
     *
     * @param clauses
     *            , a list of clauses
     * @param l
     *            , a literal to set to true
     * @return a new list of clauses resulting from setting l to true
     */
    private static ImList<Clause> substitute(ImList<Clause> clauses,
                                             Literal l) {
        ImList<Clause> res = new EmptyImList<Clause>();
        Clause clause = new Clause();

        for (Clause c : clauses) {
            clause = c.reduce(l);

            if (clause != null){
                res = res.add(clause);
            }
        }
        return res;
    }


}
