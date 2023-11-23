
public class REScanner extends Debugable {
	String expression;
	char symA = 'a';
	char symB = 'b';
	char symE = 'e';
	char symOR = '|';
	char symPO = '(';
	char symPC = ')';
	char symAK = '*';
	char currChar;
	int currPos, finalPos;
	
	public REScanner(String expression) {
		this.expression = expression;
		currPos = 0;
		finalPos = expression.length() - 1;
		skipWS();
	}
	
	public boolean more() {
		return currPos <= finalPos;
	}
	
	public boolean done() {
		return currPos > finalPos;
	}
	
	public int getPos() {
		return currPos;
	}
	
	public void setPos(int pos) {
		currPos = pos;
	}
	
	public char peek() {
		if(currPos <= finalPos) {
			return expression.charAt(currPos);
		}
		else {
			return 0;
		}
	}
	
	public char peek2() {
		if(currPos < finalPos) {
			int savePos = currPos;
			currPos++;
			skipWS();
			char nextChar = peek();
			currPos = savePos;
			return nextChar;
		}
		else {
			return 0;
		}
	}
	
	public char step() {
		if(currPos <= finalPos) {
			currChar = expression.charAt(currPos);
			currPos++;
			skipWS();
			return currChar;
		}
		else {
			return 0;
		}
	}
		
	private void skipWS() {
		while(!expression.isEmpty() && expression.charAt(0) == ' ') {
			currPos++;
		}
	}
}
