public class BTreeNodeMetadata {
	private static final int NB_NUM_KEYS =	8;
	private static final int NB_NUM_CHILDREN = 8;
	private static final int NB_ADDRESS = 4;
	private static final int NB_PARENT_ADDRESS = 4;
	private static int NB_MD = NB_NUM_KEYS + NB_NUM_CHILDREN + NB_ADDRESS + NB_PARENT_ADDRESS;
	
	protected long numberOfKeys = 0;
	protected long numberOfChildren = 0;
	private int nodeAddress = -1;
	private int parentAddress = -1;
	
	public BTreeNodeMetadata() {
		setNumberOfKeys(0);
		setNumberOfChildren(0);
		setNodeAddress(-1);
	}
	
	// Construct from Disk Read
	public BTreeNodeMetadata(long numberOfKeys, long numberOfChildren, int addressOnDisk, int parentAddress) {
		setNumberOfKeys(numberOfKeys);
		setNumberOfChildren(numberOfChildren);
		setNodeAddress(addressOnDisk);
		setParentAddress(parentAddress);
	}
	
	public static int size() {
		return NB_MD;
	}
	
	public int getSize() {
		return size();
	}
	
	public long getNumberOfKeys() {
		return numberOfKeys;
	}
	
	public void setNumberOfKeys(long numberOfKeys) {
		this.numberOfKeys = numberOfKeys;
	}
	
	public long getNumberOfChildren() {
		return numberOfChildren;
	}
	
	public void setNumberOfChildren(long numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}
	
	public void incrementNumberOfChildren() {
		numberOfChildren++;
	}
	
	public void decrementNumberOfChildren() {
		numberOfChildren--;
	}
	
	public void incrementNumberOfKeys() {
		numberOfKeys++;
	}
	
	public void decrementNumberOfKeys() {
		numberOfKeys--;
	}
	
	public int getNodeAddress() {
		return nodeAddress;
	}
	
	public void setNodeAddress(int addressOnDisk) {
		this.nodeAddress = addressOnDisk;
	}
	
	public int getParentAddress() {
		return parentAddress;
	}

	public void setParentAddress(int parentAddress) {
		this.parentAddress = parentAddress;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder("NumKeys=" + numberOfKeys + ", NodeAddress=" + nodeAddress + ", ParentAddress=" + parentAddress);
		return sb.toString();
	}
}
