import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


public class HuffmanTree {
	private final int NUMBER_OF_ASCII_CHARACTERS = 128;
	private final int ASCII_ZERO = 48;
	private boolean debug = false;
	private HuffmanNode root;
	private HuffmanTreePQ queue;
	private HuffmanData[] symbolFreqs;
	private BufferedReader buffRd;
	private StringBuilder strBldr;
	private HashMap<Integer,String> symToCode = new HashMap<Integer, String>(NUMBER_OF_ASCII_CHARACTERS);
	private HashMap<String,Integer> codeToSym = new HashMap<String, Integer>(NUMBER_OF_ASCII_CHARACTERS);
	
	public HuffmanTree(File source) {
		buildTree(source);
	}
	
	public HuffmanNode getRoot() {
		return root;
	}
	
	public void buildTree(File source) {
		symbolFreqs = new HuffmanData[NUMBER_OF_ASCII_CHARACTERS];
		for(int i = 0; i < NUMBER_OF_ASCII_CHARACTERS; i++) {
			symbolFreqs[i] = new HuffmanData(i,0);
		}
		try {
			initSymFreqs(source);
			displaySymFreqs();
		} catch (IOException e) {
			System.out.println("Error! - Unable to read source file.");
			e.printStackTrace();
		}
		initHTPQ();
		buildHuffmanTree();
		generateSymbolCodes();
		displaySymbolCodes();
	}
	
	public String encode(String decodedFile) throws IOException {
		strBldr = new StringBuilder();
		buffRd = new BufferedReader(new FileReader(decodedFile));
		int current;
		while((current = buffRd.read()) >=0) {
			strBldr.append(symToCode.get(current));
        }
		buffRd.close();
		return strBldr.toString();
	}
	
	public String decode(String encodedFile) throws IOException {
		strBldr = new StringBuilder();
		buffRd = new BufferedReader(new FileReader(encodedFile));
		HuffmanNode nextNode;
		int current;
		while((current = buffRd.read()) >=0) {
			if(current == ASCII_ZERO) {
				nextNode = root.gLC();
			}
			else {
				nextNode = root.gRC();
			}
			strBldr.append(next(nextNode));
        }
		buffRd.close();
		return strBldr.toString();
	}
	
	private char next(HuffmanNode node) throws IOException {
		if(node.isLeaf()) {
			return node.data().getSymbol();
		}
		else {
			int current = buffRd.read();
			if(current == ASCII_ZERO) {
				return next(node.gLC());
			}
			else {
				return next(node.gRC());
			}
		}
	}
	
	private void initSymFreqs(File source) throws IOException {
		buffRd = new BufferedReader(new FileReader(source));
		int current;
		while((current = buffRd.read()) >=0) {
			symbolFreqs[current].incrementFrequency();
        }
		buffRd.close();
	}
	
	private void displaySymFreqs() {
		System.out.println("\n---------- Symbol Frequencies ----------");
		for(int i=0; i < NUMBER_OF_ASCII_CHARACTERS; i++) {
			System.out.println(" [" + displaySymbol(i) + "]\t: " + symbolFreqs[i].freq());
		}
	}
	
	public void displaySymbolCodes() {
		System.out.println("\n---------- Symbol Codes ----------");
		for(int i=0; i < NUMBER_OF_ASCII_CHARACTERS; i++) {
			System.out.println(" [" + displaySymbol(i) + "]\t: " + symToCode.get(i));
		}
	}
	
	private void initHTPQ() {
		queue = new HuffmanTreePQ();
		for(HuffmanData d : symbolFreqs) {
			queue.enQueue(new HuffmanNode(d));
		}
	}
	
	private void buildHuffmanTree() {
		while(queue.size() > 1) {
			HuffmanNode hN1 = queue.deQueue();
			HuffmanNode hN2 = queue.deQueue();
			if(debug) {
				System.out.println("hN1: " + hN1.toString());
				System.out.println("hN2: " + hN2.toString());
			}
			int sumOfFreqs = hN1.data().freq() + hN2.data().freq();
			HuffmanNode iNode = new HuffmanNode(new HuffmanData(300,sumOfFreqs));
			hN1.setParent(iNode);
			hN2.setParent(iNode);
			iNode.setLeftChild(hN1);
			iNode.setRightChild(hN2);
			iNode.setCode("");
			if(debug) {
				System.out.println("iNode: " + iNode.toString());
			}
			if(debug) {
				System.out.println("Queue Size: " + queue.size());
			}
			queue.enQueue(iNode);
		}
		root = queue.deQueue();
	}
	
	private void generateSymbolCodes() {
		root.setCode("");
		trace(root);
		for(int i=0; i < NUMBER_OF_ASCII_CHARACTERS; i++) {
			codeToSym.put(symToCode.get(i),i);
		}
	}
	
	private void trace(HuffmanNode node) {
		String pathCode = node.getCode();
		if(node.gLC() != null) {
			node.gLC().setCode(pathCode + "0");
			trace(node.gLC());
		}
		if(node.gRC() != null) {
			node.gRC().setCode(pathCode + "1");
			trace(node.gRC());
		}
		if(node.gLC() == null && node.gRC() == null) {
			symToCode.put(node.data().getSymbolIndex(),pathCode);
		}
	}
	
	private String displaySymbol(int i) {
		String s;
		switch(i) {
			case 0: s = "NUL";
			break;
			case 1: s = "SOH";
			break;
			case 2: s = "STX";
			break;
			case 3: s = "ETX";
			break;
			case 4: s = "EOT";
			break;
			case 5: s = "ENQ";
			break;
			case 6: s = "ACK";
			break;
			case 7: s = "BEL";
			break;
			case 8: s = "BS";
		    break;
			case 9: s = "TAB";
		    break;
			case 10: s = "LF";
		    break;
			case 11: s = "VT";
		    break;
			case 12: s = "FF";
		    break;
			case 13: s = "CR";
		    break;
			case 14: s = "SO";
		    break;
			case 15: s = "SI";
		    break;
			case 16: s = "DLE";
		    break;
			case 17: s = "DC1";
		    break;
			case 18: s = "DC2";
		    break;
			case 19: s = "DC3";
		    break;
			case 20: s = "DC4";
		    break;
			case 21: s = "NAK";
		    break;
			case 22: s = "SYN";
		    break;
			case 23: s = "ETB";
		    break;
			case 24: s = "CAN";
		    break;
			case 25: s = "EM";
		    break;
			case 26: s = "SUB";
		    break;
			case 27: s = "ESC";
		    break;
			case 28: s = "FS";
		    break;
			case 29: s = "GS";
		    break;
			case 30: s = "RS";
		    break;
			case 31: s = "US";
		    break;
			case 32: s = "Space";
		    break;
			case 127: s = "DEL";
			break;
			default: s = (char)i + "";	
		}
		return s;
	}
}
