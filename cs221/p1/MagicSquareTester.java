/**
 * The Class MagicSquareTester is a driver class that tests the functionality of the MagicSquare class.
 * @author jcarlson
 */
public class MagicSquareTester {
	/** The Magic Square used to check input file or create output file	*/
	private static MagicSquare mySquare;
	
	/**
	 * The main method - Execution begins with the main method.
	 *
	 * @param args - The command line arguments
	 */
	public static void main(String[] args) {
		// Validate command line arguments - Display program usage information if command line arguments are invalid
		if(validateCommandLineArgs(args)) {
			// Determine program execution mode (-check or -create)
			if(args[0].equals("-check")) {
				mySquare = new MagicSquare(args[1]); // Pass input filename to MagicSquare constructor to invoke -check mode
				System.out.println(mySquare.toString()); // Display results for user-specified input file
			}
			else {
				mySquare = new MagicSquare(args[1], Integer.parseInt(args[2])); // Pass output filename and "matrix size" to MagicSquare constructor to invoke -create mode
			}
		}
		else {
			displayCommandLineArgsUsage(); // Display program usage information
		}
	}
	
	/**
	 * Validates command line arguments.
	 *
	 * @param argsArr - The command line arguments array
	 * @return true, if command line arguments are valid
	 */
	private static boolean validateCommandLineArgs(String[] argsArr) {
		// Validate option, number of arguments, and filename - Display appropriate error message upon failure 
		if(argsArr[0].equals("-check")) {
			
			// Validate number of arguments for -check option
			if(argsArr.length == 2) {
				
				//Validate input filename
				if(validateFilename(argsArr[1])) {
					return true;
				}
				else {
					return false;
				}
			}
			else {
				System.out.println("Error! - Invalid number or command line arguments for the -check option.\n"); // Display error message
				System.out.println("The -check option should be followed by a filename (String).\n"); // Display error-specific tip
				return false;
			}
		}
		else if(argsArr[0].equals("-create")) {
			// Validate number of arguments for -create option
			if(argsArr.length == 3) {
				
				// Validate output filename
				if(validateFilename(argsArr[1])) {
					try {
					    int matrixSize = Integer.parseInt(argsArr[2]);
					    
					    // Verify that the size is an odd number
						if((matrixSize % 2) == 0) {
							System.out.println("Error! - Size argument must be an odd Integer.\n"); // Display error message
							return false;
						}
						else {
							return true;
						}
					}
					catch (NumberFormatException e) {
					   	System.out.println("Error! - \"" + argsArr[2] + "\" is not a valid size argument for the -create option.\n"); // Display error message
					   	System.out.println("You must enter a valid integer for the size argument.\n"); // Display error-specific tip
					   	return false;
					}
				}
				else {
					return false;
				}
			}
			else {
				System.out.println("Error! - Invalid number of command line arguments for the -create option.\n"); // Display error message
				System.out.println("The -create option should be followed by a filename (String) and a size (integer).\n"); // Display error-specific tip
				return false;
			}
		}
		else if(argsArr[0].equals("-h") || argsArr[0].equals("-help")) {
			return false; // Program usage and examples are displayed when method returns false
		}
		else {
			System.out.println("Error! - \"" + argsArr[0] + "\" is not a valid option.\n"); // Display error message
			System.out.println("Command line arguments are case-sensitive and should begin with either the -check or -create options.\n"); // Display error-specific tip
			return false;
		}
	}
	
	private static boolean validateFilename(String filename) {
		String badFilenameChars = "#%&{}<>*?$!'\":@+`|="; // List used to check for bad characters in user-specified filename
		
		// Check filename for any bad characters
		for(int i=0; i < filename.length(); i++) {
			if(badFilenameChars.contains(""+filename.charAt(i))) {
				System.out.println("Error! - Filename contains at least one bad character!\n"); // Display error message
				System.out.println("The character \'" + filename.charAt(i) + "\' should not be used in a filename.\n"); // Display error-specific tip
				return false;
			}
		}
		
		// Check that filename does not start with improper characters
		if( (filename.startsWith("-")) || (filename.startsWith("_")) ) {
			System.out.println("Error! - Filename should not start with a period, hyphen, or underscore!\n"); // Display error message
			return false;
		}
		
		else {
			return true;
		}
	}
	
	/**
	 * Displays program usage with command line argument examples
	 */
	private static void displayCommandLineArgsUsage() {
		System.out.println("=================================================================");
		System.out.println("*  Program Usage                                                *");
		System.out.println("=================================================================");
		System.out.println(" MagicSquareTester [-check | -create] [filename] [ |size]\n\n");
		System.out.println("=================================================================");
		System.out.println("*   Examples                                                    *");
		System.out.println("=================================================================");
		System.out.println(" MagicSquareTester -check myMatrix.txt");
		System.out.println(" MagicSquareTester -create myMagicSquare.txt 5\n");
	}
}
