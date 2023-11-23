
public class DiskMetadata {
	// Metadata Sizes (bytes)
	private static final int NB_ROOT_ADDRESS = 4;
	private static final int NB_NEXT_OPEN_ADDRESS = 4;
	private static final int NB_SEQUENCE_LENGTH = 4;
	private static final int NB_DEGREE = 4;
	private static final int NB_NUM_NODES = 4;
	private static final int NB_NODE_MD_SIZE = 4;
	private static final int NB_MAX_NUM_KEYS = 4;
	private static final int NB_KEY_SIZE = 4;
	private static final int NB_MD = NB_ROOT_ADDRESS + NB_NEXT_OPEN_ADDRESS + NB_SEQUENCE_LENGTH + NB_DEGREE + NB_NUM_NODES + NB_NODE_MD_SIZE + NB_MAX_NUM_KEYS + NB_KEY_SIZE;
	
	private int debugMode = BTree.getDebugMode();
	
	protected int rootAddress;
	protected int nextOpenAddress;
	protected int sequenceLength;
	protected int degree;
	protected int numNodes;
	protected int nodeMetadataSize;
	protected int maxNumKeys;
	protected int keySize;
	protected long bytesRead;
	protected int numReads;
	protected long bytesWritten;
	protected int numWrites;
	
	public DiskMetadata() {}
	
	public DiskMetadata(int rootAddress, int sequenceLength, int degree, int numNodes, int nodeMetadataSize, int maxNumKeys, int keySize) {
		this.rootAddress = rootAddress;
		nextOpenAddress = 0;
		this.sequenceLength = sequenceLength;
		this.degree = degree;
		this.numNodes = numNodes;
		this.nodeMetadataSize = nodeMetadataSize;
		this.maxNumKeys = maxNumKeys;
		this.keySize = keySize;
		bytesRead = bytesWritten = numReads = numWrites = 0;
		if(debugMode > 1) {
			System.out.println("Disk Metadata Size: " + getSize());
		}
	}
	
	public static int size() {
		return NB_MD;
	}
	
	public int getSize() {
		return NB_MD;
	}
	
	public int getNextAddress() {
		updateNextOpenAddress();
		return nextOpenAddress;
	}
	
	public void updateNextOpenAddress() {
		nextOpenAddress += ((nextOpenAddress == 0) ? size() : BTreeNode.size());
		if(debugMode > 1) {
			System.out.println("Next Open Address: " + nextOpenAddress);
			System.out.println(" --- Node Size: " + BTreeNode.size());
		}
	}
}
