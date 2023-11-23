import java.io.BufferedOutputStream;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *   The Debugable class is used to make debug integration more fluid and helps to produce
 *   code that is easier to read and maintain. Debugging options are defined below:
 *   -----------------------------------------------------------------------------------
 *   ------------------------------ DEBUG Levels ---------------------------------------
 *   -----------------------------------------------------------------------------------
 *   LEVEL_0 --> Disabled (Default)
 *   LEVEL_1 --> Show Only Final Statistics - Input String (PASS/FAIL)
 *   LEVEL_2 --> LEVEL_1 + Major Procedure Start and Final Results
 *   LEVEL_3 --> LEVEL_2 + Minor Procedure Activity
 *   LEVEL_4 --> LEVEL_2 with a Delay between Procedures
 *   LEVEL_5 --> LEVEL_3 with a Delay between Procedures
 *   LEVEL_6 --> LEVEL_3 with output redirection --> ("debug.stdout.log" | "debug.stderr.log")
 *   -----------------------------------------------------------------------------------
 *     
 *      
 *   The Context-Free Grammar used for this project is also defined within this class and
 *   is defined as follows:
 *   -----------------------------------------------------------------------------------
 *   ------------------------------ RE Grammar -----------------------------------------
 *   -----------------------------------------------------------------------------------
 *   EXPR_SINGLE:			<REExpr> --> <RETerm>
 *   EXPR_COMPOUND:			<REExpr> --> <RETerm> | <REExpr>
 *   
 *   TERM_SYMBOL:			<RETerm> --> <RESymb>
 *   TERM_GROUP:			<ReTerm> --> <REGroup>
 *   
 *   SYMB_A:				<RESymb> --> 'a'
 *   SYMB_B:				<RESymb> --> 'b'
 *   SYMB_E:				<RESymb> --> 'e'
 *   
 *   GROUP_SINGLE_EXPR:		<REGroup> --> '(' <REExpr> ')'
 *   GROUP_MULT_EXPR:		<REGroup> --> '(' <REExprMult> ')'
 *   GROUP_MULT_SYMBOL:		<REGroup> --> <RESymbMult>
 *   
 *   EXPR_MULT:				<REExprMult> --> <REExpr> { <REExpr> }
 *   SYMB_MULT:				<RESymbMult> --> <RESymb> { <RESymb> }
 *	 -----------------------------------------------------------------------------------
 *
 * @author jcarlson <jaredcarlson@u.boisestate.edu>
 * 
 */
public class Debugable {
	// Used to make coding easier for the developer
	static final Debug L0 = Debug.LEVEL_0;
	static final Debug L1 = Debug.LEVEL_1;
	static final Debug L2 = Debug.LEVEL_2;
	static final Debug L3 = Debug.LEVEL_3;
	static final Debug L4 = Debug.LEVEL_4;
	static final Debug L5 = Debug.LEVEL_5;
	static final Debug L6 = Debug.LEVEL_6;
	
	// A SINGLE location for Debug Settings 
	static final Debug DEFAULT_DEBUG_LEVEL = L0;
	static final int DEFAULT_DEBUG_DELAY_SECONDS = 0;
	static final int DEBUG_DELAY_MAJOR_PROCEDURE = 3;
	static final int DEBUG_DELAY_MINOR_PROCEDURE = 2;
	static final int DEBUG_DELAY_PARSER = 1;
	static final int DEBUG_DELAY_BUILDER = 1;
	static final int DEBUG_DELAY_STRING_CHECKER = 1;
	static final int DEBUG_PARSING_BUILDING_INDENT_BASE = 4;
	static final int DEBUG_PARSING_BUILDING_INDENT_STEP = 3;
	static final String DEBUG_STDOUT_FILENAME = "debug.stdout.log";
	static final String DEBUG_STDERR_FILENAME = "debug.stderr.log";
	static final int DEBUG_MESSAGE_HEADER_WIDTH = 80;
	static final int STDOUT = 0;
	static final int STDERR = 1;
	
	// Used to produce more readable code
	static final RESymb SYMB_A = new RESymb('a');
	static final RESymb SYMB_B = new RESymb('b');
	static final RESymb SYMB_E = new RESymb('e');
	static final char CHAR_OR = '|';
	static final char CHAR_PO = '(';
	static final char CHAR_PC = ')';
	static final char CHAR_AK = '*';
	static final char CHAR_A = 'a';
	static final char CHAR_B = 'b';
	static final char CHAR_E = 'e';
	
