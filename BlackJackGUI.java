import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*; 
import java.util.ArrayList;

import javax.imageio.*; 

import java.io.*;  

public class BlackJackGUI extends JPanel implements ActionListener {

    private boolean isTurn = false;
    BlackJack game;
    Player    user;
    private JLabel title;
    private JLabel money;
    private JLabel bet;
    private JLabel userAmt;
    private JLabel dealerAmt;
    
    private int amt;
    private final int LIMIT = 21;
    
    private JButton hitButton;
    private JButton stayButton;
    private JButton playButton;
    private JTextField wager;
    
    private ArrayList<JLabel> playerHand = new ArrayList<JLabel>();
    private ArrayList<JLabel> dealerHand = new ArrayList<JLabel>();
    private ArrayList<ImageIcon> visualDeck;


    public BlackJackGUI(Player player, ArrayList<ImageIcon> vDeck){
        user = player;
        game = new BlackJack(user);
        
        visualDeck = vDeck;
        
        setPreferredSize(new Dimension(800,600));
        
        setLayout(new GridBagLayout());
        GridBagConstraints loc = new GridBagConstraints();
        

        title = new JLabel("BLACK JACK");
        money = new JLabel("Money:" + user.money);
        bet = new JLabel("Bet:");
        userAmt = new JLabel("You: ");
        dealerAmt = new JLabel ("Dealer: ");
        
        hitButton = new JButton("Hit");
        stayButton = new JButton("Stay");
        playButton = new JButton("Play");

        wager = new JTextField(5);

        loc.gridx = 0;
        loc.gridy = 0;
        loc.insets.bottom = 10;
        add(title, loc);

        loc.gridx = 0;
        loc.gridy = 2;
        add(hitButton, loc);

        loc.gridx = 2;
        loc.gridy = 2;
        loc.insets.left = 8;
        add(stayButton, loc);

        loc.gridx = 2;
        loc.gridy = 0;
        add(money, loc);
        
        loc.gridx= 0;
        loc.gridy = 1;
        loc.insets.left = 0;
        add(bet,loc);
        
        loc.gridx = 1;
        loc.gridy = 1;
        add(wager,loc);
       
        loc.gridx = 1;
        loc.gridy = 2;

        add(playButton,loc);
        
        loc.gridx = 0;
        loc.gridy = 3;
        
        add(userAmt, loc);
        
        loc.gridy = 4;
        add(dealerAmt, loc);
        
        for(int i = 1; i < 8; i++){
            playerHand.add(new JLabel(visualDeck.get(53)));
            dealerHand.add(new JLabel(visualDeck.get(53)));
            loc.gridx = i;
            loc.gridy = 3;
            add(playerHand.get(i-1),loc);
            loc.gridy = 4;
            add(dealerHand.get(i-1),loc);
        }
        
        hitButton.addActionListener(this);
        stayButton.addActionListener(this);
        playButton.addActionListener(this);
        setBackground(new Color(0,99,0));  //Sets the background to a dark green color.
    }
    
    
    //What I use to display the cards.
    public void displayCards(){
        int cardID;
        int suitMultiplyer = 1;
        
        
        int handSize = game.user.hand.size();
        userAmt.setText("You: " + game.calcHand(game.user));
        dealerAmt.setText("Dealer: " + game.calcHand(game.dealer));
        for(int i = 0; i < handSize; i++){
            switch(game.user.hand.get(i).suit){
            case "Spades": suitMultiplyer = 0;
            break;
            case "Clubs": suitMultiplyer = 1;
            break;
            case "Hearts": suitMultiplyer = 2;
            break;
            case "Diamonds": suitMultiplyer = 3;
            break;
            } 
            
            cardID = game.user.hand.get(i).rank + suitMultiplyer * 13 - 2; //FORMULA TO GET THE RIGHT CARD ID.
            playerHand.get(i).setIcon(visualDeck.get(cardID));
        }
        for(int j = handSize; j < 7; j++){
            playerHand.get(j).setIcon(visualDeck.get(53));
        }
        handSize = game.dealer.hand.size();
        
        for(int i = 0; i < handSize; i++){
            switch(game.dealer.hand.get(i).suit){
            case "Spades": suitMultiplyer = 0;
            break;
            case "Clubs": suitMultiplyer = 1;
            break;
            case "Hearts": suitMultiplyer = 2;
            break;
            case "Diamonds": suitMultiplyer = 3;
            break;
            } 
            
            cardID = game.dealer.hand.get(i).rank + suitMultiplyer * 13 - 2; //FORMULA TO GET THE RIGHT CARD ID.
            dealerHand.get(i).setIcon(visualDeck.get(cardID));
        }
        for(int j = handSize; j < 7; j++){
            dealerHand.get(j).setIcon(visualDeck.get(53));
        }
    }
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();
        
        if(source == playButton && isTurn == false){
            Deck.getInstance();
            int amt = Integer.parseInt(wager.getText());
            game.playGameGUIUser(amt);
            displayCards();
            dealerHand.get(1).setIcon(visualDeck.get(52));
            isTurn = true;
        }
        
        else if(source == hitButton && isTurn == true){
            if(game.calcHand(game.user) <= LIMIT)
                game.hit(user);
            displayCards();
            dealerHand.get(1).setIcon(visualDeck.get(52));
        }
        else if((source == stayButton || game.calcHand(game.user) > LIMIT) && isTurn == true){
            game.playGameGUIDealer(amt);
            if(game.calcHand(game.user) < LIMIT)
                displayCards();
            
            money.setText("Money:" + user.money);
            game.reset();
            isTurn = false;
        }
    }

//    public static void main(String[] args) {
//        Player testPerson = new Player(0);
//        JFrame frame = new JFrame ("CardGUITest");
//        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
//        BlackJackGUI panel = new BlackJackGUI(testPerson);
//
//        frame.getContentPane().add(panel);
//        frame.setSize (600,800);
//        frame.setVisible(true);
//
//    }

}
