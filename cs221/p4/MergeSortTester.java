import java.util.Random;

/**
 * This class tests the functionality of the MergeSort class.
 * All methods are tested against sorted, reversed, and random lists.
 * 
 * @author jcarlson
 *
 */
public class MergeSortTester<T> {
	private final int LIST_SIZE = 10;
	private final int ELEMENT_RANGE = 100;
	
	// Testing statistics 
	private int passes, failures, total;
	
	private Random rand;
	private WrappedDLL<T> list;
	
	/**
	 * Constructor creates an object and runs testing method
	 */
	public MergeSortTester() {
		passes = failures = total = 0;
		rand = new Random();
		runTests();
	}
	
	/**
	 * Creates a new MergeSortTester and then displays the results
	 * 
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		@SuppressWarnings("rawtypes")
		MergeSortTester tester = new MergeSortTester();
		tester.displayResults();
	}
	
	/**
	 * Runs all tests required to validate MergeSort methods
	 */
	private void runTests() {
		//Run Tests for sorted list
		newSortedList(LIST_SIZE);
		testNaturalOrderSort("Sorted List (Integers) - Natural Order Sort Test");
		newSortedList(LIST_SIZE);
		testNaturalOrderFindSmallest("Sorted List (Integers) - Natural Order FindSmallest Test");
		newSortedList(LIST_SIZE);
		testNaturalOrderFindLargest("Sorted List (Integers) - Natural Order FindLargest Test");
				
		//Run Tests for reversed list
		newReversedList(LIST_SIZE);
		testNaturalOrderSort("Reversed List (Integers) - Natural Order Sort Test");
		newReversedList(LIST_SIZE);
		testNaturalOrderFindSmallest("Reversed List (Integers) - Natural Order FindSmallest Test");
		newReversedList(LIST_SIZE);
		testNaturalOrderFindLargest("Reversed List (Integers) - Natural Order FindLargest Test");
				
		//Run Tests for random elements
		newRandomList(LIST_SIZE);
		testNaturalOrderSort("Random List (Integers) - Natural Order Sort Test");
		newRandomList(LIST_SIZE);
		testNaturalOrderFindSmallest("Random List (Integers) - Natural Order FindSmallest Test");
		newRandomList(LIST_SIZE);
		testNaturalOrderFindLargest("Random List (Integers) - Natural Order FindLargest Test");
		
		//Run Tests for Comparator implementation
		newSniperList(); //Creates list of ten snipers with random number of kills
		testComparatorSort("Sniper Kills - Comparator Sort (number of kills) Test");
		newSniperList(); //Creates list of ten snipers with random number of kills
		testComparatorFindSmallest("Sniper Kills - Comparator FindSmallest (smallest number of kills) Test");
		newSniperList(); //Creates list of ten snipers with random number of kills
		testComparatorFindLargest("Sniper Kills - Comparator FindLargest (largest number of kills) Test");
		
	}
	
	/**
	 * Creates a sorted list
	 * 
	 * @param size Size of the list to create
	 */
	@SuppressWarnings("unchecked")
	private void newSortedList(int size) {
		list = new WrappedDLL<T>();
				
		for(int i=0; i < size; i++) {
			list.addToRear((T) new Integer(i+1));
		}
	}
	
	/**
	 * Creates a reverse sorted list
	 * 
	 * @param size Size of the list to create
	 */
	@SuppressWarnings("unchecked")
	private void newReversedList(int size) {
		list = new WrappedDLL<T>();
				
		for(int i=size; i > 0; i--) {
			list.addToRear((T) new Integer(i));
		}
	}
	
	/**
	 * Creates a random list
	 * 
	 * @param size Size of the list to create
	 */
	@SuppressWarnings("unchecked")
	private void newRandomList(int size) {
		list = new WrappedDLL<T>();
		
		for(int i=0; i < size; i++) {
			Integer randomInteger = new Integer(rand.nextInt(ELEMENT_RANGE));
			list.addToRear((T) randomInteger);
		}
	}
	
	/**
	 * Creates a list of ten snipers
	 */
	@SuppressWarnings("unchecked")
	private void newSniperList() {
		list = new WrappedDLL<T>();
		list.addToRear((T) new Sniper("Aaron", new Integer(rand.nextInt(ELEMENT_RANGE))));
		list.addToRear((T) new Sniper("Mark", new Integer(rand.nextInt(ELEMENT_RANGE))));
		list.addToRear((T) new Sniper("Sandy", new Integer(rand.nextInt(ELEMENT_RANGE))));
		list.addToRear((T) new Sniper("Mel", new Integer(rand.nextInt(ELEMENT_RANGE))));
		list.addToRear((T) new Sniper("Rob", new Integer(rand.nextInt(ELEMENT_RANGE))));
		list.addToRear((T) new Sniper("Frank", new Integer(rand.nextInt(ELEMENT_RANGE))));
		list.addToRear((T) new Sniper("Susan", new Integer(rand.nextInt(ELEMENT_RANGE))));
		list.addToRear((T) new Sniper("Reggie", new Integer(rand.nextInt(ELEMENT_RANGE))));
		list.addToRear((T) new Sniper("Clark", new Integer(rand.nextInt(ELEMENT_RANGE))));
		list.addToRear((T) new Sniper("Morgan", new Integer(rand.nextInt(ELEMENT_RANGE))));
	}
	
