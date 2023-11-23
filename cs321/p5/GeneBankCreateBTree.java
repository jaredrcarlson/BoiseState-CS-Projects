import java.io.*;


public class GeneBankCreateBTree {

	static int cacheFlag;
	static int cacheSize = 0;
	static int degree = 0;
	static int k = 0;
	static int DEBUG = 0;
	static String gbkFileName;
	
	public static void main(String[] args) {
		
		File genomeFile;

		//--------------------------------------------- Handle input ---------------------------------------------------
		
		// If user inputs too many or too little arguments
		if (args.length < 4 || args.length > 6) {
			System.err.println("Inputs must be <0/1> <degree> <gbk file> <sequence length>" 
					+ " [<cache size>] [<debug level>]");
			System.exit(2);
		}
		
		// If user inputs 0 or 1 for Cache argument
		if (args[0].equals("0") || args[0].equals("1")) {
			if (args[0].equals("1")) {
				cacheFlag = Integer.parseInt(args[0]);
				if(args.length < 5) {
					System.err.println("Must supply Cache size argument if Cache Option is 1");
					System.exit(1);
				}
				//Check 5th argument for Cache size
				try {
					cacheSize = Integer.parseInt(args[4]);
					if (cacheSize < 0) {
						System.err.println("Cache size must be positive and greater than zero.");
						System.exit(1);
					}
				}
				catch (NumberFormatException e) {
					System.err.println("Since you have made the choice of having a Cache, Cache size must be a number.");
					System.exit(1);
				}
			}
		}
		else {
			System.err.println("First argument must be <0/1>");
			System.exit(1);
		}
		
		
		// Check second argument degree, if 0, must calculate optimal degree. Must be an integer.
		try {
			degree = Integer.parseInt(args[1]);
			if (degree < 0) {
				System.err.println("Degree must be a positive integer.");
				System.exit(1);
			}
		}
		catch (NumberFormatException e) {
			System.err.println("Second input must be an integer value: <degree>");
			System.exit(1);
		}
		
		// Check third argument gbk file, store into file name string
			genomeFile = new File(args[2]);
			gbkFileName = args[2];
		
		// Check fourth argument: sequence length
		try {
			k = Integer.parseInt(args[3]);
			if (k > 31 || k < 1) {
				System.err.println("Sequence length must be bewteen 1 and 31, inclusive.");
				System.exit(1);
			}
		}
		catch (NumberFormatException e) {
			System.err.println("Sequence length must be an integer value.");
			System.exit(1);
		}
		
		
		// Check debug level. Must be an integer.
		// If user inputs no Cache argument, debug will be 5th argument if they choose to put in a debug value
		if (cacheFlag < 1) {
			//If user inputs debug value
			if (args.length == 5) {
				try {
					int temp = Integer.parseInt(args[4]);
					if (temp < 0 || temp > 2) {
						System.err.println("Debug level " + temp + " is not supported. 0, 1, and 2 are supported.");
						System.exit(1);
					}
					DEBUG = temp;
				}
				catch (NumberFormatException e) {
					System.err.println("Debug level must be a number.");
					System.exit(1);
				}
			}
		}
		//If user has cache on, debug will be 6th argument if they choose to put in a debug value
		else {
			if (args.length == 6) {
				try {
					int temp = Integer.parseInt(args[5]);
					if (temp < 0 || temp > 1) {
						System.err.println("Debug level " + temp + " is not supported. 0 and 1 are supported.");
						System.exit(1);
					}
					DEBUG = temp;
				}
				catch (NumberFormatException e) {
					System.err.println("Debug level must be a number.");
					System.exit(1);
				}
			}
		}
		
		//----------------------------------------- END argument handling --------------------------------------------------
		
		
		//----------------------------------------- Parse the genome file --------------------------------------------------
		parseFile(genomeFile, k);

	}

	public static void parseFile(File genomeFile, int subSeqLength) {
		
//		BTree tree = new BTree(); ****
		//TODO: I'm thinking this is the right place to declare.
		BTree tree = new BTree(cacheFlag, degree, gbkFileName, k, cacheSize, DEBUG);
		
		try {			
			BufferedReader br = new BufferedReader(new FileReader(genomeFile));
			
			// Loop through every section of DNA sequences until end of the file.
			while (br.readLine()!=null) {
			
				String temp = br.readLine();
			
				//Loop to ORIGIN
				while(!temp.equals("ORIGIN      ")) {
					temp = br.readLine();
					if (temp==null) {
						System.out.println(" done!");
						tree.close();
						System.exit(1);
					}
//					System.out.println(temp);
				}
			
			
				int c = br.read();
				String genomeSequence = "";
				
				//Store individual characters into a string until we reach "//"
				while (c!= '/') {
					//Store all of the characters into a string, handling upper case as well.
					if (c == 'A' || c == 'C' || c == 'T' || c == 'G' || c == 'a' || c == 'c' || c == 't' || c=='g' || c =='n' || c =='N') {
						genomeSequence = genomeSequence + (char)c;	// Might make them binary here
					}
				
					c = br.read();
				}
				

				br.readLine();
				
				// Now used the parsed Genome Sequence to feed subsequences of length k into the b tree class
				int i = 0;
				//boolean start = true;
				while(i<=genomeSequence.length()-subSeqLength) {
					
					String subSequence = "";
					int x = i;
					for(int z=0; z<subSeqLength; z++) {
						if (genomeSequence.charAt(x)!='N' && genomeSequence.charAt(x)!='n'){
							subSequence = subSequence + genomeSequence.charAt(x);
						}
						
						x++;
					}
					//System.out.println(subSequence);
					
					//Feed subsequence to b tree class here
					//If the subsequence length is not the user input value, don't send to b tree class
					if (subSequence.length() == subSeqLength) {
						/*if (start) {
							// Matt's : BTree tree = new Btree(degree, k, subSequence, gbkFileName, cacheSize, DEBUG);
							//TODO: Are these the correct parameters?
							//         BTree(boolean cacheMode, int degree, String gbkFilename, int sequenceLength, int cacheSize, int debugLevel)
							//tree = new BTree(cacheFlag, degree, gbkFileName, k, cacheSize, DEBUG);
						}
						else {
							//BTree.insertKey(subSequence);
							//TODO: I'm sure you'll probably move this initialization?
							//      but here's what it should look like.
							//tree = new BTree(cacheFlag, degree, gbkFileName, k, cacheSize, DEBUG);
							//tree.add(subSequence);
						}*/
						tree.add(subSequence);
					}
					
					i++;
					//start = false;
				}
				
				//System.out.println("\n Next Sequence: \n");
			}
			
			System.out.println("Closing buffer, No DNA sequences left.");
			br.close();
			
		}
		catch (IOException e) {
			System.err.println("Something went wrong.");
		}
		
	}
	
}
