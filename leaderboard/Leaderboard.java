package leaderboard;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.rowset.CachedRowSet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


//import hivegame.GamePane;
import query.SQLQuery;

public class Leaderboard {
	
	
	  public static void board() {
	    EventQueue.invokeLater(new Runnable() {
	      public void run() {
	        JFrame frame;
				try {
					frame = new ScorePane();
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			        //frame.pack();
			        frame.setBounds(100, 100, 500, 500);
			       
			        frame.setVisible(true);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
	      }
	    });
	  }
	}

class ScorePane extends JFrame{
	
	int count = 0;
	Map<String, Integer> HighScores = new HashMap<String, Integer>();
	private static JTable Scores = new JTable(new DefaultTableModel(new String[]{"Names","Scores"}, 0));
	
	ScorePane() throws SQLException{
		
		Container c = new Container();
		SQLQuery sqlQuery = new SQLQuery("HiveGame", "root", "Project2022");
		CachedRowSet cachedrowset = sqlQuery.executeQuery("SELECT name, score FROM Scoreboard");
		
		
		JPanel display = new JPanel();
		
		 while (cachedrowset.next()) {
			 if(!HighScores.containsKey(cachedrowset.getString("name"))) {
				 HighScores.put(cachedrowset.getString("name"), cachedrowset.getInt("score"));
			 }
			 else if(cachedrowset.getInt("score") > HighScores.get(cachedrowset.getString("name"))) {
				 HighScores.put(cachedrowset.getString("name"), cachedrowset.getInt("score"));
			 }
		 }
		 Sorter sort = new Sorter();
	
		display.setBackground(Color.YELLOW);
		
		display.setLayout(new FlowLayout(FlowLayout.LEFT));
		display.setBounds(0, 0, 500, 500);
		
		sort.printMap(sort.SortAndDisplay(HighScores));
		display.add(Scores);
		display.add(new JScrollPane(Scores));
		this.setName("Leaderboard");
		c.add(display);
		this.add(c);
	}
	
class Sorter{
public static Map<String, Integer> SortAndDisplay(Map<String, Integer> highScores)
{
    // Create a list from elements of HashMap
    List<Map.Entry<String, Integer> > list =
           new LinkedList<Map.Entry<String, Integer> >(highScores.entrySet());

    // Sort the list
    Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
        public int compare(Map.Entry<String, Integer> o1,
                           Map.Entry<String, Integer> o2)
        {
            return (o2.getValue()).compareTo(o1.getValue());
        }
    });
     
    // put data from sorted list to hashmap
    HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
    
    for (Map.Entry<String, Integer> aa : list) {
    	
        temp.put(aa.getKey(), aa.getValue());
    }
    return temp;
    
    }

public static <K, V> void printMap(Map<K, V> map) {
    for (Map.Entry<K, V> entry : map.entrySet()) {
    	DefaultTableModel model = (DefaultTableModel) Scores.getModel();
    	model.addRow(new String[]{(String) entry.getKey(), Integer.toString((int) entry.getValue())});
    }
}
}
}