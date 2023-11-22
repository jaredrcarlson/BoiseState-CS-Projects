/**
 * Interface to calculate Flesch statistics from a text file.
 * Used in the testing program.
 *
 * @author gandersen
 */
public interface FleschStatisticsInterface
{
	/**
	 * @return the number of words in the text file
	 */
	public int getWordCount();

	/**
	 * @return the number of syllables in the text file
	 */
	public int getSyllableCount();

	/**
	 * @return the number of sentences in the text file
	 */
	public int getSentenceCount();

	/**
	 *@return the wordLengthCount array with locations [0]..[23] with location [i] 
	 * storing the number of words of length i in the text file. Location [0] is not used.
	 * Location [23] holds the count of words of length 23 and higher.
	 */
	public int[] getWordLengthCount();

	/**
	 *@return the syllableCount array with locations [0]..[15] with location [i] 
	 * storing the number words with a particular syllable count in the text file. 
	 * Location [0] is not used.
	 * Location [15] holds the count of words with syllables count greater than 15.
	 */
	public int[] getSyllablePerWordCount();

	/**
	 * @return the calculated Flesch index
	 */
	public double getFleschIndex();

	/**
	 * @return the Flesch grade level associated with the Flesch index
	 */
	public String getFleschGrade();

}