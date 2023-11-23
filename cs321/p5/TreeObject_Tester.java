public class TreeObject_Tester {
	private static String dnaString1 = "ACGT";
	private static long expLong1 = 27;
	
	private static String dnaString2 = "GgAt";
	private static long expLong2 = 163;
	
	private static String dnaString3 = "TaaC";
	private static long expLong3 = 193;
	
	private static String dnaString4 = "aaccggtt";
	private static long expLong4 = 1455;
	
	public static void main(String[] args) {
		System.out.println("--- Test: DNA Sequence (String) to Long Value ---");
		test_StringToLong(dnaString1,expLong1);
		test_StringToLong(dnaString2,expLong2);
		test_StringToLong(dnaString3,expLong3);
		test_StringToLong(dnaString4,expLong4);
		
		System.out.println("\n--- Test: Long Value to DNA Sequence (String) ---");
		test_LongToString(dnaString1,"ACGT");
		test_LongToString(dnaString2,"GGAT");
		test_LongToString(dnaString3,"TAAC");
		test_LongToString(dnaString4,"AACCGGTT");
		test_LongToString("ACGTTGCAAACCGGTTGGTTAACC","ACGTTGCAAACCGGTTGGTTAACC");
		
		System.out.println("\n--- Test: Implements Comparable ---");
		TreeObject to1,to2,to3,to4;
		to1 = new TreeObject(dnaString1);
		to2 = new TreeObject(dnaString2);
		to3 = new TreeObject(dnaString3);
		to4 = new TreeObject(dnaString3);
		test_Comparable(to1,to2,-1);
		test_Comparable(to3,to2,1);
		test_Comparable(to3,to4,0);
		
	}
	
	private static void test_StringToLong(String dnaString, long expectedValue) {
		TreeObject to = new TreeObject(dnaString);		
		System.out.println("\nDNA String: " + dnaString);
		System.out.println("  Expected: " + expectedValue);
		System.out.println("    Result: " + to.getDnaID());
		System.out.println((expectedValue == to.getDnaID()) ? "   ------ Passed ------" : "   ------ Failed ------");
	}
	
	private static void test_LongToString(String dnaString, String expectedValue) {
		TreeObject to = new TreeObject(dnaString);		
		System.out.println("\nDNA String: " + dnaString);
		System.out.println("  Expected: " + expectedValue);
		System.out.println("    Result: " + to.getDnaString());
		System.out.println((expectedValue.equals(to.getDnaString())) ? "   ------ Passed ------" : "   ------ Failed ------");
	}
	
	private static void test_Comparable(TreeObject treeObj1, TreeObject treeObj2, int exp) {
		System.out.println("\n       (This) DNA String: " + treeObj1.getDnaString() + " == Long Value: " + treeObj1.getDnaID());
		System.out.println("(Compared To) DNA String: " + treeObj2.getDnaString() + " == Long Value: " + treeObj2.getDnaID());
		System.out.println("                  Expected: " + exp);
		System.out.println(" (compareTo method) Result: " + treeObj1.compareTo(treeObj2));
		System.out.println((exp == treeObj1.compareTo(treeObj2)) ? "   ------ Passed ------" : "   ------ Failed ------");
		System.out.println("    (equals method) Result: " + treeObj1.equals(treeObj2));
	}

}
