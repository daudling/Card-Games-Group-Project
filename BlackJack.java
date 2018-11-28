

package project;

import java.util.Scanner;

public class BlackJack {
    private final int LIMIT = 21;
    private boolean isTie = false;
    boolean isStay = false;
    private int money = 1000;
    private int wager;
    private String ans = "";
    private Player dealer = new Player(1);
    private Player user;
    private Scanner scnr = new Scanner(System.in);

    public int getMoney(){
        return money;
    }

    public BlackJack(Player person){
        user = person;
        money = user.money;
    }

    public BlackJack(){
        user = new Player(0);
        money = 1000;
    }

    private void setMoney(int amount){
        money = amount;
    }

    public void hit(Player person){
        person.addRandomCard();
        user.displayHand();
        System.out.println("User:" + calcHand(user));
        dealer.displayHand();
        System.out.println("Dealer:" + calcHand(dealer));
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

    public void playGame (int bet){
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
        ans = "";
        user.hand = null;
        dealer.hand = null;
        isStay = false;
        isTie = false;

    }

    public void playBlackJack(){
        ans = "";
        while(!ans.equalsIgnoreCase("no") && money > 0){
            wager = 0;
            System.out.println("Would you like to play Black Jack?");
            System.out.println("Type Yes or No");
            ans = scnr.next();
            if(ans.equalsIgnoreCase("yes")){
                System.out.println("How much would you like to wager?");
                while(wager > money || wager <= 0){
                    System.out.println("Bet: ");
                    wager = scnr.nextInt();
                    if(wager > money)
                        System.out.println("You can't bet more than what you have!");
                    else if(wager <= 0)
                        System.out.println("You have to bet something to play!");
                }
                playGame(wager);
            }else if(ans.equalsIgnoreCase("no"))
                System.out.println("Thanks for Playing!");
            else
                System.out.println("INVALID:Please answer yes or no.");
        }

    }
    
    public void playGameGUIUser(int bet){
        Deck.blackjack();
        dealer.addHand(1);
        user.addHand(2);
        wager = bet;

        user.displayHand();
        System.out.println("User:" + calcHand(user));
        dealer.displayHand();
        System.out.println("Dealer:" + calcHand(dealer));
        
    }
    public void playGameGUIDealer(int bet){    
        isStay = false;
        
        if(calcHand(user) <= LIMIT){
            while(!isStay && calcHand(dealer) <= LIMIT){
                hit(dealer);
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
        user.hand = null;
        dealer.hand = null;
        isStay = false;
        isTie = false;

    }

    public static void main(String [] args){
        Deck.getInstance();
        BlackJack eGame = new BlackJack();
        eGame.playBlackJack();
    }
}

