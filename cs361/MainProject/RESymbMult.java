import java.util.ArrayList;

public class RESymbMult extends Debugable {
	public REGrammarCode gCode;
	private ArrayList<RESymb> symbols;
	
	public RESymbMult() {
		gCode = REGrammarCode.SYMB_MULT;
		symbols = new ArrayList<RESymb>();
	}

	public ArrayList<RESymb> getSymbols() {
		return symbols;
	}

	public void addSymb(RESymb symb) {
		symbols.add(symb);
	}
	
	public int numStatesRequired() {
		return symbols.size() - 1;
	}

	public String toShortString() {
		StringBuilder strBld = new StringBuilder();
		for(int i=0; i < symbols.size(); i++) {
			strBld.append(symbols.get(i).toShortString());
		}
		return strBld.toString();
	}
	
	public String toString() {
		StringBuilder strBld = new StringBuilder("<" + gCode.toString() + ">");
		for(int i=0; i < symbols.size(); i++) {
			strBld.append(symbols.get(i).toString());
		}
		strBld.append("</" + gCode.toString() + ">");
		return strBld.toString();
	}

	public void removeLast() {
		symbols.remove(symbols.size() - 1);
	}
}
