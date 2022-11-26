package test;
import java.sql.*; 
public class TestConnector { 
      public static void main(String[] args) { 
            try { 
            
Class.forName("com.mysql.cj.jdbc.Driver").newInstance(); 
            System.out.println("JDBC Driver loaded successfully"); 
            } catch(Exception E) { 
               System.out.println("JDBC Driver error"); 
         } 
           } 
        } 