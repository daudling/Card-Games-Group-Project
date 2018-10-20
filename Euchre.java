import java.util.ArrayList;

public class Euchre {
	
	private int team1, team2, inGame = 0;
	private final int NUM_CARDS = 5;
	private String trump, lead;
	private Cards flipUp, leftBower, rightBower;
	private ArrayList<Cards> table = new ArrayList<Cards>();
	private ArrayList<Player> turnOrder = new ArrayList<Player>();
	
	public void setTrump(String trump) {
		switch(trump) {
		case "Spades": this.trump = "Spades";
		rightBower = Deck.getCard(0, 10);
		leftBower = Deck.getCard(1, 10);
		break;
		case "Clubs": this.trump = "Clubs";
		rightBower = Deck.getCard(1, 10);
		leftBower = Deck.getCard(0, 10);
		break;
		case "Hearts": this.trump = "Hearts";
		rightBower = Deck.getCard(2, 10);
		leftBower = Deck.getCard(3, 10);
		break;
		case "Diamonds": this.trump = "DiamondS";
		rightBower = Deck.getCard(4, 10);
		leftBower = Deck.getCard(3, 10);
		}
	}
	
	/**
	 * Adds points to whichever team won the round
	 * @param teamNum - specifies which team gets points
	 * @param amount - amount to add: 1 = 3 or 4 points, 2 = 5 points
	 * 2 = 3 points when other team called trump, 4 = 5 points and alone
	 */
	public void addPoints(int teamNum, int amount) {
		if(teamNum == 1) {
			team1 += amount;
		}
		else {
			team2 += amount;
		}
		if(team1 >= 10 || team2 >= 10) {
			inGame = 1;
		}
	}
	
	public void playCard(Cards card, int playerNum) {
		for(Player x : PlayerList.players) {
			if(x.playerNum == playerNum) {
				x.removeCard(card);
			}
		}
		Deck.removeCard(card);
		table.add(card);
	}
	
	/**
	 * Proceeds game forward by taking each turn 
	 * @param r - the current round the game is on, when it reaches round 5 it should stop the match
	 * @param team1 - the number of rounds won by the team in the match
	 * @param team2 - the number of rounds won by the team in the match
	 */
	public void turn(int r, int team1, int team2) {
		table.clear();
		Cards temp, high = null;
		int round = 1;
		int points1 = team1;
		int points2 = team2;
		Player winner = null;
		for(Player x : turnOrder) {
			temp = chooseCard(x);
			table.add(temp);
			x.removeCard(temp);
		}
		System.out.println("Cards on table:");
		for(Cards y : table) {
			System.out.println(y.rank + " of " + y.suit);
			if(high == null) {
				high = y;
			}
			else {
				if(y == rightBower) {
					high = y;
				}
				else if(y == leftBower && high != rightBower) {
					high = y;
				}
				else if(isTrump(y) && !isTrump(high)) {
					high = y;
				}
				else if(y.rank > high.rank && isLead(y)) {
					high = y;
				}
			}
		}
		System.out.println("Winner of round");
		winner = turnOrder.get(table.indexOf(high));
		System.out.println(winner.playerNum);
		round++;
		if(winner.team == 1) {
			points1++;
		}
		else {
			points2++;
		}
		if(round != 5) {
			turn(round, points1, points2);
		}
		else {
			if(points1 == 5) {
				addPoints(1, 2);
			}
			else if(points1 > points2) {
				addPoints(1, 1);
			}
			else if(points2 == 5) {
				addPoints(2, 2);
			}
			else {
				addPoints(2, 1);
			}
		}
	}
	
	/**
	 * Decides who's taking the turn and how they should take it.
	 * 0 - is a player's turn
	 * 1 - is a easy bot
	 * 2 - is a medium bot
	 * 3 - is a hard bot
	 * @param player - the current players turn.
	 * @return - the card the current player chose.
	 */
	public Cards chooseCard(Player player) {
		Cards temp = new Cards();
		switch(player.difficulty) {
		case 0: temp = player.chooseCard();
		break;
		case 1: temp = easyChooseCard(player);
		break;
		case 2: temp = mediumChooseCard(player);
		break;
		case 3: temp = hardChooseCard(player);
		break;
		}
		return temp;
	}
	
	/**
	 * Tests to see if anyone wants the dealer to pickup the card flipped up at start of round.
	 * @param player - the current player deciding should they pick it up.
	 * @return - the players choice if they want to pick it up or not.
	 */
	public boolean pickUpCard(Player player) {
		switch(player.difficulty) {
		case 0: if(player.pickUp()) {
			return true;
		}
		break;
		case 1: if(easyPickUp(player)) {
			return true;
		}
		break;
		case 2: if(mediumPickUp(player)) {
			return true;
		}
		break;
		case 3: if(hardPickUp(player)) {
			return true;
		}
		break;
		}
		return false;
	}
	
