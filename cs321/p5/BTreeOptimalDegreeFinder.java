
public class BTreeOptimalDegreeFinder {
	public static int findOptimalDegree(int nodeMetadataSize, int objectSize, int nodePointerSize, int blockSize) {
		double numerator = blockSize - nodeMetadataSize + objectSize - nodePointerSize;
		double denominator = 2 * (objectSize + nodePointerSize);
		double optimalDegree = Math.floor(numerator / denominator);
		return (int)optimalDegree;
	}
}
