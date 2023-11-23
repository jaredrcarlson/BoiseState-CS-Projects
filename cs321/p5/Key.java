
public class Key extends TreeObject {
	private static int BYTE_SIZE = 22;
	private static final int NULL_ADDRESS_CEILING = DiskMetadata.size();
	private static final int NULL_ADDRESS = -1;
	
	private static int totalNumberOfKeys = 0;
	
	// Fields
	private Key previousKey;
	private Key nextKey;
	
	public Key(String keyValue) {
		this(keyValue,NULL_ADDRESS,NULL_ADDRESS,null,null);
	}
	
	public Key(String keyValue, int leftChild, int rightChild) {
		this(keyValue,leftChild,rightChild,null,null);
	}
	
	public Key(String keyValue, Key previousKey, Key nextKey) {
		this(keyValue,NULL_ADDRESS,NULL_ADDRESS,previousKey,nextKey);
	}
	
	public Key(String keyValue, int leftChild, int rightChild, Key previousKey, Key nextKey) {
		super(keyValue);
		setLeftChild(leftChild);
		setRightChild(rightChild);
		setPreviousKey(previousKey);
		setNextKey(nextKey);
	}
	
	// Build from disk
	public Key(TreeObjectMetadata md, int leftChildAddress, int rightChildAddress, long dnaID, int frequency) {
		super(md,leftChildAddress,rightChildAddress,dnaID,frequency);
	}
	
	public static int size() {
		return BYTE_SIZE;
	}
	
	public static int getTotalNumberOfKeys() {
		return totalNumberOfKeys;
	}
	
	public static void incrementTotalNumberOfKeys() {
		totalNumberOfKeys++;
	}
	
	public boolean hasLC() {
		return getLeftChildAddress() >= NULL_ADDRESS_CEILING;
	}
	
	public int getLeftChild() {
		return getLeftChildAddress();
	}
	
	public int getLC() {
		return getLeftChild();
	}
	
	public void setLeftChild(int address) {
		setLeftChildAddress(address);
	}
	
	public void setLC(int address) {
		setLeftChild(address);
	}
	
	public void setLeftChildAddress(int address) {
		leftChildAddress = address;
	}

	public boolean hasRC() {
		return getRightChildAddress() >= NULL_ADDRESS_CEILING;
	}
	
	public int getRightChild() {
		return getRightChildAddress();
	}
	
	public int getRC() {
		return getRightChildAddress();
	}

	public void setRightChild(int address) {
		setRightChildAddress(address);
	}
	
	public void setRC(int rc) {
		setRightChild(rc);
	}
	
	public void setRightChildAddress(int address) {
		rightChildAddress = address;
	}

	public Key getPreviousKey() {
		return previousKey;
	}
	
	public Key previous() {
		return getPreviousKey();
	}

	public void setPreviousKey(Key previousKey) {
		this.previousKey = previousKey;
	}
	
	public void setPrevious(Key pk) {
		setPreviousKey(pk);
	}

	public boolean hasNext() {
		return getNextKey() != null;
	}
	
	public Key getNextKey() {
		return nextKey;
	}
	
	public Key next() {
		return getNextKey();
	}

	public void setNextKey(Key nextKey) {
		this.nextKey = nextKey;
	}
	
	public void setNext(Key nk) {
		setNextKey(nk);
	}
	
	public int getNumberOfChildren() {
		int numChildren = 0;
		if(hasLC()) {
			numChildren++;
		}
		if(hasRC()) {
			numChildren++;
		}
		return numChildren;
	}
	
	public String toDumpString() {
		return "Frequency: " + getFrequency() + " DNA String: " + getDnaString();
	}
	
	public String toShortString(int index) {
		return "key" + index + " : " + getDnaString() + " : Frequency=" + getFrequency() + " : Previous=" + ((previous() == null) ? "Null" : previous().getDnaID()) + " : ID=" + getDnaID() + " : Next=" + ((next() == null) ? "Null" : next().getDnaID()) + " : LC: " + ((getLC() >= NULL_ADDRESS_CEILING) ? getLC() : "Null") + " : RC: " + ((getRC() >= NULL_ADDRESS_CEILING) ? getRC() : "Null");
	}
	
	public String toString() {
		return "<Key-MD>  " + md.toString() + ", LC: " + ((getLC() >= NULL_ADDRESS_CEILING) ? getLC() : "Null") + ", RC: " + ((getRC() >= NULL_ADDRESS_CEILING) ? getRC() : "Null") + "  </Key-MD> <Key-Data>  Sequence=" + getDnaString() + ", ID=" + dnaID + ", Freq=" + frequency;
	}
}
