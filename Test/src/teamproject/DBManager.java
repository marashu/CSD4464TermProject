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

    /**
     *
     */
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
     * @throws java.sql.SQLException
   */

    public DBManager() throws SQLException
    {       
       // Create a connection to the database.
       conn = DriverManager.getConnection(DB_URL);
    }
    
    /****************************************
    ** START USER INFORMATION CONTROL - AERI 
     * 
    ****************************************/
    
    /*
     * user register
     * @param temp
     * @throws java.sql.SQLException
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
    
    /**
     * method for getting number of id rows
     * @param id
     * @return numRow
     * @throws SQLException
     */
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
    
    /**
     * method for getting number of row email
     * @param email
     * @return emailNumRow
     * @throws SQLException
     */
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

    /**
     * method for logging in player
     * @param temp
     * @return output
     * @throws SQLException
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
            {
                stmt.close();
                return null;
            }
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
    
    /**
     * a function to return a player based on an email address
     * @param email the email index to search for
     * @return 
     * @throws java.sql.SQLException 
     */
    public Player findPlayerByEmail(String email) throws SQLException
    {
        Player output = null;
        // Create a connection to the database.  
        conn = DriverManager.getConnection(DB_URL);
        String query = "SELECT username, player_id FROM players WHERE email_address= ?";

        // Create a Statement object for the query.
        PreparedStatement stmt = conn.prepareStatement(query, 
               ResultSet.TYPE_SCROLL_INSENSITIVE,
               ResultSet.CONCUR_READ_ONLY);
        stmt.setString(1, email);
        
        ResultSet rs = stmt.executeQuery();
        
        //make sure there is a row
        rs.last();                 // Move to last row
            int numRows = rs.getRow(); // Get row number
            
            //if more than one player, or if the player doesn't exist,
            //there is an error, so return null
            if(numRows != 1)
            {
                stmt.close();
                return null;
            }
            rs.first();                // Move to first row
        
        //while(rs.next()){
        output = new Player();
        output.setUsername(rs.getString("username"));
        output.setEmail(email);
        output.setId(rs.getInt("player_id"));
        //}
        stmt.close();
        
        return output;
    }
    
    /*
     * user find id, pw 
    */

    /**
     * method for finding id and password
     * @param email
     * @return status
     * @throws SQLException
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
        stmt.close();
        return status;
    }

    /**
     * userId
     * @return
     */
    public String getUserId() {
        return userId;
    }

    /**
     * method for setting userid
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * method for getting user password
     * @return userPw
     */
    public String getUserPw() {
        return userPw;
    }

    /**
     * method for setting user password
     * @param userPw
     */
    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    /**
     * method for getting user email
     * @return userEmail
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * method for setting user email
     * @param userEmail
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     * method for getting best scores
     * @return bestScore
     */
    public int getBestScore() {
        return bestScore;
    }

    /**
     * method for setting best score
     * @param bestScore
     */
    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }

    /**
     * method for getting average score
     * @return avgScore
     */
    public int getAvgScore() {
        return avgScore;
    }

    /**
     * method for average score 
     * @param avgScore
     */
    public void setAvgScore(int avgScore) {
        this.avgScore = avgScore;
    }

    /**
     * method for setting number of rows 
     * @param numRow
     */
    public void setNumRow(int numRow) {
        this.numRow = numRow;
    }

    /**
     * method for setting email number of rows
     * @param emailNumRow
     */
    public void setEmailNumRow(int emailNumRow) {
        this.emailNumRow = emailNumRow;
    }
    
    /*
     * user edit 
    */

    /**
     * method for editing user profile
     * @param temp
     * @throws SQLException
     */

    
    public void editUser(Player temp) throws SQLException{
        
        int id = temp.getId();
        String pw = temp.getPassword();
        String em = temp.getEmail();
        
        
        
        // Create a connection to the database.  
        conn = DriverManager.getConnection(DB_URL);
        //String query = "UPDATE players SET password=?, email_address=? WHERE player_id=?";
        String query = "UPDATE players SET password=?, email_address=? WHERE player_id=?";
        try ( // Create a Statement object for the query.
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, pw);
            stmt.setString(2, em);
            stmt.setInt(3, id);
            
            stmt.executeUpdate();
            stmt.close();
        }
        catch(Exception ex)
        {
            System.err.println(ex.getMessage());
        }
    }
    
    /**
     * method for removing user information
     * @param temp
     * @throws SQLException
     */
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
     * @param list
     * @return 
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
                ans_stmt.close();
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
    
    /***
     * Code for the scores
     * 
     */
    
    /**
     *  This will get the player's top score and average score, and add it to
     * the player
     * @param p The player to update
     * @return the updated player
     * @exception SQLException if the sql query doesn't work
     */
    public Player GetScoreData(Player p) throws SQLException
    {
        Player output = p;
         conn = DriverManager.getConnection(DB_URL);
        String query = "SELECT COUNT(score_id), SUM(total_score), MAX(total_score) FROM scores WHERE player_id= ?";

        // Create a Statement object for the query.
        PreparedStatement stmt = conn.prepareStatement(query, 
               ResultSet.TYPE_SCROLL_INSENSITIVE,
               ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, output.getId());
        
        ResultSet rs = stmt.executeQuery();
        
        //make sure there is a row
        rs.last();                 // Move to last row
        int numRows = rs.getRow(); // Get row number
            
            //if more than one player, or if the player doesn't exist,
            //there is an error, so return null
           
        rs.first();                // Move to first row
        
            
        if(numRows <= 0 || rs.getInt(1) <= 0)
        {
            output.setBestScore(0);
            output.setAverageScore(0);
            stmt.close();
            return output;
        }
        //while(rs.next()){
        double best = rs.getInt(3) / 1000.0 * 100;
        output.setBestScore((int)best);
        //get the average score
        double avg = rs.getInt(2)/rs.getInt(1);
        //next, get the percent by dividing it by the highest possible score
        avg /= (1000);
        //finally, multiply it by 100 and make it an int
        avg *= 100;
        output.setAverageScore((int)avg);
        
        //}
        stmt.close();
        
        return output;
    }
    
    /**
     * method for counting games played by user.
     * @param p the player whose id we need to check
     * @return the number of games played
     * @throws SQLException if the sql query doesn't work
     */
    public int GetGamesPlayed(Player p) throws SQLException
    {
        conn = DriverManager.getConnection(DB_URL);
        String query = "SELECT COUNT(score_id) FROM scores WHERE player_id= ?";

        // Create a Statement object for the query.
        PreparedStatement stmt = conn.prepareStatement(query, 
               ResultSet.TYPE_SCROLL_INSENSITIVE,
               ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, p.getId());
        
        ResultSet rs = stmt.executeQuery();
        
        
        //}
        stmt.close();
        return rs.getInt(1);
    }
    
    /**
     *  a function to add scores to the persistent score database
     * @param iScore the score to add
     * @param p the player who scored
     * @throws SQLException if the sql doesn't work
     */
    public void AddScore(int iScore, Player p) throws SQLException
    {
        // Create a connection to the database.  
      conn = DriverManager.getConnection(DB_URL);

      // Create a Statement object for the query.
      PreparedStatement stmt = conn.prepareStatement("INSERT INTO scores ("
              + "total_score, player_id) VALUES(?,?)");
      
      stmt.setInt(1, iScore);
      stmt.setInt(2, p.getId());

      // Execute the query.
      stmt.executeUpdate();

      // Close the connection and statement objects.
      stmt.close();
    }
    
    /**
     * reset the player's scores
     * @param p the current player
     * @throws SQLException if the sql doesn't work
     */
    public void DeleteScores(Player p) throws SQLException
    {
        
        // Create a connection to the database.  
        conn = DriverManager.getConnection(DB_URL);
        String query = "DELETE FROM scores WHERE player_id=?";

        // Create a Statement object for the query.
        PreparedStatement stmt = conn.prepareStatement(query, 
               ResultSet.TYPE_SCROLL_INSENSITIVE,
               ResultSet.CONCUR_READ_ONLY);
        stmt.setInt(1, p.getId());
        
        stmt.executeUpdate();
        stmt.close();
    }
}
