import java.lang.String;

public class Cards {
	
	public String suit;
	public int rank, quantity;
	
	/**
	 * Constructor method
	 * @param s - Suit of the card. 1 == "Spades", 2 == "Clubs", 3 == "Hearts", 4 == "Diamonds"
	 * @param r - Rank of the card. 1 through 10 == 1 through 10, 11 == Jack, 12 == Queen, 13 == King, 14 == Ace
	 */
	public Cards(String s, int r) {
		suit = s;
		rank = r;
		quantity = 0;
	}
}