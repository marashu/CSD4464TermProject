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
              + "username, password, email_address, player_id) VALUES(?,?,?, NEXT VALUE FOR players_seq)");
      
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
    public boolean loginPlayer(Player temp) throws SQLException{
        String id = temp.getUsername();
        String pw = temp.getPassword();
        
        String searchId = "";
        String searchPw = "";
        boolean status = false;
        
        // Create a connection to the database.  
        conn = DriverManager.getConnection(DB_URL);
        String query = "SELECT username, password FROM players WHERE username= ?";

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
        String query = "SELECT username, password, email_address FROM players WHERE email= ?";

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
