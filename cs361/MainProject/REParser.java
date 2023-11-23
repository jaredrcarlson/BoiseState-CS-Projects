
/**
 * Parses a Regular Expression according to the defined Context-Free Grammar
 * Builds CFG Objects and stores them in consecutive Path Component objects
 * that can then be used to build the proper path for an NFA
 * 
 * @author jcarlson <jaredcarlson@u.boisestate.edu>
 *
 */
public class REParser extends Debugable {
	String expression;
	REScanner scanner;
	
	public REParser(RE regex) {
		expression = regex.getString();
		scanner = new REScanner(expression);
	}
	
	public boolean more() {
		return scanner.more();
	}
	
	public REPathComponent getNextPathComponent() {
		return new REPathComponent(parseExpr());
	}
	
	private REExpr parseExpr() {
		debugStepIn();
		int startPos = scanner.getPos();
		writePrs("<Expr> Parsing at Pos: " + startPos);
		REExpr expr = new REExpr();
		RETerm term = parseTerm();
		if(term != null) {
			expr.setTerm(term);
			if(scanner.peek() == CHAR_AK) {
				writePrs("<Expr> Detected '*'");
				scanner.step();
				term.astk = true;
			}
			if(scanner.peek() != CHAR_OR) {
				writePrs("<Expr> Detected Form: <Term>");
				expr.gCode = REGrammarCode.EXPR_SINGLE;
			}
			else {
				writePrs("<Expr> Detected Form: <Term> | <Expr>");
				expr.gCode = REGrammarCode.EXPR_COMPOUND;
				scanner.step();
				REExpr innerExpr = parseExpr();
				if(innerExpr != null) {
					expr.setExpr(innerExpr);
				}
			}
			writePrs("<Expr> Parsed From Pos: " + startPos + " to Pos: " + (scanner.getPos() - 1));
			debugBackOut();
			return expr;
		}
		else {
			writeErr("<Expr> Error! - FAILED at Pos: " + scanner.getPos());
			System.exit(1);
			debugBackOut();
			return null;
		}
	}
	
	private RETerm parseTerm() {
		debugStepIn();
		int startPos = scanner.getPos();
		writePrs("<Term> Parsing... Pos: " + startPos);
		RETerm term = new RETerm();
		REGroup group = parseGroup();
		if(group != null) {
			writePrs("<Term> Parsed From Pos: " + startPos + " to Pos: " + (scanner.getPos() - 1));
			term.setGroup(group);
			term.gCode = REGrammarCode.TERM_GROUP;
			debugBackOut();
			return term;
		}
		else {
			scanner.setPos(startPos);
			RESymb symb = parseSymb();
			if(symb != null) {
				writePrs("<Term> Parsed From Pos: " + startPos + " to Pos: " + (scanner.getPos() - 1));
				term.setSymb(symb);
				term.gCode = REGrammarCode.TERM_SYMBOL;
				debugBackOut();
				return term;
			}
			else {
				writeErr("<Term> Error! - FAILED at Pos: " + scanner.getPos());
				debugBackOut();
				return null;
			}
		}
	}
	
