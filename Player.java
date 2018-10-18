

import java.util.ArrayList;

public class Player {
	
	public ArrayList<Cards> hand;
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
	
	public boolean pickUp() {
		return false;
	}
	
	public void displayHand(){
		for(int i = 0; i < hand.size(); i++){
			switch(hand.get(i).rank){
			case 11: System.out.print("Jack " + hand.get(i).suit + "|");
			break;
			case 12: System.out.print("Queen " + hand.get(i).suit + "|");
			break;
			case 13: System.out.print("King " + hand.get(i).suit + "|");
			break;
			case 14: System.out.print("Ace " + hand.get(i).suit + "|");
			break;
			default: System.out.print(hand.get(i).rank + " " + hand.get(i).suit + "|");
			}
		}
		System.out.println("");
	}
	
	public ArrayList<Cards> getHand() {
		return hand;
	}
}