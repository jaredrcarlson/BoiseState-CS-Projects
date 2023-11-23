import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * The Test class is used to test the implementation of the Cache class.
 * Command Line Arguments must be used to specify desired cache level, cache size(s), and input file name.
 * Cache objects are instantiated and used to store and retrieve words (String objects) that are
 * parsed from the input file. Cache references, hits, and hit ratios are displayed upon completion.
 * 
 * @author jcarlson
 *
 */
public class Test {
	
	/** Used to toggle on/off visual debug mode */
	private static final boolean DEBUG_MODE = false;
	
	/** Optional delay period (in seconds) between each reference to cache - Used in visual debug mode */
	private static final int DEBUG_MODE_SECONDS_BETWEEN_SEARCHES = 5;
	
	/** Variables used to store the total number of hits and references for all cache storage */
	private static int nh, nr;
	
	/** Variables used to store the number of hits and references for individual cache storages */
	private static int nh1, nh2, nr1, nr2;
	
	/** User-defined cache level - Used to create One or Two level cache storage */
	private static int cacheLevel;
	
	/** User-defined cache size(s) */
	private static int cacheSize1, cacheSize2;
	
	/** Variables used to store global and cache level hit ratios */
	private static double hr, hr1, hr2;
	
	/** User-defined input file name to be parsed */
	private static String filename;
	
	/** Level-One cache storage object */
	private static Cache<String> cache1 = null;
	
	/** Level-Two cache storage object */
	private static Cache<String> cache2 = null;
	
	/** Used to read input file line-by-line */
	private static Scanner inputFile = null;
	
	/** Used to parse each line retrieved from the 'inputFile' scanner object */
	private static StringTokenizer lineOfTokens = null;
	
	/**
	 * Main method - Makes use of several private methods to test Cache class functionality
	 *
	 * @param args Command Line Arguments
	 */
	public static void main(String[] args) {
		validateAndParseCommandLineArguments(args);
		hr = hr1 = hr2 = nh = nh1 = nh2 = nr = nr1 = nr2 = 0;
		openInputFile();
		instantiateCache();
		processInputFile();
		calculateCacheStatistics();
		displayCacheStatistics();
	}
	
	/**
	 * Validates and parses command line arguments
	 *
	 * @param args - User-defined command line arguments
	 */
	private static void validateAndParseCommandLineArguments(String[] args) {
		if( (Integer.parseInt(args[0]) != 1) && (Integer.parseInt(args[0]) != 2) ) {
			System.out.println("Error: Invalid cache level argument.");
			displayProgramUsage();
			System.exit(2); // Invalid cache level argument
		}
		else {
			cacheLevel = Integer.parseInt(args[0]);
			try {
				cacheSize1 = Integer.parseInt(args[1]);
			} catch (NumberFormatException nf) {
				System.out.println("Error: Invalid cache size argument.");
				displayProgramUsage();
				System.exit(3); // Invalid cache size argument
			}
			if(cacheLevel == 1) {
				if(args.length != 3) {
					System.out.println("Error: Invalid number of command line arguments.");
					displayProgramUsage();
					System.exit(1); // Invalid number of command line arguments
				}
				filename = args[2];
			}
			else {
				try {
					cacheSize2 = Integer.parseInt(args[2]);
				} catch (NumberFormatException nf) {
					System.out.println("Error: Invalid cache size argument.");
					displayProgramUsage();
					System.exit(3); // Invalid cache size argument
				}
				if(args.length != 4) {
					System.out.println("Error: Invalid number of command line arguments.");
					displayProgramUsage();
					System.exit(1); // Invalid number of command line arguments
				}
				else if(cacheSize2 <= cacheSize1) {
					System.out.println("Error: 2nd-Level cache must be larger than 1st-Level cache.");
					System.exit(4); // Invalid cache2 size (cache2 is not larger than cache1)
				}
				filename = args[3];
			}
		}
	}
	
	/**
	 * Displays program usage instructions - Used when errors are found in supplied command line arguments
	 */
	private static void displayProgramUsage() {
		System.out.println("\nProgram Usage:");
		System.out.println("----------------------------------------------------------------------------------------------------");
		System.out.println("One-Level Cache: java Test 1 <cache size> <input textfile name>");
		System.out.println("Example: java Test 1 1000 myfile.txt\n");
		System.out.println("Two-Level Cache: java Test 2 <1st-level cache size> <2nd-level cache size> <input textfile name>");
		System.out.println("Example: java Test 2 1000 2000 myfile.txt");
		System.out.println("----------------------------------------------------------------------------------------------------");
	}
	
