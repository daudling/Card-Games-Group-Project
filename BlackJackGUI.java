import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.*; 
import java.util.ArrayList;

import javax.imageio.*; 

import java.io.*;  

public class BlackJackGUI extends JPanel implements ActionListener {

    private JLabel title;
    BlackJack game;
    Player    user;
    private JLabel money;
    private JLabel bet;
    
    private JButton hitButton;
    private JButton stayButton;
    private JButton playButton;
    private JTextField wager;
    
    private ArrayList<JLabel> playerHand = new ArrayList<JLabel>();
    private ArrayList<JLabel> dealerHand = new ArrayList<JLabel>();
    private ArrayList<ImageIcon> visualDeck = new ArrayList<ImageIcon>();


    public BlackJackGUI(ArrayList<ImageIcon> vDeck){
        user = PlayerList.players.get(0);
        game = new BlackJack(user);
        
        visualDeck = vDeck;
        
        setPreferredSize(new Dimension(400,500));
        
        setLayout(new GridBagLayout());
        GridBagConstraints loc = new GridBagConstraints();
        

        title = new JLabel("BLACK JACK");
        money = new JLabel("Money:" + user.money);
        bet = new JLabel("Bet:");
        
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
        
        for(int i = 0; i < 3; i++){
            playerHand.add(new JLabel(visualDeck.get(52)));
            loc.gridx = i;
            loc.gridy = 3;
            add(playerHand.get(i),loc);
        }
        
        hitButton.addActionListener(this);
        stayButton.addActionListener(this);
        playButton.addActionListener(this);
        setBackground(new Color(0,102,0));


    }

    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();
        
        if(source == playButton){
            Deck.getInstance();
            int amt = Integer.parseInt(wager.getText());
            game.playGameGUIUser(amt);
            System.out.println("PLAY");
        }
        
        else if(source == hitButton){
            game.hit(user);
        }
        else if(source == stayButton){
            game.isStay = true;
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
