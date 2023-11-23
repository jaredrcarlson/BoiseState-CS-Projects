import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
 
/**
 * The Class Interpreter - Makes use of parser and environment objects to
 * scan, parse, and interpret a program.
 * 
 * A program can be entered as multiple statements, but as a SINGLE command line argument:
 * java Interpreter "<statement; ...; statement; statement>"
 * java Interpreter x = 5; y = 6; wr x + y
 * 
 * OR
 * 
 * The -f command line argument option can specify a program file that
 * contains multiple statements:
 * java Interpreter -f <program file name>
 * java Interpreter -f MyProgramFile.txt
 * 
 * @author jcarlson
 * 
 */
public class Interpreter {
	private static boolean debugMode = false;
	private Environment env;
	private Parser parser;
	
    
	public Interpreter(Environment env) {
		this.env = env;
	}
	
	public Interpreter(Environment env, Boolean debugMode) {
		this(env);
		Interpreter.debugMode = debugMode;
	}
	
	/**
	 * Method used to perform the interpretation
	 * 
	 * @param program - The program to interpret
	 */
	public void interpret(String program) {
		parser = new Parser(debugMode);
		try {
			parser.parse(program).eval(env);
		} catch (SyntaxException e) {
			System.out.println("Interpreter - Syntax Error!");
			e.printStackTrace();
		} catch (EvalException e) {
			System.out.println("Interpreter - Evaluation Error!");
			e.printStackTrace();
		}
	}
	
	/**
     * The main method - Process command line arguments
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	boolean debug = false;
  
    	String program = processCLA(args);
    	Environment env = new Environment();
    	Interpreter itptr = new Interpreter(env,debug);
    	itptr.interpret(program);	
	}
    
    /**
     * Processes Command Line Arguments
     * 
     * @param args String array of program statements to be processed
     * 	           OR args[0] = -f AND args[1] = program file
     * 
     * @return Full program String
     */
    private static String processCLA(String[] args) {
    	StringBuilder strBldr = new StringBuilder();
    	if(args[0].equals("-f")) {
			try {
				Scanner argsScanner = new Scanner(new File(args[1]));
				while(argsScanner.hasNext()) {
					strBldr.append(argsScanner.next() + " ");
				}
				argsScanner.close();
			} catch (FileNotFoundException e) {
				System.out.println("Unable to open program file.");
				e.printStackTrace();
			}
		}
		else {
			return args[0];
		}
    	return strBldr.toString();
    }
}
