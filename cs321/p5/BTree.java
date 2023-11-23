import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.LinkedList;

public class BTree {
	private static int totalNodes;
	private static int totalDnaSequences;
	private static int debugMode;
	private static long startTime;
	private static long endTime;
	private static Disk disk;
	private static int cacheMode;
	
	private Cache cache;	
	private int degree;
	private int sequenceLength;
	
	
	private String bTreeFilename;
	private BTreeNode root;
	private BTreeNode current;
	private BTreeNode child;
	private Key currentKey;
	
	// Constructor - New BTree from gbk file
	public BTree(int cacheMode, int degree, String gbkFilename, int sequenceLength, int cacheSize, int debugLevel) {
		this.cacheMode = cacheMode;
		if(cacheMode > 0) {
			this.cache = new Cache(cacheSize);
		}
		else {
			this.cache = null;
		}
		if(degree == 0) {
			this.degree = BTreeOptimalDegreeFinder.findOptimalDegree(BTreeNodeMetadata.size(),Key.size(),4,4096);
			if(debugMode > -1) {
				try {
					System.out.print("Calculating Optimal Degree");
					for(int i=0;i<40;i++) {
						System.out.print(".");
						Thread.sleep(100);
					}
					System.out.print(" done!");
					System.out.println("\nOptimal Degree is: " + this.degree);
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// Planned Pause
					e.printStackTrace();
				}
			}
		}
		else {
			this.degree = degree;
		}
		debugMode = debugLevel;
		int nodeMetadataSize = BTreeNodeMetadata.size();
		int keySize = Key.size();
		this.sequenceLength = sequenceLength;
		disk = new Disk(gbkFilename, sequenceLength, this.degree, nodeMetadataSize, keySize);
		bTreeFilename = gbkFilename + ".btree.data." + sequenceLength + "." + this.degree;
		root = new BTreeNode();
		current = null;
		child = null;
		currentKey = null;
        if(debugMode > -1) {
            System.out.print("Building BTree...");
        }
	}
	
	// Constructor - Existing BTree from disk file
	public BTree(int cacheMode, String bTreeFilename, int cacheSize, int debugLevel) {
		this.cacheMode = cacheMode;
		if(cacheMode > 0) {
			cache = new Cache(cacheSize);
		}
		else {
			cache = null;
		}
		debugMode = debugLevel;
		this.bTreeFilename = bTreeFilename;
		disk = new Disk(this.bTreeFilename);
		root = disk.readRoot();
		this.sequenceLength = root.getFirstKey().getSequenceLength();
		current = root;
		child = null;
	}
	
	public static void incrementNodeCount() {
		totalNodes++;
	}
	
	public static int getNodeCount() {
		return totalNodes;
	}
	
	public static int getDebugMode() {
		return debugMode;
	}
	
	public static void invalidSequence(String dnaSequence) {
		System.err.println("Error in BTree.add(String dnaSequence)");
		System.err.println("The supplied argument was: " + dnaSequence);
		System.err.println("Current State of the Tree:\n" + getStatistics());
		System.exit(3);
	}
	
	public void add(String dnaSequence) {
		Key newKey = new Key(dnaSequence);
		totalDnaSequences++;
		if(debugMode > -1) {
			if(totalDnaSequences % 1000 == 0) {
				System.out.print(".");
			}
			if(totalDnaSequences % 100000 == 0) {
				System.out.println();
			}
		}
		if(debugMode > 1) {
			System.out.println("\n---> Adding: Sequence=\"" + newKey.getDnaString() + "\", ID=" + newKey.getDnaID() + "\n");
		}
		if(root.isEmpty()) {
			if(debugMode > 1) {
				System.out.println("Tree is Empty - Creating Initial Root Node");
			}
			startTime = System.nanoTime();
			root = new BTreeNode(disk.getNextAddress(),newKey,(2 * degree - 1));
			saveNode(root);
			if(debugMode > 1) {
				System.out.println(root.toShortString("Root"));
			}
		}
		else if(root.isFull()) {
			if(debugMode > 1) {
				System.out.println("Root is Full - Performing Initial Split");
			}
			current = root;
			child = new BTreeNode(disk.getNextAddress());
			root = new BTreeNode(disk.getNextAddress(),current,child);
			
			saveNodes(root,current,child);
			add(dnaSequence);
		}
		else {
			current = root;
			boolean leafReached = leafify(newKey,current);
			if(leafReached) {
				if(debugMode > 1) {
					System.out.println("--> Leaf Node Found...");
				}
				insert(newKey,current);
				if(debugMode > 1) {
					System.out.println("-> Sequence Inserted.");
				}
			}
			else {
				if(debugMode > 1) {
					System.out.println("-> Duplicate Detected - Frequency Updated.");
				}
			}
			saveNode(current);
		}
	}
	
