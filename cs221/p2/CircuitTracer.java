import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;

/**
 * Search for shortest paths between start and end points on a circuit board
 * as read from an input file using either a stack or queue as the underlying
 * search state storage structure and displaying output to the console or to
 * a GUI according to options specified via command-line arguments.
 * 
 * @author mvail
 */
public class CircuitTracer {
	private CircuitBoard board;
	private Storage<TraceState> stateStore;
	private ArrayList<TraceState> bestPaths;
	private boolean adjacentStartEnd;

	/** launch the program
	 * @param args three required arguments:
	 *  first arg: -s for stack or -q for queue
	 *  second arg: -c for console output or -g for GUI output
	 *  third arg: input file name 
	 */
	public static void main(String[] args) {
		try {
			new CircuitTracer(args); //create this with args
		}
		catch (FileNotFoundException fnf) {
			fnf.printStackTrace();
			System.err.println(fnf.getMessage()); // Display error message
			System.exit(2); // Exit Status 2 - File Not Found
		}
		catch (InvalidFileFormatException iff) {
			iff.printStackTrace();
			System.err.println(iff.getMessage()); // Display error message
			System.exit(3); // Exit Status 3 - Invalid File Format
		}
		catch (Exception e) {
			e.printStackTrace();
			System.exit(4); // Exit Status 4 - General Exception
		}
	}

	/** Print instructions for running CircuitTracer from the command line. */
	private static void printUsage() {
		// print out clear usage instructions when there are problems with any command line args
		System.out.println("\n-----------------------------------------------------------------------");
		System.out.println("  This application requires the following command line arguments:");
		System.out.println("    java CircuitTracer [ -s | -q ] [ -c | -g ] [ fileName.dat ]");
		System.out.println();
		System.out.println("  Argument 1: [ -s | -q ]");
		System.out.println("    -s  uses a Stack Data Structure to store best path information");
		System.out.println("    -q  uses a Queue Data Structure to store best path information");
		System.out.println();
		System.out.println("  Argument 2: [ -c | -g ]");
		System.out.println("    -c  displays best path results in command-line mode");
		System.out.println("    -g  displays best path results in graphical interface mode");
		System.out.println();
		System.out.println("  Argument 3: [ fileName.dat ]");
		System.out.println("    fileName.dat  the filename of the circuit board to be tested.");
		System.out.println();
		System.out.println("-----------------------------------------------------------------------");
		System.out.println("  Example: display on the console the shortest traces for a circuit");
		System.out.println("           board saved in a file called circuit2.dat using a stack");
		System.out.println();
		System.out.println("           java CircuitTracer -s -c circuit2.dat");
		System.out.println();
		System.out.println("  Example: display in a GUI the shortest traces for a circuit board");
        System.out.println("           saved in a file called circuit2.dat using a queue");
		System.out.println();
		System.out.println("           java CircuitTracer -q -g circuit2.dat");
		System.out.println();
		System.out.println("-----------------------------------------------------------------------");
	}
	
	/** 
	 * Set up the CircuitBoard and all other components based on command
	 * line arguments.
	 * 
	 * @param args command line arguments passed through from main()
	 * @throws InvalidFileFormatException 
	 * @throws FileNotFoundException 
	 */
	private CircuitTracer(String[] args) throws FileNotFoundException, InvalidFileFormatException {
		// parse command line args
		if(args.length != 3) {
			System.out.println("Error! - Invalid Number of Command Line Arguments: " + args.length + " (Expected 3 Arguments)");
			printUsage();
			System.exit(1); // Exit Status 1 - Invalid Command Line Arguments
		}
		else if((!args[0].equals("-s")) && (!args[0].equals("-q"))) {
			System.out.println("Error! - Invalid Command Line Argument One: " + args[0] + " (Expected -s or -q)");
			printUsage();
			System.exit(1); // Exit Status 1 - Invalid Command Line Arguments
		}
		else if((!args[1].equals("-c")) && (!args[1].equals("-g"))) {
			System.out.println("Error! - Invalid Command Line Argument Two: " + args[1] + " (Expected -c or -g)");
			printUsage();
			System.exit(1); // Exit Status 1 - Invalid Command Line Arguments
		}
		
		// initialize the Storage to use either a stack or queue
		switch(args[0]) {
		case "-s":
			stateStore = new Storage<TraceState>(Storage.DataStructure.stack);
			break;
		case "-q":
			stateStore = new Storage<TraceState>(Storage.DataStructure.queue);
		}
		
		// read in the CircuitBoard from the given file
		board = new CircuitBoard(args[2]);
		
		// run the search for best paths
		bestPaths = new ArrayList<TraceState>();
		
		// Check for adjacent starting and ending Points
		int xDifference = (int)(Math.abs(board.getStartingPoint().getX() - board.getEndingPoint().getX()));
		int yDifference = (int)(Math.abs(board.getStartingPoint().getY() - board.getEndingPoint().getY()));
		if( ((xDifference == 1) && (yDifference == 0)) || ((yDifference == 1) && (xDifference == 0)) ) {
			// Starting and Ending points are found to be adjacent
			adjacentStartEnd = true;
			bestPaths.add(new TraceState(new CircuitBoard(board)));
		}
		else {
			// ----------------------- <Pseudo-Code Translation> ----------------------- //
			generateInitialTraces();
						
			while(!stateStore.isEmpty()) {
				TraceState traceAttempt = stateStore.retrieve(); // Get next Trace from storage
				if(traceAttempt.isComplete()) {
					if(bestPaths.isEmpty()) {
						// First Successful Trace - Added as best path found so far
						bestPaths.add(traceAttempt); // Add trace as best solution
					}
					else if(traceAttempt.getPath().size() < bestPaths.get(0).getPath().size()) {
						// Completed Trace is shortest found so far
						bestPaths.clear(); // Remove all previous Traces from best paths
						bestPaths.trimToSize(); // Trim best paths array size
						bestPaths.add(traceAttempt); // Add trace as best solution
					}
					else if(traceAttempt.getPath().size() == bestPaths.get(0).getPath().size()) {
						bestPaths.add(traceAttempt); // Add trace to best solutions
					}
				}
				else {
					generateNextTraces(traceAttempt); // Get valid next traces for current trace
				}
			}
		}
		
		// output results to console or GUI, according to specified choice
		switch(args[1]) {
		case "-c":
			// Display results in command line mode
			StringBuilder output = new StringBuilder();
			// Check for "No Solutions" situation
			if(bestPaths.isEmpty()) {
				output.append("Sorry, No Solutions!");
			}
			else {
				// Iterate through Solutions and display to console
				Iterator<TraceState> itr = bestPaths.iterator();
				TraceState t = itr.next();
				output.append(t.getBoard().toString());
				while(itr.hasNext()) {
					t = itr.next();
					output.append("\n");
					output.append(t.getBoard().toString());				
				}
			}
			System.out.println(output.toString());
			break;
		case "-g":
			// Display results in graphical mode
			JFrame mainFrame = new JFrame("Circuit Board - Shortest Path Solutions");
			mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			mainFrame.getContentPane().add(new CircuitTracerGraphical(board, bestPaths, adjacentStartEnd));
			mainFrame.pack();
			mainFrame.setVisible(true);
		}
	}
	
