
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A unit test class for UnorderedListADT, IndexedListADT.
 * 
 * This is a set of black box tests that should work for
 *  any implementation of UnorderedListADT/IndexedListADT/ListADT.
 * 
 * @author mvail, Matt T, jcarlson
 */
public class DoubleLinkedListTester 
{
	// stats on test cases run 
	private int passes = 0;
	private int failures = 0;
	private int total = 0;
	// elements added to the lists 
	private int A = new Integer(1);
	private int B = new Integer(2);
	private int C = new Integer(3);
	private int D = new Integer(4);
	private int E = new Integer(5);
	// which class running tests on 
	private enum ListType { Good, Bad, Array, Single, Double };
	// results of a given test
	private enum Results { True, False, Fail, NoException, EmptyCollection, ElementNotFound, IndexOutOfBounds, ConcurrentModification, IllegalState };
	// stores which class running tests on 
	private ListType listType;
	
	/** 
	 * Processes command-line argument, calls method to run tests 
	 * 
	 * @param args Specifies the type of underlying list to use
	 * 				 "-g" Good UnorderedListADT
	 * 				 "-b" Bad UnorderedListADT
	 * 				 "-a" ArrayList
	 * 				 "-s" SingleLinkedList
	 * 				 "-d" DoubleLinkedList
	 */
	public static void main(String[] args) 
	{
		//to avoid every method being static
		DoubleLinkedListTester tester = new DoubleLinkedListTester();
		if(args.length != 1)
		{
			return;
		}
		else if(args[0].equals("-g"))
		{
			// tester.runTests(ListType.Good);
			System.out.println("This option is unsupported at this time.\nThe -d option is the only supported operation at this time.");
		}
		else if(args[0].equals("-b"))
		{
			// tester.runTests(ListType.Bad);
			System.out.println("This option is unsupported at this time.\nThe -d option is the only supported operation at this time.");
		}
		else if(args[0].equals("-a"))
		{
			// tester.runTests(ListType.Array);
			System.out.println("This option is unsupported at this time.\nThe -d option is the only supported operation at this time.");
		}
		else if(args[0].equals("-s"))
		{
			// tester.runTests(ListType.Single);
			System.out.println("This option is unsupported at this time.\nThe -d option is the only supported operation at this time.");
		}
		else if(args[0].equals("-d"))
		{
			tester.runTests(ListType.Double);
		}
	}
	
	/**
	 * Returns an UnorderedListADT of a given type and 
	 * with given elements added.  
	 *
	 * @return a new UnorderedListADT
	 */
	private UnorderedListADT<Integer> newUnorderedList(int ... elements)
	{
		UnorderedListADT<Integer> newUnorderedList; 
		// determine which list to create
		switch(listType)
		{
		case Good:
				//newUnorderedList = new GoodUnorderedList<Integer>();
				newUnorderedList = null;
				break;
		case Bad:
				//newUnorderedList = new BadUnorderedList<Integer>();
				newUnorderedList = null;
				break;
		case Array:
				//newUnorderedList = new ArrayList<Integer>();
				newUnorderedList = null;
				break;
		case Single:
				//newUnorderedList = new SingleLinkedList<Integer>();
				newUnorderedList = null;
				break;
		case Double:
				newUnorderedList = new DoubleLinkedList<Integer>();
				break;
		default:
				newUnorderedList = null;
		}
		// fill list with given elements
		for(int i = 0; i < elements.length; i++)
		{
			newUnorderedList.addToRear(elements[i]);
		}	
		return newUnorderedList;
	}
	
	/**
	 * Returns an IndexedListADT of a given type and 
	 * with given elements added.  
	 *
	 * @return a new IndexedListADT
	 */
	private IndexedListADT<Integer> newIndexedList(int ... elements)
	{
		IndexedListADT<Integer> newList; 
		// determine which list to create
		switch(listType)
		{
		case Array:
				//newList = new ArrayList<Integer>();
				newList = null;
				break;
		case Single:
				//newList = new SingleLinkedList<Integer>();
				newList = null;
				break;
		case Double:
				newList = new DoubleLinkedList<Integer>();
				break;
		default:
				newList = null;
		}
		// fill list with given elements
		for(int i = 0; i < elements.length; i++)
		{
			newList.add(elements[i]);
		}	
		return newList;
	}
	
	/** Print test results in a consistent format
	 * @param testDesc description of the test
	 * @param result indicates if the test passed or failed
	 */
	private void printTest(String testDesc, boolean result) 
	{
		// update number of tests run
		total++;
		if (result) 
		{
			passes++;  // update number of tests passed
		} 
		else 
		{
			failures++; // update number of tests failed 
		}
		// print the results of the test 
		System.out.printf("%-46s\t%s\n", testDesc, (result ? "   PASS" : "***FAIL***"));
	}

	/**  
	 * Print results of all tests.   
	 */
	private void printFinalSummary() 
	{
		System.out.printf("\nTotal Tests: %d,  Passed: %d,  Failed: %d\n", total, passes, failures);
	}
	
	/**
	 * Run tests to confirm required functionality of list methods
	 */
	private void runTests(ListType type) 
	{
		listType = type;
		noList_constructorTests(); // Scenario 1
		emptyList_addToFrontTests(); // Scenario 2
		emptyList_addToRearTests(); // Scenario 3
		oneElementList_removeFirstTests(); // Scenario 4
		oneElementList_removeLastTests(); // Scenario 5
		oneElementList_removeElementTests(); // Scenario 6
		oneElementList_addToFrontTests(); // Scenario 7
		oneElementList_addToRearTests(); // Scenario 8
		oneElementList_addAfterElementTests(); // Scenario 9
		twoElementList_removeFirstTests(); // Scenario 10
		twoElementList_removeLastTests(); // Scenario 11
		twoElementList_removeFirstElementTests(); // Scenario 12
		twoElementList_removeSecondElementTests(); // Scenario 13
		twoElementList_addToFrontTests(); // Scenario 14
		twoElementList_addToRearTests(); // Scenario 15
		twoElementList_addAfterFirstElementTests(); // Scenario 16
		twoElementList_addAfterSecondElementTests(); // Scenario 17
		threeElementList_removeFirstTests(); // Scenario 18
		threeElementList_removeLastTests(); // Scenario 19
		threeElementList_removeFirstElementTests(); // Scenario 20
		threeElementList_removeSecondElementTests(); // Scenario 21
		threeElementList_removeThirdElementTests(); // Scenario 22
		printFinalSummary();
	}
	
	// Scenario 1 - no list -> constructor -> []
	private void noList_constructorTests()
	{
		printTestHeader(1,"Unordered");
		printTest("noList_constructor_testAddToFront_A", testAddToFront(newUnorderedList(), A, Results.NoException));
		printTest("noList_constructor_testAddToRear_A", testAddToRear(newUnorderedList(), A, Results.NoException));
		printTest("noList_constructor_testAddAfter_B_A", testAddAfter(newUnorderedList(), B, A, Results.ElementNotFound));
		printTest("noList_constructor_testRemoveFirst", testRemoveFirst(newUnorderedList(), Results.EmptyCollection));
		printTest("noList_constructor_testRemoveLast", testRemoveLast(newUnorderedList(), Results.EmptyCollection));
		printTest("noList_constructor_testRemove_A", testRemove(newUnorderedList(), A, Results.ElementNotFound));
		printTest("noList_constructor_testFirst", testFirst(newUnorderedList(), Results.EmptyCollection));
		printTest("noList_constructor_testLast", testLast(newUnorderedList(), Results.EmptyCollection));
		printTest("noList_constructor_testContains_A", testContains(newUnorderedList(), A, Results.False));
		printTest("noList_constructor_testIsEmpty", testIsEmpty(newUnorderedList(), Results.True));
		printTest("noList_constructor_testSize", testSize(newUnorderedList(), 0));
		printTest("noList_constructor_testIterator", testIterator(newUnorderedList(), Results.NoException));
		printTest("noList_constructor_testIteratorHasNext", testIteratorHasNext(newUnorderedList(), Results.False));
		printTest("noList_constructor_testIteratorNext", testIteratorNext(newUnorderedList(), Results.ElementNotFound));
		printTest("noList_constructor_testToString", testToString(newUnorderedList(), Results.NoException));
		
		if(listType == ListType.Array || listType == ListType.Single || listType == ListType.Double) {
			printTestHeader(1,"Indexed");
			printTest("noList_constructor_testAdd_A", testAdd(newIndexedList(), A, Results.NoException));
			printTest("noList_constructor_testAdd_0_A", testAdd(newIndexedList(), 0, A, Results.IndexOutOfBounds));
			printTest("noList_constructor_testAdd_-1_A", testAdd(newIndexedList(), -1, A, Results.IndexOutOfBounds));
			printTest("noList_constructor_testAdd_5_A", testAdd(newIndexedList(), 5, A, Results.IndexOutOfBounds));
			printTest("noList_constructor_testSet_0_A", testSet(newIndexedList(), 0, A, Results.IndexOutOfBounds));
			printTest("noList_constructor_testSet_-1_A", testSet(newIndexedList(), -1, A, Results.IndexOutOfBounds));
			printTest("noList_constructor_testSet_5_A", testSet(newIndexedList(), 5, A, Results.IndexOutOfBounds));
			printTest("noList_constructor_testGet_0", testGet(newIndexedList(), 0, Results.IndexOutOfBounds));
			printTest("noList_constructor_testGet_-1", testGet(newIndexedList(), -1, Results.IndexOutOfBounds));
			printTest("noList_constructor_testGet_5", testGet(newIndexedList(), 5, Results.IndexOutOfBounds));
			printTest("noList_constructor_testIndexOf_A", testIndexOf(newIndexedList(), A, -1));
			printTest("noList_constructor_testIndexOf_B", testIndexOf(newIndexedList(), B, -1));
			printTest("noList_constructor_testRemoveAtIndex_0", testRemoveAtIndex(newIndexedList(), 0, Results.IndexOutOfBounds));
			printTest("noList_constructor_testRemoveAtIndex_-1", testRemoveAtIndex(newIndexedList(), -1, Results.IndexOutOfBounds));
			printTest("noList_constructor_testRemoveAtIndex_5", testRemoveAtIndex(newIndexedList(), 5, Results.IndexOutOfBounds));
			
		}
		
	}
	
	// Scenario 2 - [] -> addToFront(A) -> [A]
	private void emptyList_addToFrontTests()
	{
		printTestHeader(2,"Unordered");
		printTest("emptyList_addToFront_testAddToFront_C", testAddToFront(newUnorderedList(A), C, Results.NoException));
		printTest("emptyList_addToFront_testAddToRear_C", testAddToRear(newUnorderedList(A), C, Results.NoException));
		printTest("emptyList_addToFront_testAddAfter_C_A", testAddAfter(newUnorderedList(A), C, A, Results.NoException));
		printTest("emptyList_addToFront_testAddAfter_C_B", testAddAfter(newUnorderedList(A), C, B, Results.ElementNotFound));
		printTest("emptyList_addToFront_testRemoveFirst", testRemoveFirst(newUnorderedList(A), Results.NoException));
		printTest("emptyList_addToFront_testRemoveLast", testRemoveLast(newUnorderedList(A), Results.NoException));
		printTest("emptyList_addToFront_testRemove_A", testRemove(newUnorderedList(A), A, Results.NoException));
		printTest("emptyList_addToFront_testRemove_B", testRemove(newUnorderedList(A), B, Results.ElementNotFound));
		printTest("emptyList_addToFront_testFirst", testFirst(newUnorderedList(A), Results.NoException));
		printTest("emptyList_addToFront_testLast", testLast(newUnorderedList(A), Results.NoException));
		printTest("emptyList_addToFront_testContains_A", testContains(newUnorderedList(A), A, Results.True));
		printTest("emptyList_addToFront_testContains_B", testContains(newUnorderedList(A), B, Results.False));
		printTest("emptyList_addToFront_testIsEmpty", testIsEmpty(newUnorderedList(A), Results.False));
		printTest("emptyList_addToFront_testSize", testSize(newUnorderedList(A), 1));
		printTest("emptyList_addToFront_testIterator", testIterator(newUnorderedList(A), Results.NoException));
		printTest("emptyList_addToFront_testIteratorHasNext", testIteratorHasNext(newUnorderedList(A), Results.True));
		printTest("emptyList_addToFront_testIteratorNext", testIteratorNext(newUnorderedList(A), Results.NoException));
		printTest("emptyList_addToFront_testToString", testToString(newUnorderedList(A), Results.NoException));
		
		if(listType == ListType.Array || listType == ListType.Single || listType == ListType.Double) {
			printTestHeader(2,"Indexed");
			printTest("emptyList_addToFront_testAdd_B", testAdd(newIndexedList(A), B, Results.NoException));
			printTest("emptyList_addToFront_testAdd_0_B", testAdd(newIndexedList(A), 0, B, Results.NoException));
			printTest("emptyList_addToFront_testAdd_1_B", testAdd(newIndexedList(A), 1, B, Results.IndexOutOfBounds));
			printTest("emptyList_addToFront_testAdd_-1_B", testAdd(newIndexedList(A), -1, B, Results.IndexOutOfBounds));
			printTest("emptyList_addToFront_testAdd_2_B", testAdd(newIndexedList(A), 2, B, Results.IndexOutOfBounds));
			printTest("emptyList_addToFront_testSet_0_B", testSet(newIndexedList(A), 0, B, Results.NoException));
			printTest("emptyList_addToFront_testSet_-1_B", testSet(newIndexedList(A), -1, B, Results.IndexOutOfBounds));
			printTest("emptyList_addToFront_testSet_1_B", testSet(newIndexedList(A), 1, B, Results.IndexOutOfBounds));
			printTest("emptyList_addToFront_testGet_0", testGet(newIndexedList(A), 0, Results.NoException));
			printTest("emptyList_addToFront_testGet_-1", testGet(newIndexedList(A), -1, Results.IndexOutOfBounds));
			printTest("emptyList_addToFront_testGet_1", testGet(newIndexedList(A), 1, Results.IndexOutOfBounds));
			printTest("emptyList_addToFront_testIndexOf_A", testIndexOf(newIndexedList(A), A, 0));
			printTest("emptyList_addToFront_testIndexOf_B", testIndexOf(newIndexedList(A), B, -1));
			printTest("emptyList_addToFront_testRemoveAtIndex_0", testRemoveAtIndex(newIndexedList(A), 0, Results.NoException));
			printTest("emptyList_addToFront_testRemoveAtIndex_-1", testRemoveAtIndex(newIndexedList(A), -1, Results.IndexOutOfBounds));
			printTest("emptyList_addToFront_testRemoveAtIndex_1", testRemoveAtIndex(newIndexedList(A), 1, Results.IndexOutOfBounds));
		}
	}
	
