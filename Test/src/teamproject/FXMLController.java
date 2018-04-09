package teamproject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author lxbks
 */

public class FXMLController implements Initializable {
    private Parent root;
    
    /**
     * Login variable
    **/
    @FXML
    private static TextField txtId, txtPassword;
    @FXML
    private static Button btnLogin, btnRegister, btnFindIdPw;
    
    /**
     * Register variable
    **/
    @FXML
    private static TextField txtRegisterId, txtRegisterPassword, txtRegisterPasswordConfirm, textRegisterAnswer, txtRegisterEmail;
    @FXML
    private static ComboBox comboRegisterQuestion;
    
    /**
     * Find Id & pw
    **/
    @FXML
    private static TextField txtEmailFind, txtHintAnswerFind;
    @FXML
    private static ComboBox comboHintFind;
    
    /**
     * Game Lobby
     * button- btnLogOut, scrollpane - listUser can be used everywhere in Game play
    **/
    @FXML
    private static Button btnLogOut, btnLobbyCreateRoom, btnLobbyJoinRoom;
    @FXML
    private static Text txtStrongQuestion;
    @FXML
    private static BarChart graphMyStastics;
    @FXML
    private static ScrollPane listUser;
    @FXML
    private static Button btnPlayGame;
    
    /**
     * Game Host and User
    **/
    @FXML
    private static Button btnHostStartGame, btnHostExit, btnHostDropGame;
    @FXML
    private static Text roomName;
    
    /**
     * Game Play
    **/
    @FXML
    private static Button btnSubmitAnswer;
    @FXML 
    private static Text txtScore, txtTimer, txtQuestion;
    @FXML 
    private static RadioButton rdoAnswer1, rdoAnswer2, rdoAnswer3, rdoAnswer4;
    
    /**
     * Game End
    **/
    @FXML
    private static Text txtFinalScore;
    @FXML
    private static Button replayHost, backToMain;
    
    /**
     * Managers
     */
    private GameManager gm;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //create the game manager
        gm = new GameManager();
        
