
public class RESymb extends Debugable {
	public REGrammarCode gCode;
	private char c;
	
	public RESymb(char c) {
		this.c = c;
		switch(c) {
		case 'a':
			gCode = REGrammarCode.SYMB_A;
			break;
		case 'b':
			gCode = REGrammarCode.SYMB_B;
			break;
		case 'e':
			gCode = REGrammarCode.SYMB_E;
			break;
		default:
			System.out.println("*****************  ERROR *********************************");
			System.out.println("*****************  ERROR *********************************");
			System.out.println("*****************  ERROR *********************************");
		}
	}

	public char getCharacter() {
		return c;
	}
	
	public boolean equals(RESymb othersymb) {
		return gCode == othersymb.gCode;
	}
	
	public boolean isEmptyString() {
		return gCode == REGrammarCode.SYMB_E;
	}
	
	public int numStatesRequired() {
		return 0;
	}
	
	public String toShortString() {
		return "" + c;
	}
	
	public String toString() {
		StringBuilder strBld = new StringBuilder("<" + gCode.toString() + ">");
		strBld.append(c);
		strBld.append("</" + gCode.toString() + ">");
		return strBld.toString();
	}
}