	// Scenario 3 - [] -> addToRear(A) -> [A]
	private void emptyList_addToRearTests()
	{
		printTestHeader(3,"Unordered");
		printTest("emptyList_addToRear_testAddToFront_C", testAddToFront(newUnorderedList(A), C, Results.NoException));
		printTest("emptyList_addToRear_testAddToRear_C", testAddToRear(newUnorderedList(A), C, Results.NoException));
		printTest("emptyList_addToRear_testAddAfter_C_A", testAddAfter(newUnorderedList(A), C, A, Results.NoException));
		printTest("emptyList_addToRear_testAddAfter_C_B", testAddAfter(newUnorderedList(A), C, B, Results.ElementNotFound));
		printTest("emptyList_addToRear_testRemoveFirst", testRemoveFirst(newUnorderedList(A), Results.NoException));
		printTest("emptyList_addToRear_testRemoveLast", testRemoveLast(newUnorderedList(A), Results.NoException));
		printTest("emptyList_addToRear_testRemove_A", testRemove(newUnorderedList(A), A, Results.NoException));
		printTest("emptyList_addToRear_testRemove_B", testRemove(newUnorderedList(A), B, Results.ElementNotFound));
		printTest("emptyList_addToRear_testFirst", testFirst(newUnorderedList(A), Results.NoException));
		printTest("emptyList_addToRear_testLast", testLast(newUnorderedList(A), Results.NoException));
		printTest("emptyList_addToRear_testContains_A", testContains(newUnorderedList(A), A, Results.True));
		printTest("emptyList_addToRear_testContains_B", testContains(newUnorderedList(A), B, Results.False));
		printTest("emptyList_addToRear_testIsEmpty", testIsEmpty(newUnorderedList(A), Results.False));
		printTest("emptyList_addToRear_testSize", testSize(newUnorderedList(A), 1));
		printTest("emptyList_addToRear_testIterator", testIterator(newUnorderedList(A), Results.NoException));
		printTest("emptyList_addToRear_testIteratorHasNext", testIteratorHasNext(newUnorderedList(A), Results.True));
		printTest("emptyList_addToRear_testIteratorNext", testIteratorNext(newUnorderedList(A), Results.NoException));
		printTest("emptyList_addToRear_testToString", testToString(newUnorderedList(A), Results.NoException));
		
		if(listType == ListType.Array || listType == ListType.Single || listType == ListType.Double) {
			printTestHeader(3,"Indexed");
			printTest("emptyList_addToRear_testAdd_B", testAdd(newIndexedList(A), B, Results.NoException));
			printTest("emptyList_addToRear_testAdd_0_B", testAdd(newIndexedList(A), 0, B, Results.NoException));
			printTest("emptyList_addToRear_testAdd_1_B", testAdd(newIndexedList(A), 1, B, Results.IndexOutOfBounds));
			printTest("emptyList_addToRear_testAdd_-1_B", testAdd(newIndexedList(A), -1, B, Results.IndexOutOfBounds));
			printTest("emptyList_addToRear_testAdd_2_B", testAdd(newIndexedList(A), 2, B, Results.IndexOutOfBounds));
			printTest("emptyList_addToRear_testSet_0_B", testSet(newIndexedList(A), 0, B, Results.NoException));
			printTest("emptyList_addToRear_testSet_-1_B", testSet(newIndexedList(A), -1, B, Results.IndexOutOfBounds));
			printTest("emptyList_addToRear_testSet_1_B", testSet(newIndexedList(A), 1, B, Results.IndexOutOfBounds));
			printTest("emptyList_addToRear_testGet_0", testGet(newIndexedList(A), 0, Results.NoException));
			printTest("emptyList_addToRear_testGet_-1", testGet(newIndexedList(A), -1, Results.IndexOutOfBounds));
			printTest("emptyList_addToRear_testGet_1", testGet(newIndexedList(A), 1, Results.IndexOutOfBounds));
			printTest("emptyList_addToRear_testIndexOf_A", testIndexOf(newIndexedList(A), A, 0));
			printTest("emptyList_addToRear_testIndexOf_B", testIndexOf(newIndexedList(A), B, -1));
			printTest("emptyList_addToRear_testRemoveAtIndex_0", testRemoveAtIndex(newIndexedList(A), 0, Results.NoException));
			printTest("emptyList_addToRear_testRemoveAtIndex_-1", testRemoveAtIndex(newIndexedList(A), -1, Results.IndexOutOfBounds));
			printTest("emptyList_addToRear_testRemoveAtIndex_1", testRemoveAtIndex(newIndexedList(A), 1, Results.IndexOutOfBounds));
		}	
	}
	
	// Scenario 4 - [A] -> removeFirst -> []
	private void oneElementList_removeFirstTests()
	{
		printTestHeader(4,"Unordered");
		printTest("oneElementList_removeFirst_testAddToFront_E", testAddToFront(newUnorderedList(), E, Results.NoException));
		printTest("oneElementList_removeFirst_testAddToRear_E", testAddToRear(newUnorderedList(), E, Results.NoException));
		printTest("oneElementList_removeFirst_testAddAfter_B_A", testAddAfter(newUnorderedList(), B, A, Results.ElementNotFound));
		printTest("oneElementList_removeFirst_testRemoveFirst", testRemoveFirst(newUnorderedList(), Results.EmptyCollection));
		printTest("oneElementList_removeFirst_testRemoveLast", testRemoveLast(newUnorderedList(), Results.EmptyCollection));
		printTest("oneElementList_removeFirst_testRemove_A", testRemove(newUnorderedList(), A, Results.ElementNotFound));
		printTest("oneElementList_removeFirst_testFirst", testFirst(newUnorderedList(), Results.EmptyCollection));
		printTest("oneElementList_removeFirst_testLast", testLast(newUnorderedList(), Results.EmptyCollection));
		printTest("oneElementList_removeFirst_testContains_A", testContains(newUnorderedList(), A, Results.False));
		printTest("oneElementList_removeFirst_testIsEmpty", testIsEmpty(newUnorderedList(), Results.True));
		printTest("oneElementList_removeFirst_testSize", testSize(newUnorderedList(), 0));
		printTest("oneElementList_removeFirst_testIterator", testIterator(newUnorderedList(), Results.NoException));
		printTest("oneElementList_removeFirst_testIteratorHasNext", testIteratorHasNext(newUnorderedList(), Results.False));
		printTest("oneElementList_removeFirst_testIteratorNext", testIteratorNext(newUnorderedList(), Results.ElementNotFound));
		printTest("oneElementList_removeFirst_testToString", testToString(newUnorderedList(), Results.NoException));
		
		if(listType == ListType.Array || listType == ListType.Single || listType == ListType.Double) {
			printTestHeader(4,"Indexed");
			printTest("oneElementList_removeFirst_testAdd_A", testAdd(newIndexedList(), A, Results.NoException));
			printTest("oneElementList_removeFirst_testAdd_0_A", testAdd(newIndexedList(), 0, A, Results.IndexOutOfBounds));
			printTest("oneElementList_removeFirst_testAdd_-1_A", testAdd(newIndexedList(), -1, A, Results.IndexOutOfBounds));
			printTest("oneElementList_removeFirst_testAdd_5_A", testAdd(newIndexedList(), 5, A, Results.IndexOutOfBounds));
			printTest("oneElementList_removeFirst_testSet_0_A", testSet(newIndexedList(), 0, A, Results.IndexOutOfBounds));
			printTest("oneElementList_removeFirst_testSet_-1_A", testSet(newIndexedList(), -1, A, Results.IndexOutOfBounds));
			printTest("oneElementList_removeFirst_testSet_5_A", testSet(newIndexedList(), 5, A, Results.IndexOutOfBounds));
			printTest("oneElementList_removeFirst_testGet_0", testGet(newIndexedList(), 0, Results.IndexOutOfBounds));
			printTest("oneElementList_removeFirst_testGet_-1", testGet(newIndexedList(), -1, Results.IndexOutOfBounds));
			printTest("oneElementList_removeFirst_testGet_5", testGet(newIndexedList(), 5, Results.IndexOutOfBounds));
			printTest("oneElementList_removeFirst_testIndexOf_A", testIndexOf(newIndexedList(), A, -1));
			printTest("oneElementList_removeFirst_testIndexOf_B", testIndexOf(newIndexedList(), B, -1));
			printTest("oneElementList_removeFirst_testRemoveAtIndex_0", testRemoveAtIndex(newIndexedList(), 0, Results.IndexOutOfBounds));
			printTest("oneElementList_removeFirst_testRemoveAtIndex_-1", testRemoveAtIndex(newIndexedList(), -1, Results.IndexOutOfBounds));
			printTest("oneElementList_removeFirst_testRemoveAtIndex_5", testRemoveAtIndex(newIndexedList(), 5, Results.IndexOutOfBounds));
		}
	}
	
	// Scenario 5 - [A] -> removeLast -> []
	private void oneElementList_removeLastTests()
	{
		printTestHeader(5,"Unordered");
		printTest("oneElementList_removeLast_testAddToFront_E", testAddToFront(newUnorderedList(), E, Results.NoException));
		printTest("oneElementList_removeLast_testAddToRear_E", testAddToRear(newUnorderedList(), E, Results.NoException));
		printTest("oneElementList_removeLast_testAddAfter_B_A", testAddAfter(newUnorderedList(), B, A, Results.ElementNotFound));
		printTest("oneElementList_removeLast_testRemoveFirst", testRemoveFirst(newUnorderedList(), Results.EmptyCollection));
		printTest("oneElementList_removeLast_testRemoveLast", testRemoveLast(newUnorderedList(), Results.EmptyCollection));
		printTest("oneElementList_removeLast_testRemove_A", testRemove(newUnorderedList(), A, Results.ElementNotFound));
		printTest("oneElementList_removeLast_testFirst", testFirst(newUnorderedList(), Results.EmptyCollection));
		printTest("oneElementList_removeLast_testLast", testLast(newUnorderedList(), Results.EmptyCollection));
		printTest("oneElementList_removeLast_testContains_A", testContains(newUnorderedList(), A, Results.False));
		printTest("oneElementList_removeLast_testIsEmpty", testIsEmpty(newUnorderedList(), Results.True));
		printTest("oneElementList_removeLast_testSize", testSize(newUnorderedList(), 0));
		printTest("oneElementList_removeLast_testIterator", testIterator(newUnorderedList(), Results.NoException));
		printTest("oneElementList_removeLast_testIteratorHasNext", testIteratorHasNext(newUnorderedList(), Results.False));
		printTest("oneElementList_removeLast_testIteratorNext", testIteratorNext(newUnorderedList(), Results.ElementNotFound));
		printTest("oneElementList_removeLast_testToString", testToString(newUnorderedList(), Results.NoException));
		
		if(listType == ListType.Array || listType == ListType.Single || listType == ListType.Double) {
			printTestHeader(5,"Indexed");
			printTest("oneElementList_removeLast_testAdd_A", testAdd(newIndexedList(), A, Results.NoException));
			printTest("oneElementList_removeLast_testAdd_0_A", testAdd(newIndexedList(), 0, A, Results.IndexOutOfBounds));
			printTest("oneElementList_removeLast_testAdd_-1_A", testAdd(newIndexedList(), -1, A, Results.IndexOutOfBounds));
			printTest("oneElementList_removeLast_testAdd_5_A", testAdd(newIndexedList(), 5, A, Results.IndexOutOfBounds));
			printTest("oneElementList_removeLast_testSet_0_A", testSet(newIndexedList(), 0, A, Results.IndexOutOfBounds));
			printTest("oneElementList_removeLast_testSet_-1_A", testSet(newIndexedList(), -1, A, Results.IndexOutOfBounds));
			printTest("oneElementList_removeLast_testSet_5_A", testSet(newIndexedList(), 5, A, Results.IndexOutOfBounds));
			printTest("oneElementList_removeLast_testGet_0", testGet(newIndexedList(), 0, Results.IndexOutOfBounds));
			printTest("oneElementList_removeLast_testGet_-1", testGet(newIndexedList(), -1, Results.IndexOutOfBounds));
			printTest("oneElementList_removeLast_testGet_5", testGet(newIndexedList(), 5, Results.IndexOutOfBounds));
			printTest("oneElementList_removeLast_testIndexOf_A", testIndexOf(newIndexedList(), A, -1));
			printTest("oneElementList_removeLast_testIndexOf_B", testIndexOf(newIndexedList(), B, -1));
			printTest("oneElementList_removeLast_testRemoveAtIndex_0", testRemoveAtIndex(newIndexedList(), 0, Results.IndexOutOfBounds));
			printTest("oneElementList_removeLast_testRemoveAtIndex_-1", testRemoveAtIndex(newIndexedList(), -1, Results.IndexOutOfBounds));
			printTest("oneElementList_removeLast_testRemoveAtIndex_5", testRemoveAtIndex(newIndexedList(), 5, Results.IndexOutOfBounds));
		}
	}
	
	// Scenario 6 - [A] -> remove(A) -> []
	private void oneElementList_removeElementTests()
	{
		printTestHeader(6,"Unordered");
		printTest("oneElementList_removeElement_testAddToFront_E", testAddToFront(newUnorderedList(), E, Results.NoException));
		printTest("oneElementList_removeElement_testAddToRear_E", testAddToRear(newUnorderedList(), E, Results.NoException));
		printTest("oneElementList_removeElement_testAddAfter_B_A", testAddAfter(newUnorderedList(), B, A, Results.ElementNotFound));
		printTest("oneElementList_removeElement_testRemoveFirst", testRemoveFirst(newUnorderedList(), Results.EmptyCollection));
		printTest("oneElementList_removeElement_testRemoveLast", testRemoveLast(newUnorderedList(), Results.EmptyCollection));
		printTest("oneElementList_removeElement_testRemove_A", testRemove(newUnorderedList(), A, Results.ElementNotFound));
		printTest("oneElementList_removeElement_testFirst", testFirst(newUnorderedList(), Results.EmptyCollection));
		printTest("oneElementList_removeElement_testLast", testLast(newUnorderedList(), Results.EmptyCollection));
		printTest("oneElementList_removeElement_testContains_A", testContains(newUnorderedList(), A, Results.False));
		printTest("oneElementList_removeElement_testIsEmpty", testIsEmpty(newUnorderedList(), Results.True));
		printTest("oneElementList_removeElement_testSize", testSize(newUnorderedList(), 0));
		printTest("oneElementList_removeElement_testIterator", testIterator(newUnorderedList(), Results.NoException));
		printTest("oneElementList_removeElement_testIteratorHasNext", testIteratorHasNext(newUnorderedList(), Results.False));
		printTest("oneElementList_removeElement_testIteratorNext", testIteratorNext(newUnorderedList(), Results.ElementNotFound));
		printTest("oneElementList_removeElement_testToString", testToString(newUnorderedList(), Results.NoException));
		
		if(listType == ListType.Array || listType == ListType.Single || listType == ListType.Double) {
			printTestHeader(6,"Indexed");
			printTest("oneElementList_removeElement_testAdd_A", testAdd(newIndexedList(), A, Results.NoException));
			printTest("oneElementList_removeElement_testAdd_0_A", testAdd(newIndexedList(), 0, A, Results.IndexOutOfBounds));
			printTest("oneElementList_removeElement_testAdd_-1_A", testAdd(newIndexedList(), -1, A, Results.IndexOutOfBounds));
			printTest("oneElementList_removeElement_testAdd_5_A", testAdd(newIndexedList(), 5, A, Results.IndexOutOfBounds));
			printTest("oneElementList_removeElement_testSet_0_A", testSet(newIndexedList(), 0, A, Results.IndexOutOfBounds));
			printTest("oneElementList_removeElement_testSet_-1_A", testSet(newIndexedList(), -1, A, Results.IndexOutOfBounds));
			printTest("oneElementList_removeElement_testSet_5_A", testSet(newIndexedList(), 5, A, Results.IndexOutOfBounds));
			printTest("oneElementList_removeElement_testGet_0", testGet(newIndexedList(), 0, Results.IndexOutOfBounds));
			printTest("oneElementList_removeElement_testGet_-1", testGet(newIndexedList(), -1, Results.IndexOutOfBounds));
			printTest("oneElementList_removeElement_testGet_5", testGet(newIndexedList(), 5, Results.IndexOutOfBounds));
			printTest("oneElementList_removeElement_testIndexOf_A", testIndexOf(newIndexedList(), A, -1));
			printTest("oneElementList_removeElement_testIndexOf_B", testIndexOf(newIndexedList(), B, -1));
			printTest("oneElementList_removeElement_testRemoveAtIndex_0", testRemoveAtIndex(newIndexedList(), 0, Results.IndexOutOfBounds));
			printTest("oneElementList_removeElement_testRemoveAtIndex_-1", testRemoveAtIndex(newIndexedList(), -1, Results.IndexOutOfBounds));
			printTest("oneElementList_removeElement_testRemoveAtIndex_5", testRemoveAtIndex(newIndexedList(), 5, Results.IndexOutOfBounds));
		}
	}
	
