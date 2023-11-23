
public class BTreeNode implements Comparable<BTreeNode> {
	private static long maxNumberOfKeys;
	private static int KEYS_BYTE_SIZE;
	
	// Node Meta-Data
	protected BTreeNodeMetadata md;
	
	
	// ------------------------------------ Fields ------------------------------------ //
	
	protected Key firstKey;
	
	// ------------------------------------ Constructors ------------------------------------ //
	
	public BTreeNode() { }
	
	// Initial Root Node with a single key
	public BTreeNode(int address, Key key0, long maxNumKeys) {
		md = new BTreeNodeMetadata();
		setParentAddress(-1);
		setAddress(address);
		addFirstKey(key0);
		maxNumberOfKeys = maxNumKeys;
		KEYS_BYTE_SIZE = (int)(maxNumberOfKeys * Key.size());
		BTree.incrementNodeCount();
	}
	
	// Initial Root Split Only
	// New Root Node with promoted middle key and two children
	// Returns New Root node
	// leftChild - Will hold left side of keys (Node being split)
	// rightChild - Will hold right side of keys
	public BTreeNode(int address, BTreeNode leftChild, BTreeNode rightChild) {
		// Initialize this new Root Node
		md = new BTreeNodeMetadata();
		setParentAddress(-1); // Root Node has -1 Parent Address
		setAddress(address);
		
		Key key0 = leftChild.getMiddleKey(); // Promoted Key
		Key previousKey = key0.previous();
		Key nextKey = key0.next();
		
		key0.setPrevious(null);
		key0.setNext(null);
		this.addFirstKey(key0);
		key0.setLC(leftChild.getAddress());
		key0.setRC(rightChild.getAddress());
		leftChild.setParent(this);
		rightChild.setParent(this);
		
		previousKey.setNext(null);
		nextKey.setPrevious(null);
		rightChild.setFirstKey(nextKey);
		
		leftChild.updateNumberOfKeys();
		rightChild.updateNumberOfKeys();
				
		BTree.incrementNodeCount();
	}
	
	// Used to split a full Child Node
	// Returns new right child node
	// leftChild - Will hold left side of keys (Node being split)
	// rightChildAddress - Disk address for THIS new object
	public BTreeNode(BTreeNode parent, Key beforeKey, Key afterKey, BTreeNode leftChild, int rightChildAddress) {
		md = new BTreeNodeMetadata();
		setAddress(rightChildAddress);
		this.setParent(parent);
		leftChild.setParent(parent);
		
		Key proKey = leftChild.getMiddleKey();
		Key prevKey = proKey.previous();
		Key nextKey = proKey.next();
		
		proKey.setPrevious(null);
		proKey.setNext(null);
		
		prevKey.setNext(null);
		nextKey.setPrevious(null);
		
		this.setFirstKey(nextKey);
		
		this.updateNumberOfKeys();
		leftChild.updateNumberOfKeys();
		
		proKey.setLC(leftChild.getAddress());
		proKey.setRC(rightChildAddress);
		
		// Promote Middle Key
		if(beforeKey != null) {
			parent.addKeyBefore(proKey,beforeKey);
		}
		else if(afterKey != null) {
			parent.addKeyAfter(proKey,afterKey);
		}
		else {
			System.err.println("Error! Unable to determine new location of promoted key.");
		}
		
		parent.updateNumberOfKeys();
		
		BTree.incrementNodeCount();
	}
	
	// New Empty Node
	public BTreeNode(int address) {
		md = new BTreeNodeMetadata();
		setAddress(address);
		setParentAddress(-1);
		setNumberOfKeys(0);
		
		BTree.incrementNodeCount();
	}
	
	// New Node read from Disk
	public BTreeNode(BTreeNodeMetadata md, Key firstKey) {
		this.md = md;
		this.firstKey = firstKey;
	}
	
	
	// ------------------------------------ Public Methods ------------------------------------ //
	
	public static long getMaxNumKeys() {
		return maxNumberOfKeys;
	}
	
	public static int size() {
		return (BTreeNodeMetadata.size() + KEYS_BYTE_SIZE) * (int)maxNumberOfKeys;
	}
	
	public int getSize() {
		return size();
	}
	
	public boolean isFull() {
		return getNumberOfKeys() == getMaxNumberOfKeys();
	}
	
	public boolean isEmpty() {
		return getNumberOfKeys() == 0;
	}
	
	public boolean isLeaf() {
		return getNumberOfChildren() == 0;
	}
	
	public int getAddress() {
		return md.getNodeAddress();
	}

	public void setAddress(int addressOnDisk) {
		md.setNodeAddress(addressOnDisk);
	}
	
	public int getParentAddress() {
		return md.getParentAddress();
	}

	public void setParentAddress(int address) {
		md.setParentAddress(address);
	}
	
	public void setParent(BTreeNode parent) {
		setParentAddress(parent.getAddress());
	}
	
	public void updateNumberOfKeys() {
		if(firstKey != null) {
			Key nextKey = firstKey;
			long count = 1;
			while(nextKey.hasNext()) {
				nextKey = nextKey.next();
				count++;
			}
			md.setNumberOfKeys(count);
		}
		else {
			md.setNumberOfKeys(0);
		}
	}
	
