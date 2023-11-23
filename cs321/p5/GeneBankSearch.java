import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class GeneBankSearch {

	static int cacheFlag;
	static int cacheSize = 0;
	static int DEBUG = 0;
	static String BTreeBinaryFileName;
	//TODO: Is this OK?
	static BTree tree;
	static long startTime;
	static long endTime;
	
	
	
	public static void main(String[] args) {	
		
		File queryFile;
		
		//--------------------------------------------- Handle input ---------------------------------------------------
		
		// If user inputs too many or too little arguments
		if (args.length < 3 || args.length > 5) {
			System.err.println("Inputs must be <0/1> <btree file> <query file> [<cache size>] [<debug level>]");
			System.exit(2);
		}
		
		// If user inputs 0 or 1 for Cache argument
		if (args[0].equals("0") || args[0].equals("1")) {
			if (args[0].equals("1")) {
				cacheFlag = 1;
				//Check 4th argument for Cache size
				try {
					cacheSize = Integer.parseInt(args[3]);
					if (cacheSize < 0) {
						System.out.println("Cache size must be positive and greater than zero.");
						System.exit(1);
					}
				}
				catch (NumberFormatException e) {
					System.out.println("Since you have made the choice of having a Cache, Cache size must be a number.");
					System.exit(1);
				}
			}
		}
		else {
			System.out.println("First argument must be <0/1>");
			System.exit(1);
		}
		
		
		// Open and check btree file
		BTreeBinaryFileName = args[1];
		
		// Open query file
		queryFile = new File(args[2]);
		
		
		// Check debug level. Must be an integer.
		// If user inputs no Cache argument, debug will be 4th argument if they choose to put in a debug value
		if (cacheFlag < 1) {
			//If user inputs debug value
			if (args.length == 4) {
				try {
					int temp = Integer.parseInt(args[3]);
					if (temp < 0 || temp > 0) {
						System.out.println("Debug level " + temp + " is not supported. 0 is supported.");
						System.exit(1);
					}
					DEBUG = temp;
				}
				catch (NumberFormatException e) {
					System.out.println("Debug level must be a number.");
					System.exit(1);
				}
			}
		}
		//If user has cache on, debug will be 5th argument if they choose to put in a debug value
		else {
			if (args.length == 5) {
				try {
					int temp = Integer.parseInt(args[4]);
					if (temp < 0 || temp > 0) {
						System.out.println("Debug level " + temp + " is not supported. 0 is supported.");
						System.exit(1);
					}
					DEBUG = temp;
				}
				catch (NumberFormatException e) {
					System.out.println("Debug level must be a number.");
					System.exit(1);
				}
			}
		}
		
		//----------------------------------------- END argument handling --------------------------------------------------
		
		//Make new BTree object
		//TODO: Are these the correct parameters?
		//               BTree(boolean cacheMode, String bTreeFilename, int cacheSize, int debugLevel)
		tree = new BTree(cacheFlag, BTreeBinaryFileName, cacheSize, DEBUG);
		
		// Call method for BTree to search queries and print out to stdout.
		parseQuery(queryFile);
		
	}
	
	public static void parseQuery(File queryFile) {
		startTime = System.nanoTime();
		try {
			Scanner scan = new Scanner(queryFile);
			
			String seq;
			int freq;
			while (scan.hasNext()) {
				
				seq = scan.next();
				seq = seq.toLowerCase();
				
				if (DEBUG==0) {
					//System.out.println("Sequence: " + seq + " Frequency: " + /*Btree.getKeyFrequency(seq)*/"");
					//TODO: This should work!
					freq = tree.getFrequency(seq);
					if(freq > 0) {
						System.out.println(seq + ": " + freq);
					}
				}
			}
			endTime = System.nanoTime();
			if (DEBUG==0) {
				System.out.println("Querrying completed in " + formatDuration((endTime - startTime) / 1000000000.0) + "\nNo errors detected.");
			}
			
			//TODO: I'm closing the scanner, and here's why:
			//      Eclipse's little yellow warning signs have the same effect on me
			//      as the five long annoying alarm bells my Honda Odyssey yells at me
			//      when I do not buckle my seat belt. I know, I need to see a doctor
			//      and get on some kind of "chill pill."  :)
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println("Could not open queryFile.");
		}
		
	}
	
	private static String formatDuration(double seconds) {
		DecimalFormat df = new DecimalFormat("#0.##");
		String hr, mn, sc;
		if(seconds < 60) {
			sc = df.format(seconds) + ((seconds == 1) ? " second" : " seconds");
			return sc;
		}
		else {
			int minutes = (int)(seconds / 60);
			seconds = seconds % 60;
			sc = df.format(seconds) + ((seconds == 1) ? " second" : " seconds");
			if(minutes < 60) {
				mn = minutes + ((minutes == 1) ? " minute, " : " minutes, ");
				return mn + sc;
			}
			else {
				int hours = minutes / 60;
				minutes = minutes % 60;
				mn = minutes + ((minutes == 1) ? " minute, " : " minutes, ");
				hr = hours + ((hours == 1) ? " hour, " : " hours, ");
				return hr + mn + sc;
			}
		}
	}

}
