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
        assertEquals(expResult, result);
        
    }
    
    //testing for SQL Injection
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
        assertEquals(expResult, result);
    }
    
}
