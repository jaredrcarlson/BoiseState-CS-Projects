import java.util.Random;

/**
 * The Class ProcessGenerator.
 * @author jcarlson
 */
public class ProcessGenerator {
	
	/** The rand. */
	private Random randNumber;
	
	/** The percentageChanceOfNewProcessBeingAddedToPriorityQueue. */
	private double percentageChanceOfNewProcessBeingAddedToPriorityQueue; 
	
	/**
	 * Instantiates a new process generator.
	 *
	 * @param percentageChanceOfNewProcessBeingAddedToPriorityQueue the percentageChanceOfNewProcessBeingAddedToPriorityQueue
	 */
	public ProcessGenerator(double percentageChanceOfNewProcessBeingAddedToPriorityQueue) {
		this.setpercentageChanceOfNewProcessBeingAddedToPriorityQueue(percentageChanceOfNewProcessBeingAddedToPriorityQueue);
		randNumber = new Random();
	}

	/**
	 * Gets the percentageChanceOfNewProcessBeingAddedToPriorityQueue.
	 *
	 * @return the percentageChanceOfNewProcessBeingAddedToPriorityQueue
	 */
	public double getPercentageChanceOfNewProcessBeingAddedToPriorityQueue() {
		return percentageChanceOfNewProcessBeingAddedToPriorityQueue;
	}

	/**
	 * Sets the percentageChanceOfNewProcessBeingAddedToPriorityQueue.
	 *
	 * @param percentageChanceOfNewProcessBeingAddedToPriorityQueue the percentageChanceOfNewProcessBeingAddedToPriorityQueue to set
	 */
	public void setpercentageChanceOfNewProcessBeingAddedToPriorityQueue(double percentageChanceOfNewProcessBeingAddedToPriorityQueue) {
		this.percentageChanceOfNewProcessBeingAddedToPriorityQueue = percentageChanceOfNewProcessBeingAddedToPriorityQueue;
	}
	
	/**
	 * Checks for new Process based on percentageChanceOfNewProcessBeingAddedToPriorityQueue
	 *
	 * @return true, if successful
	 */
	public boolean query() {
		return randNumber.nextDouble() <= percentageChanceOfNewProcessBeingAddedToPriorityQueue;
	}

	/**
	 * Gets the new process.
	 *
	 * @param currentTime job arrival time
	 * @param maxTotalTimeRequiredForProcessToComplete the max process time
	 * @param maxLevel the max priority level
	 * @return the new process
	 */
	public Process getNewProcess(int arrivalTime, int maxTotalTimeRequiredForProcessToComplete, int maxLevel) {
		int timeRequiredForProcessToComplete = randNumber.nextInt(maxTotalTimeRequiredForProcessToComplete) + 1;
		int priorityLevel = randNumber.nextInt(maxLevel) + 1;
		return new Process(arrivalTime, timeRequiredForProcessToComplete, priorityLevel);
	}
}
