/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teamproject;

import java.io.Serializable;

/**
 * @author c0692516 Michael Patoine
 */
public class Answer implements Serializable
{
    private String sAnswer;
    private boolean bCorrect;
    
    /**
     * Class constructor
     * @param answer
     */
    public Answer(String answer)
    {
        sAnswer = answer;
        bCorrect = false;
    }
    
    /**
     * Class constructor
     * @param answer
     * @param correct
     */
    public Answer(String answer, boolean correct)
    {
        sAnswer = answer;
        bCorrect = correct;
    }
    
    /**
     * method for getting answer
     * @return sAnswer
     */
    public String GetAnswer(){return sAnswer;}

    /**
     * method for setting answer
     * @param s
     */
    public void SetAnswer(String s){sAnswer = s;}

    /**
     * method for getting correct answer
     * @return bCorrect
     */
    public boolean GetCorrect(){return bCorrect;}

    /**
     * method for setting correct answer
     * @param b
     */
    public void SetCorrect(boolean b){bCorrect = b;}
}
