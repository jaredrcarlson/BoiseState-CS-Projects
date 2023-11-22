import java.util.Comparator;

/**
 * Compares the number of kills for two Sniper objects
 *  
 * @author jcarlson
 *
 */
public class SniperCompare<T> implements Comparator<T> {
	int sniper1Kills, sniper2Kills;
	
	/**
	 * Compares number of kills for two Sniper objects
	 * 
	 * @return Returns 1 if sniper1 kills are greater than sniper2 kills
	 * 		   Returns 0 if sniper1 kills are equal to sniper2 kills
	 * 		   Returns -1 if sniper1 kills are less than or equal to sniper2 kills
	 */
	public int compare(T sniper1, T sniper2) {
		sniper1Kills = ((Sniper) sniper1).getKills();
		sniper2Kills = ((Sniper) sniper2).getKills();
		
		if(sniper1Kills > sniper2Kills) {
			return 1;
		}
		else if(sniper1Kills == sniper2Kills) {
			return 0;
		}
		else {
			return -1;
		}
	}
}
