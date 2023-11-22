import java.util.Random;
import java.util.ArrayList;
import java.awt.Point;

/** 
 * The RandomWalk class is used to simulate a random choice path for a particle traveling on a cartesian-style grid.
 * The particle begins in the top-left corner at Point (0,0) with a goal of reaching the bottom-right corner.
 * The particle will never revisit any Point on the grid so it is possible for the particle to get stuck.
 * If the particle gets stuck without any direction to move, the simulation simply resets and tries again.
 * The simulation will repeat as many times as needed for the particle to reach the bottom-right corner.
 * @author jcarlson
 *
 */
public class RandomWalk {
	/**  Defines the number of Points printed on each line when displaying the particle's path */
	private final int POINTS_PER_LINE = 12;
	
	/**  User-defined Grid Size. */
	private int gridSize;
	
	/**  Random Number Generator. */
	private Random rand;
	
	/**  Used to determine if the particle has run out of valid moves. */
	private boolean stuck;
	
	/**  Used to determine if the particle has reached it's goal. */
	private boolean done;
	
	/**  Used to store the Points traveled by the particle. */
	private ArrayList<Point> path;
	
	/**  Used to store only the next steps that are valid for each random step choice. */
	private ArrayList<Point> validSteps;
	
	/** Used to store the particle's current location. */
	private Point currentPoint;
	
	/** Used to store the particle's next location. */
	private Point nextPoint;
	
	/** Used to store the particle's end location. */
	private Point endPoint;
	
	/**  Stores the index value of randomly chosen next step for easy retrieval of next Point. */
	private int nextPointIndex;
	
	/**  Used to store the possible Point north of the current Point. */
	private Point northPoint;
	
	/**  Used to store the possible Point east of the current Point. */
	private Point eastPoint;
	
	/**  Used to store the possible Point south of the current Point. */
	private Point southPoint;
	
	/**  Used to store the possible Point west of the current Point. */
	private Point westPoint;
	
	/**  Used to define/store the grid's north boundary to make sure the particle stays on the grid. */
	private int northBoundary;
	
	/**  Used to define/store the grid's east boundary to make sure the particle stays on the grid. */
	private int eastBoundary;
	
	/**  Used to define/store the grid's south boundary to make sure the particle stays on the grid. */
	private int southBoundary;
	
	/**  Used to define/store the grid's west boundary to make sure the particle stays on the grid. */
	private int westBoundary;
	
	/**  Number of attempts required for the particle to reach it's goal */
	private long attempts;
				
	/**
	 * Initializes all object fields and Instantiates a new RandomWalk object with user-defined Grid Size.
	 *
	 * @param gridSize The size of the grid for the particle to traverse. Note: A grid size of 10 is from 0 to 9.
	 */
	RandomWalk(int gridSize) {
		this.gridSize = gridSize;
		rand = new Random();
		done = false;
		stuck = false;
		currentPoint = new Point(0,0);
		northPoint = new Point(0,-1);
		eastPoint = new Point(1,0);
		southPoint = new Point(0,1);
		westPoint = new Point(-1,0);
		endPoint = new Point(this.gridSize - 1,this.gridSize - 1);
		path = new ArrayList<Point>();
		path.add(currentPoint);
		validSteps = new ArrayList<Point>();
		northBoundary = 0;
		eastBoundary = this.gridSize - 1;
		southBoundary = this.gridSize - 1;
		westBoundary = 0;
		attempts = 0;
	}
	
	/**
	 * Initializes all object fields and Instantiates a new RandomWalk object with user-defined Grid Size and random Seed value.
	 *
	 * @param gridSize The size of the grid for the particle to traverse. Note: A grid size of 10 is from 0 to 9.
	 * @param seed The seed is used for the random number generator.  Note: Same seed produces same random numbers.
	 */
	RandomWalk(int gridSize, long seed) {
		this.gridSize = gridSize;
		rand = new Random(seed);
		done = false;
		stuck = false;
		currentPoint = new Point(0,0);
		northPoint = new Point(0,-1);
		eastPoint = new Point(1,0);
		southPoint = new Point(0,1);
		westPoint = new Point(-1,0);
		endPoint = new Point(this.gridSize - 1,this.gridSize - 1);
		path = new ArrayList<Point>();
		path.add(currentPoint);
		validSteps = new ArrayList<Point>();
		northBoundary = 0;
		eastBoundary = this.gridSize - 1;
		southBoundary = this.gridSize - 1;
		westBoundary = 0;
		attempts = 0;
	}
	
