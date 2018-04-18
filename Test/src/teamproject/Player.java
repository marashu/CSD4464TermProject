/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teamproject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author c0692516
 */
public class Player 
{
    private String username;
    private String password;
    private int bestScore;
    private int averageScore;
    private String email;
    private int id;

    /**
     * method for getting id
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * method for setting id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Class constructor
     */
    public Player()
    {
        
    }

    /**
     * Class constructor
     * @param username
     * @param password
     */
    public Player(String username, String password)
    {
        this.username = username;
        setPassword(password);
    }

    /**
     * method for getting user name
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     * method for setting user name
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * method for getting password
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * method for setting password
     * @param s
     */
    public void setPassword(String s) {
        //Get the bytes from the string
        byte[] raw = s.getBytes();
        //encrypt the raw byte data and assign it to the sPassword
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            raw = md.digest(raw);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
        password = Arrays.toString(raw);
    }
    
    /**
     * IMPORTANT: DO NOT CALL THIS FUNCTION UNLESS THE PASSWORD HAS ALREADY
     * BEEN ENCRYPTED (ie, retrieving it from the database)
     * 
     * method for encryping password
     * @param p
     */
    public void SetEncryptedPassword(String p)
    {
        this.password = p;
    }

    /**
     *
     * method for getting best score
     * @return
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
     * method for setting average score
     * @return
     */
    public int getAverageScore() {
        return averageScore;
    }

    /**
     * method for setting average score
     * @param averageScore
     */
    public void setAverageScore(int averageScore) {
        this.averageScore = averageScore;
    }
    
    /**
     * method for getting email
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * method for setting email
     * @param e
     */
    public void setEmail(String e) {
        this.email = e;
    }
    
    
}
