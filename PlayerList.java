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
		}
		return instance;
	}
	
	/**
	 * Adds new player to player list
	 * @param newPlayer - player to add
	 */
	public void addPlayer(Player newPlayer) {
		if(players.size() == 4) {
			return;
		}
		players.add(newPlayer);
	}
	
	/**
	 * Removes existing player from player list
	 * @param playerNum - player to remove
	 */
	public void removePlayer(int playerNum) {
		for(Player x : players) {
			if(x.playerNum == playerNum) {
				players.remove(x);
			}
		}
	}
}
