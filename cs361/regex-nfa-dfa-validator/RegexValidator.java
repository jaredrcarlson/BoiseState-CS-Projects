import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
/**
 * Executes the following Major procedures:
 * - Parses a Regular Expression into Context-Free Grammar objects
 * - Constructs an NFA from the resulting CFG objects
 * - Converts the NFA to a DFA that accepts the same set of strings
 * - Checks input strings for the Language defined by the original
 *   Regular Expression, printing Yes or No for each
 *   
 *   @author jcarlson <jaredcarlson@u.boisestate.edu>
 *
 */
public class RegexValidator extends Debugable {
	private static File inputFile;
	private static FileReader fileReader;
	private static BufferedReader bufferedReader;
	private static String nextLine;
	private static String fileName;
	private static RE languageDefinition;
	private static NFAStateMachine nfaSM;
	private static DFAStateMachine dfaSM;
	private static REParser parser;
	private static ArrayList<REPathComponent> pathComponents;
	private static REPathComponent component, currComp;
	private static DFAStringChecker stringChecker;
	private static ArrayList<String> strings, accepted, rejected;
	private static double numStrings, numAccepted, numRejected;
	private static boolean outputRedirected = false; // Default
	
	public static void main(String[] args) {
		// Process Command Line Arguments
		processCommandLineArgs(args);
	
		try {
			// Open Input File and Read Regular Expression from Line 1
			inputFile = new File(fileName);
			fileReader = new FileReader(inputFile);
			bufferedReader = new BufferedReader(fileReader);
			nextLine = bufferedReader.readLine();
			languageDefinition = new RE(nextLine);
			parser = new REParser(languageDefinition);
			pathComponents = new ArrayList<REPathComponent>();	
			
			// Parse Regular Expression
			writeMjr("Parse RegExpr","\nRegular Expression: " + languageDefinition.toString() + "\n");
			while(parser.more()) {
				component = parser.getNextPathComponent();
				if(component != null) {
					pathComponents.add(component);
					writeMnr("\nParsed Path Component: " + component.toShortString() + "\n");
				}
			}
			
			// Print Parsing Results
			newSB();
			for(int i=0; i < pathComponents.size(); i++) {
				currComp = pathComponents.get(i);
				appSB("\nPath Component Expr: " + currComp.toShortString());
				appSB("\nPath Component (" + (i + 1) + ") : " + currComp.toString() + "\n");
			}
			writeMjr("Parse Results","\nPath Components Parsed:\n" + getSB());
			
			// Build NFA
			if(debugLevel.equals(L3) || debugLevel.equals(L5)) {
				writeMjr("Build NFA","");
			}
			nfaSM = NFABuilder.build(pathComponents);
			
			// Print NFA Build Results
			writeMjr("NFA Build Results","\nNFA State Machine\n" + nfaSM.toString());
						
			// Convert NFA to DFA and Print Conversion Results
			if(debugLevel.equals(L3) || debugLevel.equals(L5)) {
				writeMjr("Convert NFA --> DFA","");
			}
			dfaSM = NFAtoDFAConverter.convert(nfaSM);
			
			// Print DFA Build Results
			writeMjr("NFA --> DFA Results","\nDFA State Machine\n" + dfaSM.toString());
						
			// Check Input Strings
			stringChecker = new DFAStringChecker(dfaSM);
			strings = new ArrayList<String>();
			accepted = new ArrayList<String>();
			rejected = new ArrayList<String>();
			while ((nextLine = bufferedReader.readLine()) != null) {
				strings.add(nextLine);
	        }
			
			// Check Each String Printing each Result
			if(debugLevel.equals(L3) || debugLevel.equals(L5)) {
				System.out.println(bldHdr("Input String Check"));
			}
			for(String s : strings) {
				if(stringChecker.accepts(s)) {
					writeReq("yes");
					writeStrChk("<| " + s + " |> --> Accepted");
					accepted.add(s);
				}
				else {
					writeReq("no");
					writeStrChk("<| " + s + " |> --> Rejected");
					rejected.add(s);
				}
			}
			
			// Close Input File
			fileReader.close();
			
		} catch (IOException e) {
			writeErr("Unable to find/read input file: " + fileName);
			System.exit(1);
		}
		
		// Print Final Statistics
		writeStatistics();
		
		// Print Redirection Status Message
		if(outputRedirected) {
			newSB();
			appSB("<Debug Level 6>\n");
			appSB("Standard Out/Err have been redirected to:\n");
			appSB("StdOut --> ./debug.stdout.log\n");
			appSB("StdErr --> ./debug.stderr.log\n");
			stdout_toScreen();
			System.out.println(getSB());
		}
	}
	
