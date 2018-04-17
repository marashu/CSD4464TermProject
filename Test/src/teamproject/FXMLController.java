package teamproject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import javafx.fxml.Initializable;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author lxbks
 */

public class FXMLController implements Initializable {
    private Parent root;
    
    //track what screen we are on
    private enum ScreenType{LOGIN, REGISTER,FORGOT_PASSWORD,READY,PLAY,RESULTS, EDIT};
    private static ScreenType currentScreen = ScreenType.LOGIN;
    
    /**
     * Login variable
    **/
    private static TextField txtId, txtPassword;
    
    /**
     * Register variable
    **/
    private static TextField txtRegisterId, txtRegisterEmail;
    private static PasswordField txtRegisterPassword, txtRegisterPasswordConfirm;
    private static Text reg_notice_id, reg_notice_pw, reg_notice_pwc, reg_notice_email;
    
    /**
     * Find Id & pw
    **/
    private static TextField txtEmailFind;
    private static Text find_notice_email;
    
    /**
     * Game Lobby
     * button- btnLogOut, scrollpane - listUser can be used everywhere in Game play
    **/
    private static Text txtStrongQuestion;
    private static CategoryAxis xAxis = new CategoryAxis();
    private static NumberAxis yAxis = new NumberAxis(0,100,10);
    
    private static BarChart graphMyStastics = new BarChart(xAxis, yAxis);
    
    
    
    /**
     * Game User-Edit
     * button- btnLogOut, scrollpane - listUser can be used everywhere in Game play
    **/
    private static Text txtIdEdit, edit_notice_pw, edit_notice_pwc, edit_notice_email;
    private static TextField txtEditPassword, txtEditPasswordConfirm, txtEditEmail;
    
    /**
     * Game Play
    **/
    private static Button btnSubmitAnswer;
    private static Text txtScore, txtTimer, txtQuestion;
    private static RadioButton rdoAnswer1, rdoAnswer2, rdoAnswer3, rdoAnswer4;
    
    /**
     * Game End
    **/
    private static Text txtFinalScore;
    private static Button replayHost, backToMain;
    
    /**
     * Managers
     */
    private GameManager gm;
    
    /**
     * time variables
     */
    private static Timeline timeline;
    private static final int MAXTIME = 5;
    private static int iSeconds;
    
    /**
     * Player Class variable
     */
    private static Player player;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //create the game manager
        gm = new GameManager();
        
        //generate the questions list to reduce database calls
        gm.GenerateQuestions();
        
