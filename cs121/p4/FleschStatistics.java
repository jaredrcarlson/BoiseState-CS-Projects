import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * Class to calculate Flesch statistics from a text file.
 *
 * @author jcarlson
 */
public class FleschStatistics implements FleschStatisticsInterface {
	
	// The following fields will be used by all objects and will not change.
	// For these reasons, they are static and initialized at compile time. (Before any objects are created)
	private static final int maxWordLength = 24;
	private static final int maxSyllablePerWord = 16;
	private static final String tokenDelims = " , .;:'\"&!?-_\n\t12345678910[]{}()@#$%^*/+-";
	private static final String[] fleschGradeChart;
	static {
		fleschGradeChart = new String[9];
		fleschGradeChart[0] = "Law school graduate";
		fleschGradeChart[1] = "College graduate";
		fleschGradeChart[2] = "College student";
		fleschGradeChart[3] = "High school student";
		fleschGradeChart[4] = "9th grader";
		fleschGradeChart[5] = "8th grader";
		fleschGradeChart[6] = "7th grader";
		fleschGradeChart[7] = "6th grader";
		fleschGradeChart[8] = "5th grader";
	}
	
	// Instance variables to store file statistics
	private int wordCount;
	private int syllableCount;
	private int sentenceCount;
	private int[] wordLengthCount;
	private int[] syllablePerWordCount;
	private double fleschIndex;
	private String fleschGrade;
	private String fileName;
	private String longestWord;
	private ArrayList<String> longestWordLineNumbers;
	