	static Debug debugLevel;
	static int debugDelaySeconds, debugIndent;
	
	static StringBuilder strBld;
	static final String patternWholeNumber = "####";
	static final DecimalFormat wholeNum = new DecimalFormat(patternWholeNumber);
		
	static void setDebug(Debug level) {debugLevel = level;}
	
	static void newSB() {strBld = new StringBuilder();}
	static void newSB(String s) {strBld = new StringBuilder(s);}
	static void appSB(String s) {strBld.append(s);}
	static void appSB(int i) {strBld.append(i);}
	static String getSB() {return strBld.toString();}
	
	/**
	 * Prints Required Program Output
	 * @param message Output to be Printed
	 */
	static void writeReq(String message) {
		if(debugLevel.equals(L0)) {
			System.out.println(message);
		}
	}
	
	/**
	 * Prints All Closures for a List of NFA States
	 * @param header Heading (Title)
	 * @param states List of States to be Printed
	 */
	static void writeCls(String header, ArrayList<FAState> nfaStates) {
		if(debugLevel.gt(L1)) {
			System.out.println(bldHdr(header));
			newSB();
			ArrayList<FAState> closureStates;
			for(FAState nfaState : nfaStates) {
				closureStates = (ArrayList<FAState>)nfaState.getClosure().values();
				appSB("\n" + nfaState.toShortString());
				appSB("\nClosure: {");
				for(int i=0; i < closureStates.size(); i++) {
					if(i != 0) {
						appSB(",");
					}
					appSB(closureStates.get(i).getID());
				}
				appSB("}\n");
			}
			System.out.println(getSB());
		}
	}
	
	/**
	 * Prints a Major Procedure Message
	 * @param message Message to be Printed
	 */
	static void writeMjr(String header, String message) {
		if(debugLevel.gt(L1)) {
			System.out.println(bldHdr(header));
			System.out.println(message);
			if(debugLevel.equals(L4)) {delayMjr();}
		}
	}
	
	/**
	 * Prints a Minor Procedure Message
	 * @param message Message to be Printed
	 */
	static void writeMnr(String message) {
		if(debugLevel.gt(L2)) {
			System.out.println(message);
			if(debugLevel.equals(L5)) {delayMnr();}
		}
	}
	
	/**
	 * Builds a Header
	 * @param header Header to be Printed
	 */
	static String bldHdr(String header) {
		newSB("\n");
		for(int i=0; i < (DEBUG_MESSAGE_HEADER_WIDTH - (header.length() + 6))/2; i++) appSB("-");
		appSB("<| " + header + " |>");
		for(int i=0; i < (DEBUG_MESSAGE_HEADER_WIDTH - (header.length() + 6))/2; i++) appSB("-");
		return getSB();
	}
	
	/**
	 * Prints a message to Standard Error
	 * @param message Message to be Printed
	 */
	static void writeErr(String message) {
		System.err.println(message);
	}
	
	/**
	 * Prints a "Parser-Formatted" Message to show current parsing depth
	 * (used to more easily debug recursive parsing procedures)
	 * @param message Message to be Printed
	 */
	static void writePrs(String message) {
		if(debugLevel.equals(L3) || debugLevel.equals(L5)) {
			newSB();
			for(int i=0; i < debugIndent; i++) {
				appSB("-");
			}
			appSB("> ");
			appSB(message);
			System.out.println(getSB());
			if(debugLevel.equals(L5)) {delayPrs();}
		}
	}
	
	/**
	 * Prints a "Builder-Formatted" Message to show current Building depth
	 * (used to more easily debug recursive NFA/DFA State-Building procedures)
	 * @param message Message to be Printed
	 */
	static void writeBld(String message) {
		if(debugLevel.equals(L3) || debugLevel.equals(L5)) {
			newSB();
			for(int i=0; i < debugIndent; i++) {
				appSB("-");
			}
			appSB("> ");
			appSB(message);
			System.out.println(getSB());
			if(debugLevel.equals(L5)) {delayBld();}
		}
	}
	
	static void writeStrChk(String message) {
		if(debugLevel.equals(L3) || debugLevel.equals(L5)) {
			System.out.println(message);
			if(debugLevel.equals(L5)) {delayStrChk();}
		}
	}
	
	/**
	 * Pauses Program Execution after the completion of a Major Procedure
	 * (used to view Major Procedure Results during execution)
	 */
	static void delayMjr() {
		try { Thread.sleep(DEBUG_DELAY_MAJOR_PROCEDURE * 1000);}
		catch (InterruptedException e) {e.printStackTrace();}
	}
	
