

import java.util.ArrayList;

public class PlayerList {
	
	private static PlayerList instance = null;
	public static ArrayList<Player> players;
	
	/**
	 * Default Constructor
	 */
	private PlayerList() {
		players = new ArrayList<Player>();
	}

	/**
	 * Checks to see if player list already exists
	 * @return - instance value of player list
	 */
	public static PlayerList getInstance() {
		if(instance == null) {
			instance = new PlayerList();
			for(int i = 0; i < 4; i++) {
				addPlayer();
			}
		}
		return instance;
	}
	
	/**
	 * Adds new player to player list
	 * @param newPlayer - player to add
	 */
	public static void addPlayer() {
		if(players.size() == 4) {
			return;
		}
		players.add(new Player());
	}
	
	/**
	 * Removes existing player from player list
	 * @param playerNum - player to remove
	 */
	public static void removePlayer(int playerNum) {
		players.remove(playerNum);
	}
}
