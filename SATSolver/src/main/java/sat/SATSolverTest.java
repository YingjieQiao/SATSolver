package sat;

/*
import static org.junit.Assert.*;

import org.junit.Test;
*/
import com.sun.org.apache.xpath.internal.functions.FuncStartsWith;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import sat.env.*;
import sat.formula.*;


public class SATSolverTest {
    Literal a = PosLiteral.make("a");
    Literal b = PosLiteral.make("b");
    Literal c = PosLiteral.make("c");
    Literal na = a.getNegation();
    Literal nb = b.getNegation();
    Literal nc = c.getNegation();


    public static void main(String[] args) throws IOException {

        /*
        File f = null;
        if (args.length > 0) {
            f = new File(args[0]);
        }
        */


        FileReader file = new FileReader("C:\\Users\\Jing Xiong\\AndroidStudioProjects\\Wk08\\code2d\\src\\main\\java\\sat\\sampleCNF\\s8Sat.cnf");
        BufferedReader br = new BufferedReader(file);




        BufferedWriter wr = new BufferedWriter(new FileWriter(
                "C:\\Users\\Jing Xiong\\AndroidStudioProjects\\Wk08\\code2d\\src\\main\\java\\sat\\BoolAssignment.txt"));


        String line;
        String Clause = "";
        Formula f2 = new Formula();
        int numOfVar = 0;

        while((line = br.readLine()) != null){
            if(line.length() > 0) {
                if (!(line.startsWith("p")) && !(line.startsWith("c"))) {
                    line = line.trim();
                    Clause c = new Clause();
                    String[] s = line.split(" ");
                    for (int i = 0; i < s.length - 1; i++) {
                        if (s[i].contains("-")) {
                            String next = s[i].substring(1, s[i].length());
                            c = c.add(NegLiteral.make(next));
                        } else {
                            if (s[i] != " " && s[i] != null) {
                                String next = s[i];
                                c = c.add(PosLiteral.make(next));
                            }
                        }
                    }
                    f2 = f2.addClause(c);
                }
                else if (line.startsWith("p")) {
                    String[] s = line.split(" ");
                    numOfVar = Integer.parseInt(s[2]);
                }
            }
        }

        System.out.println("SAT solver starts!!!");
        long started = System.nanoTime();
        Environment res = SATSolver.solve(f2);
        long time = System.nanoTime();
        long timeTaken= time - started;
        System.out.println("Time:" + timeTaken/1000000.0 + "ms");

        if (res == null){
            System.out.println("not satisfiable");
        }else{
            System.out.println("satisfiable");
            for (int i = 1; i <= numOfVar; i++){
                Bool ans = res.get(new Variable(Integer.toString(i)));
                if (ans == Bool.TRUE){
                    String num = Integer.toString(i);
                    wr.write( num + ":" + "TRUE");
                    wr.newLine();
                }
                else{
                    String num = Integer.toString(i);
                    wr.write( num + ":" + "FALSE");
                    wr.newLine();
                }
            }
            wr.close();
        }
    }




    public void testSATSolver1(){
        // (a v b)
        Environment e = SATSolver.solve(makeFm(makeCl(a,b))	);
/*
    	assertTrue( "one of the literals should be set to true",
    			Bool.TRUE == e.get(a.getVariable())
    			|| Bool.TRUE == e.get(b.getVariable())	);

*/
    }


    public void testSATSolver2(){
        // (~a)
        Environment e = SATSolver.solve(makeFm(makeCl(na)));
/*
    	assertEquals( Bool.FALSE, e.get(na.getVariable()));
*/
    }

    private static Formula makeFm(Clause... e) {
        Formula f = new Formula();
        for (Clause c : e) {
            f = f.addClause(c);
        }
        return f;
    }

    private static Clause makeCl(Literal... e) {
        Clause c = new Clause();
        for (Literal l : e) {
            c = c.add(l);
        }
        return c;
    }



}