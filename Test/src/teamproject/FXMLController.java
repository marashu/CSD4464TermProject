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
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author lxbks
 */

public class FXMLController implements Initializable {
       
    @FXML
    private Button btnLogin;       
    private Button btnRegister;       
    private Button btnFindIdPw;      
    
    private Parent root;
 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void openRegister(ActionEvent event) throws IOException  {
        root = FXMLLoader.load(getClass().getResource("Register.fxml"));        
        TeamProject.getPrimaryStage().setScene(new Scene(root));
        TeamProject.getPrimaryStage().show();

        System.out.println("Register.fxml opened");
    }

    @FXML
    private void openLogin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        TeamProject.getPrimaryStage().setScene(new Scene(root));
        TeamProject.getPrimaryStage().show();
        System.out.println("Login.fxml opened");
    }
    
    @FXML
    private void openFindIdPw(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("FindIdPassword.fxml"));
        TeamProject.getPrimaryStage().setScene(new Scene(root));
        TeamProject.getPrimaryStage().show();
        System.out.println("FindIdPassword.fxml opened");
    }
    
    // should be changed after making account function
    @FXML
    private void creatAccount(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        TeamProject.getPrimaryStage().setScene(new Scene(root));
        TeamProject.getPrimaryStage().show();
        System.out.println("Login.fxml opened");
    }
    // should be changed after reseting account function
    @FXML
    private void resetAccount(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        TeamProject.getPrimaryStage().setScene(new Scene(root));
        TeamProject.getPrimaryStage().show();
        System.out.println("Login.fxml opened");
    }
  
          
}
