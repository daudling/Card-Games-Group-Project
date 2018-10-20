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
	
	public void turn(int r, int team1, int team2) {
		Cards temp, high = null;
		int round = 0;
		int points1 = team1;
		int points2 = team2;
		Player winner = null;
		for(Player x : turnOrder) {
			temp = chooseCard(x);
			table.add(temp);
			x.removeCard(temp);
		}
		for(Cards y : table) {
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
		winner = turnOrder.get(table.indexOf(high));
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
	
	public Cards chooseCard(Player player) {
		Cards temp = new Cards();
		switch(player.difficulty) {
		case 0: temp = player.chooseCard(1);
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
	
	private boolean mediumPickUp(Player player) {
		return false;
	}
	
	private boolean hardPickUp(Player player) {
		return false;
	}
	
	private Cards mediumChooseCard(Player player) {
		Cards temp = null;
		return temp;
	}
	
	private Cards hardChooseCard(Player player) {
		Cards temp = null;
		return temp;
	}
	
	private boolean hasLead(Player player) {
		for(Cards x : player.getHand()) {
			if(x.suit == lead) {
				return true;
			}
		}
		return false;
	}
	
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
	
	private boolean isTrump(Cards card) {
		if(card.suit == trump) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private boolean isLead(Cards card) {
		if(card.suit == lead) {
			return true;
		}
		else {
			return false;
		}
	}
	
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
		
	public void resetTable() {
		table.clear();
	}
	
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
		Deck.euchre();
		for(Player x : PlayerList.players) {
			x.hand.clear();
			x.addHand(NUM_CARDS);
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
}