	// Scenario 7 - [A] -> addToFront(B) -> [B, A]
	private void oneElementList_addToFrontTests()
	{
		printTestHeader(7,"Unordered");
		printTest("oneElementList_addToFront_testAddToFront_C", testAddToFront(newUnorderedList(B,A), C, Results.NoException));
		printTest("oneElementList_addToFront_testAddToRear_C", testAddToRear(newUnorderedList(B,A), C, Results.NoException));
		printTest("oneElementList_addToFront_testAddAfter_C_A", testAddAfter(newUnorderedList(B,A), C, A, Results.NoException));
		printTest("oneElementList_addToFront_testAddAfter_D_C", testAddAfter(newUnorderedList(B,A), D, C, Results.ElementNotFound));
		printTest("oneElementList_addToFront_testRemoveFirst", testRemoveFirst(newUnorderedList(B,A), Results.NoException));
		printTest("oneElementList_addToFront_testRemoveLast", testRemoveLast(newUnorderedList(B,A), Results.NoException));
		printTest("oneElementList_addToFront_testRemove_A", testRemove(newUnorderedList(B,A), A, Results.NoException));
		printTest("oneElementList_addToFront_testRemove_C", testRemove(newUnorderedList(B,A), C, Results.ElementNotFound));
		printTest("oneElementList_addToFront_testFirst", testFirst(newUnorderedList(B,A), Results.NoException));
		printTest("oneElementList_addToFront_testLast", testLast(newUnorderedList(B,A), Results.NoException));
		printTest("oneElementList_addToFront_testContains_A", testContains(newUnorderedList(B,A), A, Results.True));
		printTest("oneElementList_addToFront_testContains_C", testContains(newUnorderedList(B,A), C, Results.False));
		printTest("oneElementList_addToFront_testIsEmpty", testIsEmpty(newUnorderedList(B,A), Results.False));
		printTest("oneElementList_addToFront_testSize", testSize(newUnorderedList(B,A), 2));
		printTest("oneElementList_addToFront_testIterator", testIterator(newUnorderedList(B,A), Results.NoException));
		printTest("oneElementList_addToFront_testIteratorHasNext", testIteratorHasNext(newUnorderedList(B,A), Results.True));
		printTest("oneElementList_addToFront_testIteratorNext", testIteratorNext(newUnorderedList(B,A), Results.NoException));
		printTest("oneElementList_addToFront_testToString", testToString(newUnorderedList(B,A), Results.NoException));
		
		if(listType == ListType.Array || listType == ListType.Single || listType == ListType.Double) {
			printTestHeader(7,"Indexed");
			printTest("oneElementList_addToFront_testAdd_C", testAdd(newIndexedList(B,A), C, Results.NoException));
			printTest("oneElementList_addToFront_testAdd_0_C", testAdd(newIndexedList(B,A), 0, C, Results.NoException));
			printTest("oneElementList_addToFront_testAdd_1_C", testAdd(newIndexedList(B,A), 1, C, Results.NoException));
			printTest("oneElementList_addToFront_testAdd_2_C", testAdd(newIndexedList(B,A), 2, C, Results.IndexOutOfBounds));
			printTest("oneElementList_addToFront_testAdd_-1_C", testAdd(newIndexedList(B,A), -1, C, Results.IndexOutOfBounds));
			printTest("oneElementList_addToFront_testAdd_3_C", testAdd(newIndexedList(B,A), 3, C, Results.IndexOutOfBounds));
			printTest("oneElementList_addToFront_testSet_0_C", testSet(newIndexedList(B,A), 0, C, Results.NoException));
			printTest("oneElementList_addToFront_testSet_1_C", testSet(newIndexedList(B,A), 1, C, Results.NoException));
			printTest("oneElementList_addToFront_testSet_-1_C", testSet(newIndexedList(B,A), -1, C, Results.IndexOutOfBounds));
			printTest("oneElementList_addToFront_testSet_2_C", testSet(newIndexedList(B,A), 2, C, Results.IndexOutOfBounds));
			printTest("oneElementList_addToFront_testGet_0", testGet(newIndexedList(B,A), 0, Results.NoException));
			printTest("oneElementList_addToFront_testGet_1", testGet(newIndexedList(B,A), 1, Results.NoException));
			printTest("oneElementList_addToFront_testGet_-1", testGet(newIndexedList(B,A), -1, Results.IndexOutOfBounds));
			printTest("oneElementList_addToFront_testGet_2", testGet(newIndexedList(B,A), 2, Results.IndexOutOfBounds));
			printTest("oneElementList_addToFront_testIndexOf_A", testIndexOf(newIndexedList(B,A), A, 1));
			printTest("oneElementList_addToFront_testIndexOf_B", testIndexOf(newIndexedList(B,A), B, 0));
			printTest("oneElementList_addToFront_testIndexOf_C", testIndexOf(newIndexedList(B,A), C, -1));
			printTest("oneElementList_addToFront_testRemoveAtIndex_0", testRemoveAtIndex(newIndexedList(B,A), 0, Results.NoException));
			printTest("oneElementList_addToFront_testRemoveAtIndex_1", testRemoveAtIndex(newIndexedList(B,A), 1, Results.NoException));
			printTest("oneElementList_addToFront_testRemoveAtIndex_-1", testRemoveAtIndex(newIndexedList(B,A), -1, Results.IndexOutOfBounds));
			printTest("oneElementList_addToFront_testRemoveAtIndex_2", testRemoveAtIndex(newIndexedList(B,A), 2, Results.IndexOutOfBounds));
		}
	}
	
	// Scenario 8 - [A] -> addToRear(B) -> [A, B]
	private void oneElementList_addToRearTests()
	{
		printTestHeader(8,"Unordered");
		printTest("oneElementList_addToRear_testAddToFront_C", testAddToFront(newUnorderedList(A,B), C, Results.NoException));
		printTest("oneElementList_addToRear_testAddToRear_C", testAddToRear(newUnorderedList(A,B), C, Results.NoException));
		printTest("oneElementList_addToRear_testAddAfter_C_A", testAddAfter(newUnorderedList(A,B), C, A, Results.NoException));
		printTest("oneElementList_addToRear_testAddAfter_C_D", testAddAfter(newUnorderedList(A,B), C, D, Results.ElementNotFound));
		printTest("oneElementList_addToRear_testRemoveFirst", testRemoveFirst(newUnorderedList(A,B), Results.NoException));
		printTest("oneElementList_addToRear_testRemoveLast", testRemoveLast(newUnorderedList(A,B), Results.NoException));
		printTest("oneElementList_addToRear_testRemove_A", testRemove(newUnorderedList(A,B), A, Results.NoException));
		printTest("oneElementList_addToRear_testRemove_C", testRemove(newUnorderedList(A,B), C, Results.ElementNotFound));
		printTest("oneElementList_addToRear_testFirst", testFirst(newUnorderedList(A,B), Results.NoException));
		printTest("oneElementList_addToRear_testLast", testLast(newUnorderedList(A,B), Results.NoException));
		printTest("oneElementList_addToRear_testContains_A", testContains(newUnorderedList(A,B), A, Results.True));
		printTest("oneElementList_addToRear_testContains_C", testContains(newUnorderedList(A,B), C, Results.False));
		printTest("oneElementList_addToRear_testIsEmpty", testIsEmpty(newUnorderedList(A,B), Results.False));
		printTest("oneElementList_addToRear_testSize", testSize(newUnorderedList(A,B), 2));
		printTest("oneElementList_addToRear_testIterator", testIterator(newUnorderedList(A,B), Results.NoException));
		printTest("oneElementList_addToRear_testIteratorHasNext", testIteratorHasNext(newUnorderedList(A,B), Results.True));
		printTest("oneElementList_addToRear_testIteratorNext", testIteratorNext(newUnorderedList(A,B), Results.NoException));
		printTest("oneElementList_addToRear_testToString", testToString(newUnorderedList(A,B), Results.NoException));
		
		if(listType == ListType.Array || listType == ListType.Single || listType == ListType.Double) {
			printTestHeader(8,"Indexed");
			printTest("oneElementList_addToRear_testAdd_C", testAdd(newIndexedList(A,B), C, Results.NoException));
			printTest("oneElementList_addToRear_testAdd_0_C", testAdd(newIndexedList(A,B), 0, C, Results.NoException));
			printTest("oneElementList_addToRear_testAdd_1_C", testAdd(newIndexedList(A,B), 1, C, Results.NoException));
			printTest("oneElementList_addToRear_testAdd_2_C", testAdd(newIndexedList(A,B), 2, C, Results.IndexOutOfBounds));
			printTest("oneElementList_addToRear_testAdd_-1_C", testAdd(newIndexedList(A,B), -1, C, Results.IndexOutOfBounds));
			printTest("oneElementList_addToRear_testAdd_3_C", testAdd(newIndexedList(A,B), 3, C, Results.IndexOutOfBounds));
			printTest("oneElementList_addToRear_testSet_0_C", testSet(newIndexedList(A,B), 0, C, Results.NoException));
			printTest("oneElementList_addToRear_testSet_1_C", testSet(newIndexedList(A,B), 1, C, Results.NoException));
			printTest("oneElementList_addToRear_testSet_-1_C", testSet(newIndexedList(A,B), -1, C, Results.IndexOutOfBounds));
			printTest("oneElementList_addToRear_testSet_2_C", testSet(newIndexedList(A,B), 2, C, Results.IndexOutOfBounds));
			printTest("oneElementList_addToRear_testGet_0", testGet(newIndexedList(A,B), 0, Results.NoException));
			printTest("oneElementList_addToRear_testGet_1", testGet(newIndexedList(A,B), 1, Results.NoException));
			printTest("oneElementList_addToRear_testGet_-1", testGet(newIndexedList(A,B), -1, Results.IndexOutOfBounds));
			printTest("oneElementList_addToRear_testGet_2", testGet(newIndexedList(A,B), 2, Results.IndexOutOfBounds));
			printTest("oneElementList_addToRear_testIndexOf_A", testIndexOf(newIndexedList(A,B), A, 0));
			printTest("oneElementList_addToRear_testIndexOf_B", testIndexOf(newIndexedList(A,B), B, 1));
			printTest("oneElementList_addToRear_testIndexOf_C", testIndexOf(newIndexedList(A,B), C, -1));
			printTest("oneElementList_addToRear_testRemoveAtIndex_0", testRemoveAtIndex(newIndexedList(A,B), 0, Results.NoException));
			printTest("oneElementList_addToRear_testRemoveAtIndex_1", testRemoveAtIndex(newIndexedList(A,B), 1, Results.NoException));
			printTest("oneElementList_addToRear_testRemoveAtIndex_-1", testRemoveAtIndex(newIndexedList(A,B), -1, Results.IndexOutOfBounds));
			printTest("oneElementList_addToRear_testRemoveAtIndex_2", testRemoveAtIndex(newIndexedList(A,B), 2, Results.IndexOutOfBounds));
		}
	}
	
	// Scenario 9 - [A] -> addAfter(B, A) -> [A, B]
	private void oneElementList_addAfterElementTests()
	{
		printTestHeader(9,"Unordered");
		printTest("oneElementList_addAfterElement_testAddToFront_C", testAddToFront(newUnorderedList(A,B), C, Results.NoException));
		printTest("oneElementList_addAfterElement_testAddToRear_C", testAddToRear(newUnorderedList(A,B), C, Results.NoException));
		printTest("oneElementList_addAfterElement_testAddAfter_C_A", testAddAfter(newUnorderedList(A,B), C, A, Results.NoException));
		printTest("oneElementList_addAfterElement_testAddAfter_C_D", testAddAfter(newUnorderedList(A,B), C, D, Results.ElementNotFound));
		printTest("oneElementList_addAfterElement_testRemoveFirst", testRemoveFirst(newUnorderedList(A,B), Results.NoException));
		printTest("oneElementList_addAfterElement_testRemoveLast", testRemoveLast(newUnorderedList(A,B), Results.NoException));
		printTest("oneElementList_addAfterElement_testRemove_A", testRemove(newUnorderedList(A,B), A, Results.NoException));
		printTest("oneElementList_addAfterElement_testRemove_C", testRemove(newUnorderedList(A,B), C, Results.ElementNotFound));
		printTest("oneElementList_addAfterElement_testFirst", testFirst(newUnorderedList(A,B), Results.NoException));
		printTest("oneElementList_addAfterElement_testLast", testLast(newUnorderedList(A,B), Results.NoException));
		printTest("oneElementList_addAfterElement_testContains_A", testContains(newUnorderedList(A,B), A, Results.True));
		printTest("oneElementList_addAfterElement_testContains_C", testContains(newUnorderedList(A,B), C, Results.False));
		printTest("oneElementList_addAfterElement_testIsEmpty", testIsEmpty(newUnorderedList(A,B), Results.False));
		printTest("oneElementList_addAfterElement_testSize", testSize(newUnorderedList(A,B), 2));
		printTest("oneElementList_addAfterElement_testIterator", testIterator(newUnorderedList(A,B), Results.NoException));
		printTest("oneElementList_addAfterElement_testIteratorHasNext", testIteratorHasNext(newUnorderedList(A,B), Results.True));
		printTest("oneElementList_addAfterElement_testIteratorNext", testIteratorNext(newUnorderedList(A,B), Results.NoException));
		printTest("oneElementList_addAfterElement_testToString", testToString(newUnorderedList(A,B), Results.NoException));
		
		if(listType == ListType.Array || listType == ListType.Single || listType == ListType.Double) {
			printTestHeader(9,"Indexed");
			printTest("oneElementList_addAfterElement_testAdd_C", testAdd(newIndexedList(A,B), C, Results.NoException));
			printTest("oneElementList_addAfterElement_testAdd_0_C", testAdd(newIndexedList(A,B), 0, C, Results.NoException));
			printTest("oneElementList_addAfterElement_testAdd_1_C", testAdd(newIndexedList(A,B), 1, C, Results.NoException));
			printTest("oneElementList_addAfterElement_testAdd_2_C", testAdd(newIndexedList(A,B), 2, C, Results.IndexOutOfBounds));
			printTest("oneElementList_addAfterElement_testAdd_-1_C", testAdd(newIndexedList(A,B), -1, C, Results.IndexOutOfBounds));
			printTest("oneElementList_addAfterElement_testAdd_3_C", testAdd(newIndexedList(A,B), 3, C, Results.IndexOutOfBounds));
			printTest("oneElementList_addAfterElement_testSet_0_C", testSet(newIndexedList(A,B), 0, C, Results.NoException));
			printTest("oneElementList_addAfterElement_testSet_1_C", testSet(newIndexedList(A,B), 1, C, Results.NoException));
			printTest("oneElementList_addAfterElement_testSet_-1_C", testSet(newIndexedList(A,B), -1, C, Results.IndexOutOfBounds));
			printTest("oneElementList_addAfterElement_testSet_2_C", testSet(newIndexedList(A,B), 2, C, Results.IndexOutOfBounds));
			printTest("oneElementList_addAfterElement_testGet_0", testGet(newIndexedList(A,B), 0, Results.NoException));
			printTest("oneElementList_addAfterElement_testGet_1", testGet(newIndexedList(A,B), 1, Results.NoException));
			printTest("oneElementList_addAfterElement_testGet_-1", testGet(newIndexedList(A,B), -1, Results.IndexOutOfBounds));
			printTest("oneElementList_addAfterElement_testGet_2", testGet(newIndexedList(A,B), 2, Results.IndexOutOfBounds));
			printTest("oneElementList_addAfterElement_testIndexOf_A", testIndexOf(newIndexedList(A,B), A, 0));
			printTest("oneElementList_addAfterElement_testIndexOf_B", testIndexOf(newIndexedList(A,B), B, 1));
			printTest("oneElementList_addAfterElement_testIndexOf_C", testIndexOf(newIndexedList(A,B), C, -1));
			printTest("oneElementList_addAfterElement_testRemoveAtIndex_0", testRemoveAtIndex(newIndexedList(A,B), 0, Results.NoException));
			printTest("oneElementList_addAfterElement_testRemoveAtIndex_1", testRemoveAtIndex(newIndexedList(A,B), 1, Results.NoException));
			printTest("oneElementList_addAfterElement_testRemoveAtIndex_-1", testRemoveAtIndex(newIndexedList(A,B), -1, Results.IndexOutOfBounds));
			printTest("oneElementList_addAfterElement_testRemoveAtIndex_2", testRemoveAtIndex(newIndexedList(A,B), 2, Results.IndexOutOfBounds));
		}
	}
	
	// Scenario 10 - [A, B] -> removeFirst -> [B]
	private void twoElementList_removeFirstTests()
	{
		printTestHeader(10,"Unordered");
		printTest("twoElementList_removeFirst_testAddToFront_C", testAddToFront(newUnorderedList(B), C, Results.NoException));
		printTest("twoElementList_removeFirst_testAddToRear_C", testAddToRear(newUnorderedList(B), C, Results.NoException));
		printTest("twoElementList_removeFirst_testAddAfter_C_B", testAddAfter(newUnorderedList(B), C, B, Results.NoException));
		printTest("twoElementList_removeFirst_testAddAfter_C_A", testAddAfter(newUnorderedList(B), C, A, Results.ElementNotFound));
		printTest("twoElementList_removeFirst_testRemoveFirst", testRemoveFirst(newUnorderedList(B), Results.NoException));
		printTest("twoElementList_removeFirst_testRemoveLast", testRemoveLast(newUnorderedList(B), Results.NoException));
		printTest("twoElementList_removeFirst_testRemove_B", testRemove(newUnorderedList(B), B, Results.NoException));
		printTest("twoElementList_removeFirst_testRemove_C", testRemove(newUnorderedList(B), C, Results.ElementNotFound));
		printTest("twoElementList_removeFirst_testFirst", testFirst(newUnorderedList(B), Results.NoException));
		printTest("twoElementList_removeFirst_testLast", testLast(newUnorderedList(B), Results.NoException));
		printTest("twoElementList_removeFirst_testContains_B", testContains(newUnorderedList(B), B, Results.True));
		printTest("twoElementList_removeFirst_testContains_A", testContains(newUnorderedList(B), A, Results.False));
		printTest("twoElementList_removeFirst_testIsEmpty", testIsEmpty(newUnorderedList(B), Results.False));
		printTest("twoElementList_removeFirst_testSize", testSize(newUnorderedList(B), 1));
		printTest("twoElementList_removeFirst_testIterator", testIterator(newUnorderedList(B), Results.NoException));
		printTest("twoElementList_removeFirst_testIteratorHasNext", testIteratorHasNext(newUnorderedList(B), Results.True));
		printTest("twoElementList_removeFirst_testIteratorNext", testIteratorNext(newUnorderedList(B), Results.NoException));
		printTest("twoElementList_removeFirst_testToString", testToString(newUnorderedList(B), Results.NoException));
		
		if(listType == ListType.Array || listType == ListType.Single || listType == ListType.Double) {
			printTestHeader(10,"Indexed");
			printTest("twoElementList_removeFirst_testAdd_A", testAdd(newIndexedList(B), A, Results.NoException));
			printTest("twoElementList_removeFirst_testAdd_0_A", testAdd(newIndexedList(B), 0, A, Results.NoException));
			printTest("twoElementList_removeFirst_testAdd_1_A", testAdd(newIndexedList(B), 1, A, Results.IndexOutOfBounds));
			printTest("twoElementList_removeFirst_testAdd_-1_A", testAdd(newIndexedList(B), -1, A, Results.IndexOutOfBounds));
			printTest("twoElementList_removeFirst_testAdd_2_A", testAdd(newIndexedList(B), 2, A, Results.IndexOutOfBounds));
			printTest("twoElementList_removeFirst_testSet_0_A", testSet(newIndexedList(B), 0, A, Results.NoException));
			printTest("twoElementList_removeFirst_testSet_-1_A", testSet(newIndexedList(B), -1, A, Results.IndexOutOfBounds));
			printTest("twoElementList_removeFirst_testSet_1_A", testSet(newIndexedList(B), 1, A, Results.IndexOutOfBounds));
			printTest("twoElementList_removeFirst_testGet_0", testGet(newIndexedList(B), 0, Results.NoException));
			printTest("twoElementList_removeFirst_testGet_-1", testGet(newIndexedList(B), -1, Results.IndexOutOfBounds));
			printTest("twoElementList_removeFirst_testGet_1", testGet(newIndexedList(B), 1, Results.IndexOutOfBounds));
			printTest("twoElementList_removeFirst_testIndexOf_A", testIndexOf(newIndexedList(B), A, -1));
			printTest("twoElementList_removeFirst_testIndexOf_B", testIndexOf(newIndexedList(B), B, 0));
			printTest("twoElementList_removeFirst_testRemoveAtIndex_0", testRemoveAtIndex(newIndexedList(B), 0, Results.NoException));
			printTest("twoElementList_removeFirst_testRemoveAtIndex_-1", testRemoveAtIndex(newIndexedList(B), -1, Results.IndexOutOfBounds));
			printTest("twoElementList_removeFirst_testRemoveAtIndex_1", testRemoveAtIndex(newIndexedList(B), 1, Results.IndexOutOfBounds));
		}
	}
	