	/**
	 * Opens the user-defined input file
	 */
	private static void openInputFile() {
		try {
			inputFile = new Scanner(new File(filename));
		} catch (FileNotFoundException fnf) {
			System.out.println("Error: File Not Found!");
			System.exit(5); // File Not Found
		}
	}
	
	/**
	 * Instantiates cache(s) based on user-defined cache level
	 */
	private static void instantiateCache() {
		cache1 = new Cache<String>(cacheSize1);
		System.out.println("First level cache with " + cacheSize1 + " entries has been created");
		if(cacheLevel == 2) {
			cache2 = new Cache<String>(cacheSize2);
			System.out.println("Second level cache with " + cacheSize2 + " entries has been created");
		}
	}
	
	/**
	 * Processes the input file - Input file is scanned line-by-line, each line being parsed for individual
	 *                            String objects. References to cache are made for each String object and
	 *                            reference/hit counters are updated as necessary. MRU items are updated as
	 *                            needed.
	 */
	private static void processInputFile() {
		String nextWord, retrievedData;
		
		while(inputFile.hasNextLine()) {
			lineOfTokens = new StringTokenizer(inputFile.nextLine()," \t");
			if(cacheLevel == 1) {
		    	while(lineOfTokens.hasMoreTokens()) {
		    		nextWord = lineOfTokens.nextToken();
		    		nr++;
		    		if(DEBUG_MODE) {
		    			System.out.println("\n\n* Current Cache State *");
		    			System.out.println("--------------------------------------------------------------------------------------------------");
		    			System.out.print("Cache - ");
		    			displayCacheContents(cache1);
		    			System.out.println("--------------------------------------------------------------------------------------------------");
		    			System.out.println("...Searching Cache for \"" + nextWord + "\"");
		    		}
		    		
		    		retrievedData = retrieveFromCache(cache1, nextWord);
		    		nr1++;
			    	if(retrievedData != null && retrievedData.compareTo(nextWord) == 0) {
			    		nh1++;
			    		
			    		if(DEBUG_MODE)
			    			System.out.println("HIT! - " + nextWord + " retrieved from Cache.");
			    		
			    		processData(retrievedData);
			    	}
			    	else {
			    		retrievedData = retrieveFromDisk(nextWord);
			    		addToCache(cache1, nextWord);
			    		
			    		if(DEBUG_MODE)
		    				System.out.println("......adding \"" + nextWord + "\" to Cache.");
			    		
			    		processData(retrievedData);
			    	}
			    	
			    	if(DEBUG_MODE) {
		    			try {
							Thread.sleep(DEBUG_MODE_SECONDS_BETWEEN_SEARCHES * 1000);
						} catch (InterruptedException e) {
							// Planned delay - Ignoring Exception
						}
		    		}
			    	
			    }   	
		    }
		    else {
		    	while(lineOfTokens.hasMoreTokens()) {
		    		nextWord = lineOfTokens.nextToken();
		    		nr++;
		    		if(DEBUG_MODE) {
		    			System.out.println("\n\n* Current Cache State *");
		    			System.out.println("--------------------------------------------------------------------------------------------------");
		    			System.out.print("Cache1 - ");
		    			displayCacheContents(cache1);
		    			System.out.print("Cache2 - ");
		    			displayCacheContents(cache2);
		    			System.out.println("--------------------------------------------------------------------------------------------------");
		    			System.out.println("...Searching Cache for \"" + nextWord + "\"");
		    		}
		    		
			    	retrievedData = retrieveFromCache(cache1, nextWord);
			    	nr1++;
		    		if(retrievedData != null && retrievedData.compareTo(nextWord) == 0) {
			    		nh1++;
			    		
			    		if(DEBUG_MODE)
			    			System.out.println("HIT! - \"" + nextWord + "\" retrieved from Cache Level 1.");
			    		
			    		updateCache(cache2, nextWord);
			    		
			    		if(DEBUG_MODE)
			    			System.out.println("......updating MRU in Cache2.");
			    		
			    		processData(retrievedData);
			    	}
			    	else {
			    		retrievedData = retrieveFromCache(cache2, nextWord);
			    		nr2++;
			    		
			    		if(retrievedData != null && retrievedData.compareTo(nextWord) == 0) {
			    			nh2++;
			    			
			    			if(DEBUG_MODE)
			    				System.out.println("HIT! - \"" + nextWord + "\" retrieved from Cache Level 2.");
			    			
			    			addToCache(cache1, nextWord);
			    			
			    			if(DEBUG_MODE)
			    				System.out.println("......adding \"" + nextWord + "\" to Cache1.");
			    			
				    		processData(retrievedData);
			    		}
			    		else {
			    			retrievedData = retrieveFromDisk(nextWord);
			    			addToCache(cache1, nextWord);
			    			
			    			if(DEBUG_MODE)
			    				System.out.println("......adding \"" + nextWord + "\" to Cache1.");
			    			
			    			addToCache(cache2, nextWord);
			    			
			    			if(DEBUG_MODE)
			    				System.out.println("......adding \"" + nextWord + "\" to Cache2.");
			    			
			    			processData(retrievedData);
			    		}
		    		}
		    		
		    		if(DEBUG_MODE) {
		    			try {
							Thread.sleep(DEBUG_MODE_SECONDS_BETWEEN_SEARCHES * 1000);
						} catch (InterruptedException e) {
							// Planned delay - Ignoring Exception
						}
		    		}
		    		
		    	}
		    }
		}
		if(DEBUG_MODE) {
    		System.out.println("\n\n*** Final Cache State ***");
    		System.out.println("--------------------------------------------------------------------------------------------------");
			System.out.print("Cache1 - ");
			displayCacheContents(cache1);
            if(cacheLevel == 2) {
			    System.out.print("Cache2 - ");
			    displayCacheContents(cache2);
            }
			System.out.println("--------------------------------------------------------------------------------------------------");
    	}
	}
	
