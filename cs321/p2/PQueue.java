import java.util.NoSuchElementException;

/**
 * The Class PQueue.
 * @author jcarlson
 */
public class PQueue {
	
	/** The MaxHeap used as underlying data structure. */
	private MaxHeap maxHeap;
	
	/**
	 * Instantiates a new Priority Queue.
	 */
	public PQueue() {
		maxHeap = new MaxHeap();
	}
	
	/**
	 * Adds a process to the Queue
	 *
	 * @param newProcess the process
	 */
	public void enPQueue(Process newProcess) {
		maxHeap.insertToLowestPriorityPosition(newProcess);
	}

	/**
	 * Checks if the MaxHeap is empty
	 *
	 * @return true, if is empty
	 */
	public boolean isEmpty() {
		return maxHeap.isEmpty();
	}

	/**
	 * Removes Process with highest priority
	 *
	 * @return the highest priority Process
	 */
	public Process dePQueue() throws NoSuchElementException {
		if(maxHeap.isEmpty()) {
			throw new NoSuchElementException("Cannot retrieve element. The queue is empty.");
		}
		else {
			return maxHeap.getHighestPriorityProcess();
		}
	}

	/**
	 * Updates all Process items to reduce starvation and rebuilds the MaxHeap
	 *
	 * @param waitTimeThreshold the wait time threshold
	 * @param maxPriorityLevel the max priority
	 */
	public void update(int waitTimeThreshold, int maxPriorityLevel) {
		if(!maxHeap.isEmpty()) {
			for(int i=1; i <= maxHeap.size(); i++) {
				Process p = maxHeap.getProcessAtIndex(i);
				p.incrementWaitTime();
				if( (p.getWaitTime() >= waitTimeThreshold) && (p.getPriority() < maxPriorityLevel) ) {
					p.raisePriority();
					p.resetWaitTime();
					maxHeap.updateNodeLocationInMaxHeapStructure(i);
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return maxHeap.toString();
	}
}