	// Scenario 11 - [A, B] -> removeLast -> [A]
	private void twoElementList_removeLastTests()
	{
		printTestHeader(11,"Unordered");
		printTest("twoElementList_removeLast_testAddToFront_C", testAddToFront(newUnorderedList(A), C, Results.NoException));
		printTest("twoElementList_removeLast_testAddToRear_C", testAddToRear(newUnorderedList(A), C, Results.NoException));
		printTest("twoElementList_removeLast_testAddAfter_C_A", testAddAfter(newUnorderedList(A), C, A, Results.NoException));
		printTest("twoElementList_removeLast_testAddAfter_C_B", testAddAfter(newUnorderedList(A), C, B, Results.ElementNotFound));
		printTest("twoElementList_removeLast_testRemoveFirst", testRemoveFirst(newUnorderedList(A), Results.NoException));
		printTest("twoElementList_removeLast_testRemoveLast", testRemoveLast(newUnorderedList(A), Results.NoException));
		printTest("twoElementList_removeLast_testRemove_A", testRemove(newUnorderedList(A), A, Results.NoException));
		printTest("twoElementList_removeLast_testRemove_B", testRemove(newUnorderedList(A), B, Results.ElementNotFound));
		printTest("twoElementList_removeLast_testFirst", testFirst(newUnorderedList(A), Results.NoException));
		printTest("twoElementList_removeLast_testLast", testLast(newUnorderedList(A), Results.NoException));
		printTest("twoElementList_removeLast_testContains_A", testContains(newUnorderedList(A), A, Results.True));
		printTest("twoElementList_removeLast_testContains_B", testContains(newUnorderedList(A), B, Results.False));
		printTest("twoElementList_removeLast_testIsEmpty", testIsEmpty(newUnorderedList(A), Results.False));
		printTest("twoElementList_removeLast_testSize", testSize(newUnorderedList(A), 1));
		printTest("twoElementList_removeLast_testIterator", testIterator(newUnorderedList(A), Results.NoException));
		printTest("twoElementList_removeLast_testIteratorHasNext", testIteratorHasNext(newUnorderedList(A), Results.True));
		printTest("twoElementList_removeLast_testIteratorNext", testIteratorNext(newUnorderedList(A), Results.NoException));
		printTest("twoElementList_removeLast_testToString", testToString(newUnorderedList(A), Results.NoException));
		
		if(listType == ListType.Array || listType == ListType.Single || listType == ListType.Double) {
			printTestHeader(11,"Indexed");
			printTest("twoElementList_removeLast_testAdd_B", testAdd(newIndexedList(A), B, Results.NoException));
			printTest("twoElementList_removeLast_testAdd_0_B", testAdd(newIndexedList(A), 0, B, Results.NoException));
			printTest("twoElementList_removeLast_testAdd_1_B", testAdd(newIndexedList(A), 1, B, Results.IndexOutOfBounds));
			printTest("twoElementList_removeLast_testAdd_-1_B", testAdd(newIndexedList(A), -1, B, Results.IndexOutOfBounds));
			printTest("twoElementList_removeLast_testAdd_2_B", testAdd(newIndexedList(A), 2, B, Results.IndexOutOfBounds));
			printTest("twoElementList_removeLast_testSet_0_B", testSet(newIndexedList(A), 0, B, Results.NoException));
			printTest("twoElementList_removeLast_testSet_-1_B", testSet(newIndexedList(A), -1, B, Results.IndexOutOfBounds));
			printTest("twoElementList_removeLast_testSet_1_B", testSet(newIndexedList(A), 1, B, Results.IndexOutOfBounds));
			printTest("twoElementList_removeLast_testGet_0", testGet(newIndexedList(A), 0, Results.NoException));
			printTest("twoElementList_removeLast_testGet_-1", testGet(newIndexedList(A), -1, Results.IndexOutOfBounds));
			printTest("twoElementList_removeLast_testGet_1", testGet(newIndexedList(A), 1, Results.IndexOutOfBounds));
			printTest("twoElementList_removeLast_testIndexOf_A", testIndexOf(newIndexedList(A), A, 0));
			printTest("twoElementList_removeLast_testIndexOf_B", testIndexOf(newIndexedList(A), B, -1));
			printTest("twoElementList_removeLast_testRemoveAtIndex_0", testRemoveAtIndex(newIndexedList(A), 0, Results.NoException));
			printTest("twoElementList_removeLast_testRemoveAtIndex_-1", testRemoveAtIndex(newIndexedList(A), -1, Results.IndexOutOfBounds));
			printTest("twoElementList_removeLast_testRemoveAtIndex_1", testRemoveAtIndex(newIndexedList(A), 1, Results.IndexOutOfBounds));
		}
	}
	
	// Scenario 12 - [A, B] -> remove(A) -> [B]
	private void twoElementList_removeFirstElementTests()
	{
		printTestHeader(12,"Unordered");
		printTest("twoElementList_removeFirstElement_testAddToFront_C", testAddToFront(newUnorderedList(B), C, Results.NoException));
		printTest("twoElementList_removeFirstElement_testAddToRear_C", testAddToRear(newUnorderedList(B), C, Results.NoException));
		printTest("twoElementList_removeFirstElement_testAddAfter_C_B", testAddAfter(newUnorderedList(B), C, B, Results.NoException));
		printTest("twoElementList_removeFirstElement_testAddAfter_C_A", testAddAfter(newUnorderedList(B), C, A, Results.ElementNotFound));
		printTest("twoElementList_removeFirstElement_testRemoveFirst", testRemoveFirst(newUnorderedList(B), Results.NoException));
		printTest("twoElementList_removeFirstElement_testRemoveLast", testRemoveLast(newUnorderedList(B), Results.NoException));
		printTest("twoElementList_removeFirstElement_testRemove_B", testRemove(newUnorderedList(B), B, Results.NoException));
		printTest("twoElementList_removeFirstElement_testRemove_C", testRemove(newUnorderedList(B), C, Results.ElementNotFound));
		printTest("twoElementList_removeFirstElement_testFirst", testFirst(newUnorderedList(B), Results.NoException));
		printTest("twoElementList_removeFirstElement_testLast", testLast(newUnorderedList(B), Results.NoException));
		printTest("twoElementList_removeFirstElement_testContains_B", testContains(newUnorderedList(B), B, Results.True));
		printTest("twoElementList_removeFirstElement_testContains_A", testContains(newUnorderedList(B), A, Results.False));
		printTest("twoElementList_removeFirstElement_testIsEmpty", testIsEmpty(newUnorderedList(B), Results.False));
		printTest("twoElementList_removeFirstElement_testSize", testSize(newUnorderedList(B), 1));
		printTest("twoElementList_removeFirstElement_testIterator", testIterator(newUnorderedList(B), Results.NoException));
		printTest("twoElementList_removeFirstElement_testIteratorHasNext", testIteratorHasNext(newUnorderedList(B), Results.True));
		printTest("twoElementList_removeFirstElement_testIteratorNext", testIteratorNext(newUnorderedList(B), Results.NoException));
		printTest("twoElementList_removeFirstElement_testToString", testToString(newUnorderedList(B), Results.NoException));
		
		if(listType == ListType.Array || listType == ListType.Single || listType == ListType.Double) {
			printTestHeader(12,"Indexed");
			printTest("twoElementList_removeFirstElement_testAdd_A", testAdd(newIndexedList(B), A, Results.NoException));
			printTest("twoElementList_removeFirstElement_testAdd_0_A", testAdd(newIndexedList(B), 0, A, Results.NoException));
			printTest("twoElementList_removeFirstElement_testAdd_1_A", testAdd(newIndexedList(B), 1, A, Results.IndexOutOfBounds));
			printTest("twoElementList_removeFirstElement_testAdd_-1_A", testAdd(newIndexedList(B), -1, A, Results.IndexOutOfBounds));
			printTest("twoElementList_removeFirstElement_testAdd_2_A", testAdd(newIndexedList(B), 2, A, Results.IndexOutOfBounds));
			printTest("twoElementList_removeFirstElement_testSet_0_A", testSet(newIndexedList(B), 0, A, Results.NoException));
			printTest("twoElementList_removeFirstElement_testSet_-1_A", testSet(newIndexedList(B), -1, A, Results.IndexOutOfBounds));
			printTest("twoElementList_removeFirstElement_testSet_1_A", testSet(newIndexedList(B), 1, A, Results.IndexOutOfBounds));
			printTest("twoElementList_removeFirstElement_testGet_0", testGet(newIndexedList(B), 0, Results.NoException));
			printTest("twoElementList_removeFirstElement_testGet_-1", testGet(newIndexedList(B), -1, Results.IndexOutOfBounds));
			printTest("twoElementList_removeFirstElement_testGet_1", testGet(newIndexedList(B), 1, Results.IndexOutOfBounds));
			printTest("twoElementList_removeFirstElement_testIndexOf_A", testIndexOf(newIndexedList(B), A, -1));
			printTest("twoElementList_removeFirstElement_testIndexOf_B", testIndexOf(newIndexedList(B), B, 0));
			printTest("twoElementList_removeFirstElement_testRemoveAtIndex_0", testRemoveAtIndex(newIndexedList(B), 0, Results.NoException));
			printTest("twoElementList_removeFirstElement_testRemoveAtIndex_-1", testRemoveAtIndex(newIndexedList(B), -1, Results.IndexOutOfBounds));
			printTest("twoElementList_removeFirstElement_testRemoveAtIndex_1", testRemoveAtIndex(newIndexedList(B), 1, Results.IndexOutOfBounds));
		}
	}
	
	// Scenario 13 - [A, B] -> remove(B) -> [A]
	private void twoElementList_removeSecondElementTests()
	{
		printTestHeader(13,"Unordered");
		printTest("twoElementList_removeSecondElement_testAddToFront_B", testAddToFront(newUnorderedList(A), B, Results.NoException));
		printTest("twoElementList_removeSecondElement_testAddToRear_B", testAddToRear(newUnorderedList(A), B, Results.NoException));
		printTest("twoElementList_removeSecondElement_testAddAfter_B_A", testAddAfter(newUnorderedList(A), B, A, Results.NoException));
		printTest("twoElementList_removeSecondElement_testAddAfter_C_B", testAddAfter(newUnorderedList(A), C, B, Results.ElementNotFound));
		printTest("twoElementList_removeSecondElement_testRemoveFirst", testRemoveFirst(newUnorderedList(A), Results.NoException));
		printTest("twoElementList_removeSecondElement_testRemoveLast", testRemoveLast(newUnorderedList(A), Results.NoException));
		printTest("twoElementList_removeSecondElement_testRemove_A", testRemove(newUnorderedList(A), A, Results.NoException));
		printTest("twoElementList_removeSecondElement_testRemove_E", testRemove(newUnorderedList(A), E, Results.ElementNotFound));
		printTest("twoElementList_removeSecondElement_testFirst", testFirst(newUnorderedList(A), Results.NoException));
		printTest("twoElementList_removeSecondElement_testLast", testLast(newUnorderedList(A), Results.NoException));
		printTest("twoElementList_removeSecondElement_testContains_A", testContains(newUnorderedList(A), A, Results.True));
		printTest("twoElementList_removeSecondElement_testContains_B", testContains(newUnorderedList(A), B, Results.False));
		printTest("twoElementList_removeSecondElement_testIsEmpty", testIsEmpty(newUnorderedList(A), Results.False));
		printTest("twoElementList_removeSecondElement_testSize", testSize(newUnorderedList(A), 1));
		printTest("twoElementList_removeSecondElement_testIterator", testIterator(newUnorderedList(A), Results.NoException));
		printTest("twoElementList_removeSecondElement_testIteratorHasNext", testIteratorHasNext(newUnorderedList(A), Results.True));
		printTest("towElementList_removeSecondElement_testIteratorNext", testIteratorNext(newUnorderedList(A), Results.NoException));
		printTest("twoElementList_removeSecondElement_testToString", testToString(newUnorderedList(A), Results.NoException));
		
		if(listType == ListType.Array || listType == ListType.Single || listType == ListType.Double) {
			printTestHeader(13,"Indexed");
			printTest("twoElementList_removeSecondElement_testAdd_B", testAdd(newIndexedList(A), B, Results.NoException));
			printTest("twoElementList_removeSecondElement_testAdd_0_B", testAdd(newIndexedList(A), 0, B, Results.NoException));
			printTest("twoElementList_removeSecondElement_testAdd_1_B", testAdd(newIndexedList(A), 1, B, Results.IndexOutOfBounds));
			printTest("twoElementList_removeSecondElement_testAdd_-1_B", testAdd(newIndexedList(A), -1, B, Results.IndexOutOfBounds));
			printTest("twoElementList_removeSecondElement_testAdd_2_B", testAdd(newIndexedList(A), 2, B, Results.IndexOutOfBounds));
			printTest("twoElementList_removeSecondElement_testSet_0_B", testSet(newIndexedList(A), 0, B, Results.NoException));
			printTest("twoElementList_removeSecondElement_testSet_-1_B", testSet(newIndexedList(A), -1, B, Results.IndexOutOfBounds));
			printTest("twoElementList_removeSecondElement_testSet_1_B", testSet(newIndexedList(A), 1, B, Results.IndexOutOfBounds));
			printTest("twoElementList_removeSecondElement_testGet_0", testGet(newIndexedList(A), 0, Results.NoException));
			printTest("twoElementList_removeSecondElement_testGet_-1", testGet(newIndexedList(A), -1, Results.IndexOutOfBounds));
			printTest("twoElementList_removeSecondElement_testGet_1", testGet(newIndexedList(A), 1, Results.IndexOutOfBounds));
			printTest("twoElementList_removeSecondElement_testIndexOf_A", testIndexOf(newIndexedList(A), A, 0));
			printTest("twoElementList_removeSecondElement_testIndexOf_B", testIndexOf(newIndexedList(A), B, -1));
			printTest("twoElementList_removeSecondElement_testRemoveAtIndex_0", testRemoveAtIndex(newIndexedList(A), 0, Results.NoException));
			printTest("twoElementList_removeSecondElement_testRemoveAtIndex_-1", testRemoveAtIndex(newIndexedList(A), -1, Results.IndexOutOfBounds));
			printTest("twoElementList_removeSecondElement_testRemoveAtIndex_1", testRemoveAtIndex(newIndexedList(A), 1, Results.IndexOutOfBounds));
		}
	}
	