	/**
	 * Constructor - Initializes all variables, and performs all necessary calculations to create readability report
	 *
	 * @param file text file to be processed
	 */
	public FleschStatistics(File file) {
		// Initialize Instance Variables
		wordCount = 0;
		syllableCount = 0;
		sentenceCount = 0;
		wordLengthCount = new int[maxWordLength];
		initArray(wordLengthCount, maxWordLength);
		syllablePerWordCount = new int[maxSyllablePerWord];
		initArray(syllablePerWordCount, maxSyllablePerWord);
		longestWord = "";
		longestWordLineNumbers = new ArrayList<String>(100);
		
		
		// Process text file if no exception occurs
		try {
			Scanner lineScan = new Scanner(file);
			fileName = file.getName(); // Store name of text file for display purposes
			String line; // holds current line of text file
			StringTokenizer tokenScan;
			String token; // holds current token from line
			int currentLine = 0; // keeps track of line numbers
			
			// Process each token on the line
			while(lineScan.hasNext()) {
				line = lineScan.nextLine(); // Stores current line
				currentLine++;
				tokenScan = new StringTokenizer(line, tokenDelims); // line parser
				
				// Count number of words (tokens) for current line and add them to total count
				wordCount += tokenScan.countTokens();
				
				// Step through every character of line and increment total sentence count for each "sentence-ending" character
				for(int i=0; i < line.length(); i++) {
					if(line.charAt(i) == '.' || line.charAt(i) == ':' || line.charAt(i) == ';' || line.charAt(i) == '?' || line.charAt(i) == '!') {
						sentenceCount++;
					}
				}
				
				// Process each token found in current line
				while(tokenScan.hasMoreTokens()) {
					token = tokenScan.nextToken(); // Stores next token
					int wordLength = token.length(); // Stores current word's length
					wordLengthCount[wordLength]++; // Increment the total number for words of this length
										
					// Determine if current word is the longest in the file
					if(wordLength > longestWord.length()) {
						longestWord = token;
						longestWordLineNumbers.clear();
						String tmp = new Integer(currentLine).toString();
						longestWordLineNumbers.add(tmp);
					}
					// Check if current word is same as longest word
					else if(token.equalsIgnoreCase(longestWord)) {
						String tmp = new Integer(currentLine).toString();
						longestWordLineNumbers.add(tmp);
					}
					
					// Count syllables
					int i = 0; // Start at index 0
					int syllables = 0;
					boolean hasVowel = false;
					boolean lastChar = false;
					// Step through each character in search of vowels
					while(i < wordLength) {
						if(i == wordLength - 1) {
							lastChar = true; // Flag! - Last character has been reached
						}
						
						// Check if character is a vowel -AND- not at end of word -OR- if character is a vowel (excluding 'e') at end of word  
						if( (isVowel(token.charAt(i)) && !lastChar) || ((lastChar && isVowelNotE(token.charAt(i))))) {
							hasVowel = true; // This word has at least one vowel
							syllables++; // First vowel found for any block of vowels
							
							// Skip past consecutive vowels while last character has not been reached
							while(i < wordLength && isVowel(token.charAt(i))) {
								i++; // Move to next character 
							}
						}
						// Current character is either not a vowel or is the last character of the word 
						else {
							i++; // Move to next character
						}
					}
					
					// Make sure words that do not contain vowels still count as having one syllable
					if(!hasVowel) {
						syllables++;
					}
					
					// Add syllables of current word to total syllable count
					syllableCount += syllables;
					
					// Increment the total number of words that have this number of syllables
					syllablePerWordCount[syllables]++;
				}
			}
			
			// Close Scanner
			lineScan.close();
			
			// Set Flesch index and grade
			setFleschIndex();
			setFleschGrade();
		
		// Catch and display exception error
		} catch (FileNotFoundException e) {
			System.out.print("Error: " + e.getMessage());
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * @return the number of words in the text file
	 */
	public int getWordCount() {
		return wordCount;
	}

	/**
	 * @return the number of syllables in the text file
	 */
	public int getSyllableCount() {
		return syllableCount;
	}

	/**
	 * @return the number of sentences in the text file
	 */
	public int getSentenceCount() {
		return sentenceCount;
	}

	/**
	 *@return the wordLengthCount array with locations [0]..[23] with location [i] 
	 * storing the number of words of length i in the text file. Location [0] is not used.
	 * Location [23] holds the count of words of length 23 and higher.
	 */
	public int[] getWordLengthCount() {
		return wordLengthCount;
	}

	/**
	 *@return the syllableCount array with locations [0]..[15] with location [i] 
	 * storing the number words with a particular syllable count in the text file. 
	 * Location [0] is not used.
	 * Location [15] holds the count of words with syllables count greater than 15.
	 */
	public int[] getSyllablePerWordCount() {
		return syllablePerWordCount;
	}

	/**
	 * @return the calculated Flesch index
	 */
	public double getFleschIndex() {
		return fleschIndex;
	}

	/**
	 * @return the Flesch grade level associated with the Flesch index
	 */
	public String getFleschGrade() {
		return fleschGrade;
	}
	
	/**
	 * @return a display-friendly result of the Flesch Stats including grade level
	 */
	public String toString() {
		// Concatenate display string
		String output = "==========================================================\n";
		output += "Flesch Statistics for " + fileName;
		output += "\n==========================================================\n";
		output += getWordCount() + " words\n" + getSentenceCount() + " sentences\n" + getSyllableCount() + " syllables\n";
		output += "------------------------------";
		
		// Concatenate all syllable per word counts that are greater than zero
		output += "\nSyllable Count:";
		output += String.format("\n%10s%12s", "Count", "Frequency");
		output += String.format("\n%10s%12s", "-----", "---------");
		for(int i=0; i < syllablePerWordCount.length; i++) {
			if(syllablePerWordCount[i] > 0) {
				output += String.format("\n%8s%10s", i,syllablePerWordCount[i]);
			}
		}
		output += "\n------------------------------";
		
		// Concatenate all word length counts that are greater than zero
		output += "\nWord Lengths:";
		output += String.format("\n%10s%12s", "Length", "Frequency");
		output += String.format("\n%10s%12s", "------", "---------");
		for(int i=0; i < wordLengthCount.length; i++) {
			if(wordLengthCount[i] > 0) {
				output += String.format("\n%8s%10s", i, wordLengthCount[i]);
			}
		}
		output += "\n------------------------------";
		
		// Concatenate Flesch Index and Grade Results
		DecimalFormat decFormat = new DecimalFormat("0.00");
		output += "\nFlesch Index: ";
		output += decFormat.format(getFleschIndex());
		output += "\nFlesch Grade: " + getFleschGrade();
		output += "\nLongest Word: " + longestWord;
		output += "\nLongest Word appeared on line(s): ";
		for(String s : longestWordLineNumbers) {
			output += s + " ";
		}
		
		// Return completed display string
		return output;
	}
	
	/**
	 * @param array the integer array needing to be initialized
	 * @param size the size of the array passed in
	 */
	private void initArray(int[] array, int size) {
		for(int i=0; i < size; i++) {
			array[i] = 0;
		}
	}
	
	/**
	 * @param ch the character to be checked for vowel status
	 * @return true if the character passed in is a vowel
	 */
	private boolean isVowel(char ch) {
		char c = Character.toLowerCase(ch);
		if(c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'y') {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * @param ch the character to be checked for vowel status, excluding 'e'
	 * @return true if the character passed in is a vowel, but not the vowel 'e'
	 */
	private boolean isVowelNotE(char ch) {
		char c = Character.toLowerCase(ch);
		if(c == 'a' || c == 'i' || c == 'o' || c == 'u' || c == 'y') {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Performs Flesch formula calcuation to set the Flesch index value
	 */
	private void setFleschIndex() {
		fleschIndex = 206.835 - (1.015 * (wordCount / (double)sentenceCount)) - (84.6 * (syllableCount / (double)wordCount));
	}
	
	/**
	 * Checks the Flesch index against the Flesch Grade Chart to set the Flesch Grade value
	 */
	private void setFleschGrade() {
		if(fleschIndex > 90) {
			fleschGrade = new String(fleschGradeChart[8]);
		}
		else if(fleschIndex > 80) {
			fleschGrade = new String(fleschGradeChart[7]);
		}
		else if(fleschIndex > 70) {
			fleschGrade = new String(fleschGradeChart[6]);
		}
		else if(fleschIndex > 65) {
			fleschGrade = new String(fleschGradeChart[5]);
		}
		else if(fleschIndex > 60) {
			fleschGrade = new String(fleschGradeChart[4]);
		}
		else if(fleschIndex > 50) {
			fleschGrade = new String(fleschGradeChart[3]);
		}
		else if(fleschIndex > 30) {
			fleschGrade = new String(fleschGradeChart[2]);
		}
		else if(fleschIndex >= 0) {
			fleschGrade = new String(fleschGradeChart[1]);
		}
		else {
			fleschGrade = new String(fleschGradeChart[0]);
		}
	}
	
	

}
