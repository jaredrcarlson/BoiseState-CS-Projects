/**
 * The sniper class is used to store name and kills for military snipers.
 * 
 * @author jcarlson
 *
 */
public class Sniper {
	private String name;
	private int kills;
	
	/**
	 * Constructor creates new sniper object with supplied name and number of kills
	 * 
	 * @param name The name of the sniper
	 * @param kills The initial number of kills this sniper has accrued
	 */
	public Sniper(String name, int kills) {
		this.name = name;
		this.kills = kills;
	}
	
	/**
	 * Gets the name of this sniper
	 * 
	 * @return Returns the name of this sniper
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the number of kills accrued by this sniper
	 * 
	 * @return Returns the number of kills accrued by this sniper
	 */
	public int getKills() {
		return kills;
	}
	
	/**
	 * Method used to add a kill to this sniper's record
	 */
	public void addKill() {
		kills++;
	}
}
