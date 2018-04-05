/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teamproject;

import java.io.Serializable;

/**
 * @author c0692516
 */
public class Answer implements Serializable
{
    private String sAnswer;
    private boolean bCorrect;
    
    public Answer(String answer)
    {
        sAnswer = answer;
        bCorrect = false;
    }
    
    public Answer(String answer, boolean correct)
    {
        sAnswer = answer;
        bCorrect = correct;
    }
    
    public String GetAnswer(){return sAnswer;}
    public void SetAnswer(String s){sAnswer = s;}
    public boolean GetCorrect(){return bCorrect;}
    public void SetCorrect(boolean b){bCorrect = b;}
}
