import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class HuffmanTest {
	private static final int ENCODE = 1;
	private static final int DECODE = 2;
	private static final String ENCODE_MODE = "-encode";
	private static final String DECODE_MODE = "-decode";
	
	private static int mode;
	private static File sourceFile;
	private static String encodedFile;
	private static String decodedFile;
	private static HuffmanTree hTree;
	
	public static void main(String[] args) {
		processCLA(args);
		File outputFile = new File(args[3]);
		if(mode == ENCODE) {
			sourceFile = new File(args[1]);
			decodedFile = args[2];
			hTree = new HuffmanTree(sourceFile);
			try {
				encodedFile = hTree.encode(decodedFile);
				FileOutputStream fOS = new FileOutputStream(outputFile);
				if (!outputFile.exists()) {
					outputFile.createNewFile();
				}
				byte[] fileInBytes = encodedFile.getBytes();
				fOS.write(fileInBytes);
				fOS.flush();
				fOS.close();
			} catch (IOException e) {
				System.out.println("Error! - Unable to read decoded file (arg 2)");
				e.printStackTrace();
			}
		}
		else if(mode == DECODE) {
			sourceFile = new File(args[1]);
			encodedFile = args[2];
			hTree = new HuffmanTree(sourceFile);
			try {
				decodedFile = hTree.decode(encodedFile);
				FileOutputStream fOS = new FileOutputStream(outputFile);
				if (!outputFile.exists()) {
					outputFile.createNewFile();
				}
				byte[] fileInBytes = decodedFile.getBytes();
				fOS.write(fileInBytes);
				fOS.flush();
				fOS.close();
			} catch (IOException e) {
				System.out.println("Error! - Unable to read encoded file (arg 2)");
				e.printStackTrace();
			}
		}
		
	}
	
	private static void processCLA(String[] args) {
		int numOfArgs = args.length;
		if(numOfArgs != 4) {
			System.out.println("Error! - Invalid number of arguments.");
			displayProgramUsage();
			System.exit(1);
		}
		if(args[0].equals(ENCODE_MODE)) {
			mode = ENCODE;
		}
		else if(args[0].equals(DECODE_MODE)) {
			mode = DECODE;
		}
		else {
			System.out.println("Error! - Invalid Mode.");
			displayProgramUsage();
			System.exit(1);
		}
	}
	
	private static void displayProgramUsage() {
		System.out.println("\n---------- Program Usage Instructions ----------");
		System.out.println("java HuffmanTest <Mode> <Base Filename> <Input Filename> <Output Filename>");
		System.out.println("\nMode: -encode : Encodes <Input Filename> and saves encoded file as <Output Filename>");
		System.out.println("      -decode : Decodes <Input Filename> and saves decoded file as <Output Filename>");
		System.out.println("\nBase Filename: Specifies the file to use for symbol frequency counts to build Huffman Tree");
	}

}
