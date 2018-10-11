
public class Cards {
	public static void main(String[] args) {
		int[] deck = new int[52];
		String[] suits = {"Spades", "Hearts", "Diamonds", "Clubs"};
		String[] ranks = {"2", "3" , "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
		
		//Initialize
		for(int i = 0; i < deck.length; i++) {
			deck[i] = i;
		}
		
		//shuffle
		for(int i = 0; i < deck.length; i++) {
			int index = (int)(Math.random() * deck.length);
		    int temp = deck[i];
		    deck[i] = deck[index];
		    deck[index] = temp;
		}
		
		//print
		for (int i = 0; i < 52; i++) {
			String suit = suits[deck[i] / 13];
		    String rank = ranks[deck[i] % 13];
		    System.out.println( rank + " of " + suit);
		}
	}
}