	// Scenario 14 - [A, B] -> addToFront(C) -> [A, B, C]
	private void twoElementList_addToFrontTests()
	{
		printTestHeader(14,"Unordered");
		printTest("twoElementList_addToFront_testAddToFront_D", testAddToFront(newUnorderedList(A,B,C), D, Results.NoException));
		printTest("twoElementList_addToFront_testAddToRear_E", testAddToRear(newUnorderedList(A,B,C), E, Results.NoException));
		printTest("twoElementList_addToFront_testAddAfter_D_C", testAddAfter(newUnorderedList(A,B,C), D, C, Results.NoException));
		printTest("twoElementList_addToFront_testAddAfter_E_D", testAddAfter(newUnorderedList(A,B,C), E, D, Results.ElementNotFound));
		printTest("twoElementList_addToFront_testRemoveFirst", testRemoveFirst(newUnorderedList(A,B,C), Results.NoException));
		printTest("twoElementList_addToFront_testRemoveLast", testRemoveLast(newUnorderedList(A,B,C), Results.NoException));
		printTest("twoElementList_addToFront_testRemove_A", testRemove(newUnorderedList(A,B,C), A, Results.NoException));
		printTest("twoElementList_addToFront_testRemove_D", testRemove(newUnorderedList(A,B,C), D, Results.ElementNotFound));
		printTest("twoElementList_addToFront_testFirst", testFirst(newUnorderedList(A,B,C), Results.NoException));
		printTest("twoElementList_addToFront_testLast", testLast(newUnorderedList(A,B,C), Results.NoException));
		printTest("twoElementList_addToFront_testContains_A", testContains(newUnorderedList(A,B,C), A, Results.True));
		printTest("twoElementList_addToFront_testContains_D", testContains(newUnorderedList(A,B,C), D, Results.False));
		printTest("twoElementList_addToFront_testIsEmpty", testIsEmpty(newUnorderedList(A,B,C), Results.False));
		printTest("twoElementList_addToFront_testSize", testSize(newUnorderedList(A,B,C), 3));
		printTest("twoElementList_addToFront_testIterator", testIterator(newUnorderedList(A,B,C), Results.NoException));
		printTest("twoElementList_addToFront_testIteratorHasNext", testIteratorHasNext(newUnorderedList(A,B,C), Results.True));
		printTest("twoElementList_addToFront_testIteratorNext", testIteratorNext(newUnorderedList(A,B,C), Results.NoException));
		printTest("twoElementList_addToFront_testToString", testToString(newUnorderedList(A,B,C), Results.NoException));
		
		if(listType == ListType.Array || listType == ListType.Single || listType == ListType.Double) {
			printTestHeader(14,"Indexed");
			printTest("twoElementList_addToFront_testAdd_D", testAdd(newIndexedList(A,B,C), D, Results.NoException));
			printTest("twoElementList_addToFront_testAdd_0_D", testAdd(newIndexedList(A,B,C), 0, D, Results.NoException));
			printTest("twoElementList_addToFront_testAdd_1_D", testAdd(newIndexedList(A,B,C), 1, D, Results.NoException));
			printTest("twoElementList_addToFront_testAdd_2_D", testAdd(newIndexedList(A,B,C), 2, D, Results.NoException));
			printTest("twoElementList_addToFront_testAdd_3_D", testAdd(newIndexedList(A,B,C), 3, D, Results.IndexOutOfBounds));
			printTest("twoElementList_addToFront_testAdd_-1_D", testAdd(newIndexedList(A,B,C), -1, D, Results.IndexOutOfBounds));
			printTest("twoElementList_addToFront_testAdd_4_D", testAdd(newIndexedList(A,B,C), 4, D, Results.IndexOutOfBounds));
			printTest("twoElementList_addToFront_testSet_0_D", testSet(newIndexedList(A,B,C), 0, D, Results.NoException));
			printTest("twoElementList_addToFront_testSet_1_D", testSet(newIndexedList(A,B,C), 1, D, Results.NoException));
			printTest("twoElementList_addToFront_testSet_2_D", testSet(newIndexedList(A,B,C), 2, D, Results.NoException));
			printTest("twoElementList_addToFront_testSet_-1_D", testSet(newIndexedList(A,B,C), -1, D, Results.IndexOutOfBounds));
			printTest("twoElementList_addToFront_testSet_3_D", testSet(newIndexedList(A,B,C), 3, D, Results.IndexOutOfBounds));
			printTest("twoElementList_addToFront_testGet_0", testGet(newIndexedList(A,B,C), 0, Results.NoException));
			printTest("twoElementList_addToFront_testGet_1", testGet(newIndexedList(A,B,C), 1, Results.NoException));
			printTest("twoElementList_addToFront_testGet_2", testGet(newIndexedList(A,B,C), 2, Results.NoException));
			printTest("twoElementList_addToFront_testGet_-1", testGet(newIndexedList(A,B,C), -1, Results.IndexOutOfBounds));
			printTest("twoElementList_addToFront_testGet_3", testGet(newIndexedList(A,B,C), 3, Results.IndexOutOfBounds));
			printTest("twoElementList_addToFront_testIndexOf_A", testIndexOf(newIndexedList(A,B,C), A, 0));
			printTest("twoElementList_addToFront_testIndexOf_B", testIndexOf(newIndexedList(A,B,C), B, 1));
			printTest("twoElementList_addToFront_testIndexOf_C", testIndexOf(newIndexedList(A,B,C), C, 2));
			printTest("twoElementList_addToFront_testIndexOf_D", testIndexOf(newIndexedList(A,B,C), D, -1));
			printTest("twoElementList_addToFront_testIndexOf_E", testIndexOf(newIndexedList(A,B,C), E, -1));
			printTest("twoElementList_addToFront_testRemoveAtIndex_0", testRemoveAtIndex(newIndexedList(A,B,C), 0, Results.NoException));
			printTest("twoElementList_addToFront_testRemoveAtIndex_1", testRemoveAtIndex(newIndexedList(A,B,C), 1, Results.NoException));
			printTest("twoElementList_addToFront_testRemoveAtIndex_2", testRemoveAtIndex(newIndexedList(A,B,C), 2, Results.NoException));
			printTest("twoElementList_addToFront_testRemoveAtIndex_-1", testRemoveAtIndex(newIndexedList(A,B,C), -1, Results.IndexOutOfBounds));
			printTest("twoElementList_addToFront_testRemoveAtIndex_3", testRemoveAtIndex(newIndexedList(A,B,C), 3, Results.IndexOutOfBounds));
		}
	}
	
	// Scenario 15 - [A, B] -> addToRear -> [A, B, C]
	private void twoElementList_addToRearTests()
	{
		printTestHeader(15,"Unordered");
		printTest("twoElementList_addToRear_testAddToFront_A", testAddToFront(newUnorderedList(A,B,C), A, Results.NoException));
		printTest("twoElementList_addToRear_testAddToRear_E", testAddToRear(newUnorderedList(A,B,C), E, Results.NoException));
		printTest("twoElementList_addToRear_testAddAfter_D_C", testAddAfter(newUnorderedList(A,B,C), D, C, Results.NoException));
		printTest("twoElementList_addToRear_testAddAfter_E_D", testAddAfter(newUnorderedList(A,B,C), E, D, Results.ElementNotFound));
		printTest("twoElementList_addToRear_testRemoveFirst", testRemoveFirst(newUnorderedList(A,B,C), Results.NoException));
		printTest("twoElementList_addToRear_testRemoveLast", testRemoveLast(newUnorderedList(A,B,C), Results.NoException));
		printTest("twoElementList_addToRear_testRemove_A", testRemove(newUnorderedList(A,B,C), A, Results.NoException));
		printTest("twoElementList_addToRear_testRemove_D", testRemove(newUnorderedList(A,B,C), D, Results.ElementNotFound));
		printTest("twoElementList_addToRear_testFirst", testFirst(newUnorderedList(A,B,C), Results.NoException));
		printTest("twoElementList_addToRear_testLast", testLast(newUnorderedList(A,B,C), Results.NoException));
		printTest("twoElementList_addToRear_testContains_A", testContains(newUnorderedList(A,B,C), A, Results.True));
		printTest("twoElementList_addToRear_testContains_E", testContains(newUnorderedList(A,B,C), E, Results.False));
		printTest("twoElementList_addToRear_testIsEmpty", testIsEmpty(newUnorderedList(A,B,C), Results.False));
		printTest("twoElementList_addToRear_testSize", testSize(newUnorderedList(A,B,C), 3));
		printTest("twoElementList_addToRear_testIterator", testIterator(newUnorderedList(A,B,C), Results.NoException));
		printTest("twoElementList_addToRear_testIteratorHasNext", testIteratorHasNext(newUnorderedList(A,B,C), Results.True));
		printTest("twoElementList_addToRear_testIteratorNext", testIteratorNext(newUnorderedList(A,B,C), Results.NoException));
		printTest("twoElementList_addToRear_testToString", testToString(newUnorderedList(A,B,C), Results.NoException));
		
		if(listType == ListType.Array || listType == ListType.Single || listType == ListType.Double) {
			printTestHeader(15,"Indexed");
			printTest("twoElementList_addToRear_testAdd_D", testAdd(newIndexedList(A,B,C), D, Results.NoException));
			printTest("twoElementList_addToRear_testAdd_0_D", testAdd(newIndexedList(A,B,C), 0, D, Results.NoException));
			printTest("twoElementList_addToRear_testAdd_1_D", testAdd(newIndexedList(A,B,C), 1, D, Results.NoException));
			printTest("twoElementList_addToRear_testAdd_2_D", testAdd(newIndexedList(A,B,C), 2, D, Results.NoException));
			printTest("twoElementList_addToRear_testAdd_3_D", testAdd(newIndexedList(A,B,C), 3, D, Results.IndexOutOfBounds));
			printTest("twoElementList_addToRear_testAdd_-1_D", testAdd(newIndexedList(A,B,C), -1, D, Results.IndexOutOfBounds));
			printTest("twoElementList_addToRear_testAdd_4_D", testAdd(newIndexedList(A,B,C), 4, D, Results.IndexOutOfBounds));
			printTest("twoElementList_addToRear_testSet_0_D", testSet(newIndexedList(A,B,C), 0, D, Results.NoException));
			printTest("twoElementList_addToRear_testSet_1_D", testSet(newIndexedList(A,B,C), 1, D, Results.NoException));
			printTest("twoElementList_addToRear_testSet_2_D", testSet(newIndexedList(A,B,C), 2, D, Results.NoException));
			printTest("twoElementList_addToRear_testSet_-1_D", testSet(newIndexedList(A,B,C), -1, D, Results.IndexOutOfBounds));
			printTest("twoElementList_addToRear_testSet_3_D", testSet(newIndexedList(A,B,C), 3, D, Results.IndexOutOfBounds));
			printTest("twoElementList_addToRear_testGet_0", testGet(newIndexedList(A,B,C), 0, Results.NoException));
			printTest("twoElementList_addToRear_testGet_1", testGet(newIndexedList(A,B,C), 1, Results.NoException));
			printTest("twoElementList_addToRear_testGet_2", testGet(newIndexedList(A,B,C), 2, Results.NoException));
			printTest("twoElementList_addToRear_testGet_-1", testGet(newIndexedList(A,B,C), -1, Results.IndexOutOfBounds));
			printTest("twoElementList_addToRear_testGet_3", testGet(newIndexedList(A,B,C), 3, Results.IndexOutOfBounds));
			printTest("twoElementList_addToRear_testIndexOf_A", testIndexOf(newIndexedList(A,B,C), A, 0));
			printTest("twoElementList_addToRear_testIndexOf_B", testIndexOf(newIndexedList(A,B,C), B, 1));
			printTest("twoElementList_addToRear_testIndexOf_C", testIndexOf(newIndexedList(A,B,C), C, 2));
			printTest("twoElementList_addToRear_testIndexOf_D", testIndexOf(newIndexedList(A,B,C), D, -1));
			printTest("twoElementList_addToRear_testIndexOf_E", testIndexOf(newIndexedList(A,B,C), E, -1));
			printTest("twoElementList_addToRear_testRemoveAtIndex_0", testRemoveAtIndex(newIndexedList(A,B,C), 0, Results.NoException));
			printTest("twoElementList_addToRear_testRemoveAtIndex_1", testRemoveAtIndex(newIndexedList(A,B,C), 1, Results.NoException));
			printTest("twoElementList_addToRear_testRemoveAtIndex_2", testRemoveAtIndex(newIndexedList(A,B,C), 2, Results.NoException));
			printTest("twoElementList_addToRear_testRemoveAtIndex_-1", testRemoveAtIndex(newIndexedList(A,B,C), -1, Results.IndexOutOfBounds));
			printTest("twoElementList_addToRear_testRemoveAtIndex_3", testRemoveAtIndex(newIndexedList(A,B,C), 3, Results.IndexOutOfBounds));
		}
	}
	
	// Scenario 16 - [A, B] -> addAfter(C, A) -> [A, C, B]
	private void twoElementList_addAfterFirstElementTests()
	{
		printTestHeader(16,"Unordered");
		printTest("twoElementList_addAfterFirstElement_testAddToFront_E", testAddToFront(newUnorderedList(A,C,B), E, Results.NoException));
		printTest("twoElementList_addAfterFirstElement_testAddToRear_E", testAddToRear(newUnorderedList(A,C,B), E, Results.NoException));
		printTest("twoElementList_addAfterFirstElement_testAddAfter_D_A", testAddAfter(newUnorderedList(A,C,B), D, A, Results.NoException));
		printTest("twoElementList_addAfterFirstElement_testAddAfter_D_D", testAddAfter(newUnorderedList(A,C,B), D, D, Results.ElementNotFound));
		printTest("twoElementList_addAfterFirstElement_testRemoveFirst", testRemoveFirst(newUnorderedList(A,C,B), Results.NoException));
		printTest("twoElementList_addAfterFirstElement_testRemoveLast", testRemoveLast(newUnorderedList(A,C,B), Results.NoException));
		printTest("twoElementList_addAfterFirstElement_testRemove_C", testRemove(newUnorderedList(A,C,B), C, Results.NoException));
		printTest("twoElementList_addAfterFirstElement_testRemove_D", testRemove(newUnorderedList(A,C,B), D, Results.ElementNotFound));
		printTest("twoElementList_addAfterFirstElement_testFirst", testFirst(newUnorderedList(A,C,B), Results.NoException));
		printTest("twoElementList_addAfterFirstElement_testLast", testLast(newUnorderedList(A,C,B), Results.NoException));
		printTest("twoElementList_addAfterFirstElement_testContains_B", testContains(newUnorderedList(A,C,B), B, Results.True));
		printTest("twoElementList_addAfterFirstElement_testContains_E", testContains(newUnorderedList(A,C,B), E, Results.False));
		printTest("twoElementList_addAfterFirstElement_testIsEmpty", testIsEmpty(newUnorderedList(A,C,B), Results.False));
		printTest("twoElementList_addAfterFirstElement_testSize", testSize(newUnorderedList(A,C,B), 3));
		printTest("twoElementList_addAfterFirstElement_testIterator", testIterator(newUnorderedList(A,C,B), Results.NoException));
		printTest("twoElementList_addAfterFirstElement_testIteratorHasNext", testIteratorHasNext(newUnorderedList(A,C,B), Results.True));
		printTest("twoElementList_addAfterFirstElement_testIteratorNext", testIteratorNext(newUnorderedList(A,C,B), Results.NoException));
		printTest("twoElementList_addAfterFirstElement_testToString", testToString(newUnorderedList(A,C,B), Results.NoException));
		
		if(listType == ListType.Array || listType == ListType.Single || listType == ListType.Double) {
			printTestHeader(16,"Indexed");
			printTest("twoElementList_addAfterFirstElement_testAdd_E", testAdd(newIndexedList(A,C,B), E, Results.NoException));
			printTest("twoElementList_addAfterFirstElement_testAdd_0_E", testAdd(newIndexedList(A,C,B), 0, E, Results.NoException));
			printTest("twoElementList_addAfterFirstElement_testAdd_1_E", testAdd(newIndexedList(A,C,B), 1, E, Results.NoException));
			printTest("twoElementList_addAfterFirstElement_testAdd_2_E", testAdd(newIndexedList(A,C,B), 2, E, Results.NoException));
			printTest("twoElementList_addAfterFirstElement_testAdd_3_E", testAdd(newIndexedList(A,C,B), 3, E, Results.IndexOutOfBounds));
			printTest("twoElementList_addAfterFirstElement_testAdd_-1_E", testAdd(newIndexedList(A,C,B), -1, E, Results.IndexOutOfBounds));
			printTest("twoElementList_addAfterFirstElement_testAdd_-777_E", testAdd(newIndexedList(A,C,B), -777, E, Results.IndexOutOfBounds));
			printTest("twoElementList_addAfterFirstElement_testAdd_4_E", testAdd(newIndexedList(A,C,B), 4, E, Results.IndexOutOfBounds));
			printTest("twoElementList_addAfterFirstElement_testAdd_999_E", testAdd(newIndexedList(A,C,B), 999, E, Results.IndexOutOfBounds));
			printTest("twoElementList_addAfterFirstElement_testSet_0_E", testSet(newIndexedList(A,C,B), 0, E, Results.NoException));
			printTest("twoElementList_addAfterFirstElement_testSet_1_E", testSet(newIndexedList(A,C,B), 1, E, Results.NoException));
			printTest("twoElementList_addAfterFirstElement_testSet_2_E", testSet(newIndexedList(A,C,B), 2, E, Results.NoException));
			printTest("twoElementList_addAfterFirstElement_testSet_-1_E", testSet(newIndexedList(A,C,B), -1, E, Results.IndexOutOfBounds));
			printTest("twoElementList_addAfterFirstElement_testSet_-777_E", testSet(newIndexedList(A,C,B), -777, E, Results.IndexOutOfBounds));
			printTest("twoElementList_addAfterFirstElement_testSet_3_E", testSet(newIndexedList(A,C,B), 3, E, Results.IndexOutOfBounds));
			printTest("twoElementList_addAfterFirstElement_testSet_888_E", testSet(newIndexedList(A,C,B), 888, E, Results.IndexOutOfBounds));
			printTest("twoElementList_addAfterFirstElement_testGet_0", testGet(newIndexedList(A,C,B), 0, Results.NoException));
			printTest("twoElementList_addAfterFirstElement_testGet_1", testGet(newIndexedList(A,C,B), 1, Results.NoException));
			printTest("twoElementList_addAfterFirstElement_testGet_2", testGet(newIndexedList(A,C,B), 2, Results.NoException));
			printTest("twoElementList_addAfterFirstElement_testGet_-1", testGet(newIndexedList(A,C,B), -1, Results.IndexOutOfBounds));
			printTest("twoElementList_addAfterFirstElement_testGet_3", testGet(newIndexedList(A,C,B), 3, Results.IndexOutOfBounds));
			printTest("twoElementList_addAfterFirstElement_testGet_666", testGet(newIndexedList(A,C,B), 666, Results.IndexOutOfBounds));
			printTest("twoElementList_addAfterFirstElement_testIndexOf_A", testIndexOf(newIndexedList(A,C,B), A, 0));
			printTest("twoElementList_addAfterFirstElement_testIndexOf_B", testIndexOf(newIndexedList(A,C,B), B, 2));
			printTest("twoElementList_addAfterFirstElement_testIndexOf_C", testIndexOf(newIndexedList(A,C,B), C, 1));
			printTest("twoElementList_addAfterFirstElement_testIndexOf_D", testIndexOf(newIndexedList(A,C,B), D, -1));
			printTest("twoElementList_addAfterFirstElement_testIndexOf_E", testIndexOf(newIndexedList(A,C,B), E, -1));
			printTest("twoElementList_addAfterFirstElement_testRemoveAtIndex_0", testRemoveAtIndex(newIndexedList(A,C,B), 0, Results.NoException));
			printTest("twoElementList_addAfterFirstElement_testRemoveAtIndex_1", testRemoveAtIndex(newIndexedList(A,C,B), 1, Results.NoException));
			printTest("twoElementList_addAfterFirstElement_testRemoveAtIndex_2", testRemoveAtIndex(newIndexedList(A,C,B), 2, Results.NoException));
			printTest("twoElementList_addAfterFirstElement_testRemoveAtIndex_-1", testRemoveAtIndex(newIndexedList(A,C,B), -1, Results.IndexOutOfBounds));
			printTest("twoElementList_addAfterFirstElement_testRemoveAtIndex_-555", testRemoveAtIndex(newIndexedList(A,C,B), -555, Results.IndexOutOfBounds));
			printTest("twoElementList_addAfterFirstElement_testRemoveAtIndex_3", testRemoveAtIndex(newIndexedList(A,C,B), 3, Results.IndexOutOfBounds));
			printTest("twoElementList_addAfterFirstElement_testRemoveAtIndex_444", testRemoveAtIndex(newIndexedList(A,C,B), 444, Results.IndexOutOfBounds));
		}
	}
	