	/**
	 * 
	 * @return Returns the size of this list
	 */
	private int size() {
		return LIST_SIZE;
	}
	
	/**
	 * Prints this list
	 */
	private void printList() {
		System.out.println(list.toString());
	}
	
	/**
	 * Prints this sniper list
	 */
	private void printSnipers() {
		for(T s : list) {
			System.out.println(((Sniper) s).getName() + " \tKills: " + ((Sniper) s).getKills());
		}
	}
	
	/**
	 * Verifies the result of MergeSort sort method
	 */
	private void verifySort() {
		boolean pass = true;
		
		for(int i=0; i < LIST_SIZE - 1; i++) {
			if((Integer) list.get(i + 1) < (Integer) list.get(i)) {
				pass = false;
				break;
			}
		}
		System.out.println("\nSorted List:");
		printList();
		System.out.print("\nSort Check: ");
		
		if(pass) {
			passes++;
			System.out.println("--- PASSED ---\n");
		}
		else {
			failures++;
			System.out.println("*** FAILED ***\n");
		}
	}
	
	/**
	 * Verifies the result of MergeSort findSmallest method
	 * 
	 * @param result The MergeSort findSmallest result
	 */
	private void verifyFindSmallest(Integer result) {
		boolean pass;
		System.out.println("\nList Contents: ");
		printList();
		
		if(list.isEmpty()) {
			pass = (result == null);
			System.out.println("\nSmallest Element: null");
		}
		else {
			int smallestElement = (Integer) list.get(0);
			for(int i=1; i < LIST_SIZE; i++) {
				if((Integer) list.get(i) < smallestElement) {
					smallestElement = (Integer) list.get(i);
				}
			}
			pass = (result == smallestElement);
			System.out.println("\nSmallest Element: " + smallestElement);
		}
		System.out.println("   Test returned: " + result);
		System.out.print("\nFind Smallest Check: ");
		
		if(pass) {
			passes++;
			System.out.println("--- PASSED ---\n");
		}
		else {
			failures++;
			System.out.println("*** FAILED ***\n");
		}
	}
	
	/**
	 * Verifies the result of the MergeSort findLargest method
	 * 
	 * @param result The MergeSort findLargest result
	 */
	private void verifyFindLargest(Integer result) {
		boolean pass;
		System.out.println("\nList Contents: ");
		printList();
		
		if(list.isEmpty()) {
			pass = (result == null);
			System.out.println("\nLargest Element: null");
		}
		else {
			int largestElement = (Integer) list.get(0);
			for(int i=1; i < LIST_SIZE; i++) {
				if((Integer) list.get(i) > largestElement) {
					largestElement = (Integer) list.get(i);
				}
			}
			pass = (result == largestElement);
			System.out.println("\nLargest Element: " + largestElement);
		}
		System.out.println("  Test returned: " + result);
		System.out.print("\nFind Largest Check: ");
		
		if(pass) {
			passes++;
			System.out.println("--- PASSED ---\n\n");
		}
		else {
			failures++;
			System.out.println("*** FAILED ***\n\n");
		}
	}
	
	/**
	 * Verifies the sort of snipers
	 * 
	 */
	private void verifySortSniper() {
		boolean pass = true;
		
		for(int i=0; i < LIST_SIZE - 1; i++) {
			if(((Sniper) list.get(i)).getKills() > ((Sniper) list.get(i+1)).getKills()) {
				pass = false;
				break;
			}
		}
		System.out.println("\nSnipers (Sorted by Kills):");
		printSnipers();
		System.out.print("\nSort Check: ");
		
		if(pass) {
			passes++;
			System.out.println("--- PASSED ---\n");
		}
		else {
			failures++;
			System.out.println("*** FAILED ***\n");
		}
	}
	