	public long getNumberOfKeys() {
		if(md != null) {
			return md.getNumberOfKeys();
		}
		else {
			return 0;
		}
	}
	
	public long getNumKeys() {
		return getNumberOfKeys();
	}
	
	public void setNumberOfKeys(int size) {
		md.setNumberOfKeys(size);
	}

	public long getMaxNumberOfKeys() {
		return maxNumberOfKeys;
	}
	
	public Key getFirstKey() {
		return firstKey;
	}
	
	public void setFirstKey(Key firstKey) {
		this.firstKey = firstKey;
	}
	
	public Key getMiddleKey() {
		long position = getNumberOfKeys() / 2 + 1;
		Key nextKey = firstKey;
		for(int i=1; i < position; i++) {
			nextKey = nextKey.next();
		}
		return nextKey;
	}
	
	public void addFirstKey(Key firstKey) {
		this.firstKey = firstKey;
		incrementNumberOfKeys();
	}
	
	public void addKeyBefore(Key newKey, Key referenceKey) {
		Key previousKey = referenceKey.previous();
		referenceKey.setPrevious(newKey);
		newKey.setNext(referenceKey);
		referenceKey.setLC(newKey.getRC());
		incrementNumberOfKeys();
		Key.incrementTotalNumberOfKeys();
		if(previousKey == null) {
			setFirstKey(newKey);
		}
		else {
			previousKey.setNext(newKey);
			newKey.setPrevious(previousKey);
			previousKey.setRC(newKey.getLC());
		}
	}
	
	public void addKeyAfter(Key newKey, Key referenceKey) {
		Key nextKey = referenceKey.next();
		newKey.setPrevious(referenceKey);
		referenceKey.setNext(newKey);
		referenceKey.setRC(newKey.getLC());
		incrementNumberOfKeys();
		Key.incrementTotalNumberOfKeys();
		if(nextKey != null) {
			nextKey.setPrevious(newKey);
			newKey.setNext(nextKey);
			nextKey.setLC(newKey.getRC());
		}
	}
	
	public Key removeKey(Key key) {
		if(key.previous() != null) { key.previous().setNext(null); }
		if(key.next() != null) { key.next().setPrevious(null); }
		key.setPrevious(null);
		key.setNext(null);
		decrementNumberOfKeys();
		return key;
	}
	
	public long getNumberOfChildren() {
		if(firstKey != null) {
			Key nextKey = firstKey;
			long numChildren = nextKey.getNumberOfChildren();
			while(nextKey.hasNext()) {
				nextKey = nextKey.next();
				numChildren += nextKey.getNumberOfChildren();
			}
			md.setNumberOfChildren(numChildren);
			return numChildren;
		}
		else {
			return 0;
		}
	}
	
	public boolean equals(BTreeNode otherNode) {
		return compareTo(otherNode) == 0;
	}
	
	@Override
	public int compareTo(BTreeNode otherNode) {
		if(getAddress() == otherNode.getAddress()) {
			return 0;
		}
		else if(getAddress() < otherNode.getAddress()) {
			return -1;
		}
		else {
			return 1;
		}
	}
	
	public String toShortString(String nodeName) {
		StringBuilder sb = new StringBuilder("\n-------------------------------------------------------------------------------------------------\n");
		sb.append("<| Node: " + nodeName + ", Size on Disk: " + size() + " |>\n");
		sb.append("<| Address: " + getAddress() + ", Parent Address: " + getParentAddress() + " |>\n");
		sb.append("<| isLeaf: " + ((this.isLeaf()) ? "Yes" : "No") + ", isFull: " + ((this.isFull()) ? "Yes" : "No") + " |>\n");
		sb.append("<| Max Num Keys: " + getMaxNumberOfKeys() + ", Num Keys: " + getNumberOfKeys() + ", Num Children: " + getNumberOfChildren() + " |>\n");
		Key nextKey = firstKey;
		for(int i=0;i<getNumberOfKeys();i++) {
			sb.append("\n" + nextKey.toShortString(i));
			nextKey = nextKey.next();
		}
		sb.append("\n-------------------------------------------------------------------------------------------------\n");
		return sb.toString();
	}
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("| <Node-MD>  " + md.toString() + "  </Node-MD> --|-- ");
		if(firstKey != null) {
			sb.append(firstKey.toString());
			Key nextKey = firstKey.next();
			while(nextKey != null) {
				sb.append("  |  " + nextKey.toString());
				nextKey = nextKey.next();
			}
		}
		else {
			sb.append("None");
		}
		sb.append("  </Keys>");
		String nodeContents = sb.toString();
		int length = nodeContents.length();
		int numBorderDashes = length - 2;
		sb = new StringBuilder();
		
		sb.append("|"); for(int i = 0 ; i < numBorderDashes;i++){sb.append("-");} sb.append("|\n");
		sb.append(nodeContents);
		sb.append("\n|"); for(int i = 0 ; i < numBorderDashes;i++){sb.append("-");} sb.append("|\n");
		
		return sb.toString();
	}
	
	// ------------------------------------ Private Methods ------------------------------------ //
	
	private void incrementNumberOfKeys() {
		md.incrementNumberOfKeys();	
	}
	
	private void decrementNumberOfKeys() {
		md.decrementNumberOfKeys();
	}
}
