/**
 * Represents a Single Component of the Overall State Machine Path
 * 
 * @author jcarlson <jaredcarlson@u.boisestate.edu>
 *
 */
public class REPathComponent {
	REExpr expr;
	static int count;
	
	public REPathComponent(REExpr expr) {
		this.expr = expr;
		count++;
	}
	
	public REExpr getExpr() {
		return expr;
	}
	
	public boolean isOptional() {
		return expr.isOptional();
	}
	
	public boolean acceptsEmptyString() {
		return expr.containsEmptyString();
	}
	
	public String toShortString() {
		return expr.toShortString();
	}
	
	public String toString() {
		return expr.toString();
	}
}
