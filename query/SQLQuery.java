package query;
import javax.sql.rowset.RowSetProvider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.rowset.*;
public class SQLQuery {
   private String databaseURL;
   private String username;
   private String password;
   
   public SQLQuery(String databaseName, String username, String password) {
	   databaseURL = "jdbc:mysql://localhost/" +databaseName;
	   this.username = username;
	   this.password = password;
   }
   
   public CachedRowSet executeQuery(String query) {
  
   CachedRowSet cachedRowSet;
   
   try (Connection connection = DriverManager.getConnection(databaseURL, username, password);
      Statement statement = connection.createStatement()){
	   // Open a connection to the database
	      System.out.println("Connecting to database");
	   // Next, create a statement to execute a query
	      System.out.println("Executing a query");
	// create a CachedRowSet;copy data from result into this cached row set
	      cachedRowSet = RowSetProvider.newFactory().createCachedRowSet();
	      cachedRowSet.setCommand("SELECT name, score FROM Scoreboard");
	          cachedRowSet.execute(connection);
	      return cachedRowSet;
	     
	  } catch(SQLException se) {
	      System.out.println("JDBC errors");
	      se.printStackTrace();
	  } catch(Exception err) {
	      System.out.println("Errors in Class.forName");
	      err.printStackTrace();
	   }
	   return null;
	     
	  }
public static void main(String[] args) { 
	  // create a query object for an existing database "test1", 
	        // user "root" with password "mysql1"
	  SQLQuery sqlQuery = new SQLQuery("HiveGame", "root", "Project2022");
	  
	  // execute query to retrieve id and last name of students from table Scoreboard
	  try {
	     // get the cachedRowSet that has data and display it
	     // For simplicity we will display it on the console for now
	     CachedRowSet cachedrowset = 
	sqlQuery.executeQuery("SELECT name, score FROM Scoreboard");
	     
	     System.out.println("NAME"+"\t"+"SCORE");
	     while (cachedrowset.next()) {
	    	 System.out.println(cachedrowset.getString("name") + "\t" +cachedrowset.getInt("score"));
	    			      }
	    			   } catch(SQLException s) {
	    			   System.out.println("A SQLException occurred.");
	    			   }
	    			   
}


public int addRow(String name, int score) throws 
SQLException { 
         	
	Connection connection = DriverManager.getConnection(databaseURL, username, password);
	String insertString = "insert into Scoreboard" +  "(name, score) values (?, ?)"; 
			                       
                       
           System.out.println(name + " " + score); 
           PreparedStatement preparedstatement = connection.prepareStatement(insertString); 
            // The following statements replace the five "?" symbols with the arguments 
            preparedstatement.setString(1, name); 
            preparedstatement.setInt(2, score); 
           
            // execute the insert operation; returns the number of rows affected 
            int i = preparedstatement.executeUpdate();  
            return i;      
     
    } 
}
