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
import java.util.LinkedList;

/**
 *
 * @author c0717947
 */
public class DBManager {
    
    // Constant for database URL.
    public final String DB_URL = "jdbc:derby://localhost:1527/TriviaGame;user=root;password=password";
    // Field for the database connection
    private Connection conn;
    
    /*
     * user find id, pw global variable
    */
    private String userId = "";
    private String userPw = "";
    private String userEmail = "";
    private int bestScore = 0;
    private int avgScore = 0;
    private int numRow = 0;
    private int emailNumRow = 0;


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
    public void submitPlayer(Player temp) throws SQLException
    {
      // Create a connection to the database.  
      conn = DriverManager.getConnection(DB_URL);

      String id = temp.getUsername();
      String pw = temp.getPassword();
      String email = temp.getEmail();
      // Create a Statement object for the query.
      PreparedStatement stmt = conn.prepareStatement("INSERT INTO players ("
              + "username, password, email_address) VALUES(?,?,?)");
      
      stmt.setString(1, id);
      stmt.setString(2, pw);
      stmt.setString(3, email);

      // Execute the query.
      stmt.executeUpdate();

      // Close the connection and statement objects.
      stmt.close();
    }
    
    public int getNumRow(String id) throws SQLException{
        // Create a connection to the database.  
        conn = DriverManager.getConnection(DB_URL);

        // Create a Statement object for the query.
        String query = "SELECT username FROM players WHERE username= ?";
        PreparedStatement stmt = conn.prepareStatement(query, 
                 ResultSet.TYPE_SCROLL_INSENSITIVE,
                 ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, id);
        ResultSet rs = stmt.executeQuery();
        rs.last();
        
        numRow = rs.getRow();
        setNumRow(numRow);
        
        stmt.close();
        
        return numRow;
    }
    
