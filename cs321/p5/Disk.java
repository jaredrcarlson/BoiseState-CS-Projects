import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/** The Disk class simulates a Hard Disk with Read and Write functionality
 * 
 * @author jcarlson
 *
 */
public class Disk {
	private final int NULL_ADDRESS_CEILING = DiskMetadata.size();
	private final int NB_BYTE = 1;
	private final int NB_INT = 4;
	private final int NB_LONG = 8;
	
	private int debugMode = BTree.getDebugMode();
	
	// Meta-Data
	private DiskMetadata md;
	
	// Fields
	private String diskName;
	private File diskFile;
	private RandomAccessFile raDiskFile;
	
	// Constructor - New Disk Object from GBK File (Create BTree)
	public Disk(String geneBankFilename, int sequenceLength, int degree, int nodeMetadataSize, int keySize) {
		diskName = geneBankFilename + ".btree.data." + sequenceLength + "." + degree;
        diskFile = new File(diskName);
		try {
			raDiskFile = new RandomAccessFile(diskFile,"rw");
		} catch (FileNotFoundException e) {
			System.err.println("Error in Disk.Disk() Constructor");
			e.printStackTrace();
		}
		int rootAddress = -1;
		int numNodes = 0;
		int maxNumKeys = 2 * degree - 1;
		md = new DiskMetadata(rootAddress, sequenceLength, degree, numNodes, nodeMetadataSize, maxNumKeys, keySize);
	}
	
	// Public Methods
	// New Disk Object from existing BTree File (Search BTree)
	public Disk(String bTreeFilename) {
		diskName = bTreeFilename;
        diskFile = new File(diskName);
		try {
			raDiskFile = new RandomAccessFile(diskFile,"r");
		} catch (FileNotFoundException e) {
			System.err.println("Error in Disk.getBTree() Method");
			e.printStackTrace();
		}
		md = new DiskMetadata();
		readDiskMetadata();
	}
	
	public int getNumReads() {
		return md.numReads;
	}
	
	public long getBytesRead() {
		return md.bytesRead;
	}
	
	public int getNumWrites() {
		return md.numWrites;
	}
	
	public long getBytesWritten() {
		return md.bytesWritten;
	}
	
	public int getNextAddress() {
		return md.getNextAddress();
	}

	public BTreeNode read(BTreeNode node) {
		return read(node.getAddress());
	}
	
	public BTreeNode read(int addressOnDisk) {
		return readNode(addressOnDisk);
	}
	
	public BTreeNode write(BTreeNode node) {
		int address = node.getAddress();
		if(address < md.getSize()) {
			address = md.nextOpenAddress;
			node.setAddress(getNextAddress());
		}
		writeNode(address,node);
		md.numNodes++;
		return node;
	}
	
	public BTreeNode readRoot() {
		md.numReads++;
		md.bytesRead += NB_INT;
		try {
			raDiskFile.seek(0);
			int rootAddress = raDiskFile.readInt();
			if(debugMode > -1) {
				System.out.println("Reading Root Node Address from Disk");
				System.out.println("Root Address is: " + rootAddress);
			}
			return readNode(rootAddress);
		} catch (IOException e) {
			System.err.println("Error in Disk.readRoot() Method");
			e.printStackTrace();
		}
		return null;
	}
	
	public void writeRoot(BTreeNode root) {
		md.numWrites++;
		md.bytesWritten += NB_INT;
		try {
			raDiskFile.seek(0);
			int rootAddress = root.getAddress();
			raDiskFile.writeInt(rootAddress);
		} catch (IOException e) {
			System.err.println("Error in Disk.writeRoot() Method");
			e.printStackTrace();
		}
		
	}
	
	public void close(BTreeNode root) {
		int rootAddress = root.getAddress();
		if(debugMode > -1) {
			System.out.println("Writing Root Node Address to Disk");
			System.out.println("Root Address is: " + rootAddress);
		}
		writeDiskMetadata(rootAddress,md.nextOpenAddress,md.sequenceLength,md.degree,md.numNodes,md.nodeMetadataSize,md.maxNumKeys,md.keySize,md.bytesRead,md.numReads,md.bytesWritten,md.numWrites);
		try {
			raDiskFile.close();
		} catch (IOException e) {
			System.err.println("Error in Disk.close() Method");
			e.printStackTrace();
		}
	}
	
	// Private Methods
	private void writeDiskMetadata(int rootAddress, int nextOpenAddress, int sequenceLength, int degree, int numNodes, int nodeMetadataSize, int maxNumKeys, int keySize, long bytesRead, int numReads, long bytesWritten, int numWrites) {
		md.numWrites++;
		md.bytesWritten += DiskMetadata.size();
		try {
			raDiskFile.seek(0);
			if(debugMode > 1) {
				System.out.println(" --- Writing Disk MD At: " + raDiskFile.getFilePointer());
			}
			raDiskFile.writeInt(rootAddress);
			raDiskFile.writeInt(nextOpenAddress);
			raDiskFile.writeInt(sequenceLength);
			raDiskFile.writeInt(degree);
			raDiskFile.writeInt(numNodes);
			raDiskFile.writeInt(nodeMetadataSize);
			raDiskFile.writeInt(maxNumKeys);
			raDiskFile.writeInt(keySize);
		} catch (IOException e) {
			System.err.println("Error in Disk.writeDiskMetadata() Method");
			e.printStackTrace();
		}
		
	}
	
