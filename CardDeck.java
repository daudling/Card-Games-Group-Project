
import java.util.Random;
import java.util.Stack;

public class CardDeck {
	private int cardID;
	private Stack<Integer> sDeck = new Stack<>();
	private String[] deck = new String[52];
	private String[] shuffledDeck = new String[52];
	private Random spot = new Random();
	
	public int getCardID() {
		return cardID;
	}
	public void setCardID(int cardID) {
		this.cardID = cardID;
	}
	public String[] getDeck() {
		return deck;
	}
	public String[] getShuffledDeck() {
		return shuffledDeck;
	}

	public String convertId (int cardID){
		String convertedCard = "";
		int suit = cardID / 13;
		int num = cardID % 13;
		
		if (num == 11)
			convertedCard += "J ";
		else if (num == 12)
			convertedCard += "Q ";
		else if (num == 0)
			convertedCard += "K ";
		else if (num == 1)
			convertedCard += "A ";
		else
			convertedCard += num + " ";
		
		if (suit == 0)
			convertedCard += "H";
		else if (suit == 1)
			convertedCard += "D";
		else if (suit == 2)
			convertedCard += "C";
		else if (suit == 3)
			convertedCard += "S";
		else
			convertedCard = "Error";
		return convertedCard;
	}
	
	public void createDeck(){
		for (int i = 0; i < 52; i++){
			deck[i] = convertId(i);
		}
	}
	
	public void createShuffledDeck(){
		int place = 0;
		
		place = spot.nextInt(52);
		
		for(int k = 0; k < 52; k++){
			shuffledDeck[k] = "Blank";		
		}
		
		for(int i = 0; i < 52; i++){
			while (!shuffledDeck[place].equals("Blank")){
				//System.out.println(place);
				place = spot.nextInt(52);
			}
			shuffledDeck[place] = deck[i];		
		}
	}
	
	public String getCard (int number){
		return deck[number];
	}
	
	public String getShuffledCard(int number){
		return shuffledDeck[number];
	}
	
	
}
