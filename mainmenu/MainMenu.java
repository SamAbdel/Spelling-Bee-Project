package mainmenu;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import hivegame.HiveGame;
import leaderboard.Leaderboard;
import userscores.UserScore;



public class MainMenu{
	
	public static void main(String args[]) {
		JFrame frame;
	     
			frame = new MenuPane();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	       // frame.pack();
	        frame.setBounds(100, 100, 500, 600);
	        frame.getContentPane().setBackground(Color.GREEN);
	        frame.setVisible(true);
	        
	
			
		}
	}

class MenuPane extends JFrame implements ActionListener{
	
	private JButton game = new JButton("Game");
	private JButton board = new JButton("Leaderboard");
	private JButton Score = new JButton("User Scoreboard");
	
	public MenuPane() {
		Container c = new Container();
		
		JPanel menu = new JPanel(new GridLayout(0,1));
		JLabel label = new JLabel("Hive Game");
		Font font = new Font("Courier", Font.BOLD, 18);
		menu.setBounds(0, 0, 500, 500);
		label.setFont(font);
		menu.setBackground(Color.YELLOW);
		
		game.addActionListener(this);
		board.addActionListener(this);
		Score.addActionListener(this);
		
		menu.add(label);
		menu.add(game);
		menu.add(board);
		menu.add(Score);
		
		c.add(menu);
		this.add(c);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == game) {
			HiveGame g = new HiveGame();
			g.game();
		}
		if(e.getSource() == board) {
			Leaderboard b = new Leaderboard();
			b.board();
			
		}
		if(e.getSource() == Score) {
			UserScore u = new UserScore();
			u.user();
		}
	}
	}
	