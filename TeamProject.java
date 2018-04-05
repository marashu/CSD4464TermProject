/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teamproject;

import java.io.IOException;
import java.net.UnknownHostException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Robyn
 */
public class TeamProject extends Application {
    
    private static Stage primaryStage;
    
    @Override
    public void start(Stage aprimaryStage) throws Exception {
      
        
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("Login.fxml"));
        
        primaryStage = aprimaryStage;        
        primaryStage.setTitle("Trivia Game");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

       
    }

    static public Stage getPrimaryStage() {
        return TeamProject.primaryStage;
    }    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, UnknownHostException, ClassNotFoundException {
        ClientManager.GetQuestionFromServer();
        launch(args);
    }
    
}
