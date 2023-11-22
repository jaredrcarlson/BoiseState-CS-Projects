import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Represents a 2D circuit board as read from an input file.
 *  
 * @author mvail
 */
public class CircuitBoard {
	private char[][] board;
	private Point startingPoint;
	private Point endingPoint;

	//constants you may find useful
	private final int ROWS; //initialized in constructor
	private final int COLS; //initialized in constructor
	private final char OPEN = 'O';
	//private final char CLOSED = 'X';
	private final char TRACE = 'T';
	private final char START = '1';
	private final char END = '2';
	private final String ALLOWED_CHARS = "OXT12";

	/** Construct a CircuitBoard from a given board input file, where the first
	 * line contains the number of rows and columns as ints and each subsequent
	 * line is one row of characters representing the contents of that position.
	 * Valid characters are as follows:
	 *  'O' an open position
	 *  'X' an occupied, unavailable position
	 *  '1' first of two components needing to be connected
	 *  '2' second of two components needing to be connected
	 *  'T' is not expected in input files - represents part of the trace
	 *   connecting components 1 and 2 in the solution
	 * 
	 * @param filename file containing a grid of characters
	 * @throws FileNotFoundException
	 * @throws InvalidFileFormatException
	 */
	public CircuitBoard(String filename) throws FileNotFoundException, InvalidFileFormatException {
		// Validate input file and ROWS/COLS sizes
		File inputFile = new File(filename);
		if(!inputFile.canRead()) {
			throw new FileNotFoundException("Cannot Read Input File!");
		}
		else {
			// Initialize Scanner object passing input file to constructor
			Scanner fileScan = new Scanner(inputFile);
			
			// Read ROWS and COLS sizes from input file
			String rows = fileScan.next(); // Read row size from input file
			String cols = fileScan.next(); // Read column size from input file
			
			// Validate ROWS and COLS sizes - Expected type Integer
			if(validateInteger(rows)) {
				this.ROWS = Integer.parseInt(rows); // Set ROWS field
			}
			else {
				fileScan.close(); // Close Scanner object
				throw new InvalidFileFormatException("Cannot Parse Input File! Row Size invalid - Expected type Integer"); // Throw exception
			}
			if(validateInteger(cols)) {
				this.COLS = Integer.parseInt(cols); // Set COLS field
			}
			else {
				fileScan.close(); // Close Scanner object
				throw new InvalidFileFormatException("Cannot Parse Input File! Column Size invalid - Expected type Integer");// Throw exception
			}
			
			// Parse circuit board components to populate char[][]
			board = new char[ROWS][COLS];
			parseInputFile(fileScan);
			
			// Verify circuit board contains exactly 1 starting point and 1 ending point
			int numOfStartPoints = 0;
			int numOfEndPoints = 0;
			for(int row = 0; row < ROWS; row++) { // Step through board rows
				for(int col = 0; col < COLS; col++) { // Step through board cols
					if(board[row][col] == '1') {
						numOfStartPoints++; // Keep count of number of Start positions detected
					}
					else if(board[row][col] == '2') {
						numOfEndPoints++; // Keep count of number of End positions detected
					}
				}
			}
			
			// If board does not contain one single start point and one single end point, then throw exception
			if( (numOfStartPoints != 1) || (numOfEndPoints != 1) ) {
				throw new InvalidFileFormatException("Invalid Board! - Found multiple start or end points");// Throw exception
			}
		}
	}
	
	/** Copy constructor - duplicates original board
	 * 
	 * @param original board to copy
	 */
	public CircuitBoard(CircuitBoard original) {
		board = original.getBoard();
		startingPoint = new Point(original.startingPoint);
		endingPoint = new Point(original.endingPoint);
		ROWS = original.numRows();
		COLS = original.numCols();
	}

	/** utility method for copy constructor
	 * @return copy of board array */
	private char[][] getBoard() {
		char[][] copy = new char[board.length][board[0].length];
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				copy[row][col] = board[row][col];
			}
		}
		return copy;
	}
	
	/** Return the char at board position x,y
	 * @param row row coordinate
	 * @param col col coordinate
	 * @return char at row, col
	 */
	public char charAt(int row, int col) {
		return board[row][col];
	}
	
	/** Return whether given board position is open
	 * @param row
	 * @param col
	 * @return true if position at (row, col) is open 
	 */
	public boolean isOpen(int row, int col) {
		if (row < 0 || row >= board.length || col < 0 || col >= board[row].length) {
			return false;
		}
		return board[row][col] == OPEN;
	}
	
	/** Set given position to be a 'T'
	 * @param row
	 * @param col
	 * @throws OccupiedPositionException if given position is not open
	 */
	public void makeTrace(int row, int col) {
		if (isOpen(row, col)) {
			board[row][col] = TRACE;
		}
		else {
			if(row < 0 || row >= board.length || col < 0 || col >= board[row].length) {
				throw new OccupiedPositionException("row or col is off the circuit board!");
			}
			else {
				throw new OccupiedPositionException("row " + row + ", col " + col + "contains '" + board[row][col] + "'");
				
			}
		}
	}
	
	/** @return starting Point */
	public Point getStartingPoint() {
		return new Point(startingPoint);
	}
	
	/** @return ending Point */
	public Point getEndingPoint() {
		return new Point(endingPoint);
	}
	
	/** @return number of rows in this CircuitBoard */
	public int numRows() {
		return ROWS;
	}
	
	/** @return number of columns in this CircuitBoard */
	public int numCols() {
		return COLS;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				str.append(board[row][col] + " ");
			}
			str.append("\n");
		}
		return str.toString();
	}
	
	/**
	 * Parses input file
	 * @throws InvalidFileFormatException
	 * @param fileScan scanner to use for parsing
	 */
	private void parseInputFile(Scanner fileScan) throws InvalidFileFormatException {
		// Validate and store circuit board components
		String tempString;
		char tempChar;
		for(int row = 0; row < this.ROWS; row++) { // Step through each row in board
			for(int col = 0; col < this.COLS; col++) { // Step through each col in board
				tempString = fileScan.next(); // Get next component
				// Check for invalid length or invalid characters
				if( (tempString.length() != 1) || (tempString.equals("T")) || (!ALLOWED_CHARS.contains(tempString)) ) {
					throw new InvalidFileFormatException("Cannot Parse Input File! Invalid Character Found");
				}
				else {
					tempChar = tempString.charAt(0);
					
					// Detect and set Start and End points
					if(tempChar == START) {
						startingPoint = new Point(row,col);
					}
					else if(tempChar == END) {
						endingPoint = new Point(row,col);
					}
					board[row][col] = tempChar; // Store valid character
				}
			}
		}
		fileScan.close(); // Close Scanner object
	}
	
	/**
	 * Verifies that a String value holds an Integer
	 *
	 * @param str The String to be verified
	 * @return true, if str contains valid integer
	 */
    private boolean validateInteger(String str) {
		// Verify that the String value passed in holds a valid Integer value
		try { 
	        Integer.parseInt(str); // Successful if String holds a valid Integer value
	        return true;
		}
		catch(NumberFormatException e) { // Number Format Exception if String does not hold a valid Integer value
	        return false; 
	    }
	}
	
}// class CircuitBoard
