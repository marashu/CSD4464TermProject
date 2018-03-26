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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author lxbks
 */

public class FXMLController implements Initializable {
    private Parent root;
    
    @FXML
    private Button btnLogin, btnRegister, btnFindIdPw;      
    
    
 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    * button for going to Game Lobby page if user success
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
    * button for starting game
    */
    @FXML
    private void startGame(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("GamePlay.fxml"));
        TeamProject.getPrimaryStage().setScene(new Scene(root));
        TeamProject.getPrimaryStage().show();
        System.out.println("GamePlay.fxml opened");
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
          
}
