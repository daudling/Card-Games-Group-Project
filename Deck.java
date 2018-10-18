

import java.util.ArrayList;

public class Deck {
	
	private static Cards[][] deck = new Cards[4][13];
	private static Deck instance = null;
	
	/**
	 * Populates deck with 52 Cards objects, one for each card.
	 * Sets quantity of each object to 0.
	 */
	private Deck() {
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 13; j++) {
				switch(i) {
				case 0: deck[i][j] = new Cards("Spades", j + 2);
				break;
				case 1: deck[i][j] = new Cards("Clubs", j + 2);
				break;
				case 2: deck[i][j] = new Cards("Hearts", j + 2);
				break;
				case 3: deck[i][j] = new Cards("Diamonds", j + 2);
				break;
				}
			}
		}
		reset();
	}
	
	/**
	 * Checks to see if deck already exists
	 * @return - instance value of deck
	 */
	public static Deck getInstance() {
		if(instance == null) {
			instance = new Deck();
		}
		return instance;
	}
	
	/**
	 * Resets deck to include 0 cards
	 */
	public static void reset() {
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 13; j++) {
				deck[i][j].setQuantity(0);
			}
		}
	}
	
	/**
	 * Sets deck to include one of each card
	 */
	public static void poker() {
		reset();
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 13; j++) {
				deck[i][j].quantity = 1;
			}
		}
	}
	
	/**
	 * Sets deck to include 4 of each card
	 */
	public static void blackjack() {
		reset();
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 13; j++) {
				deck[i][j].quantity = 4;
			}
		}
	}
	
	/**
	 * Sets deck to include one of each 9, 10, Jack, Queen, King, Ace
	 */
	public static void euchre() {
		reset();
		for(int i = 0; i < 4; i++) {
			for(int j = 9; j < 13; j++) {
				deck[i][j].quantity = 1;
			}
		}
	}
	
	/**
	 * Creates a hand with specified amount of cards.
	 * @param quantity - number of cards in a hand
	 * @return - complete hand of Cards object
	 */
	public static ArrayList<Cards> createHand(int quantity) {
		ArrayList<Cards> temp = new ArrayList<Cards>();
		while(temp.size() < quantity) {
			int suit = (int)(Math.random() * 4);
			int rank = (int)(Math.random() * 13);
			if(deck[suit][rank].quantity > 0) {
				temp.add(deck[suit][rank]);
				deck[suit][rank].quantity --;
			}
		}
		return temp;
	}
	
	/**
	 * Creates a hand to play Euchre, cards restricted to 9, 10, Jack, Queen, King, Ace
	 * @return - complete hand of Cards object
	 */
	public static ArrayList<Cards> createEuchre() {
		ArrayList<Cards> temp = new ArrayList<Cards>();
		while(temp.size() < 5) {
			int suit = (int)(Math.random() * 4);
			int rank = (int)(Math.random() * 6 + 8);
			if(deck[suit][rank].quantity > 0) {
				temp.add(deck[suit][rank]);
				deck[suit][rank].quantity --;
			}
		}
		return temp;
	}
	
	/**
	 * Picks a random card from the deck
	 * @return - random card available in deck
	 */
	public static Cards pickRandom() {
		int suit, rank;
		do {
			suit = (int)(Math.random() * 4);
			rank = (int)(Math.random() * 13);
		} while(deck[suit][rank].quantity == 0);
		deck[suit][rank].quantity --;
		
		return deck[suit][rank];
	}
	
	/**
	 * Removes specified card from deck used to simulate drawing a card
	 * @param card - Card to remove from deck
	 */
	public static void removeCard(Cards card) {
		for(int i = 0; i < 13; i++) {
			for(int j = 0; j < 13; j++) {
				if(deck[i][j] == card) {
					deck[i][j].quantity --;
				}
			}
		}
	}
	
	public static Cards getCard(int suit, int rank) {
		return deck[suit][rank];
	}
}
