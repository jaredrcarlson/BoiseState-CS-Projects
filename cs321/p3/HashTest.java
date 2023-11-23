import java.text.DecimalFormat;

/**
 * Tests the functionality of Linear/Double Hash probing
 * sequences implemented by the HashTable class
 * 
 * @author jcarlson
 *
 */
public class HashTest {
	private static final int DISPLAY_SUMMARY = 0;
	private static final int DISPLAY_TABLE = 1;
	private static final int DISPLAY_PROBE_COUNTS = 2;
	private static final int DISPLAY_PROBE_COUNTS_DELAY_IN_SECONDS = 5;
	private static final int DEFAULT_DEBUG_LEVEL = DISPLAY_SUMMARY;
	private static final int DISPLAY_README = 8675309;
	
	private final int TABLE_SIZE_MIN = 95500;
	private final int TABLE_SIZE_MAX = 96000;
	private final int LINEAR_HASHING = 1;
	private final int DOUBLE_HASHING = 2;
	private final int RANDOM_NUMBER = 1;
	private final int SYSTEM_TIME = 2;
	private final int TEXT_FILE = 3;
			
	private HashObjectSource source;
	private int inputType;
	private double loadFactor;
	private int debugLevel;
	private boolean displayProbeCounts = false;
	private int tableSize;
	private HashTable tableLinear;
	private HashTable tableDouble;
	private TwinPrimesFinder twinPF;
	
	private int totalInserted;
	private int totalDuplicates;
	private int totalProbesLinear;
	private double averageProbesLinear;
	private int totalProbesDouble;
	private double averageProbesDouble;
	
	
	public HashTest(int inputType, double loadFactor) {
		this(inputType, loadFactor, DEFAULT_DEBUG_LEVEL);
	}
	
	public HashTest(int inputType, double loadFactor, int debugLevel) {
		this.inputType = inputType;
		source = new HashObjectSource();
		this.loadFactor = loadFactor;
		this.debugLevel = debugLevel;
		if(debugLevel == DISPLAY_PROBE_COUNTS) {
			displayProbeCounts = true;
		}
		twinPF = new TwinPrimesFinder(TABLE_SIZE_MIN, TABLE_SIZE_MAX);
		tableSize = calculateTableSize();
		tableLinear = new HashTable(tableSize, LINEAR_HASHING, debugLevel);
		totalProbesLinear = 0;
		tableDouble = new HashTable(tableSize, DOUBLE_HASHING, debugLevel);
		totalProbesDouble = 0;
		totalDuplicates = 0;
	}
	
	public void run() {
		if(dataSourceIsRandom()) {
			while(tableLinear.getCurrentLoadFactor() < loadFactor) {
				int data = source.nextRandom();
				int hashCode = data;
				
				Integer dataLinear = new Integer(data);
				HashObject<Integer> newDataLinear = new HashObject<Integer>(dataLinear, RANDOM_NUMBER, tableSize, hashCode);
				tableLinear.insert(newDataLinear);
				
				Integer dataDouble = new Integer(data);
				HashObject<Integer> newDataDouble = new HashObject<Integer>(dataDouble, RANDOM_NUMBER, tableSize, hashCode);
				tableDouble.insert(newDataDouble);
				
				if(displayProbeCounts) {
					displayProbeCounts(newDataLinear, newDataDouble);
					delay();
				}
				
			}
		}
		else if(dataSourceIsSystemTime()) {
			while(tableLinear.getCurrentLoadFactor() < loadFactor) {
				long data = source.nextSystemTime();
				int hashCode = (int)data;
				
				Long dataLinear = new Long(data);
				HashObject<Long> newDataLinear = new HashObject<Long>(dataLinear, SYSTEM_TIME, tableSize, hashCode);
				tableLinear.insert(newDataLinear);
				
				Long dataDouble = new Long(data);
				HashObject<Long> newDataDouble = new HashObject<Long>(dataDouble, SYSTEM_TIME, tableSize, hashCode);
				tableDouble.insert(newDataDouble);
				
				if(displayProbeCounts) {
					displayProbeCounts(newDataLinear, newDataDouble);
					delay();
				}
			}
		}
		else {
			while(tableLinear.getCurrentLoadFactor() < loadFactor) {
				String data = source.nextWord();
				int hashCode = data.hashCode();
				
				HashObject<String> newDataLinear = new HashObject<String>(data, TEXT_FILE, tableSize, hashCode);
				tableLinear.insert(newDataLinear);
				
				HashObject<String> newDataDouble = new HashObject<String>(data, TEXT_FILE, tableSize, hashCode);
				tableDouble.insert(newDataDouble);
				
				if(displayProbeCounts) {
					displayProbeCounts(newDataLinear, newDataDouble);
					delay();
				}
			}
		}
	}
	
	public void displayReport() {
		calculateStatistics();
		switch(debugLevel) {
			case DISPLAY_README:
				displayReadme();
				break;
				
			case DISPLAY_TABLE:	
				displayTable();
				
			case DISPLAY_SUMMARY:
				displaySummary();
				break;
		}
	}
	
