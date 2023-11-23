/**
 * Provides Linear Hashing functionality while searching for the next
 * available position in the HashTable
 *  
 * @author jcarlson
 *
 */
public class HashProbeLinear extends HashProbe {
	private static int totalCount = 0;
	
	public HashProbeLinear(int tableSize, HashObject obj) {
		super(tableSize, obj);
	}
	
	public int getNextIndex() {
		if(count == 0) {
			nextIndex = startIndex;
		}
		else {
			nextIndex = (nextIndex + 1) % m;
		}
			count++;
			return nextIndex;
	}
	
	public int getTotalCount() {
		return totalCount;
	}
	
	public void addToTotalCount() {
		totalCount += count;
	}

	
}