	private boolean leafify(Key key, BTreeNode node) {
		current = node;
		if(debugMode > 1) {
			System.out.println("Searching for Leaf Node.....");
			System.out.println("Current Node: " + node.getAddress());
		}
		if(current.isLeaf()) {
			if(debugMode > 1) {
				System.out.println("Node " + node.getAddress() + " is a Leaf.");
			}
			return true;
		}
		else {
			currentKey = current.getFirstKey();
			while(currentKey.hasNext()) {
				if(key.equals(currentKey)) {
					currentKey.incrementFrequency();
					return false;
				}
				else if(key.lessThan(currentKey)) {
					child = openNode(currentKey.getLC());
					if(child.isFull()) {
						Key proKey = child.getMiddleKey();
						int rightChild = splitLC(proKey);
						if(key.equals(proKey)) {
							proKey.incrementFrequency();
							return false;
						}
						else if(key.lessThan(proKey)){
							return leafify(key,child);
						}
						else {
							return leafify(key,openNode(rightChild));
						}
					}
					return leafify(key,child);
				}
				else {
					currentKey = currentKey.next();
				}
			}
			// Last Key reached
			if(key.equals(currentKey)) {
				currentKey.incrementFrequency();
				return false;
			}
			else if(key.lessThan(currentKey)) {
				child = openNode(currentKey.getLC());
				if(child.isFull()) {
					Key proKey = child.getMiddleKey();
					int rightChild = splitLC(proKey);
					if(key.equals(proKey)) {
						proKey.incrementFrequency();
						return false;
					}
					else if(key.lessThan(proKey)) {
						return leafify(key,child);
					}
					else {
						return leafify(key,openNode(rightChild));
					}
				}
				return leafify(key,child);
			}
			else {
				child = openNode(currentKey.getRC());
				if(child.isFull()) {
					Key proKey = child.getMiddleKey();
					int rightChild = splitRC(proKey);
					if(key.equals(proKey)) {
						proKey.incrementFrequency();
						return false;
					}
					else if(key.lessThan(proKey)) {
						return leafify(key,child);
					}
					else {
						return leafify(key,openNode(rightChild));
					}
				}
				return leafify(key,child);
			}
		}
	}
	
	private int splitLC(Key proKey) {
		BTreeNode rightChild = split(current,currentKey,null,child);
		saveNodes(current,child,rightChild);
		if(debugMode > 1) {
			System.out.println("Left Child is Full - Performing Split");
			System.out.println("------- Split Results --------");
			System.out.println(current.toShortString("Parent"));
			System.out.println(child.toShortString("Left Child"));
			System.out.println(rightChild.toShortString("Right Child"));
		}
		return rightChild.getAddress();
	}
	
	private int splitRC(Key proKey) {
		BTreeNode rightChild = split(current,null,currentKey,child);
		saveNodes(current,child,rightChild);
		if(debugMode > 1) {
			System.out.println("Right Child is Full - Performing Split");
			System.out.println("------- Split Results --------");
			System.out.println(current.toShortString("Parent"));
			System.out.println(child.toShortString("Left Child"));
			System.out.println(rightChild.toShortString("Right Child"));
		}
		return rightChild.getAddress();
	}
	
	private BTreeNode split(BTreeNode parent, Key addBefore, Key addAfter, BTreeNode leftChild) {
		return new BTreeNode(parent,addBefore,addAfter,leftChild,disk.getNextAddress());		
	}
	
