import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class PlayerListGUI extends JPanel implements ActionListener {

	private JLabel title, diff, one, two, three, four;
	private JButton add, edit, remove, back;
	private JRadioButton player, easy, medium, hard;
	private JRadioButton bone, btwo, bthree, bfour;
	private ButtonGroup dif, pSelect;
	private int d = 0, update = 1, p = 0;
	private Color surface = new Color(0, 102, 0);
	
	public PlayerListGUI() {

		setPreferredSize(new Dimension(1280, 720));
		setLayout(new GridBagLayout());
		GridBagConstraints loc = new GridBagConstraints();
		setBackground(new Color(0, 102, 0));
		
		title = new JLabel("Player List");
		diff = new JLabel("Difficulty");
		one = new JLabel("Player 1:");
		two = new JLabel("Player 2:");
		three = new JLabel("Player 3");
		four = new JLabel("Player 4");
		add = new JButton("Add");
		edit = new JButton("Edit");
		remove = new JButton("Remove");
		back = new JButton("Back");
		player = new JRadioButton("Player");
		easy = new JRadioButton("Easy");
		medium = new JRadioButton("Medium");
		hard = new JRadioButton("Hard");
		bone = new JRadioButton();
		btwo = new JRadioButton();
		bthree = new JRadioButton();
		bfour = new JRadioButton();
		
		dif = new ButtonGroup();
		dif.add(player);
		dif.add(easy);
		dif.add(medium);
		dif.add(hard);
		player.setSelected(true);
		player.setBackground(surface);
		easy.setBackground(surface);
		medium.setBackground(surface);
		hard.setBackground(surface);
		
		pSelect = new ButtonGroup();
		pSelect.add(bone);
		pSelect.add(btwo);
		pSelect.add(bthree);
		pSelect.add(bfour);
		bone.setSelected(true);
		bone.setBackground(surface);
		btwo.setBackground(surface);
		bthree.setBackground(surface);
		bfour.setBackground(surface);
		
		add.addActionListener(this);
		edit.addActionListener(this);
		remove.addActionListener(this);
		back.addActionListener(this);
		player.addActionListener(this);
		easy.addActionListener(this);
		medium.addActionListener(this);
		hard.addActionListener(this);
		bone.addActionListener(this);
		btwo.addActionListener(this);
		bthree.addActionListener(this);
		bfour.addActionListener(this);
		
		loc.gridx = 0;
		loc.gridy = 0;
		loc.insets = new Insets(10, 10, 10, 10);
		add(title, loc);
		
		loc.gridy = 1;
		add(diff, loc);
		loc.gridy = 2;
		add(player, loc);
		loc.gridy = 3;
		add(easy, loc);
		loc.gridy = 4;
		add(medium, loc);
		loc.gridy = 5;
		add(hard, loc);
		
		loc.gridx = 5;
		loc.gridy = 2;
		add(bone, loc);
		loc.gridy = 3;
		add(btwo, loc);
		loc.gridy = 4;
		add(bthree, loc);
		loc.gridy = 5;
		add(bfour, loc);
		
		loc.gridx = 2;
		loc.gridy = 0;
		add(add, loc);
		loc.gridx = 3;
		add(edit, loc);
		loc.gridx = 4;
		add(remove, loc);
		loc.gridx = 5;
		add(back, loc);
		
		loc.gridx = 2;
		loc.gridy = 2;
		loc.gridwidth = 3;
		add(one, loc);
		loc.gridy = 3;
		add(two, loc);
		loc.gridy = 4;
		add(three, loc);
		loc.gridy = 5;
		add(four, loc);
		
		updateList();
	}
	
	private void updateList() {
		update = 1;
		one.setText("Player 1: ");
		two.setText("Player 2: ");
		three.setText("Player 3: ");
		four.setText("Player 4: ");
		for(Player x : PlayerList.players) {
			switch(update) {
			case 1:
				one.setText("Player " + x.playerNum + ": Difficulty - " + x.difficulty);
				break;
			case 2:
				two.setText("Player " + x.playerNum + ": Difficulty - " + x.difficulty);
				break;
			case 3:
				three.setText("Player " + x.playerNum + ": Difficulty - " + x.difficulty);
				break;
			case 4:
				four.setText("Player " + x.playerNum + ": Difficulty - " + x.difficulty);
				break;
			}
			update++;
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source == add) {
			PlayerList.addPlayer();
			updateList();
		}
		else if(source == edit) {
			if(!PlayerList.players.isEmpty()) {
				PlayerList.players.get(p).difficulty = d;
				updateList();
			}
		}
		else if(source == remove) {
			if(p < PlayerList.players.size()) {
				PlayerList.removePlayer(p);
				updateList();
			}
		}
		else if(source == back) {
			GUI.layout.show(GUI.panel,  "Menu");
		}
		else if(source == player) {
			d = 0;
		}
		else if(source == easy) {
			d = 1;
		}
		else if(source == medium) {
			d = 2;
		}
		else if(source == hard) {
			d = 3;
		}
		else if(source == bone) {
			p = 0;
		}
		else if(source == btwo) {
			p = 1;
		}
		else if(source == bthree) {
			p = 2;
		}
		else if(source == bfour) {
			p = 3;
		}
	}
}
