/**
 * Stores a Regular Expression
 * 
 * @author jcarlson <jaredcarlson@u.boisestate.edu>
 *
 */
public class RE {
	String regularExpression;
	
	public RE(String re) {
		regularExpression = re;
	}
	
	public String getString() {
		return regularExpression;
	}
	
	public String toString() {
		return getString();
	}
}
