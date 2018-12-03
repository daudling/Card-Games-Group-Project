


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GUI extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	
	int width, height;
	ArrayList<ImageIcon> visualCards = new ArrayList<ImageIcon>();
	//Deck playDeck = new Deck();
	JMenuItem quitItem;
	JMenuItem backItem;
	
	JButton game1 = new JButton("Black Jack");
	JButton game2 = new JButton("Poker");
	JButton game3 = new JButton("Euchre");
	JButton game4 = new JButton("Go Fish!");
	JButton playerList = new JButton("Player List");
	JButton exit = new JButton("Exit");
	
	CardLayout layout = new CardLayout();
	
	JPanel panel;
	JPanel game;
	JPanel menu; 
	JPanel blackJ; 
	JPanel players;
	JPanel Euchre;
	
	/*Creates an array list of image icons to use when displaying your cards. 
	They should correspond to the way the cards are ordered in the Deck class 
	with two more "cards" added to it. A blank card and a face down card. */
	private void createDeck(){
	    for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 13; j++) {
                switch(i) {
                case 0: visualCards.add(new ImageIcon((j+2)+ "_of_spades.png"));
                break;
                case 1: visualCards.add(new ImageIcon((j+2)+ "_of_clubs.png"));
                break;
                case 2: visualCards.add(new ImageIcon((j+2)+ "_of_hearts.png"));
                break;
                case 3: visualCards.add(new ImageIcon((j+2)+ "_of_diamonds.png"));
                break;
                }
            }
        }
	    visualCards.add(new ImageIcon("FaceDown.png")); //52
	    visualCards.add(new ImageIcon("Blank.png"));    //53
	    
	}
	

	public GUI(int width, int height,JMenuItem dquitItem,JMenuItem dbackItem){
	    createDeck();
		this.width = width;
		this.height = height;
		quitItem = dquitItem;
		backItem = dbackItem;
		panel = new JPanel();
	    game = new  JPanel();
	    menu = new JPanel();
		panel.setLayout(layout);
		
		
		addButtons();

		
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	private void addButtons(){
		game1.addActionListener(this);
		game2.addActionListener(this);
		game3.addActionListener(this);
		game4.addActionListener(this);
		playerList.addActionListener(this);
		exit.addActionListener(this);
		quitItem.addActionListener(this);
		backItem.addActionListener(this);
		
		menu.add(game1);
		menu.add(game2);
		menu.add(game3);
		menu.add(game4);
		menu.add(playerList);
		menu.add(exit);
		
		game.setBackground(Color.GREEN);
		menu.setBackground(Color.BLUE);
		
		panel.add(menu,"Menu");
		panel.add(game, "Game");
//		panel.add(blackJ,"BJ");
		
		add(panel);
		layout.show(panel,"Menu");
	}
	
	

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		
		if(source == exit){
			System.exit(0);
		}else if (source == game1){
			layout.show(panel,"BJ");
		}else if (source == game2){
			layout.show(panel, "Game 2");
		}else if (source == game2){
			layout.show(panel, "Game 3");
		}else if (source == game4){
			layout.show(panel, "Game 4");
		}else if (source == backItem){
		    layout.show(panel, "Menu");
		}else if (source == quitItem){
		    System.exit(0);
		}else if (source == playerList) {
			layout.show(panel,  "players");
		}
	}
		
	public static void main(String[] args) {
		JFrame gui = new JFrame("Card game");
		
		
		JMenuBar menuB = new JMenuBar();
	    JMenu fileMenu = new JMenu("File");
	    JMenuItem quitItem = new JMenuItem ("Exit");
	    JMenuItem backItem = new JMenuItem ("Leave Game");
	    
	    fileMenu.add(quitItem);
	    fileMenu.add(backItem);
	    gui.setJMenuBar(menuB);
	    menuB.add(fileMenu);
	    
	    GUI test = new GUI(1080,720,quitItem, backItem);
	    
		gui.getContentPane().add(test);
		
		gui.setSize(test.getWidth(), test.getHeight());
		gui.setResizable(false);
		gui.setLocationRelativeTo(null);
		gui.setVisible(true);
		gui.setTitle("Card Games!");
		gui.setDefaultCloseOperation(gui.EXIT_ON_CLOSE);
		gui.requestFocus();
	}
	
}