	/**
	 * ChooseCard method for an easy bot
	 * @param player - current bot player
	 * @return - card chosen by bot
	 */
	private Cards easyChooseCard(Player player) {
		Cards temp = null;
		if(hasLead(player)) {
			for(Cards x : player.getHand()) {
				if(temp == null) {
					temp = x;
				}
				else if(temp.rank < x.rank && x.suit == lead) {
					temp = x;
				}
			}
		}
		else if(hasTrump(player)) {
			for(Cards x : player.getHand()) {
				if(temp == null) {
					temp = x;
				}
				else if(temp.rank < x.rank && (isTrump(x) || x == leftBower)) {
					temp = x;
				}
			}
		}
		else {
			for(Cards x : player.getHand()) {
				if(temp == null) {
					temp = x;
				}
				else if(temp .rank > x.rank) {
					temp = x;
				}
			}
		}
		return temp;
	}
	
	/**
	 * Easy bot method for to decide if the dealer should pick it up
	 * @param player
	 * @return
	 */
	private boolean easyPickUp(Player player) {
		int count = 0;
		for(Cards x : player.getHand()) {
			if(x.suit == flipUp.suit) {
				count++;
			}
		}
		if(count >=3) {
			return true;
		}
		return false;
	}
	
	/**
	 * Medium bot method to decide if the dealer should pick it up
	 * @param player
	 * @return
	 */
	private boolean mediumPickUp(Player player) {
		return false;
	}
	
	/**
	 * Hard bot method to decide if the dealer should pick it up
	 * @param player
	 * @return
	 */
	private boolean hardPickUp(Player player) {
		return false;
	}
	
	/**
	 * ChooseCard method for a medium bot
	 * @param player
	 * @return
	 */
	private Cards mediumChooseCard(Player player) {
		Cards temp = null;
		return temp;
	}
	
	/**
	 * ChooseCard method for a hard bot
	 * @param player
	 * @return
	 */
	private Cards hardChooseCard(Player player) {
		Cards temp = null;
		return temp;
	}
	
	/**
	 * Method to tell if current player has the suit lead for the round
	 * @param player - current players turn
	 * @return - true if they have suit lead, false if they don't have suit lead
	 */
	private boolean hasLead(Player player) {
		for(Cards x : player.getHand()) {
			if(x.suit == lead) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Method to tell if current player has trump suit or left bower
	 * @param player - current players turn
	 * @return - true if they have suit lead, false if they don't have suit lead
	 */
	private boolean hasTrump(Player player) {
		for(Cards x : player.getHand()) {
			if(x.suit == trump) {
				return true;
			}
			else if(x == leftBower) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Decides whether input card is trump or not
	 * @param card
	 * @return
	 */
	private boolean isTrump(Cards card) {
		if(card.suit == trump) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Decides whether input card is the suit lead for the round or not
	 * @param card
	 * @return
	 */
	private boolean isLead(Cards card) {
		if(card.suit == lead) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Sets the order of turns for each round
	 */
	public void setOrder() {
		if(turnOrder.isEmpty()) {
			for(Player x : PlayerList.players) {
				turnOrder.add(x);
			}
		}
		else {
			turnOrder.add(turnOrder.get(0));
			turnOrder.remove(0);
		}
	}
		
	/**
	 * Resets the table ensures its empty.
	 */
	public void resetTable() {
		table.clear();
	}
	
	/**
	 * Starts each match by flipping up a card and letting players decide to have dealer pick it up
	 * @return
	 */
	public boolean startRound() {
		flipUp = Deck.pickRandom();
		for(Player x : turnOrder) {
			if(pickUpCard(x)) {
				x.hand.add(flipUp);
				x.removeCard(x.hand.get(0));
				return true;
			}
		}
		return false;
	}
	
	public void playEuchre() {
		resetTable();
		setOrder();
		for(Player p : turnOrder) {
			System.out.println(p.playerNum + " Difficulty: " + p.difficulty);
		}
		Deck.euchre();
		for(Player x : PlayerList.players) {
			if(!x.hand.isEmpty()) {
				x.hand.clear();
			}
			x.addHand(NUM_CARDS);
			System.out.print(x.playerNum + "'s hand: ");
			for(Cards h : x.getHand()) {
				System.out.print(h.rank + " of " + h.suit + " ");
			}
			System.out.println("");
		}
		if(startRound()) {
			while(inGame == 0) {
				turn(0, 0, 0);
			}
		}
		if(team1 >= 10) {
			for(Player y : turnOrder) {
				if(y.team == 1) {
					y.wins++;
				}
				else {
					y.losses++;
				}
			}
		}
		else {
			for(Player z : turnOrder) {
				if(z.team == 2) {
					z.wins++;
				}
				else {
					z.losses++;
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Player person = new Player(1);
		Player bot1 = new Player(2);
		Player bot2 = new Player(3);
		Player bot3 = new Player(4);
		bot1.difficulty = 1;
		bot2.difficulty = 1;
		bot3.difficulty = 1;
		person.team = 1;
		bot1.team = 1;
		bot2.team = 2;
		bot3.team = 2;
		PlayerList list = PlayerList.getInstance();
		list.addPlayer(person);
		list.addPlayer(bot1);
		list.addPlayer(bot2);
		list.addPlayer(bot3);
		
		Deck.getInstance();
		
		System.out.println("Starting Game of Euchre");
		
		Euchre game = new Euchre();
		game.playEuchre();
	}
}
