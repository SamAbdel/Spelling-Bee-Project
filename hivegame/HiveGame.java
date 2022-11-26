package hivegame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import query.SQLQuery;


public class HiveGame {
	  public void game() {
	    EventQueue.invokeLater(new Runnable() {
	      public void run() {
	        JFrame frame;
	      
	  
	        try {
				frame = new GamePane();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		        //frame.pack();
		        frame.setBounds(100, 100, 1200, 1000);
		        frame.setVisible(true);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	       
	      }
	    });
	  }
	}

class GamePane extends JFrame implements ActionListener{
	
	//Buttons 0-6 Hold the randomly generated character, 7 and 8 hold enter, delete buttons
	private JButton letter[] = new JButton[10];
	//TextField for inputting words
	private JTextField input = new JTextField("");
	//Informs user if their word was valid or not
	private JLabel results = new JLabel();
	
	//A Hashset is used to search the valid word list and used words in 0(1) time
	Set<String> wordlist = new HashSet<String>();
	Set<String> correctwords = new HashSet<String>();
	
	String alphabet = "abcdefghijklmnopqrstuvwxyz";
	String vowels = "aeiou";
	
	//charlist holds the randomly generated letters the user should use
	String charlist = "";
	//Correct word count
	int count = 0;
	
	JTextField field = new JTextField("  You have found " + count + " words");
	JTable usedwords = new JTable(new DefaultTableModel(new String[]{"Used Words"}, count));
	
	SQLQuery sqlQuery = new SQLQuery("HiveGame", "root", "Project2022");
	
	
	GamePane() throws FileNotFoundException{
		
		Container c = new Container();
		this.initialize();
		this.setName("Hive Game by Sam Abdel");
		this.getContentPane().setBackground(Color.WHITE);
		this.setName("Hive Game");

		//This Panel holds inputted words
		JPanel text = new JPanel(new GridLayout(0,1));
		text.setBounds(100, 100, 300, 200);
		
		//This Panel holds label informing user if their word was valid
		JPanel result = new JPanel(new GridLayout(0,1));
		result.setBounds(600, 600, 200, 100);
		result.setBackground(new java.awt.Color(200,200,200));
		result.add(results);
		
		//This panel holds the table of used words and used words count
		JPanel display = new JPanel();
		display.setLayout(new FlowLayout(FlowLayout.LEFT));
		display.setBounds(550, 100, 500, 500);
		display.add(field);
		display.add(usedwords);
		
		display.add(new JScrollPane(usedwords));
		display.setBackground(new java.awt.Color(200,200,200));
		
		//This panel holds the character buttons
		JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
		buttons.setBounds(150, 300, 200, 200);
		buttons.setBackground(Color.WHITE);
		
		//This panel holds the Enter/Delete buttons
		JPanel inputs = new JPanel(new GridLayout(0,2));
		inputs.setBounds(150, 500, 200, 200);
		letter[7] = new JButton("Enter");
		letter[8] = new JButton("Delete");
		letter[9] = new JButton("Upload");
		inputs.add(letter[7]);
		inputs.add(letter[8]);
		inputs.add(letter[9]);
		

		letter[7].addActionListener(this);
		letter[8].addActionListener(this);
		letter[9].addActionListener(this);
		
		//This Panel displays a JLabel describing Special rules of game
		JPanel rules = new JPanel(new GridLayout(0,1));
		rules.setBounds(0, 0, 1200, 100);
		rules.setBackground(Color.YELLOW);
		JLabel rule = new JLabel("<html> Special Rule 1: Words with length 6 or greater are worth one more<br/> Special Rule 2: Words that start with letter in yellow are worth one more</html>");
		Font font = new Font("Courier", Font.BOLD, 18);
		JLabel name = new JLabel("Hive Game");
		name.setFont(font);
		rules.add(rule);
		rules.add(name);
		
		text.add(input);
		input.addActionListener(this);
		
		//Loop generates 7 buttons using letters from char list, second button is highlighted in yellow
		for(int i = 0; i< 7; i++) {
			letter[i] = new JButton(Character.toString(charlist.charAt(i)));
			letter[i].addActionListener(this);
			letter[i].setOpaque(true);
			letter[i].setBackground(new java.awt.Color(200,200,200));
			if(i == 2) {
				letter[i].setBackground(Color.YELLOW);
			}
			buttons.add(letter[i]);
		}
		c.add(text);
		c.add(buttons);
		c.add(display);
		c.add(inputs);
		c.add(rules);
		c.add(result);
		this.add(c);
		
	}
	
