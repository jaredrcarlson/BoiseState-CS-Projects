
public class HuffmanData {
	private int symbolIndex;
	private int symbolFrequency;
	
	public HuffmanData(int index, int frequency) {
		setSymbolIndex(index);
		setSymbolFrequency(frequency);
	}

	/**
	 * @return the symbolIndex
	 */
	public int getSymbolIndex() {
		return symbolIndex;
	}
	
	public char getSymbol() {
		return (char)symbolIndex;
	}

	/**
	 * @param symbolIndex the symbolIndex to set
	 */
	public void setSymbolIndex(int symbolIndex) {
		this.symbolIndex = symbolIndex;
	}

	/**
	 * @return the symbolFrequency
	 */
	public int freq() {
		return symbolFrequency;
	}

	/**
	 * @param symbolFrequency the symbolFrequency to set
	 */
	public void setSymbolFrequency(int symbolFrequency) {
		this.symbolFrequency = symbolFrequency;
	}
	
	public void incrementFrequency() {
		symbolFrequency++;
	}
	
	public String toString() {
		if(symbolIndex == 9) {
			return "[\\t] : " + symbolFrequency;
		}
		else if(symbolIndex == 10) {
			return "[\\n] : " + symbolFrequency;
		}
		else if(symbolIndex == 32) {
			return "[space] : " + symbolFrequency;
		}
		return "[" + (char)symbolIndex + "] : " + symbolFrequency;
	}

}
