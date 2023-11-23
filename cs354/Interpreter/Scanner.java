import java.util.*;

/**
 * The Class Scanner - Makes use of several sets of strings to identify
 * characters and groups of characters in order to classify them as tokens
 * or to remove (strip) them to improve the efficiency of a parser.
 * 
 */
public class Scanner {
	/** Used for debugging */
	private boolean debug = false;
	
    /** The program string to be scanned */
    private String program;
    
    /** The current character position */
    private int pos;
    
    /** The reference to the currently captured token */
    private Token token;
    
    /** The set of strings that qualify as whitespace */
    private Set<String> whitespace = new HashSet<String>();
    
    /** The set of strings that qualify as digits */
    private Set<String> digits = new HashSet<String>();
    
    /** The set of strings that qualify as letters */
    private Set<String> letters = new HashSet<String>();
    
    /** The set of strings that qualify as legitimate keyword characters */
    private Set<String> legits = new HashSet<String>();
    
    /** The set of strings that qualify as keywords */
    private Set<String> keywords = new HashSet<String>();
    
    /** The set of strings that qualify as operators */
    private Set<String> binaryOperators = new HashSet<String>();
    
    /** The set of strings that qualify as unary operators */
    private Set<String> unaryOperators = new HashSet<String>();
    
    /** The set of strings that qualify as relation operators */
    private Set<String> relationOperators = new HashSet<String>();
    
    /** The set of strings that qualify as comment delimiters */
    private Set<String> comments = new HashSet<String>();
    
    /** The set of strings that qualify as statement separators */
    private Set<String> separators = new HashSet<String>();

    /**
     * Populates a set of strings in the range from low character to high character
     *
     * @param s the set of strings to populate
     * @param lo the low character
     * @param hi the high character
     */
    private void fill(Set<String> s, char lo, char hi) {
	for (char c=lo; c<=hi; c++)
	    s.add(c+""); // Converts character to string
    }    

    /**
     * Initializes the whitespace set of strings
     *
     * @param s the set to initialize
     */
    private void initWhitespace(Set<String> s) {
	s.add(" ");
	s.add("\n");
	s.add("\t");
    }

    /**
     * Initializes the digits set of strings
     *
     * @param s the set to initialize
     */
    private void initDigits(Set<String> s) {
	fill(s,'0','9');
	s.add(".");
    }

    /**
     * Initializes the letters set of strings
     *
     * @param s the set to initialize
     */
    private void initLetters(Set<String> s) {
	fill(s,'A','Z');
	fill(s,'a','z');
    }

    /**
     * Initializes the legitimate keywords set of strings
     *
     * @param s the set to initialize
     */
    private void initLegits(Set<String> s) {
	s.addAll(letters);
	s.addAll(digits);
    }

    /**
     * Initializes the operators set of strings
     *
     * @param s the set to initialize
     */
    private void initOperators(Set<String> s) {
	s.add("=");
	s.add("+");
	s.add("-");
	s.add("*");
	s.add("/");
	s.add("(");
	s.add(")");
    }
    
    /**
     * Initializes the unary operators set of strings
     *
     * @param s the set to initialize
     */
    private void initUnaryOperators(Set<String> s) {
    	s.add("-");
    }
    
    /**
     * Initializes the unary operators set of strings
     *
     * @param s the set to initialize
     */
    private void initRelationOperators(Set<String> s) {
    	s.add("<");
    	s.add("<=");
    	s.add(">");
    	s.add(">=");
    	s.add("<>");
    	s.add("==");
    }
    
    /**
     * Initializes the comment delimiters set of strings
     *
     * @param s the set to initialize
     */
    private void initComments(Set<String> s) {
    	s.add("^");
    }

    /**
     * Initializes the keywords set of strings
     *
     * @param s the set to initialize
     */
    private void initKeywords(Set<String> s) {
    	s.add("rd");
    	s.add("wr");
    	s.add("if");
    	s.add("then");
    	s.add("else");
    	s.add("while");
    	s.add("do");
    	s.add("begin");
    	s.add("end");
    }
    
    /**
     * Initializes the statement separator set of strings
     * 
     * @param s the set to initialize
     */
    private void initSeparators(Set<String> s) {
    	s.add(";");
    }

    /**
     * Instantiates a new scanner.
     *
     * @param program the program string to be scanned
     */
    public Scanner(String program) {
		this.program = program;
		pos = 0;
		token = null;
		initWhitespace(whitespace);
		initDigits(digits);
		initLetters(letters);
		initLegits(legits);
		initKeywords(keywords);
		initOperators(binaryOperators);
		initUnaryOperators(unaryOperators);
		initRelationOperators(relationOperators);
		initComments(comments);
		initSeparators(separators);
    }
    
    public Scanner(String program, Boolean debug) {
    	this(program);
    	this.debug = debug;
    }
    
    /**
     * Checks if last character has been reached
     *
     * @return true, if all characters have been processed
     */
    public boolean done() {
    	return pos>=program.length();
    }
    
    /**
     * Advances beyond a supplied set of symbols
     *
     * @param s the set of symbols
     */
    private void many(Set<String> s) {
    	while (!done() && s.contains(program.charAt(pos)+""))
    		pos++;
    }
   