	/*Button Action Listener adds appropriate letter to JTextField or enters input*/
	public void actionPerformed(ActionEvent e) {
		
		// Pressing a button adds the character to JTextField above
		if(e.getSource() == letter[0]) input.setText(input.getText()+letter[0].getText());
		if(e.getSource() == letter[1]) input.setText(input.getText()+letter[1].getText());
		if(e.getSource() == letter[2]) input.setText(input.getText()+letter[2].getText());
		if(e.getSource() == letter[3]) input.setText(input.getText()+letter[3].getText());	
		if(e.getSource() == letter[4]) input.setText(input.getText()+letter[4].getText());
		if(e.getSource() == letter[5]) input.setText(input.getText()+letter[5].getText());
		if(e.getSource() == letter[6]) input.setText(input.getText()+letter[6].getText());
		
		if(e.getSource() == input || e.getSource() == letter[7]) {
			
			boolean flag = sameChars(input.getText(), charlist);
			
			//This statement checks the word is a real word, contains the correct letters, is longer than 2 letters, and hasn't been used already
			if(wordlist.contains(input.getText()) && flag && input.getText().length() > 2 && !correctwords.contains(input.getText())) {
				count++;
				
				if(input.getText().length() > 5) {
					count++; //Words of 6 or longer are worth 2
				}
				
				if(input.getText().indexOf(charlist.charAt(2)) == 0) {
					count++; //Words starting with second letter of charlist are worth an extra point
				}
				
				DefaultTableModel model = (DefaultTableModel) usedwords.getModel();
				model.addRow(new String[]{input.getText()}); //Adds row to usedwords table
				
				field.setText("  You have found " + count + " words.");
				
				if(count == 1) {
					field.setText("  You have found " + count + " word.");
				}
				
				correctwords.add(input.getText());
				input.setText("");
				results.setText("");
				
			}
			else if (input.getText().length() < 2) {
				results.setText("Word must be longer than 2 letters");
			}
			else if (!flag) {
				results.setText("Word contains invalid letter");
			}
			else if (correctwords.contains(input.getText())) {
				results.setText("This is a repeated word");
			}
				
			else {
				results.setText("Invalid Word");
			}
			input.setText("");
		}
		if(e.getSource() == letter[8]) {
			input.setText("");
		}
		if(e.getSource() == letter[9]) {
			this.setVisible(false);
			int submit = count;
			this.dispose();
			JFrame frame = new JFrame();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setBounds(0,0,500,500);
			JPanel panel = new JPanel(new GridLayout(0,1));
			JLabel field = new JLabel("Please Enter a Name");
			JTextField name = new JTextField("");
			panel.add(field);
			panel.add(name);
			frame.add(panel);
			frame.setVisible(true);
			
					
			ActionListener a = new ActionListener() {
				
						public void actionPerformed(ActionEvent e) {
							try {
								sqlQuery.addRow(name.getText(), submit);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							frame.setVisible(false); //you can't see me!
							frame.dispose();
							
						}
			        };
			        name.addActionListener(a);
		}
    }  
	
	//This method checks whether the first String contains only letters from the secondStr
	private boolean sameChars(String firstStr, String secondStr) {
		  for(int i = 0; i < firstStr.length(); i++) {
			  if(secondStr.indexOf(firstStr.charAt(i)) == -1) {
				  return false;
			  }
		  }
		  return true;
		}
	
	//This method initializes the wordlist HashSet, and generates random letters for charlist
	
	public void initialize() throws FileNotFoundException {
		
		Scanner scan = new Scanner(new File("/Users/samabdel/Desktop/Project/hivegame/words.txt"));
		while(scan.hasNextLine()) {
			String w = scan.nextLine();
			w = w.toLowerCase();
			wordlist.add(w);
		}
		int lettercount = 25;
		
		Random rand = new Random();
		int num;
		
		//Ensure one letter generated is a vowel
		num = rand.nextInt(4);
		char v = vowels.charAt(num);
		charlist += v;
		alphabet.replace(Character.toString(v), "");
		
		//Generate random letters for game
		for(int i = 1; i < 7; i++) {
			num = rand.nextInt(lettercount);
			char c = alphabet.charAt(num);
			
			if(charlist.indexOf(c) == -1 ) { //This if statement prevents duplicate letters from being generated
				charlist += c;
				alphabet.replace(Character.toString(c), "");
			}
			else {
				i--;
			}
		}
		
	}
}

