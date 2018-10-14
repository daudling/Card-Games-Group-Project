import java.util.ArrayList;

public class Player {
	
	public ArrayList<Cards> hand;
	public int playerNum, wins, losses, difficulty;
	
	public Player(int player, ArrayList<Cards> cards) {
		playerNum = player;
		hand = cards;
		wins = 0;
		losses = 0;
		difficulty = 0;
	}
}
