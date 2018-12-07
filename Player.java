
import java.util.Scanner;
import java.util.ArrayList;

public class Player {
	
	public ArrayList<Cards> hand;
	public int playerNum, wins, losses, difficulty, team, money;
	
	/**
	 * Constructor
	 */
	public Player() {
		playerNum = PlayerList.players.size() + 1;
		hand = new ArrayList<Cards>();
		wins = 0;
		losses = 0;
		difficulty = 0;
		team = 0;
		money = 0;
	}
	public Player(int player) {
		playerNum = player;
		hand = new ArrayList<Cards>();
	}
	
	/**
	 * Removes selected card from players hand
	 * @param card - card selected
	 */
	public void removeCard(Cards card) {
		hand.remove(card);
	}
	
	public void removeCard() {
		
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
		boolean cardValid = false;
		System.out.println("Current Cards in hand");
		for(Cards c : hand) {
			System.out.print(c.rank + " of " + c.suit + " ");
		}
		System.out.println();
		Scanner sc = new Scanner(System.in);
		int card = -1;
		System.out.println("Choose which card to play");
		while(cardValid == false) {
			card = sc.nextInt();
			if(card < 0 || card > 4) {
				System.out.println("Choose card 0-4");
			}
			else {
				cardValid = true;
			}
		}
		return hand.get(card);
	}
	
	public boolean pickUp() {
		return true;
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
