import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class EuchreGUI extends JPanel implements ActionListener {
	
	private JLabel title;
	public JButton one, two, three, four, five, start;
	public JLabel lone, ltwo, lthree, lfour, lfive;
	public JLabel tone, ttwo, tthree, tfour, tfive;
	public JLabel rone, rtwo, rthree, rfour, rfive;
	public JLabel bc, lc, tc, rc;
	
	Euchre game;
	
	public EuchreGUI() {
		game = new Euchre();
		
		setPreferredSize(new Dimension(1280, 720));
		setLayout(new GridBagLayout());
		GridBagConstraints loc = new GridBagConstraints();
		setBackground(new Color(0, 102, 0));
		
		title = new JLabel("Euchre");
		one = new JButton();
		two = new JButton();
		three = new JButton();
		four = new JButton();
		five = new JButton();
		start = new JButton();
		one.setVisible(false);
		two.setVisible(false);
		three.setVisible(false);
		four.setVisible(false);
		five.setVisible(false);
		
		lone = new JLabel(Deck.getBack());
		ltwo = new JLabel(Deck.getBack());
		lthree = new JLabel(Deck.getBack());
		lfour = new JLabel(Deck.getBack());
		lfive = new JLabel(Deck.getBack());
		lone.setVisible(false);
		ltwo.setVisible(false);
		lthree.setVisible(false);
		lfour.setVisible(false);
		lfive.setVisible(false);
		
		tone = new JLabel(Deck.getBack());
		ttwo = new JLabel(Deck.getBack());
		tthree = new JLabel(Deck.getBack());
		tfour = new JLabel(Deck.getBack());
		tfive = new JLabel(Deck.getBack());
		tone.setVisible(false);
		ttwo.setVisible(false);
		tthree.setVisible(false);
		tfour.setVisible(false);
		tfive.setVisible(false);
		
		rone = new JLabel(Deck.getBack());
		rtwo = new JLabel(Deck.getBack());
		rthree = new JLabel(Deck.getBack());
		rfour = new JLabel(Deck.getBack());
		rfive = new JLabel(Deck.getBack());
		rone.setVisible(false);
		rtwo.setVisible(false);
		rthree.setVisible(false);
		rfour.setVisible(false);
		rfive.setVisible(false);
		
		bc = new JLabel(Deck.getBack());
		lc = new JLabel(Deck.getBack());
		tc = new JLabel(Deck.getBack());
		rc = new JLabel(Deck.getBack());
		bc.setVisible(false);
		lc.setVisible(false);
		tc.setVisible(false);
		rc.setVisible(false);
		
		start.setVisible(true);
		
		loc.gridx = 0;
		loc.gridy = 0;
		loc.insets.bottom = 10;
		add(title, loc);
		loc.gridx = 3;
		loc.gridy = 3;
		add(start, loc);
		
		loc.gridx = 1;
		loc.gridy = 7;
		add(one, loc);
		loc.gridx = 2;
		add(two, loc);
		loc.gridx = 3;
		add(three, loc);
		loc.gridx = 4;
		add(four, loc);
		loc.gridx = 5;
		add(five, loc);
		
		loc.gridx = 0;
		loc.gridy = 1;
		add(lone);
		loc.gridy = 2;
		add(ltwo);
		loc.gridy = 3;
		add(lthree);
		loc.gridy = 4;
		add(lfour);
		loc.gridy = 5;
		add(lfive);
		
		loc.gridx = 1;
		loc.gridy = 0;
		add(tone, loc);
		loc.gridx = 2;
		add(ttwo, loc);
		loc.gridx = 3;
		add(tthree, loc);
		loc.gridx = 4;
		add(tfour, loc);
		loc.gridx = 5;
		add(tfive, loc);
		
		loc.gridx = 7;
		loc.gridy = 1;
		add(rone, loc);
		loc.gridy = 2;
		add(rtwo, loc);
		loc.gridy = 3;
		add(rthree, loc);
		loc.gridy = 4;
		add(rfour, loc);
		loc.gridy = 5;
		add(rfive, loc);
		
		loc.gridx = 3;
		loc.gridy = 4;
		add(bc, loc);
		loc.gridx = 2;
		loc.gridy = 4;
		add(lc, loc);
		loc.gridx = 3;
		loc.gridy = 2;
		add(tc, loc);
		loc.gridx = 4;
		loc.gridy = 3;
		add(rc, loc);
		
		one.addActionListener(this);
		two.addActionListener(this);
		three.addActionListener(this);
		four.addActionListener(this);
		five.addActionListener(this);
		start.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if(source == one) {
			
		}
		else if(source == two) {
			
		}
		else if(source == three) {
			
		}
		else if(source == four) {
			
		}
		else if(source == five) {
			
		}
		else if(source == start) {
			game.playEuchre();
		}
	}
	
	private void start() {
		one.setVisible(true);
		two.setVisible(true);
		three.setVisible(true);
		four.setVisible(true);
		five.setVisible(true);
		
		lone.setVisible(true);
		ltwo.setVisible(true);
		lthree.setVisible(true);
		lfour.setVisible(true);
		lfive.setVisible(true);
		
		tone.setVisible(true);
		ttwo.setVisible(true);
		tthree.setVisible(true);
		tfour.setVisible(true);
		tfive.setVisible(true);
		
		rone.setVisible(true);
		rtwo.setVisible(true);
		rthree.setVisible(true);
		rfour.setVisible(true);
		rfive.setVisible(true);
		
		bc.setVisible(true);
		lc.setVisible(true);
		tc.setVisible(true);
		rc.setVisible(true);
		
		start.setVisible(false);
		start.disable();
	}
}