	private void readDiskMetadata() {
		try {
			raDiskFile.seek(0);
			if(debugMode > 1) {
				System.out.println(" --- Reading Disk MD At: " + raDiskFile.getFilePointer());
			}
			md.rootAddress = raDiskFile.readInt();
			md.nextOpenAddress = raDiskFile.readInt();
			md.sequenceLength = raDiskFile.readInt();
			md.degree = raDiskFile.readInt();
			md.numNodes = raDiskFile.readInt();
			md.nodeMetadataSize = raDiskFile.readInt();
			md.maxNumKeys = raDiskFile.readInt();
			md.keySize = raDiskFile.readInt();
		} catch (IOException e) {
			System.err.println("Error in Disk.readDiskMetadata() Method");
			e.printStackTrace();
		}
	}
	
	private BTreeNode readNode(int addressOnDisk) {
		md.numReads++;;
		md.bytesRead += NB_LONG * 2 + NB_INT * 2;
		try {
			raDiskFile.seek(addressOnDisk);
			if(debugMode > 1) {
				System.out.println("<| --- Disk.readNode(int addressOnDisk) --- |>");
				System.out.println("<| Current File position: " + raDiskFile.getFilePointer() + " |>");
			}
			long numberOfKeys = raDiskFile.readLong();
			long numberOfChildren = raDiskFile.readLong();
			int address = raDiskFile.readInt();
			int parentAddress = raDiskFile.readInt();
			BTreeNodeMetadata md = new BTreeNodeMetadata(numberOfKeys,numberOfChildren,address,parentAddress);
			
			ArrayList<Key> keys = new ArrayList<Key>();
			Key firstKey = readKey();
			keys.add(firstKey);
			for(int i=1; i < numberOfKeys; i++) {
				keys.add(readKey());
				keys.get(i-1).setNext(keys.get(i));
				keys.get(i).setPrevious(keys.get(i-1));
			}
			if(debugMode > 1) {
				System.out.println("<| Disk - Keys Read |>");
				for(int i=0; i < numberOfKeys; i++) {
					System.out.println(keys.get(i).toShortString(i));
				}
				System.out.println();
			}
			return new BTreeNode(md,firstKey);
		} catch (IOException e) {
			System.err.println("Error in Disk.readNode() Method");
			e.printStackTrace();
		}
		return null;
	}
	
	private Key readKey() {
		md.bytesRead += NB_BYTE * 2 + NB_LONG + NB_INT * 3;
		try {
			if(debugMode > 1) {
				System.out.println("<| --- Disk.readKey() --- |>");
				System.out.println("<| Current File position: " + raDiskFile.getFilePointer() + " |>");
			}
			byte sequenceLength = raDiskFile.readByte();
			byte numZerosStripped = raDiskFile.readByte();
			TreeObjectMetadata md = new TreeObjectMetadata(sequenceLength,numZerosStripped);
			int leftChildAddress = raDiskFile.readInt();
			int rightChildAddress = raDiskFile.readInt();
			long keyID = raDiskFile.readLong();
			int frequency = raDiskFile.readInt();
			return new Key(md,leftChildAddress,rightChildAddress,keyID,frequency);
		} catch (IOException e) {
			System.err.println("Error in Disk.readKey() Method");
			e.printStackTrace();
		}
		return null;
	}
	
	private void writeNode(int addressOnDisk, BTreeNode node) {
		md.numWrites++;
		md.bytesWritten += NB_LONG * 2 + NB_INT * 2;
		try {
			raDiskFile.seek(addressOnDisk);
			if(debugMode > 1) {
				System.out.println("<| --- Disk.writeNode(int addressOnDisk, BTreeNode node) --- |>");
				System.out.println("<| Current File position: " + raDiskFile.getFilePointer() + " |>");
			}
			long currNumKeys = node.getNumberOfKeys();
			long numNullKeys = md.maxNumKeys - currNumKeys;
			long currNumChildren = node.getNumberOfChildren();
			int parentAddress = node.getParentAddress();
			
			// Metadata
			raDiskFile.writeLong(currNumKeys);
			raDiskFile.writeLong(currNumChildren);
			raDiskFile.writeInt(addressOnDisk);
			raDiskFile.writeInt(parentAddress);
			
			// Keys
			Key nextKey = node.getFirstKey();
			for(int i = 0; i < currNumKeys; i++) {
				writeKey(nextKey,i);
				nextKey = nextKey.next();
			}
			int bytesSkipped = raDiskFile.skipBytes((int)(numNullKeys * md.keySize));
			if(debugMode > 1) {
				System.out.println("<| Bytes Skipped: " + bytesSkipped + " |>");			
			}
		} catch (IOException e) {
			System.err.println("Error in Disk.writeNode() Method");
			e.printStackTrace();
		}
	}
	
	private void writeKey(Key key, int index) {
		md.bytesWritten += NB_BYTE * 2 + NB_LONG + NB_INT * 3;
		try {
			if(debugMode > 1) {
				System.out.println("<| --- Disk.writeKey(Key key) --- |>");
				System.out.println("<| Current File position: " + raDiskFile.getFilePointer() + " |>");
				System.out.println(key.toShortString(index));
			}
			byte sequenceLength = key.getSequenceLength();
			byte numZerosStripped = key.getNumZerosStripped();
			int leftChildAddress = key.getLeftChildAddress();
			int rightChildAddress = key.getRightChildAddress();
			long keyID = key.getKey();
			int frequency = key.getFrequency();
			raDiskFile.writeByte(sequenceLength);
			raDiskFile.writeByte(numZerosStripped);
			raDiskFile.writeInt(leftChildAddress);
			raDiskFile.writeInt(rightChildAddress);
			raDiskFile.writeLong(keyID);
			raDiskFile.writeInt(frequency);
		} catch (IOException e) {
			System.err.println("Error in Disk.writeKey() Method");
			e.printStackTrace();
		}	
	}
}
