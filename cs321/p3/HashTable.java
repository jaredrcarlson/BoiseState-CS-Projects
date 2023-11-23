/**
 * Implements a HashTable data storage structure and supports
 * both Linear-Hashing and Double-Hashing Probe sequences
 * 
 * @author jcarlson
 *
 */
public class HashTable {
	private final int DISPLAY_PROBE_COUNTS = 2;
	private final int DISPLAY_FULL = 3;
	private final int LINEAR = 1;
	
	private boolean displayProbeSequence = false;
	private HashObject[] table;
	private int tableSize;
	private int probeType;
	private int loadSize;
	private double currentLoadFactor;
	private HashObject firstEntry;
	
	public HashTable(int tableSize, int probeType, int debugLevel) {
		this.probeType = probeType;
		this.tableSize = tableSize;
		table = new HashObject[tableSize];
		loadSize = 0;
		calculateLoadFactor();
		if(debugLevel == DISPLAY_PROBE_COUNTS) {
			displayProbeSequence = true;
		}
	}
	
	public double getCurrentLoadFactor() {
		return currentLoadFactor;
	}
	
	public int size() {
		return tableSize;
	}
	
	public int getLoadSize() {
		return loadSize;
	}
	
	public HashObject[] getTable() {
		return table;
	}
	
	public int getTotalProbeCount() {
		return firstEntry.getTotalProbeCount();
	}
	
	public int getTotalDuplicateCount() {
		return firstEntry.getTotalDuplicateCount();
	}
	
	public void insert(HashObject newData) {
		boolean probeIsComplete = false;
		HashProbe newProbe = (probeType == LINEAR) ? new HashProbeLinear(tableSize, newData)
											       : new HashProbeDouble(tableSize, newData);
		newData.setProbe(newProbe, probeType);
		int index = newData.getNextIndex();
		if(displayProbeSequence) {
			String pType = (probeType == LINEAR) ? "Linear" : "Double";
			System.out.println("\n\n--- " + pType + " Probe Sequence ---");
			System.out.println("New Data: [" + newData.getData() + "]");
			System.out.println("HashCode: [" + newData.getHashCode() + "]");
			System.out.println("     Key: [" + newData.getKey() + "]");
		}
		
		while(!probeIsComplete && newData.probeHasNextIndex()) {
			if(table[index] == null) {
				table[index] = newData;
				table[index].insertedSuccessfully();
				loadSize++;
				calculateLoadFactor();
				probeIsComplete = true;
				if(loadSize == 1) {
					firstEntry = table[index];
				}
				if(displayProbeSequence) {
					System.out.println("---> Table[" + index + "] == Null (Empty)");
					System.out.println("---> Table[" + index + "] <--- Inserting [" + newData.getData() + "]");
					System.out.println("---> Table[" + index + "] == " + newData.getData());
				}
			}
			else if(table[index].getData().equals(newData.getData())) {
				table[index].addDuplicate();
				probeIsComplete = true;
				if(displayProbeSequence) {
					System.out.println("---> Table[" + index + "] == " + table[index].getData());
					System.out.println("-----> Duplicate # " + table[index].getDuplicateCount() + " has been discarded.");
				}
			}
			else {
				if(displayProbeSequence) {
					System.out.println("---> Table[" + index + "] == " + table[index].getData());
				}
				index = newData.getNextIndex();
			}
		}
	}
	
	/**
	 * Display Table using System.out method
	 */
	public void displayTable() {
		for(int i=0; i < size(); i++) {
			if(table[i] != null) {
				System.out.println("Table[" + i + "]: " + table[i].toString());
			}
		}
	}
	
	private void calculateLoadFactor() {
		currentLoadFactor = loadSize / (double)tableSize;
	}
	
	/**
	 * Build a full String representation of the Table
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		for(int i=0; i < size(); i++) {
			if(table[i] != null) {
				str.append("table[" + i + "]: " + table[i].toString());
			}
		}
		return str.toString();
	}
	
	
}
