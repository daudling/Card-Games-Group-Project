import java.util.ArrayList;

public class Deck {
	
	public Cards deck[][];
	private static Deck instance = null;
	
	private Deck() {
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 14; j++) {
				switch(i) {
				case 1: deck[i][j] = new Cards("Spades", j);
				break;
				case 2: deck[i][j] = new Cards("Clubs", j);
				break;
				case 3: deck[i][j] = new Cards("Hearts", j);
				break;
				case 4: deck[i][j] = new Cards("Diamonds", j);
				break;
				}
			}
		}
	}
	
	public static Deck getInstance() {
		if(instance == null) {
			instance = new Deck();
		}
		return instance;
	}
	
	public void reset() {
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 14; j++) {
				deck[i][j].quantity = 0;
			}
		}
	}
	
	public void poker() {
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 14; j++) {
				deck[i][j].quantity = 1;
			}
		}
	}
	
	public void blackjack() {
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 14; j++) {
				deck[i][j].quantity = 4;
			}
		}
	}
	
	public void euchre() {
		for(int i = 0; i < 4; i++) {
			for(int j = 9; j < 14; j++) {
				deck[i][j].quantity = 1;
			}
		}
	}
	
	public ArrayList<Cards> createHand(int quantity) {
		ArrayList<Cards> temp = new ArrayList<Cards>();
		while(temp.size() < quantity) {
			int suit = (int)(Math.random() * 4 + 1);
			int rank = (int)(Math.random() * 14 + 1);
			if(deck[suit][rank].quantity > 0) {
				temp.add(deck[suit][rank]);
				deck[suit][rank].quantity --;
			}
		}
		return temp;
	}
	
	public Cards pickRandom() {
		int suit, rank;
		do {
			suit = (int)(Math.random() * 4 + 1);
			rank = (int)(Math.random() * 14 + 1);
		} while(deck[suit][rank].quantity == 0);
		deck[suit][rank].quantity --;
		
		return deck[suit][rank];
	}
}