	// Scenario 17 - [A, B] -> addAfter(C, B) -> [A, B, C]
	private void twoElementList_addAfterSecondElementTests()
	{
		printTestHeader(17,"Unordered");
		printTest("twoElementList_addAfterSecondElement_testAddToFront_D", testAddToFront(newUnorderedList(A,B,C), D, Results.NoException));
		printTest("twoElementList_addAfterSecondElement_testAddToRear_D", testAddToRear(newUnorderedList(A,B,C), D, Results.NoException));
		printTest("twoElementList_addAfterSecondElement_testAddAfter_D_B", testAddAfter(newUnorderedList(A,B,C), D, B, Results.NoException));
		printTest("twoElementList_addAfterSecondElement_testAddAfter_A_D", testAddAfter(newUnorderedList(A,B,C), A, D, Results.ElementNotFound));
		printTest("twoElementList_addAfterSecondElement_testRemoveFirst", testRemoveFirst(newUnorderedList(A,B,C), Results.NoException));
		printTest("twoElementList_addAfterSecondElement_testRemoveLast", testRemoveLast(newUnorderedList(A,B,C), Results.NoException));
		printTest("twoElementList_addAfterSecondElement_testRemove_C", testRemove(newUnorderedList(A,B,C), C, Results.NoException));
		printTest("twoElementList_addAfterSecondElement_testRemove_D", testRemove(newUnorderedList(A,B,C), D, Results.ElementNotFound));
		printTest("twoElementList_addAfterSecondElement_testFirst", testFirst(newUnorderedList(A,B,C), Results.NoException));
		printTest("twoElementList_addAfterSecondElement_testLast", testLast(newUnorderedList(A,B,C), Results.NoException));
		printTest("twoElementList_addAfterSecondElement_testContains_A", testContains(newUnorderedList(A,B,C), A, Results.True));
		printTest("twoElementList_addAfterSecondElement_testContains_D", testContains(newUnorderedList(A,B,C), D, Results.False));
		printTest("twoElementList_addAfterSecondElement_testIsEmpty", testIsEmpty(newUnorderedList(A,B,C), Results.False));
		printTest("twoElementList_addAfterSecondElement_testSize", testSize(newUnorderedList(A,B,C), 3));
		printTest("twoElementList_addAfterSecondElement_testIterator", testIterator(newUnorderedList(A,B,C), Results.NoException));
		printTest("twoElementList_addAfterSecondElement_testIteratorHasNext", testIteratorHasNext(newUnorderedList(A,B,C), Results.True));
		printTest("twoElementList_addAfterSecondElement_testIteratorNext", testIteratorNext(newUnorderedList(A,B,C), Results.NoException));
		printTest("twoElementList_addAfterSecondElement_testToString", testToString(newUnorderedList(A,B,C), Results.NoException));
		
		if(listType == ListType.Array || listType == ListType.Single || listType == ListType.Double) {
			printTestHeader(17,"Indexed");
			printTest("twoElementList_addAfterSecondElement_testAdd_D", testAdd(newIndexedList(A,B,C), D, Results.NoException));
			printTest("twoElementList_addAfterSecondElement_testAdd_0_D", testAdd(newIndexedList(A,B,C), 0, D, Results.NoException));
			printTest("twoElementList_addAfterSecondElement_testAdd_1_D", testAdd(newIndexedList(A,B,C), 1, D, Results.NoException));
			printTest("twoElementList_addAfterSecondElement_testAdd_2_D", testAdd(newIndexedList(A,B,C), 2, D, Results.NoException));
			printTest("twoElementList_addAfterSecondElement_testAdd_3_D", testAdd(newIndexedList(A,B,C), 3, D, Results.IndexOutOfBounds));
			printTest("twoElementList_addAfterSecondElement_testAdd_-1_D", testAdd(newIndexedList(A,B,C), -1, D, Results.IndexOutOfBounds));
			printTest("twoElementList_addAfterSecondElement_testAdd_4_D", testAdd(newIndexedList(A,B,C), 4, D, Results.IndexOutOfBounds));
			printTest("twoElementList_addAfterSecondElement_testSet_0_D", testSet(newIndexedList(A,B,C), 0, D, Results.NoException));
			printTest("twoElementList_addAfterSecondElement_testSet_1_D", testSet(newIndexedList(A,B,C), 1, D, Results.NoException));
			printTest("twoElementList_addAfterSecondElement_testSet_2_D", testSet(newIndexedList(A,B,C), 2, D, Results.NoException));
			printTest("twoElementList_addAfterSecondElement_testSet_-1_D", testSet(newIndexedList(A,B,C), -1, D, Results.IndexOutOfBounds));
			printTest("twoElementList_addAfterSecondElement_testSet_3_D", testSet(newIndexedList(A,B,C), 3, D, Results.IndexOutOfBounds));
			printTest("twoElementList_addAfterSecondElement_testGet_0", testGet(newIndexedList(A,B,C), 0, Results.NoException));
			printTest("twoElementList_addAfterSecondElement_testGet_1", testGet(newIndexedList(A,B,C), 1, Results.NoException));
			printTest("twoElementList_addAfterSecondElement_testGet_2", testGet(newIndexedList(A,B,C), 2, Results.NoException));
			printTest("twoElementList_addAfterSecondElement_testGet_-1", testGet(newIndexedList(A,B,C), -1, Results.IndexOutOfBounds));
			printTest("twoElementList_addAfterSecondElement_testGet_3", testGet(newIndexedList(A,B,C), 3, Results.IndexOutOfBounds));
			printTest("twoElementList_addAfterSecondElement_testIndexOf_A", testIndexOf(newIndexedList(A,B,C), A, 0));
			printTest("twoElementList_addAfterSecondElement_testIndexOf_B", testIndexOf(newIndexedList(A,B,C), B, 1));
			printTest("twoElementList_addAfterSecondElement_testIndexOf_C", testIndexOf(newIndexedList(A,B,C), C, 2));
			printTest("twoElementList_addAfterSecondElement_testIndexOf_D", testIndexOf(newIndexedList(A,B,C), D, -1));
			printTest("twoElementList_addAfterSecondElement_testIndexOf_E", testIndexOf(newIndexedList(A,B,C), E, -1));
			printTest("twoElementList_addAfterSecondElement_testRemoveAtIndex_0", testRemoveAtIndex(newIndexedList(A,B,C), 0, Results.NoException));
			printTest("twoElementList_addAfterSecondElement_testRemoveAtIndex_1", testRemoveAtIndex(newIndexedList(A,B,C), 1, Results.NoException));
			printTest("twoElementList_addAfterSecondElement_testRemoveAtIndex_2", testRemoveAtIndex(newIndexedList(A,B,C), 2, Results.NoException));
			printTest("twoElementList_addAfterSecondElement_testRemoveAtIndex_-1", testRemoveAtIndex(newIndexedList(A,B,C), -1, Results.IndexOutOfBounds));
			printTest("twoElementList_addAfterSecondElement_testRemoveAtIndex_3", testRemoveAtIndex(newIndexedList(A,B,C), 3, Results.IndexOutOfBounds));
		}
	}
	
	// Scenario 18 - [A, B, C] -> removeFirst -> [B, C]
	private void threeElementList_removeFirstTests()
	{
		printTestHeader(18,"Unordered");
		printTest("threeElementList_removeFirst_testAddToFront_A", testAddToFront(newUnorderedList(B,C), A, Results.NoException));
		printTest("threeElementList_removeFirst_testAddToRear_D", testAddToRear(newUnorderedList(B,C), D, Results.NoException));
		printTest("threeElementList_removeFirst_testAddAfter_D_C", testAddAfter(newUnorderedList(B,C), D, C, Results.NoException));
		printTest("threeElementList_removeFirst_testAddAfter_E_D", testAddAfter(newUnorderedList(B,C), E, D, Results.ElementNotFound));
		printTest("threeElementList_removeFirst_testRemoveFirst", testRemoveFirst(newUnorderedList(B,C), Results.NoException));
		printTest("threeElementList_removeFirst_testRemoveLast", testRemoveLast(newUnorderedList(B,C), Results.NoException));
		printTest("threeElementList_removeFirst_testRemove_C", testRemove(newUnorderedList(B,C), C, Results.NoException));
		printTest("threeElementList_removeFirst_testRemove_A", testRemove(newUnorderedList(B,C), A, Results.ElementNotFound));
		printTest("threeElementList_removeFirst_testFirst", testFirst(newUnorderedList(B,C), Results.NoException));
		printTest("threeElementList_removeFirst_testLast", testLast(newUnorderedList(B,C), Results.NoException));
		printTest("threeElementList_removeFirst_testContains_B", testContains(newUnorderedList(B,C), B, Results.True));
		printTest("threeElementList_removeFirst_testContains_A", testContains(newUnorderedList(B,C), A, Results.False));
		printTest("threeElementList_removeFirst_testIsEmpty", testIsEmpty(newUnorderedList(B,C), Results.False));
		printTest("threeElementList_removeFirst_testSize", testSize(newUnorderedList(B,C), 2));
		printTest("threeElementList_removeFirst_testIterator", testIterator(newUnorderedList(B,C), Results.NoException));
		printTest("threeElementList_removeFirst_testIteratorHasNext", testIteratorHasNext(newUnorderedList(B,C), Results.True));
		printTest("threeElementList_removeFirst_testIteratorNext", testIteratorNext(newUnorderedList(B,C), Results.NoException));
		printTest("threeElementList_removeFirst_testToString", testToString(newUnorderedList(B,C), Results.NoException));
		
		if(listType == ListType.Array || listType == ListType.Single || listType == ListType.Double) {
			printTestHeader(18,"Indexed");
			printTest("threeElementList_removeFirst_testAdd_D", testAdd(newIndexedList(B,C), D, Results.NoException));
			printTest("threeElementList_removeFirst_testAdd_0_D", testAdd(newIndexedList(B,C), 0, D, Results.NoException));
			printTest("threeElementList_removeFirst_testAdd_1_D", testAdd(newIndexedList(B,C), 1, D, Results.NoException));
			printTest("threeElementList_removeFirst_testAdd_2_D", testAdd(newIndexedList(B,C), 2, D, Results.IndexOutOfBounds));
			printTest("threeElementList_removeFirst_testAdd_-1_D", testAdd(newIndexedList(B,C), -1, D, Results.IndexOutOfBounds));
			printTest("threeElementList_removeFirst_testAdd_3_D", testAdd(newIndexedList(B,C), 3, D, Results.IndexOutOfBounds));
			printTest("threeElementList_removeFirst_testSet_0_D", testSet(newIndexedList(B,C), 0, D, Results.NoException));
			printTest("threeElementList_removeFirst_testSet_1_D", testSet(newIndexedList(B,C), 1, D, Results.NoException));
			printTest("threeElementList_removeFirst_testSet_-1_D", testSet(newIndexedList(B,C), -1, D, Results.IndexOutOfBounds));
			printTest("threeElementList_removeFirst_testSet_2_D", testSet(newIndexedList(B,C), 2, D, Results.IndexOutOfBounds));
			printTest("threeElementList_removeFirst_testGet_0", testGet(newIndexedList(B,C), 0, Results.NoException));
			printTest("threeElementList_removeFirst_testGet_1", testGet(newIndexedList(B,C), 1, Results.NoException));
			printTest("threeElementList_removeFirst_testGet_-1", testGet(newIndexedList(B,C), -1, Results.IndexOutOfBounds));
			printTest("threeElementList_removeFirst_testGet_2", testGet(newIndexedList(B,C), 2, Results.IndexOutOfBounds));
			printTest("threeElementList_removeFirst_testIndexOf_D", testIndexOf(newIndexedList(B,C), D, -1));
			printTest("threeElementList_removeFirst_testIndexOf_B", testIndexOf(newIndexedList(B,C), B, 0));
			printTest("threeElementList_removeFirst_testIndexOf_C", testIndexOf(newIndexedList(B,C), C, 1));
			printTest("threeElementList_removeFirst_testRemoveAtIndex_0", testRemoveAtIndex(newIndexedList(B,C), 0, Results.NoException));
			printTest("threeElementList_removeFirst_testRemoveAtIndex_1", testRemoveAtIndex(newIndexedList(B,C), 1, Results.NoException));
			printTest("threeElementList_removeFirst_testRemoveAtIndex_-1", testRemoveAtIndex(newIndexedList(B,C), -1, Results.IndexOutOfBounds));
			printTest("threeElementList_removeFirst_testRemoveAtIndex_2", testRemoveAtIndex(newIndexedList(B,C), 2, Results.IndexOutOfBounds));
		}
	}
	