    /**
     * Advances one position beyond a supplied character
     *
     * @param c the supplied character
     */
    private void past(char c) {
	while (!done() && c!=program.charAt(pos))
	    pos++;
	if (!done() && c==program.charAt(pos))
	    pos++;
    }
    
    /**
     * Captures an entire number and creates a number token
     * 
     */
    private void nextNumber() {
    	int old=pos;
    	many(digits);
    	token = new Token("num",program.substring(old,pos));
    	if(debug) {
    		System.out.println("New Token - Number: " + token.toString());
    	}
    }
    
    /**
     * Captures an entire keyword and creates a keyword token
     */
    private void nextKwId() {
    	int old=pos;
    	many(letters);
    	many(legits);
    	String lexeme=program.substring(old,pos);
    	if(keywords.contains(lexeme)) {
    		token = new Token("kw",lexeme);
    		if(debug) {
        		System.out.println("New Token - Keyword: " + token.toString());
        	}
    	}
    	else {
    		token = new Token("id",lexeme);
    		if(debug) {
        		System.out.println("New Token - Identifier: " + token.toString());
        	}
    	}
    }
    
    /**
     * Removes (Strips out) a comment
     * 
     */
    private void nextComment() {
    	pos++;
    	past('^');
    	if(debug) {
    		System.out.println("Comment Removed");
    	}
    }
    
    private void nextRelOp(int characters) {
    	int old=pos;
    	pos = old + characters;
    	if(!done()) {
	    	String lexeme = program.substring(old,pos);
	    	if(relationOperators.contains(lexeme)) {
	    		token = new Token("relop",lexeme);
	    		if(debug) {
	        		System.out.println("New Token - (2 character) Relational Operator: " + token.toString());
	        	}
	    		return;
	    	}
	    }
    }
    
    /**
     * Captures a binary operator and creates an operator token
     * 
     */
    private void nextOp() {
    	int old = pos;
	    pos = old + 2;
		if(!done()) {
		    String lexeme=program.substring(old,pos);
		    if(binaryOperators.contains(lexeme) || unaryOperators.contains(lexeme)) {
		    	token = new Token(lexeme);
		    	if(debug) {
		    		System.out.println("New Token - (Binary or Unary) Operator: " + token.toString());
		    	}
		    	return;
		    }
		}
		pos = old + 1;
		String lexeme=program.substring(old,pos);
		token = new Token(lexeme);
		if(debug) {
			System.out.println("New Token - (1 character) Operator: " + token.toString());
		}
    }
    
    /**
     * Captures and creates a statement separator token
     * 
     */
    private void nextSeparator() {
    	int old = pos;
    	pos = old + 1;
    	String lexeme=program.substring(old,pos);
    	token = new Token("sp",lexeme);
    	if(debug) {
    		System.out.println("New Token - Statement Separator: " + token.toString());
    	}
    }
        
    /**
     * Fetches the next complete token
     *
     * @return true, if a new token is available
     */
    public boolean next() {
    	many(whitespace); // skip past whitespace
    	if(done()) // return to caller when there are no more characters left to process
    		return false;
    	String c = program.charAt(pos)+""; // Get character at current position
    	if(digits.contains(c)) // If character is a digit
    		nextNumber(); // Get entire number
    	else if(letters.contains(c)) // If character is a letter
    		nextKwId(); // Get entire keyword or identifier
    	else if(comments.contains(c)) { // If character is opening comment
    		nextComment(); // Strip out entire comment
    		return next();
    	}
    	else if(pos+1 != program.length() && relationOperators.contains(c + program.charAt(pos+1))) {
			nextRelOp(2); // Get relation operator
		}
    	else if(relationOperators.contains(c)) {
    		nextRelOp(1);
    	}
		else if(binaryOperators.contains(c)) {
			nextOp(); // Get binary operator
		}
		else if(unaryOperators.contains(c)) {
			nextOp(); // Get unary operator
		}
		else if(separators.contains(c)) {
			nextSeparator();
		}
    	else { // Character is undefined
		    System.err.println("illegal character at position "+pos);
		    pos++;
		    return next();
		}
		return true;
    }

    /**
     * Matches a supplied token with current token
     *
     * @param t the supplied token
     * @throws SyntaxException the syntax exception
     */
    public void match(Token t) throws SyntaxException {
    	if (!t.equals(curr())) // Checks supplied token against current token
    		throw new SyntaxException(pos,t,curr());
    	next();
    }

    /**
     * Fetches the current token
     *
     * @return the current token
     * @throws SyntaxException if current token is null
     */
    public Token curr() throws SyntaxException {
    	if (token==null)
    		throw new SyntaxException(pos,new Token("ANY"),new Token("EMPTY"));
    	return token;
    }

    /**
     * Gets the current character position
     *
     * @return the current position
     */
    public int pos() {
    	return pos;
    }

    // Not used for this Interpreter
    /**
     * The main method
     * Useful for displaying each token as it is captured
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
		try {
		    Scanner scanner=new Scanner(args[0]);
		    while (scanner.next())
			System.out.println(scanner.curr());
		} catch (SyntaxException e) {
		    System.err.println(e);
		}
    }

}
