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
	
	JButton blackJack = new JButton("Black Jack");
	JButton poker = new JButton("Poker");
	JButton euchre = new JButton("Euchre");
	JButton goFish = new JButton("Go Fish!");
	JButton playerList = new JButton("Player List");
	JButton exit = new JButton("Exit");
	
	public static CardLayout layout = new CardLayout();
	
	public static JPanel panel;
	JPanel game;
	JPanel menu; 
	JPanel blackJ; 
	JPanel players;
	JPanel Euchre;
	JPanel pList;
	
	/*Creates an array list of image icons to use when displaying your cards. 
	They should correspond to the way the cards are ordered in the Deck class 
	with two more "cards" added to it. A blank card and a face down card. */
	private void createDeck(){
	    for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 13; j++) {
                switch(i) {
                case 0: visualCards.add(new ImageIcon("Cards/" + (j+2)+ "_of_spades.png"));
                break;
                case 1: visualCards.add(new ImageIcon("Cards/" + (j+2)+ "_of_clubs.png"));
                break;
                case 2: visualCards.add(new ImageIcon("Cards/" + (j+2)+ "_of_hearts.png"));
                break;
                case 3: visualCards.add(new ImageIcon("Cards/" + (j+2)+ "_of_diamonds.png"));
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
	    blackJ = new BlackJackGUI(visualCards);
	    Euchre = new EuchreGUI();
	    pList = new PlayerListGUI();
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
		blackJack.addActionListener(this);
		poker.addActionListener(this);
		euchre.addActionListener(this);
		goFish.addActionListener(this);
		playerList.addActionListener(this);
		exit.addActionListener(this);
		quitItem.addActionListener(this);
		backItem.addActionListener(this);
		
		menu.add(blackJack);
		menu.add(poker);
		menu.add(euchre);
		menu.add(goFish);
		menu.add(playerList);
		menu.add(exit);
		
		game.setBackground(new Color(0, 102, 0));
		menu.setBackground(new Color(0, 102, 0));
		
		panel.add(menu,"Menu");
		panel.add(game, "Game");
		panel.add(blackJ,"BJ");
		panel.add(Euchre, "Euchre");
		panel.add(pList, "Players");
		
		add(panel);
		layout.show(panel,"Menu");
	}
	
	private void falseEuchre() {
		int update = 0;
		for (Player x : PlayerList.players) {
			update++;
		}
		while(update < 4) {
			PlayerList.addPlayer();
			PlayerList.players.get(update).difficulty = 1;
			update++;
		}
	}
	
	private void falseBlackjack() {
		int update = 0;
		for(Player x : PlayerList.players) {
			update++;
		}
		while(update < 2) {
			PlayerList.addPlayer();
			update++;
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		
		if(source == exit){
			System.exit(0);
		}
		else if (source == blackJack){
			layout.show(panel,"BJ");
		}
		else if (source == poker){
			layout.show(panel, "Game 2");
		}
		else if (source == euchre){
			if(PlayerList.players.size() < 4) {
				falseEuchre();
			}
			euchre.removeAll();
			euchre.revalidate();
			euchre.repaint();
			layout.show(panel, "Euchre");
		}
		else if (source == goFish){
			layout.show(panel, "Game 4");
		}
		else if (source == backItem){
			EuchreGUI.game.setGame(false);
		    layout.show(panel, "Menu");
		}
		else if (source == quitItem){
		    System.exit(0);
		}
		else if (source == playerList) {
			layout.show(panel,  "Players");
		}
	}
		
	public static void main(String[] args) {
		Deck.getInstance();
		PlayerList.getInstance();
		JFrame gui = new JFrame("Card game");
		
		
		JMenuBar menuB = new JMenuBar();
	    JMenu fileMenu = new JMenu("File");
	    JMenuItem quitItem = new JMenuItem ("Exit");
	    JMenuItem backItem = new JMenuItem ("Main Menu");
	    
	    fileMenu.add(quitItem);
	    fileMenu.add(backItem);
	    gui.setJMenuBar(menuB);
	    menuB.add(fileMenu);
	    
	    GUI test = new GUI(1920,1080,quitItem, backItem);
	    
		gui.getContentPane().add(test);
		
		gui.setSize(test.getWidth(), test.getHeight());
		gui.setResizable(true);
		gui.setLocationRelativeTo(null);
		gui.setVisible(true);
		gui.setTitle("Card Games!");
		gui.setDefaultCloseOperation(gui.EXIT_ON_CLOSE);
		gui.requestFocus();
	}
	
}
