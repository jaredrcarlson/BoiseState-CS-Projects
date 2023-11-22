import java.io.*;
import java.util.Scanner;

/**
 * The Class MagicSquare is used to validate a magic square from a text file or to write a magic square to a text file.
 * @author jcarlson
 */
public class MagicSquare {
	/** The input or output filename */
	private String filename;
	
	/** The matrix size */
	private int matrixSize;
	
	/** The magic square sum */
	private int magicSum;
	
	/** The integers matrix */
	private int[][] matrix;
	
	/** The matrix in String form */
	private String[][] strMatrix;
	
	/** Set to true if this instance is a magic square */
	private boolean isMagicSquare;
		
	/**
	 * Instantiates a new magic square object and tests input file
	 *
	 * @param filename - The input filename
	 */
	public MagicSquare(String filename) {
		// Set filename to user-specified input filename
		this.filename = filename;
		
		// Validate input file and store it's contents
		readMatrix();
		
		// Compute Magic Sum  --  Magic Sum = n(n^2 + 1) / 2  --  Formula Reference URL = http://en.wikipedia.org/wiki/Magic_square
		int n = matrixSize; // using n for matrixSize to simplify the expression for Magic Sum formula
		magicSum = n * (n * n + 1) / 2;
		
		// Determine if Matrix Values form a Magic Square
		if(checkMatrix()) {
			isMagicSquare = true;
		}
		else {
			isMagicSquare = false;
		}
	}
	
	/**
	 * Instantiates a new magic square object and writes a magic square of given size to output text filename
	 *
	 * @param filename - The output filename
	 * @param matrixSize - The magic square matrix size
	 */
	public MagicSquare(String filename, int matrixSize) {
		// Set filename to user-specified output filename
		this.filename = filename;
		
		// Set matrixSize to user-specified matrix size
		this.matrixSize = matrixSize;
				
		// Create Magic Square matrix using matrixSize field
		createMagicSquare();
		
		// Attempt to write Magic Square matrix to output file
		writeMatrix();	
	}
	
	/**
	 * Validates input file and stores values in an array of Strings
	 */
	private void readMatrix() {
		// Instantiate Scanner object for reading input file
		Scanner fileIn;
		
		// Attempt to open input file
		try {
			fileIn = new Scanner(new File(this.filename)); // Initialize Scanner object by passing input file to constructor
			String strMatrixSize = fileIn.next(); // Read matrix size from input file
			
			// Validate matrix size making sure it is an Integer value
			if(validateInteger(strMatrixSize)) {
				matrixSize = Integer.parseInt(strMatrixSize); // Set matrixSize field
				strMatrix = new String[matrixSize][matrixSize]; // Initialize matrix array of String values
				for(int row=0; row < matrixSize; row++) { // Step through each row
					for(int col=0; col < matrixSize; col++) { // Step through each row element
						strMatrix[row][col] = fileIn.next(); // Store each element
					}
				}
				fileIn.close(); // Close Scanner object
			}
			else {
				System.out.println("Error! - Matrix Size Invalid (Must be an Integer)"); // Display error message
				fileIn.close(); // Close Scanner object
				System.exit(2); // Exit Status 2 - Matrix size invalid
			}
		}
		catch (FileNotFoundException e) {
			System.out.println("Error! - Input file not found!\n"); // Display error message
			System.exit(1); // Exit Status 1 - Input file not found
		}
	}
	
	/**
	 * Verifies that all matrix String values hold Integers
	 *
	 * @return true, if successful
	 */
	private boolean checkMatrix() {
		// Verify that all matrix values are valid Integers
		for(String[] strArr : strMatrix) { // Step through each array of Strings
			for(String s : strArr) { // Step through each String element
				if(!validateInteger(s)) { // Verify that each element is an Integer
					return false;
				}
			}
		} // All values are valid Integers
		
		// Convert and copy matrix String values to Integer values
		convertStrMatrixToMatrix();
		
		// Verify that all matrix values are sequential and are within the valid range (Valid range = [1 ... n^2])
		if(!validateMatrixValues()) {
			return false;
		}
		
		// Verify that all row, column, and diagonal sums are equal to the magic square sum
		if(!validateMagicSum()) {
			return false;
		}
		
		// Matrix values have passed all the required Magic Square checks
		return true;
	}
	
