import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;

public class EuchreGUI extends JPanel implements ActionListener {
	
	private JLabel title;
	public static JLabel flip;
	public static JButton start, yes, no;
	public static ArrayList<JButton> bot = new ArrayList<JButton>();
	public static ArrayList<JLabel> left = new ArrayList<JLabel>();
	public static ArrayList<JLabel> top = new ArrayList<JLabel>();
	public static ArrayList<JLabel> right = new ArrayList<JLabel>();
	public ArrayList<JLabel> center = new ArrayList<JLabel>();
	public JButton one = new JButton(), two = new JButton(), three = new JButton(), four = new JButton(), five = new JButton();
	public JLabel lone = new JLabel(Deck.getBack()),
			ltwo = new JLabel(Deck.getBack()),
			lthree = new JLabel(Deck.getBack()),
			lfour = new JLabel(Deck.getBack()),
			lfive = new JLabel(Deck.getBack());
	public JLabel tone = new JLabel(Deck.getBack()),
			ttwo = new JLabel(Deck.getBack()),
			tthree = new JLabel(Deck.getBack()),
			tfour = new JLabel(Deck.getBack()),
			tfive = new JLabel(Deck.getBack());
	public JLabel rone = new JLabel(Deck.getBack()),
			rtwo = new JLabel(Deck.getBack()),
			rthree = new JLabel(Deck.getBack()),
			rfour = new JLabel(Deck.getBack()),
			rfive = new JLabel(Deck.getBack());
	public JLabel bc = new JLabel(Deck.getBack()),
			lc = new JLabel(Deck.getBack()),
			tc = new JLabel(Deck.getBack()),
			rc = new JLabel(Deck.getBack());
	
	public static Euchre game;
	private static ArrayList<Cards> hand;
	
	public EuchreGUI() {
		game = new Euchre();
		
		bot.add(one);
		bot.add(two);
		bot.add(three);
		bot.add(four);
		bot.add(five);
		left.add(lone);
		left.add(ltwo);
		left.add(lthree);
		left.add(lfour);
		left.add(lfive);
		top.add(tone);
		top.add(ttwo);
		top.add(tthree);
		top.add(tfour);
		top.add(tfive);
		right.add(rone);
		right.add(rtwo);
		right.add(rthree);
		right.add(rfour);
		right.add(rfive);
		center.add(bc);
		center.add(lc);
		center.add(tc);
		center.add(rc);
		
		setPreferredSize(new Dimension(2560, 1440));
		setLayout(new GridBagLayout());
		GridBagConstraints loc = new GridBagConstraints();
		setBackground(new Color(0, 102, 0));
		
		title = new JLabel("Euchre");
		flip = new JLabel(Deck.getBack());
		flip.setVisible(false);
		for(JButton x : bot) {
			x.setIcon(Deck.getBack());
			x.setVisible(true);
			x.setBackground(new Color(0, 102, 0));
		}
		
		for(JLabel x : left) {
			x.setVisible(true);
		}
		
		for(JLabel x : top) {
			x.setVisible(true);
		}
		
		for(JLabel x : right) {
			x.setVisible(true);
		}
		
		for(JLabel x : center) {
			x.setVisible(true);
		}
		
		start = new JButton("START");
		start.setVisible(true);
		yes = new JButton("YES");
		yes.setVisible(false);
		yes.setEnabled(false);
		no = new JButton("NO");
		no.setVisible(false);
		no.setEnabled(false);
		flip = new JLabel(Deck.getBack());
		flip.setVisible(false);
		
		loc.gridx = 0;
		loc.gridy = 0;
		loc.insets.bottom = 10;
		add(title, loc);
		loc.gridx = 3;
		loc.gridy = 3;
		add(start, loc);
		add(flip, loc);
		loc.gridx = 2;
		loc.gridy = 5;
		add(yes, loc);
		loc.gridx = 4;
		add(no, loc);
		
		loc.gridx = 1;
		loc.gridy = 7;
		for(JButton x : bot) {
			add(x, loc);
			loc.gridx++;
		}
		
		loc.gridx = 0;
		loc.gridy = 1;
		for(JLabel x : left) {
			add(x, loc);
			loc.gridy++;
		}
		
		loc.gridx = 1;
		loc.gridy = 0;
		for(JLabel x : top) {
			add(x, loc);
			loc.gridx++;
		}
		
		loc.gridx = 7;
		loc.gridy = 1;
		for(JLabel x : right) {
			add(x, loc);
			loc.gridy++;
		}
		
		loc.gridx = 3;
		loc.gridy = 4;
		add(center.get(0), loc);
		loc.gridx = 2;
		loc.gridy = 3;
		add(center.get(1), loc);
		loc.gridx = 3;
		loc.gridy = 2;
		add(center.get(2), loc);
		loc.gridx = 4;
		loc.gridy = 3;
		add(center.get(3), loc);
		
		one.addActionListener(this);
		two.addActionListener(this);
		three.addActionListener(this);
		four.addActionListener(this);
		five.addActionListener(this);
		start.addActionListener(this);
		yes.addActionListener(this);
		no.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if(source == one) {
			game.input = Euchre.hand.get(0);
			game.decided = true;
		}
		else if(source == two) {
			game.input = Euchre.hand.get(1);
			game.decided = true;
		}
		else if(source == three) {
			game.input = Euchre.hand.get(2);
			game.decided = true;
		}
		else if(source == four) {
			game.input = Euchre.hand.get(3);
			game.decided = true;
		}
		else if(source == five) {
			game.input = Euchre.hand.get(4);
			game.decided = true;
		}
		else if(source == start) {
			start();
			game.setGame(true);
			game.playEuchre();
		}
		else if(source == yes) {
			game.pickUp = true;
			game.decided = true;
			flip.setVisible(false);
		}
		else if(source == no) {
			game.pickUp = false;
			game.decided = true;
			flip.setVisible(false);
		}
	}
	
	public static void updateTable() {
		int update = 0;
		for(JButton x : bot) {
			hand = PlayerList.players.get(0).getHand();
			x.setIcon(Deck.getVisual(hand.get(update).suit, hand.get(update).rank));
			update++;
		}
		for(JLabel x : left) {
			hand = PlayerList.players.get(0).getHand();
			update = 0;
			x.setIcon(Deck.getVisual(hand.get(update).suit, hand.get(update).rank));
			update++;
		}
		for(JLabel x : top) {
			hand = PlayerList.players.get(0).getHand();
			update = 0;
			x.setIcon(Deck.getVisual(hand.get(update).suit, hand.get(update).rank));
			update++;
		}
		for(JLabel x : right) {
			hand = PlayerList.players.get(0).getHand();
			update = 0;
			x.setIcon(Deck.getVisual(hand.get(update).suit, hand.get(update).rank));
			update++;
		}
	}
	
	private void start() {
		start.setVisible(false);
		start.setEnabled(false);
		flip.setVisible(true);
	}
}