	private void calculateStatistics() {
		totalInserted = tableLinear.getLoadSize();
		totalDuplicates = tableLinear.getTotalDuplicateCount();
		totalProbesLinear = tableLinear.getTotalProbeCount();
		averageProbesLinear = totalProbesLinear / (double)totalInserted;
		totalProbesDouble = tableDouble.getTotalProbeCount();
		averageProbesDouble = totalProbesDouble / (double)totalInserted;	
	}
	
	private void displaySummary() {
		System.out.println("A good table size is found: " + tableLinear.size());
		System.out.print("Data source type: ");
		if(inputType == RANDOM_NUMBER) {
			System.out.println("Random Number Generator");
		}
		else if(inputType == SYSTEM_TIME) {
			System.out.println("Current System Time");
		}
		else if(inputType == TEXT_FILE) {
			System.out.println("Text File");
		}
		else {} //Future data sources
		
		System.out.println("\nUsing Linear Hashing...");
		System.out.println("Inserted " + totalInserted + " elements, of which " + totalDuplicates + " duplicates");
		System.out.println("Load factor = " + loadFactor + ", Avg. no. of probes " + averageProbesLinear);
		
		System.out.println("\nUsing Double Hashing...");
		System.out.println("Inserted " + totalInserted + " elements, of which " + totalDuplicates + " duplicates");
		System.out.println("Load factor = " + loadFactor + ", Avg. no. of probes " + averageProbesDouble);
	}
	
	private void displayTable() {
		System.out.println("\n\n< Table Contents - Linear Hashing >\n");
		tableLinear.displayTable();
		System.out.println("\n\n< Table Contents - Double Hashing >\n");
		tableDouble.displayTable();
	}
	
	private void displayProbeCounts(HashObject<?> newDataLinear, HashObject<?> newDataDouble) {
		System.out.println("\n|-- Number of probes required to Insert Source Data: " + newDataLinear.getData() + " --|");
		System.out.println("Linear Hashing: " + newDataLinear.getProbeCount());
		System.out.println("Double Hashing: " + newDataDouble.getProbeCount());
		System.out.println();
	}
	
	private void displayReadme() {
		DecimalFormat dec2 = new DecimalFormat("0.00");
		DecimalFormat dec1 = new DecimalFormat("0.0");
		StringBuilder str = new StringBuilder();
		if(loadFactor == 0.5) {
			str.append("Input Source " + inputType + ": ");
			switch(inputType) {
				case RANDOM_NUMBER:
					str.append("Random Number\n\n");
					break;
				case SYSTEM_TIME:
					str.append("System Time\n\n");
					break;
				case TEXT_FILE:
					str.append("Text File\n\n");
					break;
			}
			str.append("alpha\t\tlinear\t\tdouble\n--------------------------------------\n");
		}
		if(inputType == SYSTEM_TIME) {
			str.append(loadFactor + "\t\t" + dec1.format(averageProbesLinear) + "\t\t" + dec1.format(averageProbesDouble) + "\n");
		}
		else {
			str.append(loadFactor + "\t\t" + dec2.format(averageProbesLinear) + "\t\t" + dec2.format(averageProbesDouble) + "\n");
		}
		System.out.print(str.toString());
	}
	
	private int calculateTableSize() {
		return twinPF.getLargerTwin();
	}
	
	private boolean dataSourceIsRandom() {
		return inputType == RANDOM_NUMBER;
	}
	
	private boolean dataSourceIsSystemTime() {
		return inputType == SYSTEM_TIME;
	}
	
	public static void main(String[] args) {
		validateCLA(args);
		HashTest test;
		if(args.length == 2) {
			test = new HashTest(Integer.parseInt(args[0]), Double.parseDouble(args[1]));
		}
		else {
			test = new HashTest(Integer.parseInt(args[0]), Double.parseDouble(args[1]), Integer.parseInt(args[2]));
		}
		
		test.run();
		test.displayReport();	
	}
	
	private static void validateCLA(String[] args) {
		if(args.length < 2 || args.length > 3) {
			System.out.println("Error: Invalid number of command line arguments!");
			printProgramUsageMessage();
			System.exit(1);
		}
		else if(Integer.parseInt(args[0]) < 1 || Integer.parseInt(args[0]) > 3 || Double.parseDouble(args[1]) > 1 || Double.parseDouble(args[1]) < 0) {
			System.out.println("Error: Invalid command line arguments!");
			printProgramUsageMessage();
			System.exit(1);
		}
		else if(args.length == 3) {
			if((Integer.parseInt(args[2]) > 2 && Integer.parseInt(args[2])  != 8675309) || Integer.parseInt(args[2]) < 0) {
				System.out.println("Error: Invalid number of command line arguments!");
				printProgramUsageMessage();
				System.exit(1);
			}
		}
		
	}
	
	private void delay() {
		System.out.print("\n--> Next Insert in ");
		for(int i = DISPLAY_PROBE_COUNTS_DELAY_IN_SECONDS; i >= 0; i--) {
			System.out.print("..." + i);
			try {
				Thread.sleep(1000);
			}
			catch (InterruptedException e) {
			// Deliberate Pause Time for debugging Probe Counting Sequence
			e.printStackTrace();
			}
		}
	}
	
	private static void printProgramUsageMessage() {
		System.out.println("Program Usage:\njava HashTest <input type> <load factor> [<debug level>]");
		System.out.println(" Valid: <input type> = 1-3, <load factor> = 0.0-1.0, <debug level> = 0-2 (optional)");
	}
}