	/**
	 * Verifies that a String value holds an Integer
	 *
	 * @param str - The String to be verified
	 * @return true, if successful
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
	
	/**
	 * Converts and copies matrix String values to matrix Integer values
	 */
	private void convertStrMatrixToMatrix() {
		// Initialize Integer matrix array
		matrix = new int[matrixSize][matrixSize];
		
		// Convert and copy String values to Integer values
		for(int row=0; row < matrixSize; row++) { // Step through each row
			for(int col=0; col < matrixSize; col++) { // Step though each row element
				matrix[row][col] = Integer.parseInt(strMatrix[row][col]); // Convert and copy each element
			}
		}
	}
	
	/**
	 * Validates all matrix values making sure they are sequential and within the valid range for the size of the matrix
	 *
	 * @return true, if successful
	 */
	private boolean validateMatrixValues() {
		int minValue = 1; // Minimum Integer value for a magic square = 1
		int maxValue = matrixSize * matrixSize; // Maximum Integer value for a magic square = n^2
		int[] matrixValues = new int[maxValue]; // Initialize single dimension array
		int index=0;
		
		// Convert matrix values to single dimension array to simplify validation operations
		for(int[] intArr : matrix) { // Step through each array of Integers
			for(int i : intArr) { // Step through each array element
				matrixValues[index] = i; // Copy each element to single dimension array
				index++;
			}
		}
		
		// Make sure all values are within range [1 ... n^2]
		for(int i : matrixValues) { // Step through each array element
			if( (i < minValue) || (i > maxValue) ) { // Check that each element is within valid range
				return false;
			}
		}
		
		// Search for duplicate values
		for(int i=0; i < matrixValues.length - 1; i++) { // Step through all but the last array element
			for(int j= i + 1; j < matrixValues.length; j++) { // Step through all remaining elements
				if(matrixValues[i] == matrixValues[j]) { // Check that each remaining element is not a duplicate of current element
					return false;
				}
			}
		}
		
		// All values are within the valid range and no duplicates were found, so they must be sequential.
		return true;
	}
	
