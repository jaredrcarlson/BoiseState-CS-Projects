
public class RETerm extends Debugable {
	public REGrammarCode gCode;
	public boolean astk;
	private REGroup group;
	private RESymb symb;
	
	public RETerm() {
		astk = false;
	}
	
	public boolean isOptional() {
		if(astk) {
			return true;
		}
		else {
			switch(gCode) {
				case TERM_SYMBOL:
					return symb.isEmptyString();
				case TERM_GROUP:
					return group.isOptional(); 
				default:
					return false;
			}
		}
	}
	
	public REGroup getGroup() {
		return group;
	}

	public void setGroup(REGroup group) {
		this.group = group;
	}
	
	public RESymb getSymb() {
		return symb;
	}

	public void setSymb(RESymb symb) {
		this.symb = symb;
	}
	
	public boolean isEmptyString() {
		switch(gCode) {
			case TERM_SYMBOL:
				return symb.isEmptyString();
			case TERM_GROUP:
				return group.containsEmptyString(); 
			default:
				return false;
		}
	}
	
	public boolean acceptsEmptyString() {
		switch(gCode) {
			case TERM_SYMBOL:
				return symb.isEmptyString();
			case TERM_GROUP:
				return group.containsEmptyString();
			default:
				return false;
		}
	}
	
	public boolean isSymbol() {
		return gCode == REGrammarCode.TERM_SYMBOL;
	}
	
	public boolean isGroup() {
		return gCode == REGrammarCode.TERM_GROUP;
	}
	
	public int numStatesRequired() {
		int numStatesRequired = 0;
		switch(gCode) {
			case TERM_SYMBOL:
				numStatesRequired += symb.numStatesRequired();
				break;
			case TERM_GROUP:
				numStatesRequired += group.numStatesRequired();
				break;
			default:
				return 1000000; // gCode not set correctly
		}
		if(astk) {
			numStatesRequired += 2;
		}
		return numStatesRequired;
	}
	
	public String toShortString() {
		switch(gCode) {
			case TERM_SYMBOL:
				return symb.toShortString() + ((astk) ? "*" : "");
			case TERM_GROUP:
				return group.toShortString() + ((astk) ? "*" : "");
			default:
				return "NULL"; // Term not set correctly
		}
	}
	
	public String toString() {
		StringBuilder strBld = new StringBuilder("<" + gCode.toString() + ">");
		switch(gCode) {
			case TERM_SYMBOL:
				strBld.append(symb.toString() + ((astk) ? "*" : ""));
				break;
			case TERM_GROUP:
				strBld.append(group.toString() + ((astk) ? "*" : ""));
				break;
			default:
				strBld.append("NULL"); // gCode not set correctly
				break;
		}
		strBld.append("</" + gCode.toString() + ">");
		return strBld.toString();
	}
}