	/**
	 * Pauses Program Execution after the completion of a Minor Procedure
	 * (used to view Minor Procedure Results during execution)
	 */
	static void delayMnr() {
		try { Thread.sleep(DEBUG_DELAY_MINOR_PROCEDURE * 1000);}
		catch (InterruptedException e) {e.printStackTrace();}
	}
	
	/**
	 * Pauses Program Execution during Parsing routines
	 * (used to view parsing pattern and current parsing depth)
	 */
	static void delayPrs() {
		try { Thread.sleep(DEBUG_DELAY_PARSER * 1000);}
		catch (InterruptedException e) {e.printStackTrace();}
	}
	
	/**
	 * Pauses Program Execution during Building routines
	 * (used to view state building procedures and current building depth)
	 */
	static void delayBld() {
		try { Thread.sleep(DEBUG_DELAY_BUILDER * 1000);}
		catch (InterruptedException e) {e.printStackTrace();}
	}
	
	/**
	 * Pauses Program Execution for each input string checked
	 * (used to view the Accept/Reject result for each string)
	 */
	static void delayStrChk() {
		try { Thread.sleep(DEBUG_DELAY_STRING_CHECKER * 1000);}
		catch (InterruptedException e) {e.printStackTrace();}
	}
	
	// Handle the Indentation of Debug Messages for Recursive Parsing/Building Routines
	static void debugStepIn() { debugIndent += DEBUG_PARSING_BUILDING_INDENT_STEP; }
	static void debugBackOut() { debugIndent -= DEBUG_PARSING_BUILDING_INDENT_STEP; }
	static void debugIndentReset() { debugIndent = DEBUG_PARSING_BUILDING_INDENT_BASE; }
	
	/**
	 * Redirects Standard Output to file
	 * @param filename The filename to be redirected to
	 * @throws FileNotFoundException File Not Found Exception
	 */
	static void stdout_toFile(String filename) throws FileNotFoundException {
		System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(filename)),true));
	}
	
	/**
	 * Resets Standard Output to Default (On Screen Display)
	 * 
	 */
	static void stdout_toScreen() {
		System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
	}
	
	/**
	 * Redirects Standard Error to file
	 * @param filename The filename to be redirected to
	 * @throws FileNotFoundException File Not Found Exception
	 */
	static void stderr_toFile(String filename) throws FileNotFoundException {
		System.setErr(new PrintStream(new BufferedOutputStream(new FileOutputStream(filename)),true));
	}
	
	/**
	 * Debug Level Enum Class
	 * Used to Ensure that Debug Messages are only Printed when their respective levels are ACTIVE
	 * 
	 * @author jcarlson <jaredcarlson@u.boisestate.edu>
	 *
	 */
	static enum Debug {
		LEVEL_0, LEVEL_1, LEVEL_2, LEVEL_3, LEVEL_4, LEVEL_5, LEVEL_6;
		boolean gt(Debug level) { return this.ordinal() > level.ordinal(); }
		static Debug parseLevel(String string) {
			switch(string) {
				case "0":
					return LEVEL_0;
				case "1":
					return LEVEL_1;
				case "2":
					return LEVEL_2;
				case "3":
					return LEVEL_3;
				case "4":
					return LEVEL_4;
				case "5":
					return LEVEL_5;
				case "6":
					return LEVEL_6;
				default:
					return LEVEL_0;
			}
		}
	}
	
	/**
	 * Context-Free Grammar Enum Class
	 * Aids in the Parsing/Building Procedures and helps produce more readable code
	 * 
	 * @author jcarlson <jaredcarlson@u.boisestate.edu>
	 *
	 */
	static enum REGrammarCode {
		EXPR_SINGLE("EXPR_SINGLE"), EXPR_COMPOUND("EXPR_COMPOUND"),
		TERM_SYMBOL("TERM_SYMBOL"), TERM_GROUP("TERM_GROUP"),
		SYMB_A("SYMB_A"), SYMB_B("SYMB_B"), SYMB_E("SYMB_E"),
		GROUP_SINGLE_EXPR("GROUP_SINGLE_EXPR"), GROUP_MULT_EXPR("GROUP_MULT_EXPR"), GROUP_MULT_SYMBOL("GROUP_MULT_SYMBOL"),
		EXPR_MULT("EXPR_MULT"), SYMB_MULT("SYMB_MULT");
		private REGrammarCode(String label) {}
	}
}
