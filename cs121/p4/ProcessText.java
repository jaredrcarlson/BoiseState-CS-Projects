import java.io.File;
import java.io.FileNotFoundException;

/**
 * Main driver class to validate command-line arguments, create Flesch Statistics objects, and display results.
 */
public class ProcessText {
	
	/**
	 * Checks to make sure a file name exists and can be accessed
	 * 
	 * @param file the File object to be validated
	 * @return true if the file exists and can be accessed for reading
	 */
	private static boolean validateFile(File file) {
		if(file.exists() && file.canRead()) {
				return true;
		}
		else {
				return false;
		}
	}
	
	/**
	 * Processes command-line arguments, creates Flesch Statistics objects, and displays results.
	 * @param args the String array used to store all file names passed in as command-line arguments.
	 */
	public static void main(String[] args) {
		// Process Command-Line Arguments
		if(args.length == 0) {
			System.out.println("Error: File name(s) must be provided as arguments.");
			System.out.println("ProcessText Usage: java ProcessText file1 [file2 ...]");
		}
		else {
			for(int i=0; i < args.length; i++) {
				String fileName = args[i];
				File file = new File(fileName);
				if(validateFile(file)) {
					// File exists and can be read
					FleschStatisticsInterface fleschStats = new FleschStatistics(file);
					System.out.println(fleschStats.toString());
				}
				else {
					// No such File or Unable to Read
					System.out.println("Error: \"" + fileName + "\" either doesn't exist or could not be read. (Check filename and read permissions)");
				}
			}
		}
	}
}

