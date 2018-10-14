import java.util.ArrayList;

public class PlayerList {
	
	private static PlayerList instance = null;
	public ArrayList<Player> players;
	
	private PlayerList() {
		players = new ArrayList<Player>();
	}
	
	public static PlayerList getInstance() {
		if(instance == null) {
			instance = new PlayerList();
		}
		return instance;
	}
	
	public void addPlayer(Player newPlayer) {
		players.add(newPlayer);
	}
	
	public void removePlayer(int playerNum) {
		for(Player x : players) {
			if(x.playerNum == playerNum) {
				players.remove(x);
			}
		}
	}
	
	public void addWin(int playerNum) {
		for(Player x : players) {
			if(x.playerNum == playerNum) {
				x.wins ++;
			}
		}
	}
	
	public void addLoss(int playerNum) {
		for(Player x : players) {
			if(x.playerNum == playerNum) {
				x.losses ++;
			}
		}
	}
}
