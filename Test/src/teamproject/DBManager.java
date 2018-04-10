/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teamproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author c0717947
 */
public class DBManager {
    
    // Constant for database URL.
    public final String DB_URL = "jdbc:derby://localhost:1527/TriviaGame;user=root;password=password";
    // Field for the database connection
    private Connection conn;
    
    /**
      Constructor
   */

    public DBManager() throws SQLException
    {       
       // Create a connection to the database.
       conn = DriverManager.getConnection(DB_URL);
    }
   
    public void submitPlayer(String id, String pw, String email) throws SQLException
    {
      // Create a connection to the database.  
      conn = DriverManager.getConnection(DB_URL);

      // Create a Statement object for the query.
      PreparedStatement stmt = conn.prepareStatement("INSERT INTO player ("
              + "username, password, email) VALUES(?,?,?)");
      
      stmt.setString(1, id);
      stmt.setString(2, pw);
      stmt.setString(3, email);

      // Execute the query.
      stmt.executeUpdate();

      // Close the connection and statement objects.
      stmt.close();
    }
   
    public boolean loginPlayer(String id, String pw) throws SQLException{
        String searchId = "";
        String searchPw = "";
        boolean status = false;
        
        // Create a connection to the database.  
        conn = DriverManager.getConnection(DB_URL);
        String query = "SELECT username, password FROM player WHERE username= ?";

        // Create a Statement object for the query.
        PreparedStatement stmt = conn.prepareStatement(query, 
               ResultSet.TYPE_SCROLL_INSENSITIVE,
               ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, id);
        
        ResultSet rs = stmt.executeQuery();
        
        while(rs.next()){
            searchId = rs.getString("username");
            searchPw = rs.getString("password");
        }        
        
        if(!id.equals(searchId) || !pw.equals(searchPw)){
            status = false;
        }else{
            status = true;
        }   

        // Close the connection and statement objects.
        stmt.close();
        return status;
    }
    
    private String findId = "";
    private String findPw = "";
    
    public boolean findIdPw(String email) throws SQLException{
        boolean status = false;
        
        // Create a connection to the database.  
        conn = DriverManager.getConnection(DB_URL);
        String query = "SELECT username, password FROM player WHERE email= ?";

        // Create a Statement object for the query.
        PreparedStatement stmt = conn.prepareStatement(query, 
               ResultSet.TYPE_SCROLL_INSENSITIVE,
               ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, email);
        
        ResultSet rs = stmt.executeQuery();
        
        while(rs.next()){
            findId = rs.getString("username");
            findPw = rs.getString("password");
        }
        
        if(findId.equals("") || findPw.equals("")){
            status = false;
        }else{
            setFindId(findId);
            setFindPw(findPw);
            status = true;
        }
        return status;
    }
    
    public String getFindId() {
        return findId;
    }

    public void setFindId(String findId) {
        this.findId = findId;
    }

    public String getFindPw() {
        return findPw;
    }

    public void setFindPw(String findPw) {
        this.findPw = findPw;
    }
   
    
}
