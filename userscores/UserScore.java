package userscores;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


import query.SQLQuery;

public class UserScore {
	
	
	  public static void user() {
	    EventQueue.invokeLater(new Runnable() {
	      public void run() {
	        JFrame frame;
				try {
					frame = new UserPane();
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			        //frame.pack();
			        frame.setBounds(100, 100, 1000, 500);
			       
			        frame.setVisible(true);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
	      }
	    });
	  }
	}

class UserPane extends JFrame{
	
	int count = 0;
	Map<String, Integer> HighScores = new HashMap<String, Integer>();
	private static JTable Scores = new JTable(new DefaultTableModel(new String[]{"Names","Scores"}, 0));
	
	UserPane() throws SQLException{
		
		
		Container c = new Container();
		
		JTextField f = new JTextField("Enter Name");
		JPanel name = new JPanel(new FlowLayout());
		
		DefaultTableModel model = (DefaultTableModel) Scores.getModel();
		ActionListener actionListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				
				int[] score = new int[10];
				model.setRowCount(0);
				try {
					
					SQLQuery sqlQuery = new SQLQuery("HiveGame", "root", "Project2022");
					CachedRowSet cachedrowset = sqlQuery.executeQuery("SELECT name, score FROM Scoreboard");
				int i = 0;
				while (cachedrowset.next()) {
					if(cachedrowset.getString("name").equals(f.getText())) {
						score[i] = cachedrowset.getInt("score");
						i++;
						 }
					
				 }
				Arrays.sort(score, 0, i);
				for(int j = score.length - 1; j > -1; j--) {
					if(score[j] != 0) {
						
						model.addRow(new String[]{(String) f.getText(), Integer.toString((int) score[j])});
					}
				}
				
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				
			}
		};
		JPanel display = new JPanel();
		f.addActionListener(actionListener);
	
		 
		 
	
		display.setBackground(Color.YELLOW);
		name.setBackground(Color.YELLOW);
		
		display.setLayout(new FlowLayout(FlowLayout.LEFT));
		name.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		display.setBounds(500, 0, 500, 500);
		name.setBounds(0,0, 500, 500);
		
		//sort.printMap(sort.SortAndDisplay(HighScores));
		//display.add(Scores);
		display.add(new JScrollPane(Scores));
		name.add(f);
		c.add(display);
		c.add(name);
		this.setName("User Scoreboard");
		this.add(c);
	}
}
