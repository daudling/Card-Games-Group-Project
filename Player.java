import java.util.ArrayList;

public class Player {
	
	private ArrayList<Cards> hand;
	public int playerNum, wins, losses, difficulty, team;
	
	/**
	 * Constructor
	 * @param player - Player number
	 */
	public Player(int player) {
		playerNum = player;
		hand = null;
		wins = 0;
		losses = 0;
		difficulty = 0;
		team = 0;
	}
	
	/**
	 * Removes selected card from players hand
	 * @param card - card selected
	 */
	public void removeCard(Cards card) {
		for(Cards x : hand) {
			if(x.rank == card.rank && x.suit == card.suit) {
				hand.remove(x);
			}
		}
	}
	
	/**
	 * Adds random card to users hand to simulate drawing a card
	 */
	public void addRandomCard() {
		hand.add(Deck.pickRandom());
	}
	
	/**
	 * Adds selected amount of cards to create a users hand
	 * @param quantity - amount of cards to draw
	 */
	public void addHand(int quantity) {
		hand = Deck.createHand(quantity);
	}
	
	/**
	 * Prompts user to select card from hand
	 * @return - Card selected by user
	 */
	public Cards chooseCard() {
		Cards temp;
		temp = hand.get(1);
		return temp;
	}
}