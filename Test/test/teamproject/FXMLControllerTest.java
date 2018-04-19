/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teamproject;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author c0692516
 */
public class FXMLControllerTest {
    
    public FXMLControllerTest() {
    }
    
    //thanks to stackoverflow, the following code is needed to run junit tests
    //on an fxml controller
    public static class AsNonApp extends Application {
        @Override
        public void start(Stage primaryStage) throws Exception {
            // noop
        }
    }

    @BeforeClass
    public static void initJFX() {
        Thread t = new Thread("JavaFX Init Thread") {
            public void run() {
                Application.launch(AsNonApp.class, new String[0]);
            }
        };
        t.setDaemon(true);
        t.start();
    }
    
    @Before
    public void setUp() {
        System.out.println("Testing FXMLController");
    }
    
    @After
    public void tearDown() {
        System.out.println("Done testing FXMLController");
    }

   

    /**
     * Test of checkID method, of class FXMLController.
     * Test for valid, invalid, duplicate, and null data
     */
    @Test
    public void testCheckIDValidData() throws Exception {
        System.out.println("checkID");
        String id = "Qwerty";
        Text txt = new Text();
        FXMLController instance = new FXMLController();
        
        boolean result = instance.checkID(id, txt);
        
        assertTrue("Supplied a valid username, but was considered invalid.",result);
    }
    
    /**
     * Test checkID with invalid data
     * @throws Exception 
     */
    @Test
    public void testCheckIDInvalidData() throws Exception {
        System.out.println("checkIDInvalid");
        String id = "Qwe";
        Text txt = new Text();
        
        FXMLController instance = new FXMLController();
        boolean result = instance.checkID(id, txt);
        
        
        assertFalse("Supplied a username that was too short, but was considered valid.",result);
    }
    
    /**
     * Test CheckID with duplicate data
     * @throws Exception 
     */
    @Test
    public void testCheckIDDuplicateData() throws Exception{
         System.out.println("checkIDDuplicate");
        String id = "Qwerty";
        Text txt = new Text();
        DBManager db = new DBManager();
        Player p = new Player(id,"a");
        p.setEmail("qwdres@b.bb");
        db.submitPlayer(p);
        p = db.loginPlayer(p);
        FXMLController instance = new FXMLController();
        boolean result = instance.checkID(id, txt);
        
        //remove the user
        db.removeUser(p);
        assertFalse("Provided a username that existed, but was considered valid.",result);
    }
    
    /**
     * Test CheckID with null data
     * @throws Exception 
     */
    @Test
    public void testCheckIDNullData() throws Exception {
        System.out.println("checkIDNullData");
        String id = "";
        Text txt = new Text();
        
        FXMLController instance = new FXMLController();
        boolean result = instance.checkID(id, txt);
        
        
        assertFalse("Provided an empty string, but was considered valid.",result);
    }

    /**
     * Test of checkPW method, of class FXMLController.
     * Test with good, bad, and null data
     */
    @Test
    public void testCheckPWValidData() {
        System.out.println("checkPWValid");
        String id = "Qwerty";
        Text txt = new Text();
        
        FXMLController instance = new FXMLController();
        boolean result = instance.checkPW(id, txt);
        
        assertTrue("Provided valid password, but was considered invalid.",result);
    }
    
    /**
     * Test checkPW with invalid data
     */
    @Test
    public void testCheckPWInvalidValidData() {
        System.out.println("checkPWInvalid");
        String id = "Qwe";
        Text txt = new Text();
        
        FXMLController instance = new FXMLController();
        boolean result = instance.checkPW(id, txt);
        
        assertFalse("Provided password that was too short, but was considered valid.",result);
    }
    
    /**
     * Test checkPW with null data
     */
    @Test
    public void testCheckPWNullValidData() {
        System.out.println("checkPWNull");
        String id = "";
        Text txt = new Text();
        
        FXMLController instance = new FXMLController();
        boolean result = instance.checkPW(id, txt);
        
        assertFalse("Provided empty string password, but was considered valid.",result);
    }

    /**
     * Test of checkEmail method, of class FXMLController.
     * check valid, invalid, duplicate, and null
     */
    @Test
    public void testCheckEmailValidData() throws Exception {
        System.out.println("checkEmail");
        String id = "qwerty@e.mail";
        Text txt = new Text();
        
        FXMLController instance = new FXMLController();
        boolean result = instance.checkEmail(id, txt);
        
        
        assertTrue("Provided valid email, but was considered invalid",result);
    }
    
    /**
     * Test checkEmail with invalid data
     * @throws Exception 
     */
    @Test
    public void testCheckEmailInvalidData() throws Exception {
        System.out.println("checkEmailInvalid");
        String id = "Qwe";
        Text txt = new Text();
        
        FXMLController instance = new FXMLController();
        boolean result = instance.checkEmail(id, txt);
        
        
        assertFalse("Provided email that did not match regex, but was considered valid.",result);
    }
    
    /**
     * Test checkEmail with duplicate data
     * @throws Exception 
     */
    @Test
    public void testCheckEmailDuplicateData() throws Exception{
         System.out.println("checkEmail");
        String id = "test@e.mail";
        Text txt = new Text();
        DBManager db = new DBManager();
        Player p = new Player("acc","acc");
        p.setEmail(id);
        db.submitPlayer(p);
        p = db.loginPlayer(p);
        FXMLController instance = new FXMLController();
        boolean result = instance.checkEmail(id, txt);
        
        //remove the user
        db.removeUser(p);
        assertFalse("Provided duplicate email, but was considered valid.",result);
    }
    
    /**
     * Test checkEmail with null data
     * @throws Exception 
     */
    @Test
    public void testCheckEmailNullData() throws Exception {
        System.out.println("checkEmailNullData");
        String id = "";
        Text txt = new Text();
        
        FXMLController instance = new FXMLController();
        boolean result = instance.checkEmail(id, txt);
        
        
        assertEquals("Provided empty string, but was considered valid.",false,result);
    }
    
}