        //generate the questions list to reduce database calls
        gm.GenerateQuestions();
        
    }    
    
    /*
    * button for going to register page
    */
    @FXML
    private void openRegister(ActionEvent event) throws IOException  {
        root = FXMLLoader.load(getClass().getResource("Register.fxml"));        
        TeamProject.getPrimaryStage().setScene(new Scene(root));
        TeamProject.getPrimaryStage().show();

        System.out.println("Register.fxml opened");
    }
    
    
    
    /*
    * button for going to Login page
    */
    @FXML
    private void goToLogin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        TeamProject.getPrimaryStage().setScene(new Scene(root));
        TeamProject.getPrimaryStage().show();
        
        System.out.println("Login.fxml opened");
    }
    
    /*
    * button for going to Game Lobby page if user success
    */
    @FXML
    private void openLogin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("GameLobby.fxml"));
        TeamProject.getPrimaryStage().setScene(new Scene(root));
        TeamProject.getPrimaryStage().show();
        
        
        //System.out.println(btnPlayGame.getText());
        System.out.println("GameLobby.fxml opened");
    }
    
    /*
    * button for going to find username and password page
    */
    @FXML
    private void openFindIdPw(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("FindIdPassword.fxml"));
        TeamProject.getPrimaryStage().setScene(new Scene(root));
        TeamProject.getPrimaryStage().show();
        System.out.println("FindIdPassword.fxml opened");
    }
    
    /*
    * button for creating user account
    */
    @FXML
    private void creatAccount(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        TeamProject.getPrimaryStage().setScene(new Scene(root));
        TeamProject.getPrimaryStage().show();
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Create Account");
        alert.setContentText("User Account is created");
        alert.showAndWait();
        
        System.out.println("Login.fxml opened");
    }
    
    /*
    * button for reseting user Account 
    */
    @FXML
    private void resetAccount(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        TeamProject.getPrimaryStage().setScene(new Scene(root));
        TeamProject.getPrimaryStage().show();
        System.out.println("Login.fxml opened");
    }
    
    /*
    * button for creating room 
    */
    @FXML
    private void createRoom(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("GameHost.fxml"));
        TeamProject.getPrimaryStage().setScene(new Scene(root));
        TeamProject.getPrimaryStage().show();
        System.out.println("GameHost.fxml opened");
    }
    
    /*
    * button for entering game as user
    */
    @FXML
    private void loginAsUser(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("GameUser.fxml"));
        TeamProject.getPrimaryStage().setScene(new Scene(root));
        TeamProject.getPrimaryStage().show();
        System.out.println("GameUser.fxml opened");
    }
    
    /*
    * button for going to Game Lobby page if user success
    */
    @FXML
    private void userLogout(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        TeamProject.getPrimaryStage().setScene(new Scene(root));
        TeamProject.getPrimaryStage().show();
        System.out.println("Login.fxml opened");
    }
    
    /*
    * button for starting game
    */
    @FXML
    private void startGame(ActionEvent event) throws IOException {
        //System.out.println((Node)event.getSource());
        root = FXMLLoader.load(getClass().getResource("GamePlay.fxml"));
        TeamProject.getPrimaryStage().setScene(new Scene(root));
        
        
        //generate the list of questions for this set
        gm.RandomizeQuestions();
        
        //if text fields do not exist, create them
        
        
        //set the text
        SetQuestionAndAnswers();
        
        
        
        
        System.out.println("GamePlay.fxml opened");
        
        //TODO: include a timer
        //sample code can be found here:
        //http://asgteach.com/2011/10/javafx-animation-and-binding-simple-countdown-timer-2/
        
        
        TeamProject.getPrimaryStage().show();
    }
    
    
    
    /**
     * Function to update the text for questions and answers
     */
    private void SetQuestionAndAnswers()
    {
        //shuffle the answers
        gm.GetCurrentQuestion().ShuffleAnswers();
        
        //the fxml isn't loading the elements for some reason, so pull them
        //from the stage
        Text txtQuest = (Text) TeamProject.getPrimaryStage().getScene().lookup("#txtQuestion");
        RadioButton a1 = (RadioButton)TeamProject.getPrimaryStage().getScene().lookup("#rdoAnswer1");
        RadioButton a2 = (RadioButton)TeamProject.getPrimaryStage().getScene().lookup("#rdoAnswer2");
        RadioButton a3 = (RadioButton)TeamProject.getPrimaryStage().getScene().lookup("#rdoAnswer3");
        RadioButton a4 = (RadioButton)TeamProject.getPrimaryStage().getScene().lookup("#rdoAnswer4");
        
        //set the text
        txtQuest.setText(gm.GetCurrentQuestion().GetQuestion());
        
        a1.setText(gm.GetCurrentQuestion().GetAnswers().get(0).GetAnswer());
        a2.setText(gm.GetCurrentQuestion().GetAnswers().get(1).GetAnswer());
        a3.setText(gm.GetCurrentQuestion().GetAnswers().get(2).GetAnswer());
        a4.setText(gm.GetCurrentQuestion().GetAnswers().get(3).GetAnswer());
        
    }
    
    /*
    * button for exit game
    */
    @FXML
    private void exitGame(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("GameLobby.fxml"));
        TeamProject.getPrimaryStage().setScene(new Scene(root));
        TeamProject.getPrimaryStage().show();
        System.out.println("GameLobby.fxml opened");
    }
    
    /*
    * button for dropping game
    */
    @FXML
    private void dropGame(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("GameLobby.fxml"));
        TeamProject.getPrimaryStage().setScene(new Scene(root));
        TeamProject.getPrimaryStage().show();
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("This room is dropped");
        alert.showAndWait();
        
        System.out.println("GameLobby.fxml opened");
    }
    
    @FXML
    private void submitAnswer(ActionEvent event){
//        this is the button for submit quiz answer
    }
    
    /*
    * button for going back to main
    */
    @FXML
    private void backToMain(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("GameLobby.fxml"));
        TeamProject.getPrimaryStage().setScene(new Scene(root));
        TeamProject.getPrimaryStage().show();
        System.out.println("GameLobby.fxml opened");
    }
    
    /*
    * button for going back to user game lobby
    */
    @FXML
    private void replayUser(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("GameUser.fxml"));
        TeamProject.getPrimaryStage().setScene(new Scene(root));
        TeamProject.getPrimaryStage().show();
        System.out.println("GameUser.fxml opened");
    }
    
    /*
    * button for going back to user game lobby host
    */
    @FXML
    private void replayHost(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("GameHost.fxml"));
        TeamProject.getPrimaryStage().setScene(new Scene(root));
        TeamProject.getPrimaryStage().show();
        System.out.println("GameHost.fxml opened");
    }
    
    @FXML
    private void resetMyTotalResult(ActionEvent event) throws IOException
    {
        
    }
    
    @FXML
    private void editUserProfile(ActionEvent event) throws IOException
    {
        
    }
          
}
