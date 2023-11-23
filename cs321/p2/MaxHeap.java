/**
 * The Class MaxHeap.
 * @author jcarlson
 */
public class MaxHeap {
	
	/** Default initial capacity if not supplied to constructor. */
	private static final int DEFAULT_INITIAL_CAPACITY = 10;
	
	/** Rate at which to expand the Heap capacity when full */
	private final int EXPANSION_MULTIPLIER = 2;
	
	/** The array to simulate the heap storage. */
	private Process[] array;
	
	/** The current number of Process items in the Heap */
	private int size;
	
	/** The root index of this MaxHeap */
	private int root;
	
	/** The last position (Lowest Priority) in this Heap */
	private int lastIndex;
	
	/**
	 * Instantiates a new MaxHeap.
	 */
	public MaxHeap() {
		this(DEFAULT_INITIAL_CAPACITY + 1);
	}
	
	/**
	 * Instantiates a new Max Heap with supplied initial capacity.
	 *
	 * @param initialCapacity the initial capacity
	 */
	public MaxHeap(int initialCapacity) {
		array = new Process[initialCapacity + 1];
		root = size = lastIndex = 0;
	}
	
	/**
	 * @return the lastIndex
	 */
	public int getLastIndex() {
		return size;
	}

	/**
	 * @param lastIndex the lastIndex to set
	 */
	public void setLastIndex(int lastIndex) {
		this.lastIndex = lastIndex;
	}

	/**
	 * Adds a process to the Heap
	 *
	 * @param newProcess the new process to add to this Heap
	 */
	public void insertToLowestPriorityPosition(Process newProcess) {
		if(isFull()) {
			expandArray();
		}
		array[++size] = newProcess;
		siftUp(getLastIndex());
	}
	
	/**
	 * Removes highest priority process
	 *
	 * @return the highest priority process
	 */
	public Process getHighestPriorityProcess() {
		if(!isEmpty()) {
			Process highestPriorityItem = array[root];
			swapNodes(root,lastIndex);
			size--;
			siftDown(root);
			return highestPriorityItem;
		}
		else {
			return null;
		}
	}
	
	/**
	 * Checks if this Heap is empty.
	 *
	 * @return true, if is empty
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * Checks if this Heap is full.
	 *
	 * @return true, if is full
	 */
	private boolean isFull() {
		return size == array.length - 1;
	}
	
	/**
	 * Current number of items in this Heap
	 *
	 * @return the current size of this Heap
	 */
	public int size() {
		return size;
	}
	
	/**
	 * Expands the array when full.
	 */
	private void expandArray() {
		Process[] originalArray = array;
		array = new Process[(size * EXPANSION_MULTIPLIER) + 1];
		for(int i=1; i <= size; i++) {
			array[i] = originalArray[i];
		}
		originalArray = null;
	}

	/**
	 * Updates a node's location in MaxHeap tree
	 *
	 * @param indexOfNode the node to update
	 * */
	public void updateNodeLocationInMaxHeapStructure(int indexOfNode) {
		siftUp(indexOfNode);
	}
	
	/**
	 * Sifts node up in MaxHeap tree recursively
	 *
	 * @param indexOfNode the node to sift up
	 */
	private void siftUp(int indexOfNode) {
		if(hasParent(indexOfNode)) {
			int parentIndex = getParent(indexOfNode);
			if(array[indexOfNode].hasHigherPriorityThan(array[parentIndex])) {
				swapNodes(indexOfNode, parentIndex);
				siftUp(parentIndex);
			}
		}	
	}
	
	/**
	 * Sifts node down in MaxHeap tree recursively
	 *
	 * @param indexOfParent the node to sift down
	 */
	private void siftDown(int indexOfParent) {
		int indexOfLeftChildOfParent = 0, indexOfRightChildOfParent = 0, higherPriorityChildIndex = 0;
		if(hasChild(indexOfParent)) {
			higherPriorityChildIndex = indexOfLeftChildOfParent = indexOfParent * 2;
			if(indexOfLeftChildOfParent != size) {
				indexOfRightChildOfParent = indexOfLeftChildOfParent + 1;
				if(array[indexOfRightChildOfParent].hasHigherPriorityThan(array[indexOfLeftChildOfParent])) {
					higherPriorityChildIndex = indexOfRightChildOfParent; 
				}
			}
			if(array[higherPriorityChildIndex].hasHigherPriorityThan(array[indexOfParent])) {
				swapNodes(indexOfParent, higherPriorityChildIndex);
				siftDown(higherPriorityChildIndex);
			}
		}
	}
	
	/**
	 * Swap nodes.
	 *
	 * @param indexOfNodeA the index of nodeA
	 * @param indexOfNodeB the index of nodeB
	 */
	private void swapNodes(int indexOfNodeA, int indexOfNodeB) {
		Process temporaryPointerToNode = array[indexOfNodeA];
		array[indexOfNodeA] = array[indexOfNodeB];
		array[indexOfNodeB] = temporaryPointerToNode;
		temporaryPointerToNode = null;
	}
	
	/**
	 * Checks if node has a Parent
	 *
	 * @param indexOfChild the node to check
	 * @return true, if successful
	 */
	private boolean hasParent(int indexOfChild) {
		return indexOfChild > 1;
	}
	
	/**
	 * Checks if node has any children
	 *
	 * @param indexOfParent the node to check
	 * @return true, if successful
	 */
	private boolean hasChild(int indexOfParent) {
		return indexOfParent <= size / 2;
	}
	
	/**
	 * Gets the parent node
	 *
	 * @param indexOfChild the child node
	 * @return the parent node
	 */
	private int getParent(int indexOfChild) {
		return indexOfChild / 2;
	}
	
	/**
	 * Gets the process at index supplied
	 * @param indexOfProcess the index of the process to retrieve
	 * @return the process at index parameter i
	 */
	public Process getProcessAtIndex(int indexOfProcess) {
		return array[indexOfProcess];
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder heapContents = new StringBuilder("\n --- Priority Queue ---");
		for(int i=1; i <= size; i++) {
			heapContents.append("\n-------------------------------------------------------------------------------------------");
			heapContents.append("\n[Slot " + i + "] - JOB ID: " + array[i].getArrivalTime() 
					                       + ", Process Time: " + array[i].getTotalTimeRequiredForProcessToComplete()
					                           + ", Priority: " + array[i].getPriority() 
					                           + ", WaitTime: " + array[i].getWaitTime() 
					             + ", Remaining Process Time: " + array[i].getTimeRemainingToComplete());
			heapContents.append("\n-------------------------------------------------------------------------------------------");
		}
		heapContents.append("\n-------------------------------------------------------------------------------------------");
		return heapContents.toString();
	}
}
