
public class BTreeOptimalDegreeFinder_Tester {

	public static void main(String[] args) {
		// Testing with our Exam Problem Q5(b)
		int nodeMetaDataSize = 40;
		int treeObjectSize = 32;
		int nodePointerSize = 4;
		int blockSize = 4096;
		int expectedValue = 56;
		
		int optimalDegree = BTreeOptimalDegreeFinder.findOptimalDegree(nodeMetaDataSize,treeObjectSize,nodePointerSize,blockSize);
		
		System.out.println("        TreeObject Size: " + treeObjectSize);
		System.out.println("    Node Meta-Data Size: " + nodeMetaDataSize);
		System.out.println("      Node Pointer Size: " + nodePointerSize);
		System.out.println("        Disk Block Size: " + blockSize);
		System.out.println("  Optimal Degree Result: " + optimalDegree);
		System.out.println("Optimal Degree Expected: " + expectedValue);
		System.out.println((optimalDegree == expectedValue) ? "   ------ Passed ------" : "   ------ Failed ------");

	}

}
