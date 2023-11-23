import java.util.ArrayList;

public class REExprMult extends Debugable {
	public REGrammarCode gCode;
	private ArrayList<REExpr> expressions;
	private int numExpressions;
	
	public REExprMult() {
		gCode = REGrammarCode.EXPR_MULT;
		expressions = new ArrayList<REExpr>();
		numExpressions = 0;
	}
	
	public int size() {
		return numExpressions;
	}
	
	public ArrayList<REExpr> getExpressions() {
		return expressions;
	}
	
	public void addExpr(REExpr expr) {
		expressions.add(expr);
		numExpressions++;
	}
	
	public boolean isOptional() {
		boolean optional = true;
		for(int i=0; i < expressions.size(); i++) {
			if(!expressions.get(i).isOptional()) {
				optional = false;
			}
		}
		return optional;
	}
	
	public boolean acceptsEmptyString() {
		for(int i=0; i < expressions.size(); i++) {
			if(!expressions.get(i).containsEmptyString()) {
				return false;
			}
		}
		return true;
	}
	
	public int numStatesRequired() {
		int numStatesRequired = 0;
		for(int i=0; i < expressions.size(); i++) {
			numStatesRequired += expressions.get(i).numStatesRequired();
			numStatesRequired++;
		}
		return numStatesRequired - 1;
	}
	
	public String toShortString() {
		StringBuilder strBld = new StringBuilder();
		for(int i=0; i < expressions.size(); i++) {
			strBld.append(expressions.get(i).toShortString());
		}
		return strBld.toString();
	}
	
	public String toString() {
		StringBuilder strBld = new StringBuilder("<" + gCode.toString() + ">");
		for(int i=0; i < expressions.size(); i++) {
			strBld.append("<" + (i+1) + ">" + expressions.get(i).toString() + "</" + (i+1) + ">");
		}
		strBld.append("</" + gCode.toString() + ">");
		return strBld.toString();
	}
}