	/**
	 * Displays the contents of a single cache storage
	 *
	 * @param cache the cache
	 */
	private static void displayCacheContents(Cache<String> cache) {
		System.out.println(cache.toString());
	}
	    	
	/**
	 * Retrieves a word (String) from cache or return null if not found in cache
	 *
	 * @param cache the cache to be searched
	 * @param word the word to search for
	 * @return returns the word searched for if found in cache, returns null if not found
	 */
	private static String retrieveFromCache(Cache<String> cache, String word) {
		return cache.getObject(word);
	}
	
	/**
	 * Adds a word to a single cache storage
	 *
	 * @param cache the cache to add the word to
	 * @param word the word to be added
	 */
	private static void addToCache(Cache<String> cache, String word) {
		cache.addObject(word);
	}
	
	/**
	 * Updates the MRU item for a single cache storage using the word parameter supplied
	 *
	 * @param cache the cache storage to be updated
	 * @param word the most recently used word  
	 */
	private static void updateCache(Cache<String> cache, String word) {
		cache.removeObject(word);
		cache.addObject(word);
	}
	
	/**
	 * Retrieves a word from disk
	 *
	 * @param word the word to retrieve
	 * @return returns the word retrieved from disk
	 */
	private static String retrieveFromDisk(String word) {
		// Retrieve data from Hard Disk
		String wordFromDisk = word;
		return wordFromDisk;
	}
	
	/**
	 * Processes the word within this application (i.e. Makes use of the word)
	 *
	 * @param word the word to process
	 */
	private static void processData(String word) {
		// Make use of data in Application
	}
	
	/**
	 * Calculates the reference, hit, and ratio statistics for cache usage
	 */
	private static void calculateCacheStatistics() {
		if(cacheLevel == 1) {
			nh = nh1;
			hr1 = nh1 / (double)nr1;
			hr = nh / (double)nr;	
    	}
    	else {
    		nh = nh1 + nh2;
    		hr1 = nh1 / (double)nr1;
    		hr2 = nh2 / (double)nr2;
    		hr = (nh1 + nh2) / (double)nr1;
    	}
	}
	
	/**
	 * Displays the cache usage statistics
	 */
	private static void displayCacheStatistics() {
		System.out.println("..............................");
		System.out.println("Total number of references: " + nr);
		System.out.println("Total number of cache hits: " + nh);
		System.out.println("The global hit ratio: " + hr);
		System.out.println("Number of 1st-level cache hits: " + nh1); 
		System.out.println("1st-level cache hit ratio " + hr1);
		if(cacheLevel == 2) {
			System.out.println("Number of 2nd-level cache hits: " + nh2); 
			System.out.println("2nd-level cache hit ratio " + hr2);
		}
	}
}
