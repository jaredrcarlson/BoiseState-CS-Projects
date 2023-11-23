import java.util.NoSuchElementException;

/**
 * The Class HuffmanTreePQ.
 * @author jcarlson
 */
public class HuffmanTreePQ {
	
	/** The MinHeap used as underlying data structure. */
	private MinHeap minHeap;
	
	/**
	 * Instantiates a new Priority Queue.
	 */
	public HuffmanTreePQ() {
		minHeap = new MinHeap();
	}
	
	public int size() {
		return minHeap.size();
	}
	
	/**
	 * Adds a Item to the Queue
	 *
	 * @param newItem the Item
	 */
	public void enQueue(HuffmanNode newItem) {
		minHeap.insert(newItem);
	}

	/**
	 * Checks if the MinHeap is empty
	 *
	 * @return true, if is empty
	 */
	public boolean isEmpty() {
		return minHeap.size() == 0;
	}

	/**
	 * Removes Item with highest priority
	 *
	 * @return the highest priority Item
	 */
	public HuffmanNode deQueue() throws NoSuchElementException {
		if(minHeap.size() == 0) {
			throw new NoSuchElementException("Cannot retrieve element. The queue is empty.");
		}
		else {
			return (HuffmanNode)minHeap.remove();
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return minHeap.toString();
	}
}
