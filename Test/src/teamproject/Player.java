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
    
    public Player()
    {
        
    }
    public Player(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

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

    public int getBestScore() {
        return bestScore;
    }

    public void setBestScore(int bestScore) {
        this.bestScore = bestScore;
    }

    public int getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(int averageScore) {
        this.averageScore = averageScore;
    }
    
    
}
