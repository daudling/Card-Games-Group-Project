

import java.util.Scanner;

public class BlackJack {
	private final int LIMIT = 21;
	private boolean isTie = false;
	private boolean isStay = false;
	private int money = 1000;
	private int wager;
	private String ans = "";
	private Player dealer = new Player(0);
	private Player user = new Player(1);
	private Scanner scnr = new Scanner(System.in);

	public int getMoney(){
		return money;
	}

	private void setMoney(int amount){
		money = amount;
	}

	public void hit(Player person){
		person.addRandomCard();
	}

	public boolean checkWinner(){
		if(calcHand(user) == calcHand(dealer))
			isTie = true;

		if(calcHand(user) > calcHand(dealer))
			return true;
		else
			return false;	
	}

	public void results(boolean isWin, boolean tie){
		if(isWin){
			System.out.println("YOU WIN! You've gained " + wager + "$");
			money += wager;
		}
		else if(!isWin && !tie){
			System.out.println("YOU LOSE! You've lost " + wager + "$");
			money -= wager;
		}
		else{ 
			System.out.println("YOU TIED! Your bet was returned");
			money += 0;
		}
	}

	public int calcHand(Player person){
		int total = 0;
		int cardNum;
		int aceCount = 0;
		int i = 0;
		while(i < person.getHand().size()){
			cardNum = person.getHand().get(i).getRank();

			if(cardNum > 10 && cardNum < 14)
				total += 10;
			else if (cardNum == 14 && (total + 11) <= LIMIT){
				total += 11;
				aceCount++;
			}
			else if (cardNum == 14 && (total + 11) > LIMIT)
				total += 1;
			else
				total += cardNum;

			i++;
		}

		while(total > 21 && aceCount > 0){
			total -= 10;
			aceCount--;
		}
		return total;
	}

	public void playBlackJack (int bet){
		Deck.blackjack();
		dealer.addHand(1);
		user.addHand(2);
		wager = bet;

		user.displayHand();
		System.out.println("User:" + calcHand(user));
		dealer.displayHand();
		System.out.println("Dealer:" + calcHand(dealer));

		while(!isStay && calcHand(user) <=LIMIT){
			System.out.println("Hit or Stay?: ");
			ans = scnr.next();
			if(ans.equalsIgnoreCase("hit")){
				hit(user);
				user.displayHand();
				System.out.println("User:" + calcHand(user));
				dealer.displayHand();
				System.out.println("Dealer:" + calcHand(dealer));
			}
			else
				isStay = true;
		}

		isStay = false;

		if(calcHand(user) <= LIMIT){
			while(!isStay && calcHand(dealer) <= LIMIT){
				hit(dealer);
				user.displayHand();
				System.out.println("User:" + calcHand(user));
				dealer.displayHand();
				System.out.println("Dealer:" + calcHand(dealer));

				if(calcHand(user) <= calcHand(dealer))
					isStay = true;
			}

			user.displayHand();
			System.out.println("User:" + calcHand(user));
			dealer.displayHand();
			System.out.println("Dealer:" + calcHand(dealer));

			if(calcHand(dealer) <= LIMIT)
				results(checkWinner(), isTie);
			else
				results(true,false);
		}
		else
			results(false,false);

		System.out.println("MONEY:" + money);

	}
	
	public static void main(String [] args){
		
		
		Deck.getInstance();
		BlackJack eGame = new BlackJack();
		eGame.playBlackJack(200);
	}
}