	// Scenario 19 - [A, B, C] -> removeLast -> [A, B]
	private void threeElementList_removeLastTests()
	{
		printTestHeader(19,"Unordered");
		printTest("threeElementList_removeLast_testAddToFront_E", testAddToFront(newUnorderedList(A,B), E, Results.NoException));
		printTest("threeElementList_removeLast_testAddToRear_C", testAddToRear(newUnorderedList(A,B), C, Results.NoException));
		printTest("threeElementList_removeLast_testAddAfter_C_B", testAddAfter(newUnorderedList(A,B), C, B, Results.NoException));
		printTest("threeElementList_removeLast_testAddAfter_D_C", testAddAfter(newUnorderedList(A,B), D, C, Results.ElementNotFound));
		printTest("threeElementList_removeLast_testRemoveFirst", testRemoveFirst(newUnorderedList(A,B), Results.NoException));
		printTest("threeElementList_removeLast_testRemoveLast", testRemoveLast(newUnorderedList(A,B), Results.NoException));
		printTest("threeElementList_removeLast_testRemove_A", testRemove(newUnorderedList(A,B), A, Results.NoException));
		printTest("threeElementList_removeLast_testRemove_C", testRemove(newUnorderedList(A,B), C, Results.ElementNotFound));
		printTest("threeElementList_removeLast_testFirst", testFirst(newUnorderedList(A,B), Results.NoException));
		printTest("threeElementList_removeLast_testLast", testLast(newUnorderedList(A,B), Results.NoException));
		printTest("threeElementList_removeLast_testContains_A", testContains(newUnorderedList(A,B), A, Results.True));
		printTest("threeElementList_removeLast_testContains_E", testContains(newUnorderedList(A,B), E, Results.False));
		printTest("threeElementList_removeLast_testIsEmpty", testIsEmpty(newUnorderedList(A,B), Results.False));
		printTest("threeElementList_removeLast_testSize", testSize(newUnorderedList(A,B), 2));
		printTest("threeElementList_removeLast_testIterator", testIterator(newUnorderedList(A,B), Results.NoException));
		printTest("threeElementList_removeLast_testIteratorHasNext", testIteratorHasNext(newUnorderedList(A,B), Results.True));
		printTest("threeElementList_removeLast_testIteratorNext", testIteratorNext(newUnorderedList(A,B), Results.NoException));
		printTest("threeElementList_removeLast_testToString", testToString(newUnorderedList(A,B), Results.NoException));
		
		if(listType == ListType.Array || listType == ListType.Single || listType == ListType.Double) {
			printTestHeader(19,"Indexed");
			printTest("threeElementList_removeLast_testAdd_C", testAdd(newIndexedList(A,B), C, Results.NoException));
			printTest("threeElementList_removeLast_testAdd_0_C", testAdd(newIndexedList(A,B), 0, C, Results.NoException));
			printTest("threeElementList_removeLast_testAdd_1_C", testAdd(newIndexedList(A,B), 1, C, Results.NoException));
			printTest("threeElementList_removeLast_testAdd_2_C", testAdd(newIndexedList(A,B), 2, C, Results.IndexOutOfBounds));
			printTest("threeElementList_removeLast_testAdd_-1_C", testAdd(newIndexedList(A,B), -1, C, Results.IndexOutOfBounds));
			printTest("threeElementList_removeLast_testAdd_3_C", testAdd(newIndexedList(A,B), 3, C, Results.IndexOutOfBounds));
			printTest("threeElementList_removeLast_testSet_0_C", testSet(newIndexedList(A,B), 0, C, Results.NoException));
			printTest("threeElementList_removeLast_testSet_1_C", testSet(newIndexedList(A,B), 1, C, Results.NoException));
			printTest("threeElementList_removeLast_testSet_-1_C", testSet(newIndexedList(A,B), -1, C, Results.IndexOutOfBounds));
			printTest("threeElementList_removeLast_testSet_2_C", testSet(newIndexedList(A,B), 2, C, Results.IndexOutOfBounds));
			printTest("threeElementList_removeLast_testGet_0", testGet(newIndexedList(A,B), 0, Results.NoException));
			printTest("threeElementList_removeLast_testGet_1", testGet(newIndexedList(A,B), 1, Results.NoException));
			printTest("threeElementList_removeLast_testGet_-1", testGet(newIndexedList(A,B), -1, Results.IndexOutOfBounds));
			printTest("threeElementList_removeLast_testGet_2", testGet(newIndexedList(A,B), 2, Results.IndexOutOfBounds));
			printTest("threeElementList_removeLast_testIndexOf_A", testIndexOf(newIndexedList(A,B), A, 0));
			printTest("threeElementList_removeLast_testIndexOf_B", testIndexOf(newIndexedList(A,B), B, 1));
			printTest("threeElementList_removeLast_testIndexOf_C", testIndexOf(newIndexedList(A,B), C, -1));
			printTest("threeElementList_removeLast_testRemoveAtIndex_0", testRemoveAtIndex(newIndexedList(A,B), 0, Results.NoException));
			printTest("threeElementList_removeLast_testRemoveAtIndex_1", testRemoveAtIndex(newIndexedList(A,B), 1, Results.NoException));
			printTest("threeElementList_removeLast_testRemoveAtIndex_-1", testRemoveAtIndex(newIndexedList(A,B), -1, Results.IndexOutOfBounds));
			printTest("threeElementList_removeLast_testRemoveAtIndex_2", testRemoveAtIndex(newIndexedList(A,B), 2, Results.IndexOutOfBounds));
		}
	}
	
	// Scenario 20 - [A, B, C] -> remove(A) -> [B, C]
	private void threeElementList_removeFirstElementTests()
	{
		printTestHeader(20,"Unordered");
		printTest("threeElementList_removeFirstElement_testAddToFront_A", testAddToFront(newUnorderedList(B,C), A, Results.NoException));
		printTest("threeElementList_removeFirstElement_testAddToRear_D", testAddToRear(newUnorderedList(B,C), D, Results.NoException));
		printTest("threeElementList_removeFirstElement_testAddAfter_D_C", testAddAfter(newUnorderedList(B,C), D, C, Results.NoException));
		printTest("threeElementList_removeFirstElement_testAddAfter_E_A", testAddAfter(newUnorderedList(B,C), E, A, Results.ElementNotFound));
		printTest("threeElementList_removeFirstElement_testRemoveFirst", testRemoveFirst(newUnorderedList(B,C), Results.NoException));
		printTest("threeElementList_removeFirstElement_testRemoveLast", testRemoveLast(newUnorderedList(B,C), Results.NoException));
		printTest("threeElementList_removeFirstElement_testRemove_C", testRemove(newUnorderedList(B,C), C, Results.NoException));
		printTest("threeElementList_removeFirstElement_testRemove_D", testRemove(newUnorderedList(B,C), D, Results.ElementNotFound));
		printTest("threeElementList_removeFirstElement_testFirst", testFirst(newUnorderedList(B,C), Results.NoException));
		printTest("threeElementList_removeFirstElement_testLast", testLast(newUnorderedList(B,C), Results.NoException));
		printTest("threeElementList_removeFirstElement_testContains_B", testContains(newUnorderedList(B,C), B, Results.True));
		printTest("threeElementList_removeFirstElement_testContains_A", testContains(newUnorderedList(B,C), A, Results.False));
		printTest("threeElementList_removeFirstElement_testIsEmpty", testIsEmpty(newUnorderedList(B,C), Results.False));
		printTest("threeElementList_removeFirstElement_testSize", testSize(newUnorderedList(B,C), 2));
		printTest("threeElementList_removeFirstElement_testIterator", testIterator(newUnorderedList(B,C), Results.NoException));
		printTest("threeElementList_removeFirstElement_testIteratorHasNext", testIteratorHasNext(newUnorderedList(B,C), Results.True));
		printTest("threeElementList_removeFirstElement_testIteratorNext", testIteratorNext(newUnorderedList(B,C), Results.NoException));
		printTest("threeElementList_removeFirstElement_testToString", testToString(newUnorderedList(B,C), Results.NoException));
		
		if(listType == ListType.Array || listType == ListType.Single || listType == ListType.Double) {
			printTestHeader(20,"Indexed");
			printTest("threeElementList_removeFirst_testAdd_D", testAdd(newIndexedList(B,C), D, Results.NoException));
			printTest("threeElementList_removeFirst_testAdd_0_D", testAdd(newIndexedList(B,C), 0, D, Results.NoException));
			printTest("threeElementList_removeFirst_testAdd_1_D", testAdd(newIndexedList(B,C), 1, D, Results.NoException));
			printTest("threeElementList_removeFirst_testAdd_2_D", testAdd(newIndexedList(B,C), 2, D, Results.IndexOutOfBounds));
			printTest("threeElementList_removeFirst_testAdd_-1_D", testAdd(newIndexedList(B,C), -1, D, Results.IndexOutOfBounds));
			printTest("threeElementList_removeFirst_testAdd_3_D", testAdd(newIndexedList(B,C), 3, D, Results.IndexOutOfBounds));
			printTest("threeElementList_removeFirst_testSet_0_D", testSet(newIndexedList(B,C), 0, D, Results.NoException));
			printTest("threeElementList_removeFirst_testSet_1_D", testSet(newIndexedList(B,C), 1, D, Results.NoException));
			printTest("threeElementList_removeFirst_testSet_-1_D", testSet(newIndexedList(B,C), -1, D, Results.IndexOutOfBounds));
			printTest("threeElementList_removeFirst_testSet_2_D", testSet(newIndexedList(B,C), 2, D, Results.IndexOutOfBounds));
			printTest("threeElementList_removeFirst_testGet_0", testGet(newIndexedList(B,C), 0, Results.NoException));
			printTest("threeElementList_removeFirst_testGet_1", testGet(newIndexedList(B,C), 1, Results.NoException));
			printTest("threeElementList_removeFirst_testGet_-1", testGet(newIndexedList(B,C), -1, Results.IndexOutOfBounds));
			printTest("threeElementList_removeFirst_testGet_2", testGet(newIndexedList(B,C), 2, Results.IndexOutOfBounds));
			printTest("threeElementList_removeFirst_testIndexOf_D", testIndexOf(newIndexedList(B,C), D, -1));
			printTest("threeElementList_removeFirst_testIndexOf_B", testIndexOf(newIndexedList(B,C), B, 0));
			printTest("threeElementList_removeFirst_testIndexOf_C", testIndexOf(newIndexedList(B,C), C, 1));
			printTest("threeElementList_removeFirst_testRemoveAtIndex_0", testRemoveAtIndex(newIndexedList(B,C), 0, Results.NoException));
			printTest("threeElementList_removeFirst_testRemoveAtIndex_1", testRemoveAtIndex(newIndexedList(B,C), 1, Results.NoException));
			printTest("threeElementList_removeFirst_testRemoveAtIndex_-1", testRemoveAtIndex(newIndexedList(B,C), -1, Results.IndexOutOfBounds));
			printTest("threeElementList_removeFirst_testRemoveAtIndex_2", testRemoveAtIndex(newIndexedList(B,C), 2, Results.IndexOutOfBounds));
		}
	}
	
	// Scenario 21 - [A, B, C] -> remove(B) -> [A, C]
	private void threeElementList_removeSecondElementTests()
	{
		printTestHeader(21,"Unordered");
		printTest("threeElementList_removeSecondElement_testAddToFront_E", testAddToFront(newUnorderedList(A,C), E, Results.NoException));
		printTest("threeElementList_removeSecondElement_testAddToRear_D", testAddToRear(newUnorderedList(A,C), D, Results.NoException));
		printTest("threeElementList_removeSecondElement_testAddAfter_D_C", testAddAfter(newUnorderedList(A,C), D, C, Results.NoException));
		printTest("threeElementList_removeSecondElement_testAddAfter_C_B", testAddAfter(newUnorderedList(A,C), C, B, Results.ElementNotFound));
		printTest("threeElementList_removeSecondElement_testRemoveFirst", testRemoveFirst(newUnorderedList(A,C), Results.NoException));
		printTest("threeElementList_removeSecondElement_testRemoveLast", testRemoveLast(newUnorderedList(A,C), Results.NoException));
		printTest("threeElementList_removeSecondElement_testRemove_A", testRemove(newUnorderedList(A,C), A, Results.NoException));
		printTest("threeElementList_removeSecondElement_testRemove_B", testRemove(newUnorderedList(A,C), B, Results.ElementNotFound));
		printTest("threeElementList_removeSecondElement_testFirst", testFirst(newUnorderedList(A,C), Results.NoException));
		printTest("threeElementList_removeSecondElement_testLast", testLast(newUnorderedList(A,C), Results.NoException));
		printTest("threeElementList_removeSecondElement_testContains_A", testContains(newUnorderedList(A,C), A, Results.True));
		printTest("threeElementList_removeSecondElement_testContains_E", testContains(newUnorderedList(A,C), E, Results.False));
		printTest("threeElementList_removeSecondElement_testIsEmpty", testIsEmpty(newUnorderedList(A,C), Results.False));
		printTest("threeElementList_removeSecondElement_testSize", testSize(newUnorderedList(A,C), 2));
		printTest("threeElementList_removeSecondElement_testIterator", testIterator(newUnorderedList(A,C), Results.NoException));
		printTest("threeElementList_removeSecondElement_testIteratorHasNext", testIteratorHasNext(newUnorderedList(A,C), Results.True));
		printTest("threeElementList_removeSecondElement_testIteratorNext", testIteratorNext(newUnorderedList(A,C), Results.NoException));
		printTest("threeElementList_removeSecondElement_testToString", testToString(newUnorderedList(A,C), Results.NoException));
		
		if(listType == ListType.Array || listType == ListType.Single || listType == ListType.Double) {
			printTestHeader(21,"Indexed");
			printTest("threeElementList_removeSecondElement_testAdd_E", testAdd(newIndexedList(A,C), E, Results.NoException));
			printTest("threeElementList_removeSecondElement_testAdd_0_E", testAdd(newIndexedList(A,C), 0, E, Results.NoException));
			printTest("threeElementList_removeSecondElement_testAdd_1_E", testAdd(newIndexedList(A,C), 1, E, Results.NoException));
			printTest("threeElementList_removeSecondElement_testAdd_2_E", testAdd(newIndexedList(A,C), 2, E, Results.IndexOutOfBounds));
			printTest("threeElementList_removeSecondElement_testAdd_-1_E", testAdd(newIndexedList(A,C), -1, E, Results.IndexOutOfBounds));
			printTest("threeElementList_removeSecondElement_testAdd_8_E", testAdd(newIndexedList(A,C), 8, E, Results.IndexOutOfBounds));
			printTest("threeElementList_removeSecondElement_testSet_0_E", testSet(newIndexedList(A,C), 0, E, Results.NoException));
			printTest("threeElementList_removeSecondElement_testSet_1_E", testSet(newIndexedList(A,C), 1, E, Results.NoException));
			printTest("threeElementList_removeSecondElement_testSet_-1_E", testSet(newIndexedList(A,C), -1, E, Results.IndexOutOfBounds));
			printTest("threeElementList_removeSecondElement_testSet_8_E", testSet(newIndexedList(A,C), 8, E, Results.IndexOutOfBounds));
			printTest("threeElementList_removeSecondElement_testGet_0", testGet(newIndexedList(A,C), 0, Results.NoException));
			printTest("threeElementList_removeSecondElement_testGet_1", testGet(newIndexedList(A,C), 1, Results.NoException));
			printTest("threeElementList_removeSecondElement_testGet_-1", testGet(newIndexedList(A,C), -1, Results.IndexOutOfBounds));
			printTest("threeElementList_removeSecondElement_testGet_8", testGet(newIndexedList(A,C), 8, Results.IndexOutOfBounds));
			printTest("threeElementList_removeSecondElement_testIndexOf_A", testIndexOf(newIndexedList(A,C), A, 0));
			printTest("threeElementList_removeSecondElement_testIndexOf_B", testIndexOf(newIndexedList(A,C), B, -1));
			printTest("threeElementList_removeSecondElement_testIndexOf_C", testIndexOf(newIndexedList(A,C), C, 1));
			printTest("threeElementList_removeSecondElement_testIndexOf_D", testIndexOf(newIndexedList(A,C), D, -1));
			printTest("threeElementList_removeSecondElement_testRemoveAtIndex_0", testRemoveAtIndex(newIndexedList(A,C), 0, Results.NoException));
			printTest("threeElementList_removeSecondElement_testRemoveAtIndex_1", testRemoveAtIndex(newIndexedList(A,C), 1, Results.NoException));
			printTest("threeElementList_removeSecondElement_testRemoveAtIndex_-1", testRemoveAtIndex(newIndexedList(A,C), -1, Results.IndexOutOfBounds));
			printTest("threeElementList_removeSecondElement_testRemoveAtIndex_8", testRemoveAtIndex(newIndexedList(A,C), 8, Results.IndexOutOfBounds));
		}
	}
	
