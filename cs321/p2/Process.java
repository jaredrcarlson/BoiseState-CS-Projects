/**
 * The Class Process.
 * @author jcarlson
 */
public class Process implements Comparable<Object> {
	
	/** The arrival time. */
	private int arrivalTime;
	
	/** The Time required for this process to complete. */
	private int totalTimeRequiredForProcessToComplete;
	
	/** The priority. */
	private int priority;
	
	/** The wait time. */
	private int waitTime;
	
	/** The time remaining for this process to complete. */
	private int timeRemainingForProcessToComplete;
	
	/**
	 * Instantiates a new process.
	 *
	 * @param arrivalTime the arrival time
	 * @param processTime the process time
	 * @param priorityLevel the priority level
	 */
	public Process(int arrivalTime, int processTime, int priorityLevel) {
		setArrivalTime(arrivalTime);
		setProcessTime(processTime);
		setPriority(priorityLevel);
		setWaitTime(0);
		timeRemainingForProcessToComplete = processTime;
	}

	/**
	 * Gets the arrival time.
	 *
	 * @return the arrivalTime
	 */
	public int getArrivalTime() {
		return arrivalTime;
	}

	/**
	 * Sets the arrival time.
	 *
	 * @param arrivalTime the arrivalTime to set
	 */
	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	/**
	 * Gets the time required for this process to complete.
	 *
	 * @return the time required for this proces to complete
	 */
	public int getTotalTimeRequiredForProcessToComplete() {
		return totalTimeRequiredForProcessToComplete;
	}

	/**
	 * Sets the process time.
	 *
	 * @param totalTimeRequiredForProcessToComplete the total amount of time required for a process to complete
	 */
	public void setProcessTime(int totalTimeRequiredForProcessToComplete) {
		this.totalTimeRequiredForProcessToComplete = totalTimeRequiredForProcessToComplete;
	}
	
	/**
	 * Gets the priority.
	 *
	 * @return the priorityLevel
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * Sets the priority.
	 *
	 * @param priorityLevel the priorityLevel to set
	 */
	public void setPriority(int priorityLevel) {
		this.priority = priorityLevel;
	}
	
	/**
	 * Raise priority.
	 */
	public void raisePriority() {
		priority++;
	}

	/**
	 * Gets the wait time.
	 *
	 * @return the waitTime
	 */
	public int getWaitTime() {
		return waitTime;
	}

	/**
	 * Sets the wait time.
	 *
	 * @param waitTime the waitTime to set
	 */
	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}
	
	/**
	 * Increment wait time.
	 */
	public void incrementWaitTime() {
		waitTime++;
	}
	
	/**
	 * Reset wait time.
	 */
	public void resetWaitTime() {
		setWaitTime(0);
	}
	
	/**
	 * Checks if this Process has a higher priority than some
	 * other process.
	 *
	 * @param p the other Process
	 * @return true, if successful
	 */
	public boolean hasHigherPriorityThan(Process p) {
		return compareTo(p) == 1;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Object obj) {
		Process otherProcess = (Process)obj;
		if(this.priority > otherProcess.getPriority()) {
			return 1;
		}
		else if(this.priority < otherProcess.getPriority()){
			return -1;
		}
		else {
			if(this.arrivalTime < otherProcess.getArrivalTime()) {
				return 1;
			}
			else {
				return -1;
			}
		}
	}

	/**
	 * Gets the time remaining.
	 *
	 * @return the time remaining
	 */
	public int getTimeRemainingToComplete() {
		return timeRemainingForProcessToComplete;
	}

	/**
	 * Reduce time remaining.
	 */
	public void reduceTimeRemaining() {
		if(timeRemainingForProcessToComplete > 0) {
			timeRemainingForProcessToComplete--;
		}
	}

	/**
	 * Checks if a Process has completed
	 *
	 * @return true, if successful
	 */
	public boolean finish() {
		return timeRemainingForProcessToComplete == 0;
	}

	/**
	 * Reset time not processed.
	 */
	public void resetTimeNotProcessed() {
		resetWaitTime();
	}
}
