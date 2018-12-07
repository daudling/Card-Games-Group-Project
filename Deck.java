import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Deck {
	
	private static Cards[][] deck = new Cards[4][13];
	private static ImageIcon[][] visualDeck = new ImageIcon[4][13];
	private static ImageIcon back, blank;
	private static Deck instance = null;
	
	/**
	 * Populates deck with 52 Cards objects, one for each card.
	 * Sets quantity of each object to 0.
	 * Populates second "visualDeck" to get image references for each card and provides blank/back image
	 */
	private Deck() {
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 13; j++) {
				switch(i) {
				case 0: deck[i][j] = new Cards("Spades", j + 2);
				visualDeck[i][j] = new ImageIcon("Cards/" + (j+2) + "_of_spades.png");
				break;
				case 1: deck[i][j] = new Cards("Clubs", j + 2);
				visualDeck[i][j] = new ImageIcon("Cards/" + (j+2) + "_of_clubs.png");
				break;
				case 2: deck[i][j] = new Cards("Hearts", j + 2);
				visualDeck[i][j] = new ImageIcon("Cards/" + (j+2) + "_of_hearts.png");
				break;
				case 3: deck[i][j] = new Cards("Diamonds", j + 2);
				visualDeck[i][j] = new ImageIcon("Cards/" + (j+2) + "_of_diamonds.png");
				break;
				}
			}
		}
		back = new ImageIcon("Cards/FaceDown.png");
		blank = new ImageIcon("Cards/Blank.png");
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
			for(int j = 7; j < 13; j++) {
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
			int rank = (int)(Math.random() * 8 + 8);
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
		for(int i = 0; i < 4; i++) {
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
	
	public static Cards getCard(String suit, int rank) {
		switch(suit) {
		case "Spades": return deck[0][rank];
		case "Clubs": return deck[1][rank];
		case "Hearts": return deck[2][rank];
		case "Diamonds": return deck[3][rank];
		}
		return null;
	}
	
	public static Cards getLeft(String suit) {
		switch(suit) {
		case "Spades": return deck[1][9];
		case "Clubs": return deck[0][9];
		case "Hearts": return deck[3][9];
		case "Diamonds": return deck[2][9];
		}
		return null;
	}
	
	public static ImageIcon getVisual(String suit, int rank) {
		int suitRank = 0;
		switch(suit) {
		case "Spades": suitRank = 0;
		break;
		case "Clubs": suitRank = 1;
		break;
		case "Hearts": suitRank = 2;
		break;
		case "Diamonds": suitRank = 3;
		}
		return visualDeck[suitRank][rank];
	}
	
	public static ImageIcon getBack() {
		return back;
	}
	
	public static ImageIcon getBlank() {
		return blank;
	}
	
	public static ArrayList<Cards> getDeck() {
		ArrayList<Cards> temp = new ArrayList<Cards>();
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 13; j++) {
				if(deck[i][j].quantity > 0) {
					temp.add(deck[i][j]);
				}
			}
		}
		return temp;
	}
}