	// Scenario 22 - [A, B, C] -> remove(C) -> [A, B]
	private void threeElementList_removeThirdElementTests()
	{
		printTestHeader(22,"Unordered");
		printTest("threeElementList_removeThirdElement_testAddToFront_E", testAddToFront(newUnorderedList(A,B), E, Results.NoException));
		printTest("threeElementList_removeThirdElement_testAddToRear_C", testAddToRear(newUnorderedList(A,B), C, Results.NoException));
		printTest("threeElementList_removeThirdElement_testAddAfter_C_B", testAddAfter(newUnorderedList(A,B), C, B, Results.NoException));
		printTest("threeElementList_removeThirdElement_testAddAfter_D_C", testAddAfter(newUnorderedList(A,B), D, C, Results.ElementNotFound));
		printTest("threeElementList_removeThirdElement_testRemoveFirst", testRemoveFirst(newUnorderedList(A,B), Results.NoException));
		printTest("threeElementList_removeThirdElement_testRemoveLast", testRemoveLast(newUnorderedList(A,B), Results.NoException));
		printTest("threeElementList_removeThirdElement_testRemove_A", testRemove(newUnorderedList(A,B), A, Results.NoException));
		printTest("threeElementList_removeThirdElement_testRemove_C", testRemove(newUnorderedList(A,B), C, Results.ElementNotFound));
		printTest("threeElementList_removeThirdElement_testFirst", testFirst(newUnorderedList(A,B), Results.NoException));
		printTest("threeElementList_removeThirdElement_testLast", testLast(newUnorderedList(A,B), Results.NoException));
		printTest("threeElementList_removeThirdElement_testContains_A", testContains(newUnorderedList(A,B), A, Results.True));
		printTest("threeElementList_removeThirdElement_testContains_E", testContains(newUnorderedList(A,B), E, Results.False));
		printTest("threeElementList_removeThirdElement_testIsEmpty", testIsEmpty(newUnorderedList(A,B), Results.False));
		printTest("threeElementList_removeThirdElement_testSize", testSize(newUnorderedList(A,B), 2));
		printTest("threeElementList_removeThirdElement_testIterator", testIterator(newUnorderedList(A,B), Results.NoException));
		printTest("threeElementList_removeThirdElement_testIteratorHasNext", testIteratorHasNext(newUnorderedList(A,B), Results.True));
		printTest("threeElementList_removeThirdElement_testIteratorNext", testIteratorNext(newUnorderedList(A,B), Results.NoException));
		printTest("threeElementList_removeThirdElement_testToString", testToString(newUnorderedList(A,B), Results.NoException));
		
		if(listType == ListType.Array || listType == ListType.Single || listType == ListType.Double) {
			printTestHeader(22,"Indexed");
			printTest("threeElementList_removeThirdElement_testAdd_C", testAdd(newIndexedList(A,B), C, Results.NoException));
			printTest("threeElementList_removeThirdElement_testAdd_0_C", testAdd(newIndexedList(A,B), 0, C, Results.NoException));
			printTest("threeElementList_removeThirdElement_testAdd_1_C", testAdd(newIndexedList(A,B), 1, C, Results.NoException));
			printTest("threeElementList_removeThirdElement_testAdd_2_C", testAdd(newIndexedList(A,B), 2, C, Results.IndexOutOfBounds));
			printTest("threeElementList_removeThirdElement_testAdd_-1_C", testAdd(newIndexedList(A,B), -1, C, Results.IndexOutOfBounds));
			printTest("threeElementList_removeThirdElement_testAdd_3_C", testAdd(newIndexedList(A,B), 3, C, Results.IndexOutOfBounds));
			printTest("threeElementList_removeThirdElement_testSet_0_C", testSet(newIndexedList(A,B), 0, C, Results.NoException));
			printTest("threeElementList_removeThirdElement_testSet_1_C", testSet(newIndexedList(A,B), 1, C, Results.NoException));
			printTest("threeElementList_removeThirdElement_testSet_-1_C", testSet(newIndexedList(A,B), -1, C, Results.IndexOutOfBounds));
			printTest("threeElementList_removeThirdElement_testSet_2_C", testSet(newIndexedList(A,B), 2, C, Results.IndexOutOfBounds));
			printTest("threeElementList_removeThirdElement_testGet_0", testGet(newIndexedList(A,B), 0, Results.NoException));
			printTest("threeElementList_removeThirdElement_testGet_1", testGet(newIndexedList(A,B), 1, Results.NoException));
			printTest("threeElementList_removeThirdElement_testGet_-1", testGet(newIndexedList(A,B), -1, Results.IndexOutOfBounds));
			printTest("threeElementList_removeThirdElement_testGet_2", testGet(newIndexedList(A,B), 2, Results.IndexOutOfBounds));
			printTest("threeElementList_removeThirdElement_testIndexOf_A", testIndexOf(newIndexedList(A,B), A, 0));
			printTest("threeElementList_removeThirdElement_testIndexOf_B", testIndexOf(newIndexedList(A,B), B, 1));
			printTest("threeElementList_removeThirdElement_testIndexOf_C", testIndexOf(newIndexedList(A,B), C, -1));
			printTest("threeElementList_removeThirdElement_testRemoveAtIndex_0", testRemoveAtIndex(newIndexedList(A,B), 0, Results.NoException));
			printTest("threeElementList_removeThirdElement_testRemoveAtIndex_1", testRemoveAtIndex(newIndexedList(A,B), 1, Results.NoException));
			printTest("threeElementList_removeThirdElement_testRemoveAtIndex_-1", testRemoveAtIndex(newIndexedList(A,B), -1, Results.IndexOutOfBounds));
			printTest("threeElementList_removeThirdElement_testRemoveAtIndex_2", testRemoveAtIndex(newIndexedList(A,B), 2, Results.IndexOutOfBounds));
		}
	}

	/** 
	 * Runs test for addToFront method 
	 * @return test success 
	 */
	private boolean testAddToFront(UnorderedListADT<Integer> list, Integer i, Results expectedResult) 
	{
		Results result; 
		try 
		{
			list.addToFront(i);
			result = Results.NoException;
		} 
		catch (Exception e) 
		{
			System.out.printf("%s caught unexpected %s\n", "newList_testAddToFront", e.toString());
			result = Results.Fail;
		}
		return result == expectedResult; 
	}

	/** 
	 * Runs test for addToRear method 
	 * @return test success 
	 */
	private boolean testAddToRear(UnorderedListADT<Integer> list, Integer i, Results expectedResult) 
	{
		Results result;
		try 
		{
			list.addToRear(i);
			result = Results.NoException;
		} 
		catch (Exception e) 
		{
			System.out.printf("%s caught unexpected %s\n", "newList_testAddToRear", e.toString());
			result = Results.Fail;
		}
		return result == expectedResult;
	}

	/** 
	 * Runs test for addToAfter method 
	 * @return test success 
	 */
	private boolean testAddAfter(UnorderedListADT<Integer> list, Integer i, Integer j, Results expectedResult) 
	{
		Results result;
		try 
		{
			list.addAfter(i, j);
			result = Results.NoException;
		} 
		catch (ElementNotFoundException e) 
		{
			result = Results.ElementNotFound;
		} 
		catch (Exception e) 
		{
			System.out.printf("%s expected %s, caught %s\n", "newList_testAddAfter", "ElementNotFoundException", e.toString());
			result = Results.Fail;
		}
		return result == expectedResult; 
	}

	/** 
	 * Runs test for removeFirst method 
	 * @return test success 
	 */
	private boolean testRemoveFirst(UnorderedListADT<Integer> list, Results expectedResult) 
	{
		Results result; 
		try 
		{
			list.removeFirst();
			result = Results.NoException;
		} 
		catch (EmptyCollectionException e) 
		{
			result = Results.EmptyCollection;
		} 
		catch (Exception e) 
		{
			System.out.printf("%s expected %s, caught %s\n", "newList_testRemoveFirst", "EmptyCollectionException", e.toString());
			result = Results.Fail;
		}
		return result == expectedResult;
	}

	/** 
	 * Runs test for removeLast method 
	 * @return test success 
	 */
	private boolean testRemoveLast(UnorderedListADT<Integer> list, Results expectedResult) 
	{
		Results result;
		try 
		{
			list.removeLast();
			result = Results.NoException;
		} 
		catch (EmptyCollectionException e) 
		{
			result = Results.EmptyCollection;
		} 
		catch (Exception e) 
		{
			System.out.printf("%s expected %s, caught %s\n", "newList_testRemoveLast", "EmptyCollectionException", e.toString());
			result = Results.Fail;
		}
		return result == expectedResult;
	}

	/** 
	 * Runs test for remove method 
	 * @return test success 
	 */
	private boolean testRemove(UnorderedListADT<Integer> list, Integer i, Results expectedResult) 
	{
		Results result;
		try 
		{
			list.remove(i);
			result = Results.NoException;
		} 
		catch (ElementNotFoundException e) 
		{
			result = Results.ElementNotFound;
		} 
		catch (Exception e) 
		{
			System.out.printf("%s expected %s, caught %s\n", "newList_testRemoveElement", "ElementNotFoundException", e.toString());
			result = Results.Fail;
		}
		return result == expectedResult;
	}

	/** 
	 * Runs test for first method 
	 * @return test success 
	 */
	private boolean testFirst(UnorderedListADT<Integer> list, Results expectedResult) 
	{
		Results result;
		try 
		{
			list.first();
			result = Results.NoException;
		} 
		catch (EmptyCollectionException e) 
		{
			result = Results.EmptyCollection;
		} 
		catch (Exception e) 
		{
			System.out.printf("%s expected %s, caught %s\n", "newList_testFirst", "EmptyCollectionException", e.toString());
			result = Results.Fail;
		}
		return result == expectedResult;
	}

	/** 
	 * Runs test for last method 
	 * @return test success 
	 */
	private boolean testLast(UnorderedListADT<Integer> list, Results expectedResult) 
	{
		Results result;
		try 
		{
			list.last();
			result = Results.NoException;
		} 
		catch (EmptyCollectionException e) 
		{
			result = Results.EmptyCollection;
		} 
		catch (Exception e) 
		{
			System.out.printf("%s expected %s, caught %s\n", "newList_testLast", "EmptyCollectionException", e.toString());
			result = Results.Fail;
		}
		return result == expectedResult;
	}

	/** 
	 * Runs test for contains method 
	 * @return test success 
	 */
	private boolean testContains(UnorderedListADT<Integer> list, Integer i, Results expectedResult) 
	{
		Results result;
		try 
		{
			if(list.contains(i))
			{
				result = Results.True;
			}
			else
			{
				result = Results.False;
			}
		} 
		catch (Exception e) 
		{
			System.out.printf("%s caught unexpected %s\n", "newList_testContains", e.toString());
			result = Results.Fail;
		}
		return result == expectedResult;
	}

	/** 
	 * Runs test for isEmpty method 
	 * @return test success 
	 */
	private boolean testIsEmpty(UnorderedListADT<Integer> list, Results expectedResult) 
	{
		Results result;
		try 
		{
			if(list.isEmpty())
			{
				result = Results.True;
			}
			else
			{
				result = Results.False;
			}
		} 
		catch (Exception e) 
		{
			System.out.printf("%s caught unexpected %s\n", "newList_testIsEmpty", e.toString());
			result = Results.Fail;
		}
		return result == expectedResult;
	}
	
	/** 
	 * Runs test for size method 
	 * @return test success 
	 */
	private boolean testSize(UnorderedListADT<Integer> list, int expectedSize) 
	{
		try 
		{
			return (list.size() == expectedSize);
		} 
		catch (Exception e) 
		{
			System.out.printf("%s caught unexpected %s\n", "newList_testSize", e.toString());
			return false;
		}
	}
	
	/** 
	 * Runs test for iterator method 
	 * @return test success 
	 */
	private boolean testIterator(UnorderedListADT<Integer> list, Results expectedResult) 
	{
		Results result; 
		try 
		{
			@SuppressWarnings("unused")
			Iterator<Integer> it = list.iterator();
			result = Results.NoException;
		} 
		catch (Exception e) 
		{
			System.out.printf("%s caught unexpected %s\n", "newUnorderedList_testIterator", e.toString());
			result = Results.Fail;
		}
		return result == expectedResult;
	}
	
	/** 
	 * Runs test for hasNext method for iterator.
	 * Iterator method.
	 * @return test success 
	 */
	private boolean testIteratorHasNext(UnorderedListADT<Integer> list, Results expectedResult) 
	{
		Results result;
		try 
		{
			Iterator<Integer> it = list.iterator();
			if(it.hasNext())
			{
				result = Results.True;
			}
			else
			{
				result = Results.False;
			}
		} 
		catch (Exception e) 
		{
			System.out.printf("%s caught unexpected %s\n", "newUnorderedList_testIteratorHasNext", e.toString());
			result = Results.Fail;
		}
		return result == expectedResult;
	}
	
	/** 
	 * Runs test for next method for iterator.
	 * Iterator method. 
	 * @return test success 
	 */
	private boolean testIteratorNext(UnorderedListADT<Integer> list, Results expectedResult) 
	{
		Results result;
		try 
		{
			Iterator<Integer> it = list.iterator();
			it.next();
			result = Results.NoException;
		} 
		catch (NoSuchElementException e) 
		{
			result = Results.ElementNotFound;
		} 
		catch (Exception e) 
		{
			System.out.printf("%s caught unexpected %s\n", "newUnorderedList_testIteratorNext", e.toString());
			result = Results.Fail;
		}
		return result == expectedResult;
	}
		
	/** 
	 * Runs test for toString method 
	 * difficult to test - just confirm that default address output has been overridden
	 * @return test success */
	private boolean testToString(UnorderedListADT<Integer> list, Results expectedResult) 
	{
		Results result;
		try 
		{
			String str = list.toString();
			System.out.println("toString() output: " + str);
			if (str.length() == 0) 
			{
				result = Results.Fail;
			}
			char lastChar = str.charAt(str.length()-1);
			if (str.contains("@") && !str.contains(" ") && Character.isLetter(str.charAt(0)) && (Character.isDigit(lastChar)
							|| (lastChar >= 'a' && lastChar <= 'f'))) 
			{
				result = Results.Fail; //looks like default toString()
			}
			else
			{
				result = Results.NoException;
			}
		} 
		catch (Exception e) 
		{
			System.out.printf("%s caught unexpected %s\n", "newList_testToString", e.toString());
			result = Results.Fail;
		}
		return result == expectedResult;
	}
	
	/** 
	 * Runs test for add(element) method 
	 * @return test success 
	 */
	private boolean testAdd(IndexedListADT<Integer> list, Integer i, Results expectedResult) 
	{
		Results result;
		try 
		{
			list.add(i);
			result = Results.NoException;
		} 
		catch (Exception e) 
		{
			System.out.printf("%s caught unexpected %s\n", "newList_testAdd", e.toString());
			result = Results.Fail;
		}
		return result == expectedResult; 
	}
	
	/** 
	 * Runs test for add(index, element) method 
	 * @return test success 
	 */
	private boolean testAdd(IndexedListADT<Integer> list, int i, Integer j, Results expectedResult) 
	{
		Results result;
		try 
		{
			list.add(i, j);
			result = Results.NoException;
		} 
		catch (IndexOutOfBoundsException e) 
		{
			result = Results.IndexOutOfBounds;
		} 
		catch (Exception e) 
		{
			System.out.printf("%s expected %s, caught %s\n", "newList_testAdd", "IndexOutOfBoundsException", e.toString());
			result = Results.Fail;
		}
		return result == expectedResult; 
	}
	
	/** 
	 * Runs test for set method 
	 * @return test success 
	 */
	private boolean testSet(IndexedListADT<Integer> list, int i, Integer j, Results expectedResult) 
	{
		Results result;
		try 
		{
			list.set(i, j);
			result = Results.NoException;
		} 
		catch (IndexOutOfBoundsException e) 
		{
			result = Results.IndexOutOfBounds;
		} 
		catch (Exception e) 
		{
			System.out.printf("%s expected %s, caught %s\n", "newList_testSet", "IndexOutOfBoundsException", e.toString());
			result = Results.Fail;
		}
		return result == expectedResult; 
	}
	
	/** 
	 * Runs test for get method 
	 * @return test success 
	 */
	private boolean testGet(IndexedListADT<Integer> list, int i, Results expectedResult) 
	{
		Results result;
		try 
		{
			list.get(i);
			result = Results.NoException;
		} 
		catch (IndexOutOfBoundsException e) 
		{
			result = Results.IndexOutOfBounds;
		} 
		catch (Exception e) 
		{
			System.out.printf("%s expected %s, caught %s\n", "newList_testGet", "IndexOutOfBoundsException", e.toString());
			result = Results.Fail;
		}
		return result == expectedResult; 
	}
	
	/** 
	 * Runs test for IndexOf method 
	 * @return test success 
	 */
	private boolean testIndexOf(IndexedListADT<Integer> list, Integer i, int expectedResult) 
	{
		int result = list.indexOf(i);
		return result == expectedResult;
	}
	
	/** 
	 * Runs test for remove(index) method 
	 * @return test success 
	 */
	private boolean testRemoveAtIndex(IndexedListADT<Integer> list, int i, Results expectedResult)
	{
		Results result;
		try 
		{
			list.remove(i);
			result = Results.NoException;
		}
		catch (IndexOutOfBoundsException e) 
		{
			result = Results.IndexOutOfBounds;
		} 
		catch (Exception e) 
		{
			System.out.printf("%s expected %s, caught %s\n", "newList_testRemoveAt", "IndexOutOfBoundsException", e.toString());
			result = Results.Fail;
		}
		return result == expectedResult; 
	}
	
	/**
	 * Prints test header
	 * 
	 * @param scenario scenario number being tested
	 * @param listType type of list being tested
	 */
	private void printTestHeader(int scenario, String listType) {
		System.out.println("\n<--- Scenario " + scenario + " - " + listType + " List Tests --->");
	}
}

