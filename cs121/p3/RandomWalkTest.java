import java.util.Scanner;

/**
 * The RandomWalkTest driver class tests the functionality of the RandomWalk class.
 * User input for grid size and seed values are gathered and then a random walk
 * is created with the results displayed when the walk completes.
 * @author jcarlson
 * 
 */
public class RandomWalkTest {
	/** Stores the Grid Size for the RandomWalk object */
	private static int gridSize = 0;
	
	/** Stores the Seed for the random number generator - Useful during testing */
	private static long seed = 0;
	
	/** Reference variable used to create Scanner object for user input */
	private static Scanner kbd;
		
	/**
	 * Prompts user for Grid Size and optional random Seed value while checking user input for errors.
	 */
	private static void getInput() {
		// Create Scanner object for keyboard input
		kbd = new Scanner(System.in);
		
		// Loop until user inputs a valid grid size
		do {
			System.out.print("Enter Grid Size: ");
			gridSize = kbd.nextInt();
			// Grid Size must be greater than Zero
			if(gridSize < 1) {
				System.out.println("Error: The Grid Size must be a positive Integer. Try Again!");
			}
		} while(gridSize < 1);
		
		// Loop until user inputs a valid seed
		do {
			System.out.print("Enter Seed (0 for no Seed): ");
			seed = kbd.nextLong();
			// Seed value must be Positive
			if(seed < 0) {
				System.out.println("Error: The Seed must be  Try Again!");
			}
		} while(seed < 0);
		
		// Close Scanner
		kbd.close();
	}
		
	/**
	 * Displays program description, gets user input to create RandomWalk object, calls createWalk method for the RandomWalk object and uses the toString method to display the results.
	 * @param args Command line arguments are not supported.
	 */
	public static void main(String[] args) {
		// Create RandomWalk reference pointer
		RandomWalk randWalk;
		
		// Display Program Description
		System.out.println("=================================================================================================================");
		System.out.println("=  This program simulates a particle taking a random path from start to finish on a cartesian-style grid.       =");
		System.out.println("=  The particle begins in the top-left corner at point (0,0) and ends in the bottom-right corner of the grid.   =");
		System.out.println("=  You will need to choose a grid size and optional seed for the \"random\" turns the particle will make.         =");
		System.out.println("=                                                                                                               =");
		System.out.println("=  A Common Example: Grid Size = 10,  Seed = 0        ***The same seed will produce the same results            =");
		System.out.println("=================================================================================================================\n");
		
		// Call getInput and process user input
		getInput();

		// Create RandomWalk object using the appropriate constructor
		if(seed > 0) {
			randWalk = new RandomWalk(gridSize, seed);
		}
		else {
			randWalk = new RandomWalk(gridSize);
		}
		
		// Create the random walk and display the path Points from start to finish
		randWalk.createWalk();	
		System.out.println(randWalk.toString());
	}
}
