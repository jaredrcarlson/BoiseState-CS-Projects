import java.util.Scanner;
import java.io.IOException;
import java.text.DecimalFormat;

/**
 * Stores 3 Song objects to create a playlist and then play the music
 * @author jcarlson
 *
 */
public class PlayList {
		
	public static void main(String[] args) throws InterruptedException, IOException {
		final int SECONDS_PER_4_MINUTES = 240;
		String title, artist, strDuration, fileName, tempStr;
		int colonIndex, minutes, seconds, duration, closestFour;
		int totalDuration = 0;
		Song track_1, track_2, track_3;
		double aveDuration;
		Scanner kbd = new Scanner(System.in);
		
		//Get song 1
		System.out.println("<--- Enter information for Song 1 --->");
		System.out.print("Title: ");
		title = kbd.nextLine();
		System.out.print("Artist: ");
		artist = kbd.nextLine();
		System.out.print("Duration (mm:ss): ");
		strDuration = kbd.nextLine();
		System.out.print("Filename:");
		fileName = kbd.nextLine();
		
		colonIndex = strDuration.indexOf(':');
		tempStr = strDuration.substring(0, colonIndex);
		minutes = Integer.parseInt(tempStr);
		tempStr = strDuration.substring(colonIndex + 1);
		seconds = Integer.parseInt(tempStr);
		duration = minutes * 60 + seconds;
		totalDuration += duration;
		
		Song song1 = new Song(title, artist, duration, fileName);
		
		//Get song 2
		System.out.println("");
		System.out.println("<--- Enter information for Song 2 --->");
		System.out.print("Title: ");
		title = kbd.nextLine();
		System.out.print("Artist: ");
		artist = kbd.nextLine();
		System.out.print("Duration (mm:ss): ");
		strDuration = kbd.nextLine();
		System.out.print("Filename:");
		fileName = kbd.nextLine();
		
		colonIndex = strDuration.indexOf(':');
		tempStr = strDuration.substring(0, colonIndex);
		minutes = Integer.parseInt(tempStr);
		tempStr = strDuration.substring(colonIndex + 1);
		seconds = Integer.parseInt(tempStr);
		duration = minutes * 60 + seconds;
		totalDuration += duration;
		
		Song song2 = new Song(title, artist, duration, fileName);
		
		//Get song 3
		System.out.println("");
		System.out.println("<--- Enter information for Song 3 --->");
		System.out.print("Title: ");
		title = kbd.nextLine();
		System.out.print("Artist: ");
		artist = kbd.nextLine();
		System.out.print("Duration (mm:ss): ");
		strDuration = kbd.nextLine();
		System.out.print("Filename:");
		fileName = kbd.nextLine();
		
		colonIndex = strDuration.indexOf(':');
		tempStr = strDuration.substring(0, colonIndex);
		minutes = Integer.parseInt(tempStr);
		tempStr = strDuration.substring(colonIndex + 1);
		seconds = Integer.parseInt(tempStr);
		duration = minutes * 60 + seconds;
		totalDuration += duration;
		
		Song song3 = new Song(title, artist, duration, fileName);
				
		kbd.close();
		
		//Average Duration
		aveDuration = totalDuration / 3.0;
		DecimalFormat decFormat = new DecimalFormat("0.00");
		System.out.println("");
		System.out.println("\nThe average play time for your songs is: " + decFormat.format(aveDuration) + " seconds.");
		
		//Closest to 4 minutes
		closestFour = 1;
		seconds = Math.abs(SECONDS_PER_4_MINUTES - song1.getPlayTime());
		if (seconds > Math.abs(SECONDS_PER_4_MINUTES - song2.getPlayTime())) {
			closestFour = 2;
			seconds = Math.abs(SECONDS_PER_4_MINUTES - song2.getPlayTime());
		}
		if (seconds > Math.abs(SECONDS_PER_4_MINUTES - song3.getPlayTime())) {
			closestFour = 3;
			seconds = Math.abs(SECONDS_PER_4_MINUTES - song3.getPlayTime());
		}
		
		System.out.print("\nThe song closest in play time to 4 minutes is: ");
		switch (closestFour) {
			case 1:
				System.out.println(song1.getTitle() + ", by " + song1.getArtist() + ".");
				break;
			case 2:
				System.out.println(song2.getTitle() + ", by " + song2.getArtist() + ".");
				break;
			case 3:
				System.out.println(song3.getTitle() + ", by " + song3.getArtist() + ".");
				break;
		}
		
		//Create Playlist (shortest to longest)
		for (int i=1; i<=78; i++) { System.out.print("="); } // Top Border
		System.out.println("");
		System.out.printf("%-20s %-20s %-25s %10s %n","Title","Artist","Filename","Duration");
		for (int i=1; i<=78; i++) { System.out.print("="); } // Middle Border
		System.out.println("");
		
		//Sort and list Songs by Duration
		if (song1.getPlayTime() < song2.getPlayTime() && song1.getPlayTime() < song3.getPlayTime()) {
			track_1 = song1; // Song 1 is shortest
			if (song2.getPlayTime() < song3.getPlayTime()) {
				track_2 = song2; // Song 2 is middle
				track_3 = song3; // Song 3 is longest
			}
			else {
				track_2 = song3; // Song 3 is middle
				track_3 = song2; // Song 2 is longest
			}
		}
		else if (song2.getPlayTime() < song3.getPlayTime()) {
			track_1 = song2; // Song 2 is shortest
			if (song1.getPlayTime() < song3.getPlayTime()) {
				track_2 = song1; // Song 1 is middle
				track_3 = song3; // Song 3 is longest
			}
			else {
				track_2 = song3; // Song 3 is middle
				track_3 = song1; // Song 1 is longest
			}
		}
		else {
			track_1 = song3; // Song 3 is shortest
			if (song1.getPlayTime() < song2.getPlayTime()) {
				track_2 = song1; // Song 1 is middle
				track_3 = song2; // Song 2 is longest
			}
			else {
				track_2 = song2; // Song 2 is middle
				track_3 = song1; // Song 1 is longest
			}
		}
		System.out.println(track_1.toString());
		System.out.println(track_2.toString());
		System.out.println(track_3.toString());
		for (int i=1; i<=78; i++) { System.out.print("="); } // Bottom Border
		
		//Extra Credit
		track_1.play();
		track_2.play();
		track_3.play();
	    
        System.out.println("\n\nYour PlayList is complete. -- Good-Bye!\n\n");    
		
	}
		
}