    public int getEmailNumRow(String email) throws SQLException{
        // Create a connection to the database.  
        conn = DriverManager.getConnection(DB_URL);

        // Create a Statement object for the query.
        String query = "SELECT email_address FROM players WHERE email_address= ?";
        PreparedStatement stmt = conn.prepareStatement(query, 
                 ResultSet.TYPE_SCROLL_INSENSITIVE,
                 ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();
        rs.last();
        
        emailNumRow = rs.getRow();
        setNumRow(emailNumRow);
        stmt.close();
        
        return emailNumRow;
    }
    
    /*
     * user login
    */
    public Player loginPlayer(Player temp) throws SQLException{
        String id = temp.getUsername();
        String pw = temp.getPassword();
        
        Player output = null;
        
        String searchId = "";
        String searchPw = "";
        boolean status = false;
        
        // Create a connection to the database.  
        conn = DriverManager.getConnection(DB_URL);
        String query = "SELECT username, password, player_id, email_address FROM players WHERE username= ?";

        // Create a Statement object for the query.
        PreparedStatement stmt = conn.prepareStatement(query, 
               ResultSet.TYPE_SCROLL_INSENSITIVE,
               ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, id);
        
        ResultSet rs = stmt.executeQuery();
        
        // Get the number of rows.
            rs.last();                 // Move to last row
            int numRows = rs.getRow(); // Get row number
            
            //if more than one player, or if the player doesn't exist,
            //there is an error, so return null
            if(numRows != 1)
                return null;
            rs.first();                // Move to first row
            
        
            searchId = rs.getString("username");
            searchPw = rs.getString("password");
            output = new Player(searchId, searchPw);
            output.SetEncryptedPassword(searchPw);
            output.setEmail(rs.getString("email_address"));
            output.setId(rs.getInt("player_id"));
                  
        
        if(!id.equals(searchId) || !pw.equals(searchPw)){
            status = false;
        }else{
            status = true;
        }   

        // Close the connection and statement objects.
        stmt.close();
        if(!status)
            return null;
        return output;
    }
    
    
    /*
     * user find id, pw 
    */
    public boolean findIdPw(String email) throws SQLException{
        boolean status = false;
        
        // Create a connection to the database.  
        conn = DriverManager.getConnection(DB_URL);
        String query = "SELECT username, password, email_address FROM players WHERE email_address= ?";

        // Create a Statement object for the query.
        PreparedStatement stmt = conn.prepareStatement(query, 
               ResultSet.TYPE_SCROLL_INSENSITIVE,
               ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, email);
        
        ResultSet rs = stmt.executeQuery();
        
        while(rs.next()){
            userId = rs.getString("username");
            userPw = rs.getString("password");
            userEmail = rs.getString("email_address");
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
    public int getBestScore() {
        return bestScore;
    }
    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }
    public int getAvgScore() {
        return avgScore;
    }
    public void setAvgScore(int avgScore) {
        this.avgScore = avgScore;
    }
    public void setNumRow(int numRow) {
        this.numRow = numRow;
    }
    public void setEmailNumRow(int emailNumRow) {
        this.emailNumRow = emailNumRow;
    }
    
    /*
     * user edit 
    */
    
    public void editUser(Player temp) throws SQLException{
        
        String id = temp.getUsername();
        String pw = temp.getPassword();
        String em = temp.getEmail();
        // Create a connection to the database.  
        conn = DriverManager.getConnection(DB_URL);
        String query = "UPDATE players SET password =?, email_address =? WHERE username= ?";

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
    
    public void editPassword(String email, String pw) throws SQLException{
        
        // Create a connection to the database.  
        conn = DriverManager.getConnection(DB_URL);
        String query = "UPDATE players SET password =? WHERE email_address = ?";

        // Create a Statement object for the query.
        PreparedStatement stmt = conn.prepareStatement(query, 
               ResultSet.TYPE_SCROLL_INSENSITIVE,
               ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, pw);
        stmt.setString(2, email);
        
        stmt.executeUpdate();
        stmt.close();
    }
    
    public void removeUser(Player temp) throws SQLException
    {
        int id = temp.getId();
        
        // Create a connection to the database.  
        conn = DriverManager.getConnection(DB_URL);
        String query = "DELETE FROM players WHERE player_id=?";

        // Create a Statement object for the query.
        PreparedStatement stmt = conn.prepareStatement(query, 
               ResultSet.TYPE_SCROLL_INSENSITIVE,
               ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, id);
        
        stmt.executeUpdate();
        stmt.close();
        
    }
    /*
    ** END USER INFORMATION CONTROL - AERI 
    */
   
    /**
     * BEGIN QUESTION INFORMATION CONTROL - MICHAEL
     */
    public static boolean GetQuestions(LinkedList<Question> list)
    {
        final String DB_URL = "jdbc:derby://localhost:1527/TriviaGame;user=root;password=password;create=true";
        Connection conn = null;
        PreparedStatement stmt = null;
        try
        {
            // Create a connection to the database.
            conn = DriverManager.getConnection(DB_URL);
            String query = "SELECT question_id, question, correct_answer_id "
                    + "FROM questions";
            stmt = conn.prepareStatement(query, 
               ResultSet.TYPE_SCROLL_INSENSITIVE,
               ResultSet.CONCUR_READ_ONLY);
            
            
            // Execute the query.
            ResultSet resultSet =
                   stmt.executeQuery();
            
            // Get the number of rows.
            resultSet.last();                 // Move to last row
            int numRows = resultSet.getRow(); // Get row number
            resultSet.first();                // Move to first row
            
            //reset the questions
            list.clear();
            
            for(int row = 0; row < numRows; row++)
            {
                int id = resultSet.getInt(1);
                query = "SELECT answer_id, answer FROM answers "
                    + "WHERE question_id=" + id;
                
                PreparedStatement ans_stmt = conn.prepareStatement(query, 
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
                ResultSet ans_resultSet =
                   ans_stmt.executeQuery();
                
                ans_resultSet.last();
                int numAns = ans_resultSet.getRow(); // Get row number
                ans_resultSet.first();
                
                LinkedList<String> ans = new LinkedList();
                
                for(int j = 0; j < numAns; j++)
                {
                    if(ans_resultSet.getInt(1) == resultSet.getInt(3))
                        ans.addFirst(ans_resultSet.getString(2));
                    else
                        ans.addLast(ans_resultSet.getString(2));
                    ans_resultSet.next();
                }
                
                Question q = new Question(id, resultSet.getString(2), ans.get(0), ans.get(1), ans.get(2), ans.get(3));
                list.add(q);
                
                resultSet.next();
            }
        }
        catch (Exception ex)
        {
            System.out.println("ERROR: " + ex.getMessage());
        }
        finally
        {
            try {
                if (conn != null) 
                { 
                    //close the connection
                    conn.close(); 
                }
            }
            catch (Exception ex) {
                // log this error
                System.out.println("ERROR: " + ex.getMessage());
            }
            try{
                if (stmt != null)
                {
                    stmt.close();
                }
            }
            catch (Exception ex) {
                // log this error
                System.out.println("ERROR: " + ex.getMessage());
            }
        }
        return true;
    }
    
}