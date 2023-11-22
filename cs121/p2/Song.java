import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


/**
 * Represents information about a song.
 * @author amit
 *
 */
public class Song
{
	private String title;
	private String artist;
	private int playTime; // in seconds
	private String fileName;
	
	/**
	 * Constructor: build a song using the given parameters.
	 * @param title song's title
	 * @param artist song's artist
	 * @param playTime song's length in seconds
	 * @param fileName song file to load
	 */
	public Song(String title, String artist, int playTime, String fileName)
	{
		this.title = title;
		this.artist = artist;
		this.playTime = playTime;
		this.fileName = fileName;
	}

	/**
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @return the artist
	 */
	public String getArtist()
	{
		return artist;
	}

	/**
	 * @return the playTime
	 */
	public int getPlayTime()
	{
		return playTime;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName()
	{
		return fileName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return  String.format("%-20s %-20s %-25s %10s",title, artist, fileName, playTime);
	}
	
	/**
	 * Extra Credit Play Method: Plays the song while displaying song info
	 * @param : none
	 * @return : void
	 * @author jcarlson
	 * 
	 **/
	public void play() throws InterruptedException, IOException
	{
		try {
			File soundFile = new File(this.getFileName()).getAbsoluteFile();
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.start();
	        
	    } catch(Exception e) {
	        System.out.println("PlayMusic: Error with playing sound:" + e);
	        e.printStackTrace();
	    }
	    
		int finalMinute = this.getPlayTime() / 60;
		int finalSeconds = this.getPlayTime() % 60;
		String youAreListeningTo = "\n-  NOW PLAYING ----> " + this.getTitle() + " by " + this.getArtist() + "  -";
				
		System.out.println("");
		for (int i=1; i<=youAreListeningTo.length() - 1; i++) { System.out.print("-"); } //Top Border
		System.out.println(youAreListeningTo);
		for (int i=1; i<=youAreListeningTo.length() - 1; i++) { System.out.print("-"); } //Bottom Border
		System.out.println("");
	    DecimalFormat nowPlayingFormat = new DecimalFormat("00");
		
	    for (int minute = 0; minute <= finalMinute; minute++) {
	        if(minute < finalMinute) {
	        	for (int second = 0; second < 60; second++) {
	        		System.out.print("\r-  " + nowPlayingFormat.format(minute) + ":" + nowPlayingFormat.format(second) + " / " + nowPlayingFormat.format(finalMinute) + ":" + nowPlayingFormat.format(finalSeconds) + "  - ");
	        		Thread.sleep(1000);
	        	}
	        }
	        else {
	        	for (int second = 0; second <= finalSeconds; second++) {
	        		System.out.print("\r-  " + nowPlayingFormat.format(minute) + ":" + nowPlayingFormat.format(second) + " / " + nowPlayingFormat.format(finalMinute) + ":" + nowPlayingFormat.format(finalSeconds) + "  - ");
	        		Thread.sleep(1000);
	        	}
			}
		}
	}
}
