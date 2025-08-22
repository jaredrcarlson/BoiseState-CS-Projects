
public class REGroup extends Debugable {
	public REGrammarCode gCode;
	private REExprMult exprMult;
	private REExpr expr;
	private RESymbMult symbMult;
	
	public REExprMult getExprMult() {
		return exprMult;
	}

	public boolean setExprMult(REExprMult exprMult) {
		this.exprMult = exprMult;
		return exprMult != null;
	}
	
	public REExpr getExpr() {
		return expr;
	}

	public boolean setExpr(REExpr expr) {
		this.expr = expr;
		return expr != null;
	}
	
	public RESymbMult getSymbMult() {
		return symbMult;
	}

	public void setSymbMult(RESymbMult symbMult) {
		this.symbMult = symbMult;
	}
	
	public boolean containsEmptyString() {
		if(exprMult != null && exprMult.acceptsEmptyString()) {
			return true;
		}
		else if(expr != null && expr.containsEmptyString()) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isSymbMult() {
		return gCode == REGrammarCode.GROUP_MULT_SYMBOL;
	}
	
	public boolean isExprMult() {
		return gCode == REGrammarCode.GROUP_MULT_EXPR;
	}
	
	public boolean isOptional() {
		switch(gCode) {
			case GROUP_MULT_SYMBOL:
				return false;
			case GROUP_SINGLE_EXPR:
				return expr.isOptional();
			case GROUP_MULT_EXPR:
				return exprMult.isOptional();
			default:
				return false;
		}
	}
	
	public int numStatesRequired() {
		switch(gCode) {
			case GROUP_MULT_SYMBOL:
				return symbMult.numStatesRequired();
			case GROUP_SINGLE_EXPR:
				return expr.numStatesRequired();
			case GROUP_MULT_EXPR:
				return exprMult.numStatesRequired();
			default:
				return 100;
		}
	}
	
	public String toShortString() {
		switch(gCode) {
			case GROUP_MULT_SYMBOL:
				return symbMult.toShortString();
			case GROUP_SINGLE_EXPR:
				return '(' + expr.toShortString() + ')';
			case GROUP_MULT_EXPR:
				return '(' + exprMult.toShortString() + ')';
			default:
				return "";
		}
	}
	
	public String toString() {
		StringBuilder strBld = new StringBuilder("<" + gCode.toString() + ">");
		switch(gCode) {
			case GROUP_MULT_SYMBOL:
				strBld.append(symbMult.toString());
				break;
			case GROUP_SINGLE_EXPR:
				strBld.append(expr.toString());
				break;
			case GROUP_MULT_EXPR:
				strBld.append(exprMult.toString());
				break;
			default:
				break;
		}
		strBld.append("</" + gCode.toString() + ">");
		return strBld.toString();
	}
}
