/**
 * Represents a single item to be stored in the HashTable
 * 
 * @author jcarlson
 *
 * @param <T>
 */
public class HashObject<T> {
	private static int totalDuplicates = 0;

	protected final int LINEAR = 1;
	protected final int DOUBLE = 2;
	protected final int RANDOM_NUMBER = 1;
	protected final int SYSTEM_TIME = 2;
	protected final int TEXT_FILE = 3;
	
	protected T data;
	protected int dataType;
	protected int m;
	protected int hashCode;
	protected int key;
	protected boolean unique;
	protected int duplicateCount;
	protected HashProbe probe;
	protected int probeType;
		
	public HashObject(T obj, int dataType, int tableSize, int hashCode) {
		data = obj;
		this.dataType = dataType;
		m = tableSize;
		this.hashCode = hashCode;
		key = Math.abs(hashCode) % m;
		unique = false;
		duplicateCount = 0;
	}
	
	public boolean equals(HashObject obj) {
		if(dataType == TEXT_FILE) {
			return data.equals(obj.getData()); 
		}
		else {
			return data == obj.getData();
		}
	}
	
	public String toString() {
		return data + " " + duplicateCount;
	}
	
	public int getKey() {
		return key;
	}
	
	public T getData() {
		return data;
	}
	
	public int getDataType() {
		return dataType;
	}
	
	public int getHashCode() {
		return hashCode;
	}
	
	public int getDuplicateCount() {
		return duplicateCount;
	}
	
	public int getTotalDuplicateCount() {
		return totalDuplicates / 2;
	}
	
	public int getTotalProbeCount() {
		return probe.getTotalCount();
	}
	
	public void setProbe(HashProbe probe, int probeType) {
		this.probe = probe;
		this.probeType = probeType;
	}
	
	public int getProbeCount() {
		return probe.getCount();
	}
	
	public boolean probeHasNextIndex() {
		return probe.hasNextIndex();
	}
	
	public int getNextIndex() {
		return probe.getNextIndex();
	}
	
	public void insertedSuccessfully() {
		probe.addToTotalCount();
		unique = true;
	}
	
	public boolean isUnique() {
		return unique;
	}
	
	protected void addDuplicate() {
		duplicateCount++;
		totalDuplicates++;
	}
}
