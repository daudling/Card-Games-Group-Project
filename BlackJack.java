import java.util.Scanner;
import javax.swing.JOptionPane;

public class BlackJack {
    private final int LIMIT = 21;
    public boolean isTie = false;
    boolean isStay = false;
    public int money = 1000;
    private int wager;
    private String ans = "";
    Player dealer = new Player();
    Player user;
    private Scanner scnr = new Scanner(System.in);

    public int getMoney(){
        return money;
    }

    public BlackJack(Player person){
        user = person;
        money = user.money;
    }

    public BlackJack(){
        user = new Player();
        money = 1000;
    }

    private void setMoney(int amount){
        money = amount;
    }
    
    /**
     * Adds a card to either the dealer or the user.
     * @param person - The player who gets the card.
     */
    public void hit(Player person){
        person.addRandomCard();
        user.displayHand();
        System.out.println("User:" + calcHand(user));
        dealer.displayHand();
        System.out.println("Dealer:" + calcHand(dealer));
    }
    
    /**
     * Determines the winner for Black Jack.
     * @return - True if the player wins, false if they lose.
     */
    public boolean checkWinner(){
        if(calcHand(user) <= LIMIT){
            if(calcHand(user) == calcHand(dealer))
                isTie = true;

            if(calcHand(user) > calcHand(dealer))
                return true;
            else
                return false;	
        }
        else
            return false;
    }
    
    /**
     * Gives or takes away money from the player depending on if they win or not.
     * @param isWin - Uses check winner to determine that the player has won.
     * @param tie - Checks to see if there is a tie.
     */
    public void results(boolean isWin, boolean tie){
        if(isWin){
            JOptionPane.showMessageDialog(null,"YOU WIN! You've gained " + wager + "$");
            money += wager;
        }
        else if(!isWin && !tie){
            JOptionPane.showMessageDialog(null,"YOU LOSE! You've lost " + wager + "$");
            money -= wager;
        }
        else{ 
            JOptionPane.showMessageDialog(null,"YOU TIED! Your bet was returned");
            money += 0;
        }
    }
    /**
     * Calculates the score based on what the player or dealer has.
     * @param person - Either the dealer or the user.
     * @return
     */
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
    /**
     * Plays the text based version of a game of Black Jack without the GUI.
     * @param bet - The amount the user is willing to bet.
     */
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
        reset();

    }
    /**
     * Plays a full text based version of Black Jack where
     * you can continue playing after either winning or losing.
     */
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
    
    /**
     * Plays Black Jack with the use of a GUI. This part starts the game when the 
     * user clicks on the "Play Button". 
     * @param bet - The amount that the user wants to bet. 
     */
    public void playGameGUIUser(int bet){
        Deck.blackjack();
        dealer.addHand(1);
        user.addHand(2);
        wager = bet;
    }
    
    /**
     * Plays Black Jack with the use of a GUI. This part ends the game when the user
     * clicks on the "Stay Button" and the dealer takes his turn.
     * @param bet - The amount that the user wants to bet.
     */
    public void playGameGUIDealer(int bet){    
        isStay = false;
        
        if(calcHand(user) <= LIMIT){
            while(!isStay && calcHand(dealer) <= LIMIT){
                hit(dealer);
                if(calcHand(user) <= calcHand(dealer))
                    isStay = true;
            }
        }
    }
    
    /**
     * Resets the game by making the user and dealer's hand null and makes everything return to false.
     */
    public void reset(){
        user.hand = null;
        dealer.hand = null;
        isStay = false;
        isTie = false;
    }
    
    /**
     * The main for Black Jack that runs the text version.
     * @param args
     */
    public static void main(String [] args){
        Deck.getInstance();
        BlackJack eGame = new BlackJack();
        eGame.playBlackJack();
    }
}