	public void insert(Key key, BTreeNode node) {
		boolean inserted = false;
		current = node;
		currentKey = current.getFirstKey();
		while(!inserted && currentKey.hasNext()){
			if(key.equals(currentKey)) {
				currentKey.incrementFrequency();
				inserted = true;
			}
			else if(key.lessThan(currentKey)) {
				current.addKeyBefore(key,currentKey);
				inserted = true;
			}
			else {
				currentKey = currentKey.next();
			}
		}
		if(!inserted) {
			if(key.equals(currentKey)) {
				currentKey.incrementFrequency();
				inserted = true;
			}
			else if(key.lessThan(currentKey)) {
				current.addKeyBefore(key,currentKey);
				inserted = true;
			}
			else {
				current.addKeyAfter(key,currentKey);
				inserted = true;
			}
		}
	}
	
	public int getFrequency(String dnaString) {
		Key searchKey = new Key(dnaString);
		Key locatedKey = locate(searchKey,root);
		if(locatedKey != null) {
			return locatedKey.getFrequency();
		}
		else {
			return 0;
		}
	}
	
	public void close() {
		endTime = System.nanoTime();
		
		createDumpFile();
		if(cacheMode > 0) {
			LinkedList<BTreeNode> cacheList = cache.dumpCacheList();
			int size = cacheList.size();
			for(int i=0;i<size;i++) {
				disk.write(cacheList.get(i));
			}
			disk.close(root);
		}
		else {
			disk.close(root);
		}
		if(debugMode > -2) {
			System.out.println(getStatistics());
		}
	}
	
	private Key locate(Key searchKey, BTreeNode node) {
		currentKey = node.getFirstKey();
		while(currentKey.hasNext()) {
			if(searchKey.equals(currentKey)) {
				return currentKey;
			}
			else if(searchKey.lessThan(currentKey)) {
				if(node.isLeaf()) {
					return null;
				}
				else {
					return locate(searchKey, openNode(currentKey.getLC()));
				}
			}
			else {
				currentKey = currentKey.next();
			}
		}
		// Last Key reached
		if(searchKey.equals(currentKey)) {
			return currentKey;
		}
		else if(searchKey.lessThan(currentKey)) {
			if(node.isLeaf()) {
				return null;
			}
			else {
				return locate(searchKey, openNode(currentKey.getLC()));
			}
		}
		else {
			if(node.isLeaf()) {
				return null;
			}
			else {
				return locate(searchKey, openNode(currentKey.getRC()));
			}
		}
 	}
	
	private BTreeNode openNode(int address) {
		if(cacheMode > 0 && cache.contains(address)) {
			if(debugMode > 1) {
				System.out.println("Reading Node from Cache at address: " + address);
			}
			//TODO: Test Cache Read
			BTreeNode node = (BTreeNode)cache.get(address);
			return ((node != null) ? node : disk.read(address));
		}
		else {
			if(debugMode > 1) {
				System.out.println("Reading Node from Disk at address: " + address);
			}
			return disk.read(address);
		}
	}
	
	private void saveNode(BTreeNode node) {
		if(cacheMode > 0) {
			BTreeNode bumpedNode = cacheWrite(node);
			if(bumpedNode != null) {
				diskWrite(bumpedNode);
			}
		}
		else {
			diskWrite(node);
		}
	}
	
	private void saveNodes(BTreeNode node1, BTreeNode node2, BTreeNode node3) {
		saveNode(node1);
		saveNode(node2);
		saveNode(node3);
	}
	
	private boolean cacheContains(BTreeNode node) {
		return node != null;
	}
	
	private BTreeNode cacheWrite(BTreeNode node) {
		if(debugMode > 1) {
			System.out.println("\n<| --- Writing Node to Cache --- |>\n" + node.toShortString("Cache Write"));
		}
		//TODO: Implement cache write
		return (BTreeNode)cache.addObject(node);
	}
	
