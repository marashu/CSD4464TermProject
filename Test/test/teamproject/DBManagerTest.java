/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teamproject;

import java.sql.SQLException;
import java.util.LinkedList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author c0692516
 */
public class DBManagerTest {
    
    public DBManagerTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    

    /**
     * Test of loginPlayer method, of class DBManager.
     */
    @Test
    public void testLoginPlayer() throws Exception {
        System.out.println("loginPlayer");
        Player temp = new Player("a", "a");
        temp.setEmail("e");
        DBManager instance = new DBManager();
        //create a dummy player
        instance.submitPlayer(temp);
        //get the player by email so we can find the correct id to remove later.
        temp = instance.loginPlayer(temp);
        boolean expResult = true;
        //try logging the player in
        boolean result = !(instance.loginPlayer(temp) == null);
        //remove the temp player
        try{
            instance.removeUser(temp);
        } catch(SQLException ex)
        {
            System.err.println(ex.getMessage());
        }
        //assert that login was successful
        assertEquals("Provided a valid username and password, but did not log in.",expResult, result);
    }
    
    
    /**
     * Test LoginPlayer with incorrect details
     * @throws Exception 
     */
    @Test
    public void testIncorrectLoginPlayer() throws Exception
    {
        System.out.println("Test for unsuccessful login");
        //no player has a username or password 1 character long
        Player temp = new Player("ab", "ab");
        temp.setEmail("a@a.aa");
        
        DBManager instance = new DBManager();
        boolean expResult = false;
        //try logging the player in
        boolean result = !(instance.loginPlayer(temp) == null);
        //assert that login was not successful
        assertEquals("Provided an incorrect username and password, but logged in.",expResult, result);
    }
    
    //testing for SQL Injection
    /**
     * Test the database for SQL injection
     * @throws Exception 
     */
    @Test
    public void textSQLInjection() throws Exception
    {
        System.out.println("Test for SQL injection");
        Player temp = new Player("1' or '1' = '1", "1' or '1' = '1");
        temp.setEmail("1' or '1' = '1");
        
        DBManager instance = new DBManager();
        boolean expResult = false;
        //try logging the player in
        boolean result = !(instance.loginPlayer(temp) == null);
        //assert that login was not successful
        assertEquals("Database was found to be vulnerable to SQL Injections.  Use a PreparedStatement to fix this.",expResult, result);
    }
    
}
