
/**
 * The Class Parser - Makes use of a scanner object and private methods to parse a
 * program supplied to the parse method as a String argument.
 *  
 */
public class Parser {

	private boolean debug = false;
	
	/** The scanner used to scan the input program string */
    private Scanner scanner;
    
    /** The scanner used to read from Standard Input */
    private java.util.Scanner sysIn;
    
    
    public Parser() {}
    public Parser(Boolean debug) {
    	this.debug = debug;
    	sysIn = new java.util.Scanner(System.in);
    }
    
    /**
     * Attempts to match the supplied token string with the current token
     *
     * @param s the supplied token string
     * @throws SyntaxException the syntax exception
     */
    private void match(String s) throws SyntaxException {
    	Token t = new Token(s);
    	if(debug) {
    		System.out.print("Match Attempt -->  (" + t.tok() + " == " + curr().tok() + ") ?  -->  ");
    	}
    	scanner.match(t);
    }

    /**
     * Fetches the current token of this scanner
     *
     * @return the current token
     * @throws SyntaxException the syntax exception
     */
    private Token curr() throws SyntaxException {
    	return scanner.curr();
    }

    /**
     * Fetches the current character position of this scanner
     *
     * @return the current position
     */
    private int pos() {
    	return scanner.pos();
    }
    
    public boolean done() {
    	return scanner.done();
    }
    
    public void next() {
    	scanner.next();
    }
    
    //------  Parsing Routine  ------//
    
    /**
     * Entry method into this Parser (Method called by the Interpreter)
     * 
     * Parses the next token from the scanner attempting to find a single
     * Assignment Statement Node and returns that Node or throws an Exception if parsing fails
     *
     * @param program the statement to parse (assigned to scanner)
     * @return a node (containing assignment statement)
     * @throws SyntaxException if the statement syntax does not follow the grammar
     * @throws EvalException 
     */
    public NodeBlock parse(String program) throws SyntaxException, EvalException {
    	if(debug) {
    		Node.debug(true);
    		System.out.println("Node parse()...");
    	}
    	scanner = new Scanner(program,debug);
    	next();
    	NodeBlock block = parseBlock();
    	return block;
    }
    
    /**
     * Matches the sequence of tokens expected for a Block and returns
     * a Block Node or throws an Exception if match fails
     * 
     * @return A Block Node
     * 
     * @throws SyntaxException
     * @throws EvalException 
     */
    public NodeBlock parseBlock() throws SyntaxException, EvalException {
    	if(debug) {
    		System.out.println("parseBlock()...  " + curr().toString());
    	}
    	NodeStmt stmt = parseStmt();
    	if(stmt != null) {
			String lexeme = curr().lex();
			if(lexeme.equals(";")) {
				match("sp");
				NodeBlock block = parseBlock();
				if(block != null) {
					return new NodeBlock(pos(),stmt,block);
				}
				else {
					throw new SyntaxException(pos(),new Token("block"),curr());
				}
        	}
			else {
				return new NodeBlock(pos(),stmt,null);
			}
    	}
    	else {
    		return null;
    	}
    }
    
    /**
     * Matches the sequence of tokens expected for a Statement and returns
     * a Statement Node or throws an Exception if match fails
     *
     * @return a statement node
     * @throws SyntaxException the syntax exception
     * @throws EvalException 
     */
    private NodeStmt parseStmt() throws SyntaxException, EvalException {
    	if(debug) {
    		System.out.println("parseStmt()...  " + curr().toString());
    	}
    	NodeStmtAssn assn = parseStmtAssn();
    	if(assn != null) {
    		return assn;
    	}
    	NodeStmtRd rd = parseStmtRd();
    	if(rd != null) {
    		return rd;
    	}
    	NodeStmtWr wr = parseStmtWr();
    	if(wr != null) {
    		return wr;
    	}
    	NodeStmtCondIf condIf = parseStmtCondIf();
    	if(condIf != null) {
    		return condIf;
    	}
    	NodeStmtCondWhile condWhile = parseStmtCondWhile();
        if(condWhile != null) {
        	return condWhile;
        }
        NodeStmtBody body = parseStmtBody();
        if(body != null) {
        	return body;
        }
        return null;
    }
    
