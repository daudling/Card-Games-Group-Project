import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class EuchreGUI extends JPanel implements ActionListener {
	
	private JLabel title;
	private JButton one, two, three, four, five;
	Euchre game;
	
	public EuchreGUI() {
		game = new Euchre();
		game.playEuchre();
		
		setPreferredSize(new Dimension(1080, 720));
		
		setLayout(new GridBagLayout());
		GridBagConstraints loc = new GridBagConstraints();
		
		title = new JLabel("Euchre");
		one = new JButton();
		two = new JButton();
		three = new JButton();
		four = new JButton();
		five = new JButton();
		
		loc.gridx = 0;
		loc.gridy = 0;
		loc.insets.bottom = 10;
		add(title, loc);
		
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
		
		one.addActionListener(this);
		two.addActionListener(this);
		three.addActionListener(this);
		four.addActionListener(this);
		five.addActionListener(this);
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
	}
}