	private REGroup parseGroup() {
		debugStepIn();
		int startPos = scanner.getPos();
		writePrs("<Group> Parsing... Pos: " + startPos);
		REGroup group = new REGroup();
		if(scanner.peek() == CHAR_PO) {
			writePrs("<Group> Detected '('");
			scanner.step();
			int markPos = scanner.getPos();
			REExprMult exprMult = parseExprMult();
			if(exprMult != null) {
				group.setExprMult(exprMult);
				group.gCode = REGrammarCode.GROUP_MULT_EXPR;
				if(scanner.peek() == CHAR_PC) {
					writePrs("<Group> Detected ')'");
					scanner.step();
					writePrs("<Group> Parsed From Pos: " + startPos + " to Pos: " + (scanner.getPos() - 1));
					debugBackOut();
					return group;
				}
				else {
					writeErr("<Group> Error! - Failed at Pos: " + scanner.getPos());
					debugBackOut();
					return null;
				}
			}
			else {
				scanner.setPos(markPos);
				REExpr expr = parseExpr();
				if(expr != null) {
					group.setExpr(expr);
					group.gCode = REGrammarCode.GROUP_SINGLE_EXPR;
					if(scanner.peek() == CHAR_PC) {
						writePrs("<Group> Detected ')'");
						scanner.step();
						writePrs("<Group> Parsed From Pos: " + startPos + " to Pos: " + (scanner.getPos() - 1));
						debugBackOut();
						return group;
					}
					else {
						writeErr("<Group> Error! - Failed at Pos: " + scanner.getPos());
						debugBackOut();
						return null;
					}
				}
				else {
					writeErr("<Group> Error! - Failed at Pos: " + scanner.getPos());
					debugBackOut();
					return null;
				}
			}
		}
		else {
			RESymbMult symbMult = parseSymbMult();
			if(symbMult != null) {
				group.gCode = REGrammarCode.GROUP_MULT_SYMBOL;
				writePrs("<Group> Parsed From Pos: " + startPos + " to Pos: " + (scanner.getPos() - 1));
				group.setSymbMult(symbMult);
				debugBackOut();
				return group;
			}
			else {
				writePrs("<Group> NOT FOUND - Returning to Pos: " + startPos);
				debugBackOut();
				return null;
			}
		}
	}
	
	private REExprMult parseExprMult() {
		debugStepIn();
		int startPos = scanner.getPos();
		writePrs("<ExprMult> Parsing... Pos: " + startPos);
		REExprMult exprMult = new REExprMult();
		int numExpressions = 0;
		while(scanner.peek() != CHAR_PC) {
			REExpr expr = parseExpr();
			exprMult.addExpr(expr);
			numExpressions++;
			writePrs("<ExprMult> Added Expression (" + numExpressions + ") : " + expr.toString());
		}
		if(numExpressions > 1) {
			writePrs("<ExprMult> Parsed From Pos: " + startPos + " to Pos: " + (scanner.getPos() - 1));
			debugBackOut();
			return exprMult;
		}
		else {
			writePrs("<ExprMult> NOT FOUND - Returning to Pos: " + startPos);
			debugBackOut();
			return null;
		}
	}
	
	private RESymbMult parseSymbMult() {
		debugStepIn();
		int startPos = scanner.getPos();
		writePrs("<SymbMult> Parsing... Pos: " + startPos);
		if(isValidSymbol(scanner.peek()) && isValidSymbol(scanner.peek2())) {
			RESymbMult symbMult = new RESymbMult();
			RESymb currentSymb = parseSymb();
			symbMult.addSymb(currentSymb);
			while(isValidSymbol(scanner.peek())) {
				symbMult.addSymb(parseSymb());
			}
			if(scanner.peek() == '*') {
				symbMult.removeLast();
				scanner.setPos(scanner.getPos() - 1);
			}
			writePrs("<SymbMult> Parsed From Pos: " + startPos + " to Pos: " + (scanner.getPos() - 1));
			debugBackOut();
			return symbMult;
		}
		else {
			writePrs("<SymbMult> NOT FOUND - Returning to Pos: " + startPos);
			debugBackOut();
			return null;
		}
	}
	
	private RESymb parseSymb() {
		debugStepIn();
		writePrs("<Symb> Parsing... Pos: " + scanner.getPos());
		RESymb symbol;
		switch(scanner.peek()) {
		case CHAR_A:
			writePrs("<Symb> Detected 'a'");
			symbol = new RESymb(scanner.step());
			debugBackOut();
			return symbol;
		case CHAR_B:
			writePrs("<Symb> Detected 'b'");
			symbol = new RESymb(scanner.step());
			debugBackOut();
			return symbol;
		case CHAR_E:
			writePrs("<Symb> Detected 'e'");
			symbol = new RESymb(scanner.step());
			debugBackOut();
			return symbol;
		default:
			writePrs("<Symb> NOT FOUND at Pos: " + scanner.getPos());
			debugBackOut();
			return null;
		}
	}
	
	private boolean isValidSymbol(char symb) {
		switch(symb) {
		case CHAR_A:
			return true;
		case CHAR_B:
			return true;
		case CHAR_E:
			return true;
		default:
			return false;
		}
	}	
}
