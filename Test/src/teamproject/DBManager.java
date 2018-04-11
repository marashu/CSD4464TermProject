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
    
    /****************************************
    ** START USER INFORMATION CONTROL - AERI 
    ****************************************/
    
    /*
     * user register
    */
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
    
    /*
     * user login
    */
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
    
    /*
     * user find id, pw global variable
    */
    private String userId = "";
    private String userPw = "";
    private String userEmail = "";
    private int bestScore = 0;
    private int avgScore = 0;
    
    /*
     * user find id, pw 
    */
    public boolean findIdPw(String email) throws SQLException{
        boolean status = false;
        
        // Create a connection to the database.  
        conn = DriverManager.getConnection(DB_URL);
        String query = "SELECT username, password, email FROM player WHERE email= ?";

        // Create a Statement object for the query.
        PreparedStatement stmt = conn.prepareStatement(query, 
               ResultSet.TYPE_SCROLL_INSENSITIVE,
               ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, email);
        
        ResultSet rs = stmt.executeQuery();
        
        while(rs.next()){
            userId = rs.getString("username");
            userPw = rs.getString("password");
            userEmail = rs.getString("email");
        }
        
        if(userId.equals("") || userPw.equals("")){
            status = false;
        }else{
            setUserId(userId);
            setUserPw(userPw);
            setUserEmail(userEmail);
            status = true;
        }
        return status;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserPw() {
        return userPw;
    }
    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    
    /*
     * user edit 
    */
    
    public void editUser(String id, String pw, String em) throws SQLException{
        
        // Create a connection to the database.  
        conn = DriverManager.getConnection(DB_URL);
        String query = "UPDATE player SET password =?, email =? WHERE username= ?";

        // Create a Statement object for the query.
        PreparedStatement stmt = conn.prepareStatement(query, 
               ResultSet.TYPE_SCROLL_INSENSITIVE,
               ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, pw);
        stmt.setString(2, em);
        stmt.setString(3, id);
        
        stmt.executeUpdate();
        stmt.close();
    }
    /*
    ** END USER INFORMATION CONTROL - AERI 
    */
   
    
}
