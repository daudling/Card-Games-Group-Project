

import java.lang.String;

public class Cards {
	
	public String suit;
	public int rank, quantity;
	
	/**
	 * Default Constructor
	 */
	public Cards() {
		suit = null;
		rank = 0;
		quantity = 0;
	}
	/**
	 * Constructor method
	 * @param s - Suit of the card. 1 == "Spades", 2 == "Clubs", 3 == "Hearts", 4 == "Diamonds"
	 * @param r - Rank of the card. 2 through 10 == 2 through 10, 11 == Jack, 12 == Queen, 13 == King, 14 == Ace
	 */
	public Cards(String s, int r) {
		suit = s;
		rank = r;
		quantity = 0;
	}
	
	public int getRank(){
		return rank;
	}
	
	public String getSuit(){
		return suit;
	}
	
	public void setQuantity(int num){
		quantity = num;
	}
}