        if(player == null)
            player = new Player();
    }    
    /**
     * THIS REGION CONTAINS THE CONTROLLER FUNCTIONS THAT MICHAEL HAS BEEN WORKING ON
     * (I may work on other functions and others may work on these, but these functions
     * are largely maintained by Michael)
     */
    public static void SetScreenResources()
    {
        //get resources based on the current screen
        switch(currentScreen)
        {
            case LOGIN:
                //TextField
                txtId = (TextField) TeamProject.getPrimaryStage().getScene().lookup("#txtId");
                txtPassword = (TextField) TeamProject.getPrimaryStage().getScene().lookup("#txtPassword");
                break;
            case REGISTER:
                //TextField
                txtRegisterId= (TextField) TeamProject.getPrimaryStage().getScene().lookup("#txtRegisterId");
                txtRegisterEmail= (TextField) TeamProject.getPrimaryStage().getScene().lookup("#txtRegisterEmail");
                //PasswordField
                txtRegisterPassword= (PasswordField) TeamProject.getPrimaryStage().getScene().lookup("#txtRegisterPassword");
                txtRegisterPasswordConfirm= (PasswordField) TeamProject.getPrimaryStage().getScene().lookup("#txtRegisterPasswordConfirm");
                //Text
                reg_notice_id= (Text) TeamProject.getPrimaryStage().getScene().lookup("#reg_notice_id");
                reg_notice_pw= (Text) TeamProject.getPrimaryStage().getScene().lookup("#reg_notice_pw");
                reg_notice_pwc= (Text) TeamProject.getPrimaryStage().getScene().lookup("#reg_notice_pwc");
                reg_notice_email= (Text) TeamProject.getPrimaryStage().getScene().lookup("#reg_notice_email");
                break;
            case FORGOT_PASSWORD:
                //TextField
                txtEmailFind= (TextField) TeamProject.getPrimaryStage().getScene().lookup("#txtEmailFind");
                //Text
                find_notice_email = (Text) TeamProject.getPrimaryStage().getScene().lookup("#find_notice_email");
                break;
            case READY:
                //Text
                txtStrongQuestion= (Text) TeamProject.getPrimaryStage().getScene().lookup("#txtStrongQuestion");
                //CategoryAxis
                xAxis = (CategoryAxis) TeamProject.getPrimaryStage().getScene().lookup("#xAxis");
                //NumberAxis
                //yAxis = (NumberAxis) TeamProject.getPrimaryStage().getScene().lookup("#yAxis");
                
                //BarChart
                graphMyStastics= (BarChart) TeamProject.getPrimaryStage().getScene().lookup("#graphMyStastics");
                //XYChart.Series
                graphMyStastics.getYAxis().setAutoRanging(false);
                ((NumberAxis)graphMyStastics.getYAxis()).setUpperBound(100);
                
                //barData = (XYChart.Series) TeamProject.getPrimaryStage().getScene().lookup("#barData");
                break;
            case PLAY:
                //Button
                btnSubmitAnswer= (Button) TeamProject.getPrimaryStage().getScene().lookup("#btnSubmitAnswer");
                //Text
                txtScore= (Text) TeamProject.getPrimaryStage().getScene().lookup("#txtScore");
                txtTimer= (Text) TeamProject.getPrimaryStage().getScene().lookup("#txtTimer");
                txtQuestion= (Text) TeamProject.getPrimaryStage().getScene().lookup("#txtQuestion");
                //RadioButton
                rdoAnswer1= (RadioButton) TeamProject.getPrimaryStage().getScene().lookup("#rdoAnswer1");
                rdoAnswer2= (RadioButton) TeamProject.getPrimaryStage().getScene().lookup("#rdoAnswer2");
                rdoAnswer3= (RadioButton) TeamProject.getPrimaryStage().getScene().lookup("#rdoAnswer3");
                rdoAnswer4= (RadioButton) TeamProject.getPrimaryStage().getScene().lookup("#rdoAnswer4");
                break;
            case RESULTS:
                //Text
                txtFinalScore= (Text) TeamProject.getPrimaryStage().getScene().lookup("#txtFinalScore");
                //Button
                replayHost= (Button) TeamProject.getPrimaryStage().getScene().lookup("#replayHost");
                backToMain= (Button) TeamProject.getPrimaryStage().getScene().lookup("#backToMain");
                break;
            case EDIT:
                //Text
                txtIdEdit = (Text) TeamProject.getPrimaryStage().getScene().lookup("#txtIdEdit");
                edit_notice_pw = (Text) TeamProject.getPrimaryStage().getScene().lookup("#edit_notice_pw");
                edit_notice_pwc = (Text) TeamProject.getPrimaryStage().getScene().lookup("#edit_notice_pwc");
                edit_notice_email = (Text) TeamProject.getPrimaryStage().getScene().lookup("#edit_notice_email");
                //TextField
                txtEditPassword = (TextField) TeamProject.getPrimaryStage().getScene().lookup("#txtEditPassword");
                txtEditPasswordConfirm = (TextField) TeamProject.getPrimaryStage().getScene().lookup("#txtEditPasswordConfirm");
                txtEditEmail = (TextField) TeamProject.getPrimaryStage().getScene().lookup("#txtEditEmail");
                break;
            default:
                break;
        }
    }
    
    /*
    * button for starting game
    */
    @FXML
    private void startGame(ActionEvent event) throws IOException {
        //System.out.println((Node)event.getSource());
        currentScreen = ScreenType.PLAY;
        root = FXMLLoader.load(getClass().getResource("GamePlay.fxml"));
        TeamProject.getPrimaryStage().setScene(new Scene(root));
        SetScreenResources();
        
        //generate the list of questions for this set
        gm.RandomizeQuestions();
        
        //reset the score
        gm.ResetScore();
        txtScore.setText(Integer.toString(gm.GetScore()));
        
        //set the text
        SetQuestionAndAnswers();
        
        //Start the timer
        iSeconds = MAXTIME;
        StartTimer();
        txtTimer.setText(Integer.toString(iSeconds));
        
        System.out.println("GamePlay.fxml opened");
        
        //TODO: include a timer
        //sample code can be found here:
        //http://asgteach.com/2011/10/javafx-animation-and-binding-simple-countdown-timer-2/
        
        
        TeamProject.getPrimaryStage().show();
    }
    
    private void StartTimer()
    {
        if(timeline != null)
            timeline.stop();
        iSeconds = MAXTIME;
        txtTimer.setText(Integer.toString(iSeconds));
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
            txtTimer.setText(Integer.toString(--iSeconds));
            if(iSeconds <= 0)
                try {
                    GoToNextQuestion();
                } catch (IOException ex) {
                    Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    
    /**
     * Function to update the text for questions and answers
     */
    private void SetQuestionAndAnswers()
    {
        //shuffle the answers
        gm.GetCurrentQuestion().ShuffleAnswers();
        
        //set the text
        txtQuestion.setText(gm.GetCurrentQuestion().GetQuestion());
        
        rdoAnswer1.setText(gm.GetCurrentQuestion().GetAnswers().get(0).GetAnswer());
        rdoAnswer2.setText(gm.GetCurrentQuestion().GetAnswers().get(1).GetAnswer());
        rdoAnswer3.setText(gm.GetCurrentQuestion().GetAnswers().get(2).GetAnswer());
        rdoAnswer4.setText(gm.GetCurrentQuestion().GetAnswers().get(3).GetAnswer());
        
        
    }
    
    /*
    * button for exit game
    */
    @FXML
    private void exitGame(ActionEvent event) throws IOException {
        currentScreen = ScreenType.READY;
        root = FXMLLoader.load(getClass().getResource("GameLobby.fxml"));
        TeamProject.getPrimaryStage().setScene(new Scene(root));
        TeamProject.getPrimaryStage().show();
        SetScreenResources();
        XYChart.Series best = getBestScore(player, "Best Score");
        XYChart.Series avg = getAvgScore(player, "Average Score");
        System.out.println("GameLobby.fxml opened");
    }
    
    
    
    @FXML
    private void submitAnswer(ActionEvent event) throws IOException{
//        this is the button for submit quiz answer
        //first, check the submitted answer
        //if none of the answers are selected, this button does nothing.
        int iAnswer;
        //Also deselect the button for the next question
        if(rdoAnswer1.isSelected())
        {
            iAnswer = 0;
            rdoAnswer1.setSelected(false);
        }
        else if(rdoAnswer2.isSelected())
        {
            iAnswer = 1;
            rdoAnswer2.setSelected(false);
        }
        else if(rdoAnswer3.isSelected())
        {
            iAnswer = 2;
            rdoAnswer3.setSelected(false);
        }
        else if(rdoAnswer4.isSelected())
        {
            iAnswer = 3;
            rdoAnswer4.setSelected(false);
        }
        else
            return;
        
        //Now check the answer
        gm.CheckAnswer(iAnswer, gm.GetCurrentQuestion());
        
        
        
        //Go to the next question
        GoToNextQuestion();
    }
    
    private void GoToNextQuestion() throws IOException
    {
        //Increment the question and find out if there are any more questions
        gm.IncrementQuestion();
        if(gm.GetNumQuestionsRemaining() > 0)
        {
            iSeconds = MAXTIME;
            txtScore.setText(Integer.toString(gm.GetScore()));
            SetQuestionAndAnswers();
            //reset the timer
            //timer = new Timer();
            StartTimer();
        }
        else
        {
            timeline.stop();
            //if no more questions, go to the results screen
            currentScreen = ScreenType.RESULTS;
            root = FXMLLoader.load(getClass().getResource("GameEnd.fxml"));
            TeamProject.getPrimaryStage().setScene(new Scene(root));
            TeamProject.getPrimaryStage().show();
            SetScreenResources();
            //update the score
            txtFinalScore.setText(Integer.toString(gm.GetScore()));
            System.out.println("GameEnd.fxml opened");
        }
    }
    
    /*
    * button for going back to user game lobby host
    */
    @FXML
    private void replayHost(ActionEvent event) throws IOException {
        currentScreen = ScreenType.PLAY;
        root = FXMLLoader.load(getClass().getResource("GamePlay.fxml"));
        TeamProject.getPrimaryStage().setScene(new Scene(root));
        TeamProject.getPrimaryStage().show();
        SetScreenResources();
        System.out.println("GameLobby.fxml opened");
        //generate the list of questions for this set
        gm.RandomizeQuestions();
        
        //reset the score
        gm.ResetScore();
        txtScore.setText(Integer.toString(gm.GetScore()));
        
        //set the text
        SetQuestionAndAnswers();
        //reset the timer
        iSeconds = MAXTIME;
        StartTimer();
        txtTimer.setText(Integer.toString(iSeconds));
    }
    
    
    
    
    
    
    
    /**
     * 
     * This section is functions to be maintained by Aeri
     */
 
    
    
    /*
    * button for going to register page
    */
    @FXML
    private void openRegister(ActionEvent event) throws IOException  {
        currentScreen = ScreenType.REGISTER;
        root = FXMLLoader.load(getClass().getResource("Register.fxml"));        
        TeamProject.getPrimaryStage().setScene(new Scene(root));
        TeamProject.getPrimaryStage().show();
        SetScreenResources();
        System.out.println("Register.fxml opened");
    }
    
    /*
    * button for going to Login page
    */
    @FXML
    private void goToLogin(ActionEvent event) throws IOException {
        currentScreen = ScreenType.LOGIN;
        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        TeamProject.getPrimaryStage().setScene(new Scene(root));
        TeamProject.getPrimaryStage().show();
        SetScreenResources();
        System.out.println("Login.fxml opened");
    }
    
    /*
    * button for going to Game Lobby page if user success
    */
    
    @FXML
    private void openLogin(ActionEvent event) throws IOException {
        String id = txtId.getText();
        String pw = txtPassword.getText();
        String email;

        Alert alert = new Alert(AlertType.INFORMATION);
        
        try{
            if(id.equals("") || pw.equals("")){
                alert.setTitle("Login Failed");
                alert.setContentText("Please check your ID and password");
                alert.showAndWait();
            }else{
                DBManager db = new DBManager();
                Player temp = new Player(id, pw);
                temp = db.loginPlayer(temp);
                boolean checkLogin = !(temp == null);
                if(checkLogin){
                    player = temp;
                    
                    currentScreen = ScreenType.READY;
                    root = FXMLLoader.load(getClass().getResource("GameLobby.fxml"));
                    TeamProject.getPrimaryStage().setScene(new Scene(root));
                    TeamProject.getPrimaryStage().show();
                    SetScreenResources();  

                    XYChart.Series best = getBestScore(player, "Best Score");
                    XYChart.Series avg = getAvgScore(player, "Average Score");
                    graphMyStastics.getData().addAll(best, avg);
                    
                    System.out.println("GameLobby.fxml opened");
                }else{
                    alert.setTitle("Login Failed");
                    alert.setContentText("Please check your ID and password");
                    alert.showAndWait();
                }
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    
    public XYChart.Series getBestScore (Player p, String name){
        int numScore = p.getBestScore();
        XYChart.Series data = new XYChart.Series<>();
        data.setName(name);
        XYChart.Data<String, Number> best = new XYChart.Data<String, Number>("", 75);
        data.getData().add(best);
        
        return data;
    }
    public XYChart.Series getAvgScore (Player p, String name){
        int numScore = p.getAverageScore();
        XYChart.Series data = new XYChart.Series<>();
        data.setName(name);
        XYChart.Data<String, Number> best = new XYChart.Data<String, Number>("", 65);
        data.getData().add(best);
        
        return data;
    }
    
    /*
    * button for going to find username and password page
    */
    @FXML
    private void openFindIdPw(ActionEvent event) throws IOException {
        currentScreen = ScreenType.FORGOT_PASSWORD;
        root = FXMLLoader.load(getClass().getResource("FindIdPassword.fxml"));
        TeamProject.getPrimaryStage().setScene(new Scene(root));
        TeamProject.getPrimaryStage().show();
        SetScreenResources();
        System.out.println("FindIdPassword.fxml opened");
    }
    
    /*
    * button for creating user account
    */
    @FXML
    private void creatAccount(ActionEvent event) throws IOException, SQLException{
        String id = txtRegisterId.getText();
        String pw = txtRegisterPassword.getText();
        String pwc = txtRegisterPasswordConfirm.getText();
        String email = txtRegisterEmail.getText();
        boolean checkID = checkID(id, reg_notice_id);
        boolean checkPW = checkPW(pw, reg_notice_pw);
        boolean checkPWC = checkPWC(pwc, pw, reg_notice_pwc);
        boolean checkEmail = checkEmail(email, reg_notice_email);
        
        try{
            if(checkID && checkPW & checkPWC && checkEmail){
                Player temp = new Player(id, pw);
                temp.setEmail(email);
                DBManager db = new DBManager();
                db.submitPlayer(temp);
                
                currentScreen = ScreenType.LOGIN;
                root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                TeamProject.getPrimaryStage().setScene(new Scene(root));
                TeamProject.getPrimaryStage().show();
                SetScreenResources();

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Create Account");
                alert.setContentText("User Account is created");
                alert.showAndWait();

                System.out.println("Login.fxml opened");
            }
        }catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
    }
    public boolean checkID(String id, Text txt) throws SQLException{
        boolean status = false;
        DBManager db = new DBManager();
        int numRow = db.getNumRow(id);
        
        if(id.equals("")){
            txt.setText("This field is required.");
            status = false;
        }else{
            if(numRow == 0){
                char[] idArray = id.toCharArray();
                if(idArray.length < 5 || idArray.length > 8){
                    txt.setText("Length of ID should be between 5 and 8");
                    status = false;
                }else{
                    txt.setText("");
                    status = true;
                }
            }else{
                txt.setText("This ID is duplicated");
                status = false;
            }
        }
        return status;
    }
    public boolean checkPW(String pw, Text txt){
        boolean status = false;
        if(pw.equals("")){
            txt.setText("This field is required.");
            status = false;
        }else{
            char[] pwArray = pw.toCharArray();
            if(pwArray.length < 5){
                txt.setText("Password is weak");
                status = false;
            }else{
                txt.setText("");
                status = true;
            }
        }
        return status;
    }
    public boolean checkPWC(String pwc, String pw, Text txt){
        boolean status = false;
        if(pwc.equals("")){
            txt.setText("This field is required.");
            status = false;
        }else{
            if(!pwc.equals(pw)){
                txt.setText("This is not mached to Password.");
                status = false;
            }else{
                txt.setText("");
                status = true;
            }
        }
        return status;
    }
    public boolean checkEmail(String email, Text txt) throws SQLException{
        boolean status = false;
        DBManager db = new DBManager();
        int emailNumRow = db.getEmailNumRow(email);
        String regex =  "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regex);
        
        if(email.equals("")){
            txt.setText("This field is required.");
            status = false;
        }else{
            if(emailNumRow == 0){
                Matcher matcher  = pattern.matcher(email);
                if(!matcher.matches()){
                    txt.setText("This is not email format.");
                    status= false;
                }else{
                    txt.setText("");
                    status= true;
                } 
            }else{
                txt.setText("Email is duplicated.");
                status= false;
            }
        }
        return status;
    }
    
    /*
    * button for reseting user Account 
    */
    @FXML
    private void findAccount(ActionEvent event) throws IOException{
        Alert alert = new Alert(AlertType.CONFIRMATION);
        String email = txtEmailFind.getText();
        String findId = "";
        String findPw = "";
        
        String regex =  "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regex);
        
        try{
            DBManager db = new DBManager();
            int emailNumRow = db.getEmailNumRow(email);
            
            if(email.equals("")){
                
                alert.setTitle("Login Information");
                alert.setContentText("Email should be entered");
            }else{
                Matcher matcher  = pattern.matcher(email);
                if(!matcher.matches()){
                    find_notice_email.setText("This is not email format.");
                }else{
                    find_notice_email.setText("");
                    if(emailNumRow == 1){
                        boolean checkAccount = db.findIdPw(email);
                        if(checkAccount){
                            findId = db.getUserId();
                            findPw = db.getUserPw();

                            JFrame frame = new JFrame("InputDialoague");
                            String resetPassword = "";
                            String input = JOptionPane.showInputDialog(frame, "Your id :" + findId + "\n" +
                                    "Please reset your password");
                            
                            db.editPassword(email, input);
                            
                            Optional<ButtonType> result = alert.showAndWait();
                            if(result.get() == ButtonType.OK){
                                currentScreen = ScreenType.LOGIN;
                                root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                                TeamProject.getPrimaryStage().setScene(new Scene(root));
                                TeamProject.getPrimaryStage().show();
                                SetScreenResources();
                                System.out.println("Login.fxml opened");
                            }
                        }else{
                            alert.setTitle("Login Information");
                            alert.setContentText("Can't find user information\nWill you find again?");

                            Optional<ButtonType> result = alert.showAndWait();
                            if(result.get() == ButtonType.CANCEL){
                                currentScreen = ScreenType.LOGIN;
                                root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                                TeamProject.getPrimaryStage().setScene(new Scene(root));
                                TeamProject.getPrimaryStage().show();
                                SetScreenResources();
                                System.out.println("Login.fxml opened");
                            }
                        }
                    }else{
                        find_notice_email.setText("Can't Find email");
                    }
                } 
            }
            
        }catch(SQLException ex){
            System.out.println(ex.getMessage());            
        }
    }

    /*
    * button for going to Game Lobby page if user success
    */
    @FXML
    private void userLogout(ActionEvent event) throws IOException {
        // initializing when user log out
        player.setUsername("");
        player.setPassword("");
        player.setEmail("");
        
        currentScreen = ScreenType.LOGIN;
        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        TeamProject.getPrimaryStage().setScene(new Scene(root));
        TeamProject.getPrimaryStage().show();
        SetScreenResources();
        System.out.println("Login.fxml opened");
    }
    
    /*
    * button for going back to main
    */
    @FXML
    private void backToMain(ActionEvent event) throws IOException {
        currentScreen = ScreenType.READY;
        root = FXMLLoader.load(getClass().getResource("GameLobby.fxml"));
        TeamProject.getPrimaryStage().setScene(new Scene(root));
        TeamProject.getPrimaryStage().show();
        SetScreenResources();
        XYChart.Series best = getBestScore(player, "Best Score");
        XYChart.Series avg = getAvgScore(player, "Average Score");
        System.out.println("GameLobby.fxml opened");
    }
    
    @FXML
    private void resetMyTotalResult(ActionEvent event) throws IOException
    {
        //reset user score
    }
    
    @FXML
    private void editUserProfile(ActionEvent event) throws IOException
    {
        currentScreen = ScreenType.EDIT;
        root = FXMLLoader.load(getClass().getResource("EditProfile.fxml"));
        TeamProject.getPrimaryStage().setScene(new Scene(root));
        TeamProject.getPrimaryStage().show();
        SetScreenResources();
        
        String id = player.getUsername();
        txtIdEdit.setText(id);
        System.out.println("EditProfile.fxml opened");
    }
    
    @FXML
    private void editUserProfileConfirm(ActionEvent event) throws IOException, SQLException{
        String id = player.getUsername();
        String pw = txtEditPassword.getText();
        String pwc = txtEditPasswordConfirm.getText();
        String email = txtEditEmail.getText();
        
        boolean checkPW = checkPW(pw, edit_notice_pw);
        boolean checkPWC = checkPWC(pwc, pw, edit_notice_pwc);
        boolean checkEmail = checkEmail(email, edit_notice_email);

        try{
            if(checkPW && checkPWC && checkEmail){
                DBManager db = new DBManager();
                Player temp = new Player(id, pw);
                temp.setEmail(email);
                db.editUser(temp);
                
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Edit Profile");
                alert.setContentText("User information is edited");
                alert.showAndWait();
                
                currentScreen = ScreenType.READY;
                root = FXMLLoader.load(getClass().getResource("GameLobby.fxml"));
                TeamProject.getPrimaryStage().setScene(new Scene(root));
                TeamProject.getPrimaryStage().show();
                SetScreenResources();
                XYChart.Series best = getBestScore(player, "Best Score");
                XYChart.Series avg = getAvgScore(player, "Average Score");
                System.out.println("GameLobby.fxml opened");
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    
}