	/**
	 * Prints final Statistics for number of input strings checked, Accepted, and Rejected
	 * Also prints both of the lists of Accepted and Rejected strings
	 */
	private static void writeStatistics() {
		if(debugLevel.gt(L0)) {
			numStrings = strings.size();
			numAccepted = accepted.size();
			numRejected = rejected.size();
			System.out.println(bldHdr("Statistics"));
			newSB();
			appSB("\n  Language Definition: " + languageDefinition.toString());
			appSB("\n\n  -----<| Accepted |>-----");
			for(String s : accepted) {
				appSB("\n  " + s);
			}
			appSB("\n\n  -----<| Rejected |>-----");
			for(String s : rejected) {
				appSB("\n  " + s);
			}
			appSB("\n\n     Total: " + wholeNum.format(numStrings));
			appSB("\n  Accepted: " + wholeNum.format(numAccepted) + " [ " + wholeNum.format((numAccepted/numStrings)*100) + "% ]");
			appSB("\n  Rejected: " + wholeNum.format(numRejected) + " [ " + wholeNum.format((numRejected/numStrings)*100) + "% ]\n");
			System.out.println(getSB());
		}
	}
	
	/**
	 * Processing Command-Line Arguments
	 * @param args The arguments to be processed
	 */
	private static void processCommandLineArgs(String[] args) {
		if(args.length < 1 || args.length > 2) {
			writeErr("Error! - Invalid number of arguments");
			printUsage();
			System.exit(1);
		}
		else {
			fileName = args[0];
			if(args.length == 1) {
				setDebug(DEFAULT_DEBUG_LEVEL);
				debugDelaySeconds = DEFAULT_DEBUG_DELAY_SECONDS;
				debugIndent = DEBUG_PARSING_BUILDING_INDENT_BASE;
			}
			else {
				setDebug(Debug.parseLevel(args[1]));
				if(debugLevel.equals(L6)) {
					outputRedirected = true;
					setDebug(L3);
					try {
						stdout_toFile(DEBUG_STDOUT_FILENAME);
					} catch (FileNotFoundException e) {
						writeErr("Error! - Unable to Create/Modify debug file: " + DEBUG_STDOUT_FILENAME);
						e.printStackTrace();
					}
					try {
						stderr_toFile(DEBUG_STDERR_FILENAME);
					} catch (FileNotFoundException e) {
						writeErr("Error! - Unable to Create/Modify debug file: " + DEBUG_STDERR_FILENAME);
						e.printStackTrace();
					}
				}
			}
		}	
	}
	
	/**
	 * Displays Detailed Program Usage Information
	 */
	private static void printUsage() {
		newSB("\n-------------------------------<| Program Usage |>-------------------------------\n");
		appSB("\njava RegexValidator <input filename> [debug level]\n");
		appSB("\n  <input filename>\n");
		appSB("  - contains a Regular Expresion on Line 1 with a series of \"Test Strings\" on subsequent lines\n\n");
		appSB("  [debug level] is one of the following:\n");
		appSB("  [0] --> Disabled (Default)\n");
		appSB("  [1] --> Show Only Final Statistics - String Check Results (PASS/FAIL)\n");
		appSB("  [2] --> [1] + Major Procedure Start and Final Results\n");
		appSB("  [3] --> [2] + Minor Procedure Activity\n");
		appSB("  [4] --> [2] with a Delay between Procedures\n");
		appSB("  [5] --> [3] with a Delay between Procedures\n");
		appSB("  [6] --> [3] with output redirection --> (\"debug.stdout.log\" | \"debug.stderr.log\")\n");
		appSB("\nExample: Display only Major Process Results\n");
		appSB("java RegexValidator inputFile.txt 1\n");
		appSB("\nExample: Redirect Full Debug Report to (stdout/stderr) log files\n");
		appSB("java RegexValidator inputFile.txt 6\n");
		System.out.println(getSB());
	}
	
	

}
