import java.io.File;
import java.util.Arrays;

/**
 * Simple unit tester for the FleschStatistics class.
 * @author amit
 *
 */
public class FleschStatisticsTest
{
	private final static int PRECISION = 2; //number of digits after floating point to match

	/**
	 * Compares two doubles to see if they are equal to within the given precision
	 * @param x
	 * @param y
	 * @param precision number of digits after the decimal point to use in testing equality
	 * @return
	 */
	private static boolean approxEquals(double x, double y, int precision) {
		final double EPSILON = Math.pow(10, -precision);
		if (Math.abs(x - y) < EPSILON) 
			return true;
		else
			return false;
	}

	/**
	 * Test given FleschStatistics object with given expected results.
	 * @param stats  The FleschStatistics object to test
	 * @param numWords number of words
	 * @param numSyllables number of syllables
	 * @param numSentences number of sentences
	 * @param wordFreq  array of word frequencies [0..23]
	 * @param syllFreq  array of syllable frequencies [0..15]
	 * @param fleschIndex text fleschIndex
	 */
	private static void test(FleschStatisticsInterface stats, 
			int numWords, 
			int numSyllables, 
			int numSentences, 
			int[] wordFreq, 
			int[] syllFreq, 
			double fleschIndex)
	{

		if (stats.getWordCount() == numWords) {
			System.out.println("Passed! getWordCount()");
		} else {
			System.out.println("----> Failed ! getWordCount()  correct: " + numWords + " generated: " + stats.getWordCount());
		}
		if (stats.getSyllableCount() == numSyllables) {
			System.out.println("Passed! getSyllableCount()");
		} else {
			System.out.println("----> Failed ! getSyllableCount()  correct: " + numSyllables + " generated: " + stats.getSyllableCount());
		}
		if (stats.getSentenceCount() == numSentences) {
			System.out.println("Passed! getSentenceCount()");
		} else {
			System.out.println("----> Failed ! getSentenceCount()  correct: " + numSentences + " generated: " + stats.getSentenceCount());
		}
		int [] testWordFreq = stats.getWordLengthCount();
		if (Arrays.equals(testWordFreq, wordFreq)) {
			System.out.println("Passed! Word Arrays frequencies");
		} else {
			System.out.println("\n----> Failed ! Word Arrays frequencies\n\n" +  
					"   correct: " + Arrays.toString(wordFreq) + "\n" +
					" generated: " + Arrays.toString(testWordFreq) + "\n");
		}
		int [] testSyllFreq = stats.getSyllablePerWordCount();
		if (Arrays.equals(testSyllFreq, syllFreq)) {
			System.out.println("Passed! Syllable Arrays frequencies");
		} else {
			System.out.println("\n----> Failed ! Syllable Arrays frequencies\n\n" +  
					"   correct: " + Arrays.toString(syllFreq) + "\n" +
					" generated: " + Arrays.toString(testSyllFreq) + "\n");
		}
		if (approxEquals(stats.getFleschIndex(), fleschIndex, PRECISION)) {
			System.out.println("Passed! getFleschIndex()");
		} else {
			System.out.printf("----> Failed ! getFleschIndex()  correct: %.2f generated: %.2f\n", fleschIndex, stats.getFleschIndex());
		}

		System.out.println();
	}

	/**
	 * Test over a list of predefined files.
	 * @param args
	 */
	public static void main(String[] args)
	{
		// expected results
		String [] textfile = {"testfile.txt", "etext/APoemForParsing.txt", "etext/Gettysburg-Address.txt", "etext/Alice-in-Wonderland.txt", "etext/MoreText.txt"};
		int[][] wordFreq = {{0, 3, 13, 24, 13, 10, 2, 5, 3, 1, 3, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 14, 22, 28, 34, 14, 11, 13, 5, 2, 2, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 8, 50, 55, 61, 35, 27, 17, 7, 10, 6, 4, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 1549, 4387, 6950, 5884, 3489, 1989, 1567, 745, 447, 189, 108, 36, 12, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 2, 20, 16, 5, 2, 11, 11, 9, 11, 8, 6, 3, 3, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0}
		};
		int[][] syllFreq = {{0, 63, 8, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 110, 27, 8, 3, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 202, 49, 21, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 21246, 4749, 1077, 245, 38, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 47, 18, 21, 18, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
		};
		int[] numWords = {79, 149, 281, 27357, 110};
		int[] numSentences = {8, 20, 44, 2055, 2};
		int[] numSyllables = {103, 205, 399, 35157, 248};
		double[] fleschIndex = {86.51, 82.88, 80.23, 84.60, -39.72};

		for (int i = 0; i < textfile.length; i++) {
			File nextFile = new File(textfile[i]);
			if (nextFile.exists() && nextFile.canRead()) {
				System.out.println("\nTesting on data file:" + textfile[i] + "\n");
				FleschStatisticsInterface stats = new FleschStatistics(nextFile);
				test(stats, numWords[i], numSyllables[i], numSentences[i], 
						wordFreq[i], syllFreq[i], fleschIndex[i]);
			} else {
				System.err.println("Cannot access test file: " + textfile[i]);
			}
		}
	}
}