	/**
	 * Verifies that all row, column, and diagonal sums are equal to the Magic Sum
	 * 
	 * @return true, if successful
	 */
	private boolean validateMagicSum() {
		// Verify that all row, column, and diagonal sums are equal to the Magic Sum
		if(checkRowSums() && checkColSums() && checkDiagSums()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	
	/**
	 * Verifies that each row sum is equal to Magic Sum
	 * 
	 * @return true, if successful
	 */
	private boolean checkRowSums() {
		// Verify that all row sums are equal to the Magic Sum
		for(int row=0; row < matrixSize; row++) { // Step through each row
			int sum = 0;
			for(int col=0; col < matrixSize; col++) { // Step through each row element
				sum += matrix[row][col]; // Add row element to total sum
			}
			if(sum != magicSum) { // Row sum does not equal magic sum!
				return false;
			}
		}
		
		// All of the row sums have been validated
		return true;
	}
	
	/**
	 * Verifies that each column sum is equal to Magic Sum
	 * 
	 * @return true, if successful
	 */
	private boolean checkColSums() {
		// Verify that all column sums are equal to the Magic Sum
		for(int col=0; col < matrixSize; col++) { // Step through each column
			int sum = 0;
			for(int row=0; row < matrixSize; row++) { // Step through each column element
				sum += matrix[row][col]; // Add column element to total sum
			}
			if(sum != magicSum) { // Column sum does not equal magic sum!
				return false;
			}
		}
		
		// All of the column sums have been validated
		return true;
	}
	
	/**
	 * Verifies that each diagonal sum is equal to Magic Sum
	 * 
	 * @return true, if successful
	 */
	private boolean checkDiagSums() {
		int sum = 0;
		int col = 0;
		// Verify that the Top-Left to Bottom-Right diagonal sum is equal to the Magic Sum
		for(int row=0; row < matrixSize; row++) { // Step through each row (ascending)
			sum += matrix[row][col]; // Add diagonal element to total sum
			col++; // Increment column to correctly locate next diagonal element
		}
		if(sum != magicSum) { // Diagonal sum does not equal magic sum!
			return false;
		}
		
		sum = 0;
		col = 0;
		// Verify that the Bottom-Left to Top-Right diagonal sum is equal to the Magic Sum
		for(int row = matrixSize - 1; row >= 0; row--) { // Step through each row (descending)
			sum += matrix[row][col]; // Add diagonal element to total sum
			col++; // Increment column to correctly locate next diagonal element
		}
		if(sum != magicSum) { // Diagonal sum does not equal magic sum!
			return false;
		}
		
		// Both diagonal sums have been validated
		return true;
	}
	
	/**
	 * Creates a magic square with proper dimension based on the matrix size
	 */
	private void createMagicSquare() {
		// Using n for matrixSize and k for matrix values to simplify pseudo-code translation
		int n = matrixSize;
		int row = n - 1; // Starting row is set to last row
		int col = n / 2; // Starting column is set to center column
		
		// Initialize matrix
		matrix = new int[n][n];
		
		// Translate given pseudo-code to algorithm in Java syntax
		for(int k=1; k <= n * n; k++) { // For values k [ 1 ... n^2 ]
			matrix[row][col] = k; // Place k at [row][column]
			int row_original = row; // Keep track of previous row index
			int col_original = col; // Keep track of previous column index
			row++; // Increment row index
			col++; // Increment column index
			if(row == n) { // If row index is out of bounds...
				row = 0;   // set row index to zero
			}
			if(col == n) { // If column index is out of bounds...
				col = 0;   // set column index to zero
			}
			if(matrix[row][col] != 0) { // If element at [row][column] is already filled...     
				row = row_original;		// set row index to previous value...
				col = col_original;     // set column index to previous value...
				row--;     				// and decrement row index 
			}
		}
		
		// Magic Square created
		isMagicSquare = true;
	}
	
	/**
	 * Writes the created magic square to the output text file with proper formatting
	 */
	private void writeMatrix() {
		// Instantiate File object passing user-defined output filename to the constructor
		File outputFile = new File(this.filename);
		
		// Attempt to open output file for writing - Display error message if unsuccessful
		try {
			// Instantiate FileOutputStream object for writing to output file
			FileOutputStream fileOut = new FileOutputStream(outputFile);
			
			// Create output file if it doesn't already exist - Display error if unsuccessful
			if(!outputFile.exists()) {
				try {
					outputFile.createNewFile(); // Create output file
				}
				catch (IOException e) {
					System.out.println("Error! - Unable to create the output file"); // Display error message
					System.exit(4); // Exit Status 4 - Unable to create output file
				}
			}
			
			// Instantiate StringBuilder object for creating complete text to be written to output file
			StringBuilder output = new StringBuilder(Integer.toString(matrixSize)); // Pass "matrix size" value to StringBuilder constructor (first line of output file)
			
			// Append matrix values to text to be written to output file
			for(int row=0; row < matrixSize; row++) { // Step through each row
				output.append("\n"); // Append newline character (begin each row on a new line)
				for(int col=0; col < matrixSize; col++) { // Step through each row element
					output.append(matrix[row][col] + " "); // Append element followed by a space
				}
			} // Complete text has now been built and is ready to be written to output file
			
			// Encode complete text String into a sequence of bytes and store result in an array of bytes
			byte[] outputInBytes = output.toString().getBytes();
			
			// Attempt to write complete text to output file - Display error message if unsuccessful
			try {
				fileOut.write(outputInBytes); // Write complete text (byte form) to output file
				fileOut.close(); // close FileOutputStream
			}
			catch (IOException e) {
				System.out.println("Error! - Unable to write to the output file"); // Display error message
				System.exit(5); // Exit Status 5 - Unable to access/write to output file
			}	
		}
		catch (FileNotFoundException e) {
			System.out.println("Error! - Unable to locate the output file"); // Display error message
			System.exit(3); // Exit Status 3 - Output file not found
		}
	}
	
	/**
	 * Displays matrix values and reports whether or not they form a magic square - Overriding default toString() method
	 */
	public String toString() {
		// Instantiate StringBuilder object for creating complete text to be displayed
		StringBuilder output = new StringBuilder("The matrix:\n\n"); // Pass first line of text to the StringBuilder constructor
		
		// Append matrix values to text to be displayed
		for(int row=0; row < matrixSize; row++) { // Step through each row
			for(int col=0; col < matrixSize; col++) { // Step through each row element
				output.append(String.format("%3s", matrix[row][col])); // Append formatted element (3 characters - Left Padded)
			}
			output.append("\n"); // Append newline character (move to next line)
		}
		
		// Append final line to text to be displayed
		if(isMagicSquare) {
			output.append("\nis a magic square."); // Append if magic square has been detected
		}
		else {
			output.append("\nis not a magic square."); // Append if magic square has not been detected
		}
		
		// Return complete text String to caller
		return output.toString();
	}	
}
