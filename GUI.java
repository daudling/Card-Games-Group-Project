import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	int width, height;
	
	JButton game1 = new JButton("Game 1");
	JButton game2 = new JButton("Game 2");
	JButton game3 = new JButton("Game 3");
	JButton game4 = new JButton("Game 4");
	JButton exit = new JButton("Exit");
	
	CardLayout layout = new CardLayout();
	
	JPanel panel = new JPanel();
	JPanel game = new  JPanel();
	JPanel menu = new JPanel();
	
	public GUI(int width, int height){
		this.width = width;
		this.height = height;
		
		panel.setLayout(layout);
		addButtons();
		
		setSize(width, height);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setTitle("Card Games!");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		requestFocus();
	}
	
	private void addButtons(){
		game1.addActionListener(this);
		game2.addActionListener(this);
		game3.addActionListener(this);
		game4.addActionListener(this);
		exit.addActionListener(this);
		
		menu.add(game1);
		menu.add(game2);
		menu.add(game3);
		menu.add(game4);
		menu.add(exit);
		
		
		game.setBackground(Color.GREEN);
		menu.setBackground(Color.MAGENTA);
		
		panel.add(menu,"Menu");
		panel.add(game, "Game");
		
		add(panel);
		layout.show(panel,"Menu");
	}
	
	

	@Override
	public void actionPerformed(ActionEvent event) {
		Object source = event.getSource();
		
		if(source == exit){
			System.exit(0);
		}else if (source == game1){
			layout.show(panel, "Game 1");
		}else if (source == game2){
			layout.show(panel, "Game 2");
		}else if (source == game2){
			layout.show(panel, "Game 3");
		}else if (source == game4){
			layout.show(panel, "Game 4");
		}
	}
		
	public static void main(String[] args) {
		JFrame gui = new JFrame();
		gui.getContentPane().add(new GUI(400,500));
	}
	
}