	private void diskWrite(BTreeNode node) {
		if(debugMode > 1) {
			System.out.println("\n<| --- Writing Node to Disk --- |>\n" + node.toShortString("Disk Write"));
		}
		disk.write(node);
	}
	
	 public void createDumpFile() {
	        //Include this file name, tree degree, seq leng
	        File file = new File("dump");
	        BufferedWriter buff = null;
	        if(debugMode > -1) {
	        	System.out.println("Creating \"dump\" file with format: (DNA Sequence : Frequency)");
	        }
	        
	        try {
	            buff = new BufferedWriter(new FileWriter(file));
	            /*buff.write("Printing B-Tree represented by file: " + bTreeFilename + "\n");
	            buff.write("<> B-Tree degree: " + degree + "\n");
	            buff.write("<> B-Tree sequence length: " + sequenceLength + "\n");
	            buff.write("Frequency for each DNA string in this B-Tree: \n");*/
	            traverseTree(root, buff);
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally{
	            try {
	                buff.close();
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }
	    
	    private void traverseTree(BTreeNode node, BufferedWriter bwriter) throws IOException{
	        Key currentKey = node.firstKey;
	        while(currentKey.hasNext()){ //this operates on all keys EXCEPT for last key in node
	            if(!node.isLeaf())
	                traverseTree(openNode(currentKey.getLC()), bwriter);
	            bwriter.write(currentKey.getFrequency() + "\t" + currentKey.getDnaString());
	            bwriter.newLine();
	            currentKey = currentKey.getNextKey();
	        }
	        
	        if(!node.isLeaf())//Do this one more time to cover final key position
	            traverseTree(openNode(currentKey.getLC()), bwriter);
	        bwriter.write(currentKey.getFrequency() + "\t" + currentKey.getDnaString()); //write final key in node
	        bwriter.newLine();
	        if(!node.isLeaf())
	            traverseTree(openNode(currentKey.getRC()), bwriter);
//	        long numKeys = node.getNumberOfKeys();
//	        for(long i=0; i < numKeys; i++){
//	            if(!node.isLeaf())
//	                traverseTree(openNode(node.))
//	        }
	    }
	
	private static String getStatistics() {
		double numSeconds = (double)((endTime - startTime) / 1000000000.0);
		String duration = formatDuration(numSeconds);
		StringBuilder sb = new StringBuilder("\n<|-----------------  BTree Statistics  -----------------|>\n");
		sb.append("   Total # of DNA Sequences: " + totalDnaSequences + "\n");
		sb.append("   Total # of Nodes: " + totalNodes + "\n");
		sb.append("   Total # of Keys: " + Key.getTotalNumberOfKeys() + "\n\n");
		sb.append("   Total # of Disk Reads: " + disk.getNumReads() + "\n");
		sb.append("   Total Bytes Read: " + disk.getBytesRead() + "\n");
		sb.append("   Total # of Disk Writes: " + disk.getNumWrites() + "\n");
		sb.append("   Total Bytes Written: " + disk.getBytesWritten() + "\n\n");
		sb.append("   Cache Option: " + ((cacheMode > 0) ? "Enabled" : "Disabled") + "\n");
		sb.append("   BTree Build Time: " + duration);
		return sb.toString();
	}
	
	private static String formatDuration(double seconds) {
		DecimalFormat df = new DecimalFormat("#0.##");
		String hr, mn, sc;
		if(seconds < 60) {
			sc = df.format(seconds) + ((seconds == 1) ? " second" : " seconds");
			return sc;
		}
		else {
			int minutes = (int)(seconds / 60);
			seconds = seconds % 60;
			sc = df.format(seconds) + ((seconds == 1) ? " second" : " seconds");
			if(minutes < 60) {
				mn = minutes + ((minutes == 1) ? " minute, " : " minutes, ");
				return mn + sc;
			}
			else {
				int hours = minutes / 60;
				minutes = minutes % 60;
				mn = minutes + ((minutes == 1) ? " minute, " : " minutes, ");
				hr = hours + ((hours == 1) ? " hour, " : " hours, ");
				return hr + mn + sc;
			}
		}
	}
}
