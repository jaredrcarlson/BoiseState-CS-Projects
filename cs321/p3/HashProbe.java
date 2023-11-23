/**
 * This is the base class for Linear-Hashing and Double-Hashing Probe classes
 * 
 * @author jcarlson
 *
 */
public class HashProbe {
	private final int RANDOM_NUMBER = 1;
	private final int SYSTEM_TIME = 2;
	protected int count;
	protected int tableSize;
	protected HashObject dataObj;
	protected int dataType;
	protected int startIndex;
	protected int nextIndex;
	protected int m;
	protected int hashCode;
	protected int key;
	
	protected HashProbe(int tableSize, HashObject obj) {
		count = 0;
		m = this.tableSize = tableSize;
		dataObj = obj;
		dataType = dataObj.getDataType();
		key = dataObj.getKey();
		startIndex = key % m;
	}
	
	public boolean hasNextIndex() {
		return count < m;
	}
	
	public int getCount() {
		return count;
	}
	
	public int getHashCode() {
		return hashCode;
	}
	
	public int getKey() {
		return key;
	}
	
	/**
	 * !!! THIS METHOD MUST BE OVERRIDDEN BY ALL SUB-CLASSES !!!  
	 * @return Negative One index - INDEX OUT OF BOUNDS!
	 */
	public int getNextIndex() {return -1;}
	
	/**
	 * !!! THIS METHOD MUST BE OVERRIDDEN BY ALL SUB-CLASSES !!!  
	 * @return Negative One count - INVALID COUNT! 
	 */
	public int getTotalCount() {return -1;}
	
	/**
	 * !!! THIS METHOD MUST BE OVERRIDDEN BY ALL SUB-CLASSES !!!  
	 */
	public void addToTotalCount() {}
}