	/**
	 * Generates and Stores all valid next traces
	 * 
	 */
	private void generateInitialTraces() {
		int row = (int)board.getStartingPoint().getX(); // Get Initial Trace row
		int col = (int)board.getStartingPoint().getY(); // Get Initial Trace col
		int northRow = row - 1; // Calculate North row
		int southRow = row + 1; // Calculate South row
		int eastCol = col + 1; // Calculate East col
		int westCol = col - 1; // Calculate West col
		TraceState traceAttempt;
		
		// Try each adjacent tile and store if open
		try {
			traceAttempt = new TraceState(new CircuitBoard(board),northRow,col);
			stateStore.store(traceAttempt);
		}
		catch (OccupiedPositionException ope) {
			// Ignore Exception as it is expected when positions are occupied
		}
		try {
			traceAttempt = new TraceState(new CircuitBoard(board),row,eastCol);
			stateStore.store(traceAttempt);
		}
		catch (OccupiedPositionException ope) {
			// Ignore Exception as it is expected when positions are occupied
		}
		try {
			traceAttempt = new TraceState(new CircuitBoard(board),southRow,col);
			stateStore.store(traceAttempt);
		}
		catch (OccupiedPositionException ope) {
			// Ignore Exception as it is expected when positions are occupied
		}
		try {
			traceAttempt = new TraceState(new CircuitBoard(board),row,westCol);
			stateStore.store(traceAttempt);
		}
		catch (OccupiedPositionException ope) {
			// Ignore Exception as it is expected when positions are occupied
		}
	}
	
	/**
	 * Generates and Stores all valid next traces
	 * @param traceState current TraceState
	 */
	private void generateNextTraces(TraceState traceState) {
		int row = traceState.getRow(); // Get current Trace row
		int col = traceState.getCol(); // Get current Trace col
		int northRow = row - 1; // Calculate North row
		int southRow = row + 1; // Calculate South row
		int eastCol = col + 1; // Calculate East col
		int westCol = col - 1; // Calculate West col
		TraceState traceAttempt;
		
		// Try each adjacent tile and store if open
		try {
			traceAttempt = new TraceState(traceState,northRow,col);
			stateStore.store(traceAttempt);
		}
		catch (OccupiedPositionException ope) {
			// Ignore Exception as it is expected when positions are occupied
		}
		try {
			traceAttempt = new TraceState(traceState,row,eastCol);
			stateStore.store(traceAttempt);
		}
		catch (OccupiedPositionException ope) {
			// Ignore Exception as it is expected when positions are occupied
		}
		try {
			traceAttempt = new TraceState(traceState,southRow,col);
			stateStore.store(traceAttempt);
		}
		catch (OccupiedPositionException ope) {
			// Ignore Exception as it is expected when positions are occupied
		}
		try {
			traceAttempt = new TraceState(traceState,row,westCol);
			stateStore.store(traceAttempt);
		}
		catch (OccupiedPositionException ope) {
			// Ignore Exception as it is expected when positions are occupied
		}
	}
} // class CircuitTracer
