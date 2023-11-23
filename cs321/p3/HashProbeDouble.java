/**
 * Provides Double-Hashing Probe Sequence functionality which improves
 * hashing speed as it decreases the problem of clustering that occurs
 * from a simple Linear Hash method
 * 
 * @author jcarlson
 *
 */
public class HashProbeDouble extends HashProbe {
	private static int totalCount = 0;
	
	public HashProbeDouble(int tableSize, HashObject obj) {
		super(tableSize, obj);
	}
	
	public int getNextIndex() {
		nextIndex = (startIndex + (count * (1 + (key % (m - 2))))) % m;
		count++;
		return Math.abs(nextIndex);
	}
	
	public int getTotalCount() {
		return totalCount;
	}
	
	public void addToTotalCount() {
		totalCount += count;
	}	
}
