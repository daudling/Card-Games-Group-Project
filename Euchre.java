import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Euchre{
	
	private int inGame = 0, currentLead = 0;
	public int team1, team2, round = 1;
	private final int NUM_CARDS = 5;
	public String trump, lead;
	private Cards flipUp;
	private Cards leftBower = null;
	private Cards rightBower = null;
	private Cards currentHigh;
	public ArrayList<Cards> table = new ArrayList<Cards>();
	public ArrayList<Player> turnOrder = new ArrayList<Player>();
	
	public  Cards input = null;
	public  boolean pickUp = false;
	public static ArrayList<Cards> hand = null;
	
	public void setTrump(String trump) {
		switch(trump) {
		case "Spades": this.trump = "Spades";
		rightBower = Deck.getCard(0, 9);
		leftBower = Deck.getCard(1, 9);
		break;
		case "Clubs": this.trump = "Clubs";
		rightBower = Deck.getCard(1, 9);
		leftBower = Deck.getCard(0, 9);
		break;
		case "Hearts": this.trump = "Hearts";
		rightBower = Deck.getCard(2, 9);
		leftBower = Deck.getCard(3, 9);
		break;
		case "Diamonds": this.trump = "DiamondS";
		rightBower = Deck.getCard(3, 9);
		leftBower = Deck.getCard(2, 9);
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
	 * @throws InterruptedException 
	 */
	public void turn(int r, int team1, int team2){
		currentLead = 0;
		table.clear();
		EuchreGUI.updateTable();
		Cards temp = null, high = null;
		lead = null;
		round = r;
		int playerHigh = 0;
		int points1 = team1;
		int points2 = team2;
		Player winner = null;
		for(Player x : turnOrder) {
			temp = chooseCard(x);
			if(table.isEmpty()) {
				if(temp == leftBower) {
					lead = trump;
				}
				else {
					lead = temp.suit;
				}
			}
			table.add(temp);
			x.removeCard(temp);
			EuchreGUI.updateTable();
		}
		for(Cards y : table) {
			if(high == null) {
				high = y;
				playerHigh = table.indexOf(y);
			}
			else {
				if(y == rightBower) {
					high = y;
					playerHigh = table.indexOf(y);
				}
				else if(y == leftBower && high != rightBower) {
					high = y;
					playerHigh = table.indexOf(y);
				}
				else if(isTrump(y) && !isTrump(high) && high != leftBower) {
					high = y;
					playerHigh = table.indexOf(y);
				}
				else if(isTrump(y) && y.rank > high.rank && high != leftBower && high != rightBower) {
					high = y;
					playerHigh = table.indexOf(y);
				}
				else if(isLead(high) && !isTrump(high)) {
					if(y.rank > high.rank && isLead(y)) {
						high = y;
						playerHigh = table.indexOf(y);
					}
				}
			}
		}
		winner = turnOrder.get(playerHigh);
		if(winner.team == 1) {
			points1++;
		}
		else {
			points2++;
		}
		if(r != 5) {
			lead = null;
			EuchreGUI.updateTable();
			JOptionPane.showMessageDialog(null, "Winner is player #" + winner.playerNum);
			setWinnerOrder(winner.playerNum);
			turn(round + 1, points1, points2);
		}
		else {
			JOptionPane.showMessageDialog(null, "Winner is player #" + winner.playerNum);
			setWinnerOrder(winner.playerNum);
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
		if(team1 >= 10 || team2 >= 10) {
			setGame(false);
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
	 * @throws InterruptedException 
	 */
	public Cards chooseCard(Player player){
		int counter = 0;
		Cards temp = new Cards();
		switch(player.difficulty) {
		case 0: input = null;
		Object[] options = new Object[5];
		for(Cards x : player.getHand()) {
			options[counter] = Deck.getVisual(x.suit, x.rank - 2);
			counter++;
		}
		while(counter < 5) {
			options[counter] = Deck.getBack();
			counter++;
		}
		while(isValid(player) == false) {
			int n = JOptionPane.showOptionDialog(null, "Choose card to play", null,
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[4]);
			if(n > player.hand.size() - 1) {
				input = null;
			}
			else {
				input = player.hand.get(n);
			}
		}
		temp = input;
		break;
		case 1: temp = easyChooseCard(player);
		break;
		case 2: temp = mediumChooseCard(player);
		break;
		case 3: temp = mediumChooseCard(player);
		break;
		}
		return temp;
	}
	
	/**
	 * Tests to see if anyone wants the dealer to pickup the card flipped up at start of round.
	 * @param player - the current player deciding should they pick it up.
	 * @return - the players choice if they want to pick it up or not.
	 * @throws InterruptedException 
	 */
	public boolean pickUpCard(Player player){
		EuchreGUI.flip.setVisible(true);
		EuchreGUI.updateTable();
		switch(player.difficulty) {
		case 0: hand = player.hand;
		Object[] options = {"Yes", "no"};
		int n = JOptionPane.showOptionDialog(null, "Pick up card", null,
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[1]);
		if(n == 0) {
			return true;
		}
		break;
		case 1: if(easyPickUp(player)) {
			return easyPickUp(player);
		}
		break;
		case 2: if(mediumPickUp(player)) {
			return mediumPickUp(player);
		}
		break;
		case 3: if(hardPickUp(player)) {
			return hardPickUp(player);
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
		if(currentLead == 0) {
			if(hasTrump(player)) {
				if(trumpCount(player) == 1) {
					for(Cards x : player.getHand()) {
						if(isTrump(x)) {
							temp = x;
						}
					}
				}
				else {
					for(Cards x : player.getHand()) {
						if(temp == null && isTrump(x)) {
							temp = x;
						}
						else if(isTrump(x)) {
							if(x == rightBower) {
								temp = x;
							}
							else if(x == leftBower) {
								temp = x;
							}
							else if(x.rank > temp.rank && temp != leftBower && temp != rightBower) {
								temp = x;
							}
						}
					}
				}
			}
			else {
				for(Cards x : player.getHand()) {
					if(temp == null) {
						temp = x;
					}
					else if(x.rank > temp.rank) {
						temp = x;
					}
				}
			}
		}
		else if(hasLead(player)) {
			if(lead == trump) {
				for(Cards x : player.getHand()) {
					if(temp == null && isTrump(x)) {
						temp = x;
					}
					else if(isTrump(x)) {
						if(x == rightBower) {
							temp = x;
						}
						else if(x == leftBower) {
							temp = x;
						}
						else if(x.rank > temp.rank && temp != leftBower && temp != rightBower) {
							temp = x;
						}
					}
				}
			}
			else if(leadCount(player) == 1) {
				for(Cards x : player.getHand()) {
					if(isLead(x)) {
						temp = x;
					}
				}
			}
			else {
				for(Cards x : player.getHand()) {
					if(temp == null && isLead(x)) {
						temp = x;
					}
					else if(temp != null && x.rank > temp.rank) {
						temp = x;
					}
				}
			}
		}
		else {
			if(hasTrump(player)) {
				if(trumpCount(player) == 1) {
					for(Cards x : player.getHand()) {
						if(isTrump(x)) {
							temp = x;
						}
					}
				}
				else {
					for(Cards x : player.getHand()) {
						if(temp == null && isTrump(x)) {
							temp = x;
						}
						else if(isTrump(x)) {
							if(x == rightBower) {
								temp = x;
							}
							else if(x == leftBower) {
								temp = x;
							}
							else if(x.rank > temp.rank && temp != leftBower && temp != rightBower) {
								temp = x;
							}
						}
					}
				}
			}
			else {
				for(Cards x : player.getHand()) {
					if(temp == null) {
						temp = x;
					}
					else if(x.rank < temp.rank) {
						temp = x;
					}
				}
			}
		}
		if(currentLead == 0) {
			currentHigh = temp;
			currentLead = player.playerNum;
		}
		else if(!isTrump(temp) && !isLead(temp)) {
			return temp;
		}
		else {
			if(currentHigh == rightBower) {
				return temp;
			}
			else if(currentHigh == leftBower) {
				if(temp == rightBower) {
					currentHigh = temp;
					currentLead = player.playerNum;
				}
				else {
					return temp;
				}
			}
			else if(isTrump(currentHigh)) {
				if(isTrump(temp) && temp.rank > currentHigh.rank) {
					currentHigh = temp;
					currentLead = player.playerNum;
				}
				else {
					return temp;
				}
			}
			else if(isLead(temp)) {
				if(temp.rank > currentHigh.rank) {
					currentHigh = temp;
					currentLead = player.playerNum;
				}
				else {
					return temp;
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
			if(x.suit == flipUp.suit || x == leftBower) {
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
		Player partner = PlayerList.players.get((player.playerNum + 2) %4);
		int count = 0;
		for(Cards x : player.getHand()) {
			if(x.suit == flipUp.suit || x == leftBower) {
				count++;
			}
		}
		if(count >= 3 && partner.playerNum % 2 == (player.playerNum % 2)) {
			return true;
		}
		else if( count >= 3 && flipUp.rank < 10) {
			return true;
		}
		return false;
	}
	
	/**
	 * Hard bot method to decide if the dealer should pick it up
	 * @param player
	 * @return
	 */
	private boolean hardPickUp(Player player) {
		Player partner = PlayerList.players.get((player.playerNum + 2) % 4);
		int count = 0;
		for(Cards x : player.getHand()) {
			if(x.suit == flipUp.suit || x == leftBower) {
				count++;
			}
		}
		for(Cards y : partner.getHand()) {
			if(y.suit == flipUp.suit || y == leftBower) {
				count++;
			}
		}
		if(count >= 8) {
			return true;
		}
		else if(count >= 6 && (turnOrder.get(0) == player || turnOrder.get(0) == partner)) {
			return true;
		}
		else if(count >= 6 && flipUp.rank < 10) {
			return true;
		}
		return false;
	}
	
	/**
	 * ChooseCard method for a medium bot
	 * @param player
	 * @return
	 */
	private Cards mediumChooseCard(Player player) {
		Cards temp = null;
		if(currentLead == 0) {
			if(hasTrump(player)) {
				if(trumpCount(player) == 1) {
					for(Cards x : player.getHand()) {
						if(x == rightBower || x == leftBower) {
							temp = x;
						}
					}
				}
				else {
					for(Cards x : player.getHand()) {
						if(temp == null && isTrump(x) && x.rank > 9) {
							temp = x;
						}
						else if(temp != null && isTrump(x) && x.rank > temp.rank) {
							temp = x;
						}
					}
				}
			}
			if(temp == null) {
				for(Cards x : player.getHand()) {
					if(temp == null) {
						temp = x;
					}
					else if(x.rank > temp.rank) {
						temp =x ;
					}
				}
			}
		}
		else {
			if(hasLead(player)) {
				if(leadCount(player) == 1) {
					for(Cards x : player.getHand()) {
						if(isLead(x)) {
							temp = x;
						}
					}
				}
				else if(lead == trump) {
					if(currentHigh == rightBower) {
						for(Cards x : player.getHand()) {
							if(x == leftBower && trumpCount(player) == 1) {
								temp = x;
							}
							else if(temp == null && isTrump(x) && x != leftBower) {
								temp = x;
							}
							else if(temp != null && isTrump(x) && x != leftBower && x.rank < temp.rank) {
								temp =x ;
							}
						}
					}
					else if(currentHigh == leftBower) {
						for(Cards x : player.getHand()) {
							if(x == rightBower) {
								temp = x;
							}
							else if(temp == null && isTrump(x)) {
								temp = x;
							}
							else if(temp != null && isTrump(x) && temp != rightBower && x.rank < temp.rank) {
								temp = x;
							}
						}
					}
					else {
						for(Cards x : player.getHand()) {
							if(temp == null && isLead(x)) {
								temp = x;
							}
							else if(temp != null && isLead(x)) {
								if(temp.rank > currentHigh.rank) {
									if(x.rank < temp.rank && x.rank > currentHigh.rank) {
										temp = x;
									}
								}
								if(temp.rank < currentHigh.rank) {
									if(x.rank > currentHigh.rank) {
										temp = x;
									}
									else if(x.rank < temp.rank) {
										temp = x;
									}
								}
							}
						}
					}
				}
				else {
					for(Cards x : player.getHand()) {
						if(temp == null) {
							temp = x;
						}
						else if(isTrump(currentHigh) && x.rank < temp.rank) {
							temp = x;
						}
						else if(isLead(x) && temp.rank > currentHigh.rank) {
							if(x.rank > currentHigh.rank && x.rank < temp.rank) {
								temp = x;
							}
						}
						else if(isLead(x) && temp.rank < currentHigh.rank) {
							if(x.rank > currentHigh.rank) {
								temp = x;
							}
						}
					}
				}
			}
			else {
				if(hasTrump(player)) {
					if(currentHigh == rightBower) {
						for(Cards x : player.getHand()) {
							if(temp == null) {
								temp = x;
							}
							else if(isTrump(temp) && !isTrump(x)) {
								temp = x;
							}
							else if(!isTrump(x) && x.rank < temp.rank) {
								temp = x;
							}
						}
					}
					else if(currentHigh == leftBower) {
						for(Cards x : player.getHand()) {
							if(temp == null) {
								temp = x;
							}
							else if(x == rightBower) {
								temp = x;
							}
							else if(temp != rightBower) {
								if(isTrump(temp) && !isTrump(x)) {
									temp = x;
								}
								else if(!isTrump(x) && x.rank < temp.rank) {
									temp = x;
								}
							}
						}
					}
					else if(isTrump(currentHigh)) {
						for(Cards x : player.getHand()) {
							if(temp == null) {
								temp = x;
							}
							else if(isTrump(temp) && temp.rank < currentHigh.rank) {
								if(!isTrump(x)) {
									temp = x;
								}
								else if(isTrump(x) && x.rank > currentHigh.rank) {
									temp = x;
								}
							}
							else {
								if(x.rank < temp.rank && !isTrump(x)) {
									temp = x;
								}
								if(isTrump(x) && x.rank > currentHigh.rank) {
									temp = x;
								}
							}
						}
					}
					else {
						for(Cards x : player.getHand()) {
							if(temp == null) {
								temp = x;
							}
							else if(isTrump(x) && !isTrump(temp)) {
								temp = x;
							}
							else if(isTrump(x) && isTrump(temp) && x.rank < temp.rank) {
								temp = x;
							}
						}
					}
				}
				else {
					for(Cards x : player.getHand()) {
						if(temp == null) {
							temp = x;
						}
						else if(x.rank < temp.rank) {
							temp = x;
						}
					}
				}
			}
		}
		if(currentLead == 0) {
			currentHigh = temp;
			currentLead = player.playerNum;
		}
		else if(!isTrump(temp) && !isLead(temp)) {
			return temp;
		}
		else {
			if(currentHigh == rightBower) {
				return temp;
			}
			else if(currentHigh == leftBower) {
				if(temp == rightBower) {
					currentHigh = temp;
					currentLead = player.playerNum;
				}
				else {
					return temp;
				}
			}
			else if(isTrump(currentHigh)) {
				if(isTrump(temp) && temp.rank > currentHigh.rank) {
					currentHigh = temp;
					currentLead = player.playerNum;
				}
				else {
					return temp;
				}
			}
			else if(isLead(temp)) {
				if(temp.rank > currentHigh.rank) {
					currentHigh = temp;
					currentLead = player.playerNum;
				}
				else {
					return temp;
				}
			}
		}
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
			if(isLead(x)) {
				return true;
			}
		}
		return false;
	}
	
	private int leadCount(Player player) {
		int count = 0;
		for(Cards x: player.getHand()) {
			if(isLead(x)) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Method to tell if current player has trump suit or left bower
	 * @param player - current players turn
	 * @return - true if they have suit lead, false if they don't have suit lead
	 */
	private boolean hasTrump(Player player) {
		for(Cards x : player.getHand()) {
			if(isTrump(x)) {
				return true;
			}
		}
		return false;
	}
	
	private int trumpCount(Player player) {
		int count = 0;
		for(Cards x : player.getHand()) {
			if(isTrump(x)) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Decides whether input card is trump or not
	 * @param card
	 * @return
	 */
	private boolean isTrump(Cards card) {
		if(card.suit.equals(trump) || card == leftBower) {
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
		if(card.suit.equals(lead)) {
			return true;
		}
		else if(card == leftBower) {
			if(lead.equals(trump)) {
				return true;
			}
		}
		else {
			return false;
		}
		return false;
	}
	
	/**
	 * Sets the order of turns for first round
	 */
	public void setOrder() {
		turnOrder.clear();
		for(Player x : PlayerList.players) {
			turnOrder.add(x);
		}
	}
	
	/**
	 * Sets the order for every round after the first based on winner value
	 * @param winner - the playerNum value for each winner
	 */
	public void setWinnerOrder(int winner) {
		if(turnOrder.isEmpty() == false) {
			turnOrder.clear();
			for(int i = 0; i < 4; i++) {
				turnOrder.add(PlayerList.players.get((i + winner - 1) % 4));
			}
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
	 * @throws InterruptedException 
	 */
	public boolean startRound(){
		Deck.reset();
		Deck.euchre();
		table.clear();
		for(Player x : PlayerList.players) {
			x.hand.clear();
			x.addHand(NUM_CARDS);
		}
		flipUp = Deck.pickRandom();
		setTrump(flipUp.suit);
		EuchreGUI.flip.setIcon(Deck.getVisual(flipUp.suit, flipUp.rank - 2));
		EuchreGUI.updateTable();
		for(Player x : turnOrder) {
			if(pickUpCard(x)) {
				discard(turnOrder.get(0));
				EuchreGUI.flip.setVisible(false);
				return true;
			}
		}
		return false;
	}
	
	public void discard(Player player){
		int counter = 0;
		switch(turnOrder.get(0).difficulty) {
		case 0: hand = player.hand;
		Object[] options = new Object[5];
		for(Cards x : hand) {
			options[counter] = Deck.getVisual(x.suit,x.rank - 2);
			counter++;
		}
		int n = JOptionPane.showOptionDialog(null, "Choose card to discard", null,
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		player.hand.remove(n);
		player.hand.add(flipUp);
		break;
		case 1: discardCard(player);
		break;
		case 2: discardCard(player);
		break;
		case 3: discardCard(player);
		break;
		}
		EuchreGUI.updateTable();
	}
	
	private boolean isValid(Player player) {
		if(input == null) {
			return false;
		}
		else if(table.isEmpty()) {
			return true;
		}
		else if(hasLead(player) && isLead(input) == false) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public void discardCard(Player player) {
		Cards temp = null;
		for(Cards x : player.getHand()) {
			if(temp == null) {
				temp = x;
			}
			else if(isTrump(temp) && temp != leftBower) {
				if(!isTrump(x) && x != leftBower) {
					temp = x;
				}
			}
			else if(x.rank < temp.rank) {
				temp = x;
			}
		}
		player.hand.add(flipUp);
		player.hand.remove(temp);
	}
	
	public void setGame(boolean val) {
		if(val == true) {
			inGame = 0;
		}
		else {
			inGame = 1;
		}
	}
	
	public void playEuchre(){
		setGame(true);
		resetTable();
		setOrder();
		Deck.euchre();
		PlayerList.players.get(0).team = 1;
		PlayerList.players.get(1).team = 2;
		PlayerList.players.get(2).team = 1;
		PlayerList.players.get(3).team = 2;
		for(Player x : PlayerList.players) {
			if(!x.hand.isEmpty()) {
				x.hand.clear();
			}
			x.addHand(NUM_CARDS);
		}
		EuchreGUI.updateTable();
		while(inGame == 0) {
			if(startRound()) {
				turn(1, 0, 0);
			}
			else {
				playEuchre();
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
		resetTable();
		EuchreGUI.updateTable();
		GUI.layout.show(GUI.panel,  "Menu");
		if(team1 >= 10) {
			JOptionPane.showMessageDialog(null, "Winner is team #1");
			team1 = 0;
			team2 = 0;
		}
		else if(team2 >= 10) {
			JOptionPane.showMessageDialog(null, "Winner is team #2");
			team1 = 0;
			team2 = 0;
		}
	}
}