    /**
     * Matches the sequence of tokens expected for an Assignment and returns
     * an Assignment Node or throws an Exception if match fails
     *
     * @return an assignment node
     * @throws SyntaxException the syntax exception
     */
    private NodeStmtAssn parseStmtAssn() throws SyntaxException {
    	if(debug) {
    		System.out.println("parseStmtAssn()...  " + curr().toString());
    	}
    	Token id = curr();
    	if(id.tok().equals("id")) {
    		match("id");
    		match("=");
    		NodeExpr expr = parseExpr();
    		if(expr != null) { 
    			return new NodeStmtAssn(pos(),id.lex(),expr);
    		}
    		else {
    			throw new SyntaxException(pos(),new Token("expr"),curr());
    		}
    	}
    	else {
    		return null;
    	}
    }
    
    /**
     * Matches the sequence of tokens expected for Read Input and returns
     * a Read Input Node or throws an Exception if match fails
     * 
     * @return A Read Input Node
     * 
     * @throws SyntaxException
     */
    private NodeStmtRd parseStmtRd() throws SyntaxException {
    	if(debug) {
    		System.out.println("parseStmtRd()...  " + curr().toString());
    	}
    	String lexeme = curr().lex();
    	if(lexeme.equals("rd")) {
    		match("kw");
    		Token id = curr();
    		if(id.tok().equals("id")) {
    			match("id");
    			return new NodeStmtRd(pos(),id.lex(),sysIn);
    		}
    		else {
    			throw new SyntaxException(pos(),new Token("id"),curr());
    		}
    	}
    	else {
    		return null;
    	}
    }
    
    /**
     * Matches the sequence of tokens expected for Write Output and returns
     * a Write Output Node or throws an Exception if match fails
     * 
     * @return A Write Output Node
     * 
     * @throws SyntaxException
     */
    private NodeStmtWr parseStmtWr() throws SyntaxException {
    	if(debug) {
    		System.out.println("parseStmtWr()...  " + curr().toString());
    	}
    	String lexeme = curr().lex();
    	if(lexeme.equals("wr")) {
    		match("kw");
    		NodeExpr expr = parseExpr();
    		if(expr != null) {
    			return new NodeStmtWr(pos(),expr);
    		}
    		else {
    			throw new SyntaxException(pos(),new Token("expr"),curr());
    		}
    	}
    	else {
    		return null;
    	}
    }
    
    /**
     * Matches the sequence of tokens expected for an Expression and returns
     * an Expression Node or throws an Exception if match fails
     *
     * @return an expression node
     * @throws SyntaxException the syntax exception
     */
    private NodeExpr parseExpr() throws SyntaxException {
    	if(debug) {
    		System.out.println("parseExpr()...  " + curr().toString());
    	}
    	NodeTerm term = parseTerm();
		NodeAddop addop = parseAddop();
    	if (addop != null) {
    		NodeExpr expr = parseExpr();
    		expr.append(new NodeExpr(term,addop,null));
    		return expr;
    	}
    	else {
    		return new NodeExpr(term,null,null);
    	}
    }

    
    /**
     * Matches the sequence of tokens expected for a Term and returns
     * a Term Node or throws an Exception if match fails
     *
     * @return a term node
     * @throws SyntaxException the syntax exception
     */
    private NodeTerm parseTerm() throws SyntaxException {
    	if(debug) {
    		System.out.println("parseTerm()...  " + curr().toString());
    	}
    	NodeFact fact = parseFact();
    	NodeMulop mulop = parseMulop();
    	if(mulop == null) {
    		return new NodeTerm(fact,null,null);
    	}
    	NodeTerm term = parseTerm();
    	term.append(new NodeTerm(fact,mulop,null));
    	return term;
    }
    
    /**
     * Matches the sequence of tokens expected for a Boolean Expression and returns
     * a Boolean Expression Node or throws an Exception if match fails
     * 
     * @return A Boolean Expression Node
     * 
     * @throws SyntaxException
     */
    private NodeBoolExpr parseBoolExpr() throws SyntaxException {
    	if(debug) {
    		System.out.println("parseBoolExpr()...  " + curr().toString());
    	}
    	NodeExpr exprLeft = parseExpr();
    	if(exprLeft != null) {
    		String tok = curr().tok();
    		if(tok.equals("relop")) {
    			NodeRelop relop = parseRelop();
        		if(relop != null) {
        			NodeExpr exprRight = parseExpr();
        			if(exprRight != null) {
        				return new NodeBoolExpr(pos(),exprLeft,relop,exprRight);
        			}
        			else {
        				throw new SyntaxException(pos(),new Token("expr"),curr());
        			}
        		}
        		else {
        			throw new SyntaxException(pos(),new Token("relop"),curr());
        		}
    		}
    		else {
    			throw new SyntaxException(pos(),new Token("relop"),curr());
    		}
    	}
    	else {
    		throw new SyntaxException(pos(),new Token("expr"),curr());
    	}
    }
    