	/**
	 * The step method randomly chooses a valid next step, restarting at Point (0,0) if no valid next step exists, and continues until the particle reaches it's goal.
	 */
	public void step() {
		// Increment total number of attempts
		attempts++;
		
		// Determine which four points are possible next steps
		northPoint = new Point(currentPoint.x,currentPoint.y - 1);
		eastPoint = new Point(currentPoint.x + 1,currentPoint.y);
		southPoint = new Point(currentPoint.x,currentPoint.y + 1);
		westPoint = new Point(currentPoint.x - 1,currentPoint.y);
		
		// Mark stuck to TRUE, any valid next moves will return it to FALSE
		stuck = true;
		
		// Clear any previous list of possible steps
		validSteps.clear();
		
		// Determine which possible moves are valid and add them to list of valid steps
		if(isValid(northPoint)) {
			validSteps.add(northPoint);
			stuck = false;
		}
		if(isValid(eastPoint)) {
			validSteps.add(eastPoint);
			stuck = false;
		}
		if(isValid(southPoint)) {
			validSteps.add(southPoint);
			stuck = false;
		}
		if(isValid(westPoint)) {
			validSteps.add(westPoint);
			stuck = false;
		}
		
		// Move randomly to a valid new Point and check to see if the particle has reached it's goal
		if(!stuck) {
			nextPointIndex = rand.nextInt(validSteps.size());
			nextPoint = validSteps.get(nextPointIndex);
			path.add(nextPoint);
			currentPoint = nextPoint;
			if(currentPoint.equals(endPoint)) {
				done = true;
			}
		}
		// If the particle runs out of valid moves, reset the simulation
		else if(stuck && !isDone()) {
			currentPoint.setLocation(0,0);
			path.clear();
			path.add(currentPoint);
			stuck = false;
		}
	}
	
	/**
	 * The createWalk method executes the step method until the particle reaches it's goal; the bottom-right corner of the grid.
	 */
	public void createWalk() {
		while(!stuck && !isDone()) {
			step();
		}
	}
	
	/**
	 * The isDone method is used to check if the particle has reached the bottom-right corner of the grid.
	 * @return True if the particle has reached it's goal.
	 */
	public boolean isDone() {
		return done;
	}
	
	/**
	 * The isValid method is used to determine if a Point on the grid is a valid next step for the particle.
	 * @param step The Point to examine and determine if it is a valid next step.
	 * @return True if the Point passed in as an argument is a valid next step.
	 */
	private boolean isValid(Point step) {
		boolean valid;
		// Check if the Point has already been visited or if it is out of bounds
		if(path.contains(step) || step.x < westBoundary || step.x > eastBoundary || step.y < northBoundary || step.y > southBoundary) {
			valid = false;
		}
		else {
			valid = true;
		}
		return  valid;
	}
		
	/**
	 * The getPath method is used to return the ArrayList storing the particle's path as it travels across the grid.
	 * @return The ArrayList containing the particle's path
	 */
	public ArrayList<Point> getPath() {
		return path;
	}
	
	/**
	 * The toString method overrides the default and is used to return the particle's path for use in displaying to the screen.
	 * @return A display-friendly String of the particle's path from start to finish and number of attempts to reach it's goal.
	 */
	public String toString() {
		int newLineCounter = 1; // Used to count number of Points printed 
		String displayPath = "\nThe particle's successful path was:\n";
		for(Point p : path) {
			String displayPoint = "[" + p.x + "," + p.y + "]"; // Create String for current Point
			displayPath += String.format("%-7s", displayPoint); // Append the current Point to the path String (Width=7,Left-Justified)
			if(newLineCounter % POINTS_PER_LINE == 0) {
				displayPath += "\n"; // Carriage Return after every POINTS_PER_LINE Points
			}
			else {
				displayPath += " "; // Add a space after each Point
			}
			newLineCounter++;
		}
		displayPath += "\n\nIt took " + EnglishNumberToWords.convert(attempts) + " attempts for the particle to reach it's goal!";
		return displayPath;
	}
}