	/**
	 * Verifies the Comparator result of MergeSort
	 * 
	 * @param bestSniper The MergreSort findLargest sniper result
	 */
	private void verifyBestSniper(Sniper bestSniper) {
		boolean pass;
		
		Sniper theBestSniper = (Sniper) list.get(0);
			for(int i=1; i < LIST_SIZE; i++) {
				if(((Sniper) list.get(i)).getKills() >= theBestSniper.getKills()) {
					theBestSniper = (Sniper) list.get(i);
				}
			}
			pass = (bestSniper == theBestSniper);
			System.out.println("\n    Best Sniper: " + theBestSniper.getName());
		System.out.println("  Test returned: " + bestSniper.getName());
		System.out.print("\nBest Sniper Check: ");
		
		if(pass) {
			passes++;
			System.out.println("--- PASSED ---\n\n");
		}
		else {
			failures++;
			System.out.println("*** FAILED ***\n\n");
		}
	}
	
	/**
	 * Verifies the Comparator result of MergeSort
	 * 
	 * @param bestSniper The MergreSort findSmallest sniper result
	 */
	private void verifyWorstSniper(Sniper worstSniper) {
		boolean pass;

		Sniper theWorstSniper = (Sniper) list.get(0);
			for(int i=1; i < LIST_SIZE; i++) {
				if(((Sniper) list.get(i)).getKills() <= theWorstSniper.getKills()) {
					theWorstSniper = (Sniper) list.get(i);
				}
			}
			pass = (worstSniper == theWorstSniper);
			System.out.println("\n   Worst Sniper: " + theWorstSniper.getName());
		System.out.println("  Test returned: " + worstSniper.getName());
		System.out.print("\nBest Sniper Check: ");
		
		if(pass) {
			passes++;
			System.out.println("--- PASSED ---\n\n");
		}
		else {
			failures++;
			System.out.println("*** FAILED ***\n\n");
		}
	}
	
	/**
	 * Prints final pass/fail results
	 */
	private void displayResults() {
		System.out.println("=============================");
		System.out.println("-      Testing Results      -");
		System.out.println("=============================");
		System.out.println("       Total Tests: " + total);
		System.out.println("            Passed: " + passes);
		System.out.println("            Failed: " + failures);
		System.out.println("-----------------------------");
	}
	
	/**
	 * Tests sort method using natural order
	 * 
	 * @param testDescription Description of current test
	 */
	@SuppressWarnings("unchecked")
	private void testNaturalOrderSort(String testDescription) {
		total++;
		System.out.println("------------------ <" + testDescription + "> ----- List Size: " + size() + " ------------------\n\nOriginal List:");
		printList();
		MergeSort.sort((WrappedDLL<Integer>) list);
		verifySort();
	}
	
	/**
	 * Tests findSmallest method using natural order
	 * 
	 * @param testDescription Description of current test
	 */
	@SuppressWarnings("unchecked")
	private void testNaturalOrderFindSmallest(String testDescription) {
		total++;
		System.out.println("------------------ <" + testDescription + "> ----- List Size: " + size() + " ------------------");
		verifyFindSmallest(MergeSort.findSmallest((WrappedDLL<Integer>) list));
	}
	
	/**
	 * Tests findLargest method using natural order
	 * 
	 * @param testDescription Description of current test
	 */
	@SuppressWarnings("unchecked")
	private void testNaturalOrderFindLargest(String testDescription) {
		total++;
		System.out.println("------------------ <" + testDescription + "> ----- List Size: " + size() + " ------------------");
		verifyFindLargest(MergeSort.findLargest((WrappedDLL<Integer>) list));
	}
	
	/**
	 * Tests sort method using a comparator for sniper objects
	 * 
	 * @param testDescription Description of current test
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void testComparatorSort(String testDescription) {
		total++;
		System.out.println("------------------ <" + testDescription + "> ----- List Size: " + size() + " ------------------\n\nOriginal List:");
		printSnipers();
		MergeSort.sort((WrappedDLL<Sniper>) list, new SniperCompare());
		verifySortSniper();
	}
	
	/**
	 * Tests findSmallest method using a comparator for sniper objects
	 * 
	 * @param testDescription Description of current test
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void testComparatorFindSmallest(String testDescription) {
		total++;
		System.out.println("------------------ <" + testDescription + "> ----- List Size: " + size() + " ------------------\n\nSnipers:");
		printSnipers();
		verifyWorstSniper((Sniper) MergeSort.findSmallest((WrappedDLL<Sniper>) list, new SniperCompare()));
	}
	
	/**
	 * Tests findLargest method using a comparator for sniper objects
	 * 
	 * @param testDescription Description of current test
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void testComparatorFindLargest(String testDescription) {
		total++;
		System.out.println("------------------ <" + testDescription + "> ----- List Size: " + size() + " ------------------\n\nSnipers:");
		printSnipers();
		verifyBestSniper((Sniper) MergeSort.findLargest((WrappedDLL<Sniper>) list, new SniperCompare()));
	}
}