    /**
     * Matches a Relational Operator token and returns
     * a Relational Operator Node or throws an Exception if match fails
     * 
     * @return A Relational Operator Node
     * 
     * @throws SyntaxException
     */
    private NodeRelop parseRelop() throws SyntaxException {
    	if(debug) {
    		System.out.println("parseRelop()...  " + curr().toString());
    	}
    	String lex = curr().lex();
    	if(lex.equals("<")) {
    		match("relop");
    		return new NodeRelop(pos(),"<");
    	}
    	if(lex.equals("<=")) {
    		match("relop");
    		return new NodeRelop(pos(),"<=");
    	}
    	if(lex.equals(">")) {
    		match("relop");
    		return new NodeRelop(pos(),">");
    	}
    	if(lex.equals(">=")) {
    		match("relop");
    		return new NodeRelop(pos(),">=");
    	}
    	if(lex.equals("<>")) {
    		match("relop");
    		return new NodeRelop(pos(),"<>");
    	}
    	if(lex.equals("==")) {
    		match("relop");
    		return new NodeRelop(pos(),"==");
    	}
    	return null;
    }
    
    /**
     * Matches the sequence of tokens expected for a Body of statements and returns
     * a Body Node or throws an Exception if match fails
     * 
     * @return A Body Node
     * 
     * @throws SyntaxException
     * @throws EvalException 
     */
    private NodeStmtBody parseStmtBody() throws SyntaxException, EvalException {
    	if(debug) {
    		System.out.println("parseStmtBody()...  " + curr().toString());
    	}
    	String lexBegin = curr().lex();
    	if(lexBegin.equals("begin")) {
    		match("kw");
    		NodeBlock block = parseBlock();
    		if(block != null) {
    			String lexEnd = curr().lex();
        		if(lexEnd.equals("end")) {
        			match("kw");
        			return new NodeStmtBody(pos(),block);
        		}
        		else {
        			throw new SyntaxException(pos(),new Token("kw","end"),curr());
        		}
    		}
    		else {
    			throw new SyntaxException(pos(),new Token("block"),curr());
    		}
    	}
    	else {
    		return null;
    	}
    }
    
    /**
     * Matches the sequence of tokens expected for a Factor and returns
     * a Factor Node or throws an Exception if match fails
     *
     * @return factor node
     * @throws SyntaxException the syntax exception
     */
    private NodeFact parseFact() throws SyntaxException {
    	if(debug) {
    		System.out.println("parseFact()...  " + curr().toString());
    	}
    	NodeFactNeg negfact = parseNegFact();
    	if(negfact != null) {
    		return negfact;
    	}
    	if(curr().equals(new Token("("))) {
    		match("(");
    		NodeExpr expr=parseExpr();
    		match(")");
    		return new NodeFactExpr(expr);
    	}
    	String tok = curr().tok();
    	if(tok.equals("id")) {
    		Token id=curr();
    		match("id");
    		return new NodeFactId(pos(),id.lex());
    	}
    	Token num=curr();
    	match("num");
    	return new NodeFactNum(num.lex());
    }
    
    /**
     * Matches the sequence of tokens expected for a Negated Factor and returns
     * a Negated Factor Node or throws an Exception if match fails
     *
     * @return a negated factor node
     * @throws SyntaxException the syntax exception
     */
    private NodeFactNeg parseNegFact() throws SyntaxException {
    	if(debug) {
    		System.out.println("parseNegFact()...  " + curr().toString());
    	}
    	NodeNegop negop = parseNegop();
    	if(negop != null) {
    		NodeFact nodefact = parseFact();
    		return new NodeFactNeg(negop,nodefact);
    	}
    	else {
    		return null;
    	}
    }
    
