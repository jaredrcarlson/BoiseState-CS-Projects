
public class REExpr extends Debugable {
	public REGrammarCode gCode;
	private RETerm term;
	private REExpr expr;
	
	public int numStatesRequired() {
		int numStatesRequired = 0;
		switch(gCode) {
			case EXPR_SINGLE:
				numStatesRequired += getTerm().numStatesRequired();
				break;
			case EXPR_COMPOUND:
				REExpr currExpr = this;
				while(currExpr != null) {
					if(!currExpr.getTerm().isEmptyString()) {
						numStatesRequired += currExpr.getTerm().numStatesRequired() + 2;
					}
					currExpr = currExpr.getExpr();
				}
				break;
			default:
				break;
		}
		return numStatesRequired;
	}
	
	public RETerm getTerm() {
		return term;
	}
	
	public void setTerm(RETerm term) {
		this.term = term;
	}
	
	public REExpr getExpr() {
		return expr;
	}
	
	public void setExpr(REExpr expr) {
		this.expr = expr;
	}
	
	public boolean isOptional() {
		boolean optional = false;
		REExpr currExpr = this;
		while(currExpr != null && !optional) {
			if(term.isOptional()) {
				optional = true;
			}
			currExpr = currExpr.getExpr(); 
		}		
		return optional;
	}
	
	public boolean containsEmptyString() {
		return (term.isEmptyString() || (expr != null && expr.containsEmptyString()));
	}
	
	public boolean acceptsEmptyString() {
		return (this.isOptional() || this.containsEmptyString());
	}
	
	public String toShortString() {
		switch(gCode) {
			case EXPR_SINGLE:
				return term.toShortString();
			case EXPR_COMPOUND:
				return term.toShortString() + '|' + expr.toShortString();
			default:
				return "";
		}
	}
	
	public String toString() {
		StringBuilder strBld = new StringBuilder("<" + gCode.toString() + ">");
		switch(gCode) {
			case EXPR_SINGLE:
				strBld.append(term.toString());
				break;
			case EXPR_COMPOUND:
				strBld.append(term.toString() + "<-- OR -->" + expr.toString());
				break;
			default:
				break;
			}
		strBld.append("</" + gCode.toString() + ">");
		return strBld.toString();
	}
}
