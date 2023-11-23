
public class HuffmanNode implements Comparable<Object> {
	private HuffmanData data;
	private HuffmanNode parent;
	private HuffmanNode leftChild;
	private HuffmanNode rightChild;
	private int weight;
	private String code;
	
	public HuffmanNode(HuffmanData d) {
		setData(d);
		setWeight(d.freq() * -1);
		setCode("");
	}

	/**
	 * @return the data
	 */
	public HuffmanData data() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(HuffmanData data) {
		this.data = data;
	}
	
	public String getID() {
		return Character.toString(data.getSymbol());
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String s) {
		code = s;
	}

	/**
	 * @return the parent
	 */
	public HuffmanNode getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(HuffmanNode parent) {
		this.parent = parent;
	}
	
	public boolean hasLeftChild() {
		return leftChild != null;
	}
	
	/**
	 * @return the leftChild
	 */
	public HuffmanNode getLeftChild() {
		return leftChild;
	}
	
	public HuffmanNode gLC() {
		return leftChild;
	}

	/**
	 * @param leftChild the leftChild to set
	 */
	public void setLeftChild(HuffmanNode leftChild) {
		this.leftChild = leftChild;
	}
	
	public boolean hasRightChild() {
		return rightChild != null;
	}
	
	/**
	 * @return the rightChild
	 */
	public HuffmanNode getRightChild() {
		return rightChild;
	}
	
	public HuffmanNode gRC() {
		return rightChild;
	}

	/**
	 * @param rightChild the rightChild to set
	 */
	public void setRightChild(HuffmanNode rightChild) {
		this.rightChild = rightChild;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public void setWeight(int w) {
		weight = w;
	}
	
	public boolean isLeaf() {
		return (gLC() == null && gRC() == null);
	}
	
	
	/**
	 * Checks if this Item has a higher priority than some
	 * other Item.
	 *
	 * @param otherItem the other Item
	 * @return true, if successful
	 */
	public boolean hasHigherPriorityThan(HuffmanNode otherNode) {
		return compareTo(otherNode) == 1;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Object obj) {
		HuffmanNode otherNode = (HuffmanNode)obj;
		int otherWeight = otherNode.getWeight();
		if(this.weight < otherWeight) {
			return 1;
		}
		else {
			return -1;
		}
	}
	
	public String toString() {
		String s = data.toString() + ((parent != null) ? " Has Parent! " : "");
		if(leftChild == null) {
			s = s + " LC: null";
		}
		else {
			s = s + " LC: set";
		}
		if(rightChild == null) {
			s = s + " RC: null";
		}
		else {
			s = s + " RC: set";
		}
		return s;
	}
}