    /**
     * Matches the sequence of tokens expected for a Multiplication Operator and returns
     * a Multiplication Operator Node or throws an Exception if match fails
     *
     * @return a multiplication operator node
     * @throws SyntaxException the syntax exception
     */
    private NodeMulop parseMulop() throws SyntaxException {
    	if(debug) {
    		System.out.println("parseMulop()...  " + curr().toString());
    	}
    	if(curr().equals(new Token("*"))) {
    		match("*");
    		return new NodeMulop(pos(),"*");
    	}
    	if(curr().equals(new Token("/"))) {
    		match("/");
    		return new NodeMulop(pos(),"/");
    	}
    	return null;
    }

    /**
     * Matches the sequence of tokens expected for an Addition Operator and returns
     * an Addition Operator Node or throws an Exception if match fails
     *
     * @return an addition operator node
     * @throws SyntaxException the syntax exception
     */
    private NodeAddop parseAddop() throws SyntaxException {
    	if(debug) {
    		System.out.println("parseAddop()...  " + curr().toString());
    	}
    	if(curr().equals(new Token("+"))) {
    		match("+");
    		return new NodeAddop(pos(),"+");
    	}
    	if(curr().equals(new Token("-"))) {
    		match("-");
    		return new NodeAddop(pos(),"-");
    	}
    	return null;
    }
    
    /**
     * Matches the sequence of tokens expected for a Negation Operator and returns
     * a Negation Operator Node or throws an Exception if match fails
     *
     * @return a negation operator node
     * @throws SyntaxException the syntax exception
     */
    private NodeNegop parseNegop() throws SyntaxException {
    	if(debug) {
    		System.out.println("parseNegop()...  " + curr().toString());
    	}
    	if(curr().equals(new Token("-"))) {
    		match("-");
    		return new NodeNegop(pos(),"-");
    	}
    	else {
    		return null;
    	}
    }
    
    /**
     * Matches the sequence of tokens expected for a While Loop and returns
     * a While Loop Node or throws an Exception if match fails
     * 
     * @return A While Loop Node
     * 
     * @throws SyntaxException
     * @throws EvalException 
     */
    private NodeStmtCondWhile parseStmtCondWhile() throws SyntaxException, EvalException {
    	if(debug) {
    		System.out.println("parseStmtCondWhile()...  " + curr().toString());
    	}
    	String lexeme = curr().lex();
    	if(lexeme.equals("while")) {
	    	match("kw");	
			NodeBoolExpr boolExpr = parseBoolExpr();
			if(boolExpr != null) {
				String lexDo = curr().lex();
				if(lexDo.equals("do")) {
					match("kw");
					NodeStmt stmt = parseStmt();
					return new NodeStmtCondWhile(pos(),boolExpr,stmt);
				}
				else {
					throw new SyntaxException(pos(),new Token("kw","do"),curr());
				}
			}
			else {
				throw new SyntaxException(pos(),new Token("boolexpr"),curr());
			}
    	}
    	else {
    		return null;
    	}
    }
    
    /**
     * Matches the sequence of tokens expected for a Conditional If and returns
     * a Conditional If Node or throws an Exception if match fails
     * 
     * @return Conditional If Node
     * 
     * @throws SyntaxException
     * @throws EvalException 
     */
    private NodeStmtCondIf parseStmtCondIf() throws SyntaxException, EvalException {
    	if(debug) {
    		System.out.println("parseStmtCondIf()...  " + curr().toString());
    	}
    	String lexeme = curr().lex();
    	if(lexeme.equals("if")) {
    		match("kw");
	    	NodeBoolExpr boolExpr = parseBoolExpr();
	    	if(boolExpr != null) {
	    		String lexThen = curr().lex();
	    		if(lexThen.equals("then")) {
	    			match("kw");
	    			NodeStmt stmtCondT = parseStmt();
	    			String lexElse = curr().lex();
	    			if(lexElse.equals("else")) {
						match("kw");
						NodeStmt stmtCondF = parseStmt();
						return new NodeStmtCondIf(pos(),boolExpr,stmtCondT,stmtCondF);
					}
					else {
						return new NodeStmtCondIf(pos(),boolExpr,stmtCondT,null);
					}
	    		}
	    		else {
	    			throw new SyntaxException(pos(),new Token("kw","then"),curr());
	    		}
	    	}
	    	else {
	    		throw new SyntaxException(pos(),new Token("boolexpr"),curr());
	    	}
    	}
    	else {
    		return null;
    	}
    } 
}
