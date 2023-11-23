import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

/**
 * Provides HashObjects for insertion into the HashTable
 * The following data sources are available:
 * Random Number Integer values
 * System Time Long values
 * Text File String values
 * 
 * @author jcarlson
 *
 */
public class HashObjectSource {
	private final String FILE_NAME = "word-list";
	
	private Random randNum;
	private Scanner textFile;
	private File file;
	
	public HashObjectSource() {
		randNum = new Random();
		file = new File(FILE_NAME);
		try {
			textFile = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("Error: Unable to open input text file!");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public int nextRandom() {
		return randNum.nextInt();
	}
	
	public long nextSystemTime() {
		return System.currentTimeMillis();	
	}
	
	public String nextWord() {
		return textFile.next();
	}
 
}
