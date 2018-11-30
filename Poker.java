import java.util.Scanner;

//Current build of this class can only have one player and may have bugs when calculating hand value, this will be fixed in the next version
public class Poker {

	private static int chips = 500;
	private double pot;
	private int bet;
	private double score = 0;
	private int totalBet = 0;
	private int tableBet = 0;
	private Player dealer = new Player(0);
	private Player p1 = new Player(1);
	Cards[] finale = new Cards[7];
	private static Scanner scan = new Scanner(System.in);
	private static String action = "";
	private boolean fold = false;
	private int singles = 0, pairs = 0, triples = 0, quads = 0;
	int[] rankCount = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	int[] suitCount = { 0, 0, 0, 0 };

	public void setBet(int c) {
		bet = c;
	}

	public int getChips() {
		return chips;
	}

	public void turnAction(String action) {
		switch (action) {
		case "check":
			break;
		case "bet":
			System.out.println("How many chips do you want to wager?");
			bet = scan.nextInt();
			if (bet > chips) {
				bet = chips;
			}
			totalBet += bet;
			pot += bet;
			chips -= bet;
			break;
		case "fold":
			fold = true;
			totalBet = 0;
			bet = 0;
			break;
		case "call":
			bet += tableBet;
			if (bet > chips) {
				bet = chips;
			}
			totalBet += bet;
			pot += bet;
			chips -= bet;
			break;
		}
	}

	public void round() {
		if (!fold && chips != 0) {
			System.out.print("Table cards: ");
			dealer.displayHand();
			System.out.println();
			p1.displayHand();
			bet = 0;
			System.out.println(getChips() + " chips remaining");
			System.out.println("Current Pot: " + pot);
			System.out.println("Current table bet: " + tableBet);
			System.out.println("Check, Fold, Call, or Bet?");
			action = scan.next();
			turnAction(action);
			System.out.println(getChips() + " chips remaining");
			System.out.println("Current Pot: " + pot);
			bet = 0;
			tableBet = 0;
			System.out.println("Current Bet: " + totalBet);
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();

		}
	}

	public void createFinalHand(Player p) {
		for (int i = 0; i < p.hand.size(); i++) {
			finale[i] = new Cards(p.hand.get(i).suit, p.hand.get(i).rank);
		}

	}

	public void createFinal2(Player p) {
		int x = 5;
		for (int i = 0; i < p.hand.size(); i++) {
			finale[x] = new Cards(p.hand.get(i).suit, p.hand.get(i).rank);
			x++;
		}

	}

	public void startGame() {
		Deck.poker();
		score = 0;
		dealer.addHand(0);
		p1.addHand(2);
		fold = false;
		round();
		dealer.addRandomCard();
		dealer.addRandomCard();
		dealer.addRandomCard();
		round();
		dealer.addRandomCard();
		round();
		dealer.addRandomCard();
		round();
		createFinalHand(dealer);
		createFinal2(p1);
		counter();
		contains();
		dealer.displayHand();
		System.out.println();
		p1.displayHand();
		result();
		chips += pot * ((score / 10) + 1);
		System.out.println("winnings: " + pot * ((score / 10) + 1));
	}

	public void contains() {
		for (int i = 0; i < rankCount.length; i++) {
			if (rankCount[i] == 4) {
				quads++;
			} else if (rankCount[i] == 3) {
				triples++;
			} else if (rankCount[i] == 2) {
				pairs++;
			}
		}
	}

	public void counter() {

		for (int i = 0; i < finale.length; i++) {
			rankCount[finale[i].getRank()]++;

			switch (finale[i].getSuit()) {
			case "Diamonds":
				suitCount[0]++;
				break;
			case "Hearts":
				suitCount[1]++;
				break;
			case "Clubs":
				suitCount[2]++;
				break;
			case "Spades":
				suitCount[3]++;
				break;
			}
		}
	}

	public void result() {
		boolean flush = false;
		boolean straight = false;
		int sequence = 1;
		for (int i = 0; i < suitCount.length; i++) {
			if (suitCount[i] >= 5) {
				flush = true;
			}
		}
		for (int i = 0; i < rankCount.length; i++) {
			if (sequence != 5) {
				if (rankCount[i] != 0 && rankCount[i + 1] != 0) {
					sequence++;
				} else {
					sequence = 1;
				}
			} else {
				straight = true;
			}
		}

		if (flush && straight) {
			System.out.println("Straight Flush!");
			score += 10;
		} else if (quads >= 1) {
			System.out.println("Four of a Kind!");
			score += 8;
		} else if (triples >= 1 && pairs >= 1) {
			System.out.println("Full House!");
			score += 7;
		} else if (flush) {
			System.out.println("Flush!");
			score += 6;
		} else if (straight) {
			System.out.println("Flush!");
			score += 4;
		} else if (triples >= 1) {
			System.out.println("Three of a Kind!");
			score += 3;
		} else if (pairs >= 2) {
			System.out.println("Two Pair!");
			score += 2;
		} else if (pairs == 1) {
			System.out.println("One Pair!");
			score += 1;
		} else {
			System.out.println("Too Bad!");
			score = 0;
		}
	}

	public static void main(String[] args) {
		Deck.getInstance();
		Poker Game = new Poker();
		boolean play = true;
		while (play) {
			Game.startGame();
			System.out.println("Play another hand? (y/n): ");
			action = scan.next();
			if (action.equals("y")) {
				play = true;
			} else {
				play = false;
			}
		}
		System.out.println("Final Chip Total: " + chips);
	}
}