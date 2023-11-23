import java.math.BigInteger;

public class TreeObject implements Comparable<TreeObject> {
	private static final int NB_DNA_ID = 8;
	private static final int NB_FREQUENCY = 4;
	private static final int NB_LEFT_CHILD_ADDRESS = 4;
	private static final int NB_RIGHT_CHILD_ADDRESS = 4;
	private static final int NB_FIELDS = NB_DNA_ID + NB_FREQUENCY + NB_LEFT_CHILD_ADDRESS + NB_RIGHT_CHILD_ADDRESS;
	private static final int NB_TREEOBJECT = 2 + NB_FIELDS;
	
	// Meta-Data
	protected TreeObjectMetadata md;
	
	// Fields
	protected long dnaID; // 8 bytes
	protected int frequency; // 4 bytes
	public int leftChildAddress; // 4 bytes
	protected int rightChildAddress; // 4 bytes
	
	// Construct from GBK File
	public TreeObject(String dnaString) {
		md = new TreeObjectMetadata(dnaString);
		setDnaID(toLongValue(toBinaryStringValue(dnaString)));
		frequency = 1;
	}
	
	// Construct from Disk Read
	public TreeObject(TreeObjectMetadata md, int leftChildAddress, int rightChildAddress, long dnaID, int frequency) {
		setMetadata(md);
		setLeftChildAddress(leftChildAddress);
		setRightChildAddress(rightChildAddress);
		setDnaID(dnaID);
		setFrequency(frequency);
	}
	
	// Static Methods
	public static int size() {
		return NB_TREEOBJECT;
	}
	
	// Public Methods
	public int getSize() {
		return size();
	}
	
	public long getDnaID() {
		return dnaID;
	}
	
	public long keyValue() {
		return getDnaID();
	}
	
	public long getKey() {
		return getDnaID();
	}
	
	public byte getSequenceLength() {
		return md.getSequenceLength();
	}
	
	public byte getNumZerosStripped() {
		return md.getNumberOfLeadingZerosStripped();
	}
	
	public String getDnaString() {
		return toDnaStringValue(toBinaryStringValue(dnaID));
	}
	
	public int getFrequency() {
		return frequency;
	}

	public void incrementFrequency() {
		frequency++;
	}
	
	public int getLeftChildAddress() {
		return leftChildAddress;
	}
	
	public int getRightChildAddress() {
		return rightChildAddress;
	}
		
	public boolean equals(TreeObject otherTreeObject) {
		return compareTo(otherTreeObject) == 0;
	}
	
	public boolean lessThan(TreeObject otherTreeObject) {
		return compareTo(otherTreeObject) == -1;
	}
	
	public boolean greaterThan(TreeObject otherTreeObject) {
		return compareTo(otherTreeObject) == 1;
	}

	@Override
	public int compareTo(TreeObject otherTreeObject) {
		if(dnaID < otherTreeObject.getDnaID()) {
			return -1;
		}
		else if(dnaID == otherTreeObject.getDnaID()) {
			return 0;
		}
		else {
			return 1;
		}
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder("<Key-MD>  " + md.toString() + "  </Key-MD> <Key-Data>  " + getDnaString() + ":" + dnaID + "   |   " + frequency);
		return sb.toString();
	}
	
	// Private Methods
	
	private void setMetadata(TreeObjectMetadata md) {
		this.md = md;
	}
	
	private void setDnaID(long dnaID) {
		this.dnaID = dnaID;
	}
	
	private void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	
	private void setLeftChildAddress(int leftChildAddress) {
		this.leftChildAddress = leftChildAddress;
	}
	
	private void setRightChildAddress(int rightChildAddress) {
		this.rightChildAddress = rightChildAddress;
	}
	
	private String toBinaryStringValue(String dnaString) {
		StringBuilder sb = new StringBuilder();
		int i = md.getSequenceLength();
		String original = dnaString;
		while (i > 0) {
			sb.append(toCode(dnaString.charAt(0)));
			dnaString = dnaString.substring(1);
			i--;
		}
		String result = sb.toString();
		if(result.length() < 1) {
			BTree.invalidSequence(original);
			return null;
		}
		else {
			return result;
		}
	}
	
	private String toBinaryStringValue(long longValue) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < md.getNumberOfLeadingZerosStripped(); i++) {
			sb.append("0");
		}
		sb.append(Long.toBinaryString(longValue));
		return sb.toString();
	}
	
	private String toCode(char c) {
		switch (c) {
			case 'A':
			case 'a':
				return "00";
			case 'C':
			case 'c':
				return "01";
			case 'G':
			case 'g':
				return "10";
			case 'T':
			case 't':
				return "11";
			default:
				return "";
		}
	}
	
	private char toChar(String s) {
		switch (s) {
			case "00":
				return 'A';
			case "01":
				return 'C';
			case "10":
				return 'G';
			case "11":
				return 'T';
		}
		return 0;
	}
	
	private long toLongValue(String s) {
		return new BigInteger(s, 2).longValue();
	}
	
	private String toDnaStringValue(String s) {
		StringBuilder sB = new StringBuilder();
		while(s.length() > 0) {
			if(s.length() > 1) {
				String nextTwoBits = s.substring(0,2);
				sB.append(toChar(nextTwoBits));
				s = s.substring(2);
			}
			else {
				break;
			}
		}
		return sB.toString();
	}
}
