/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teamproject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author c0692516 Michael Patoine
 */
public class Question implements Serializable
{
    private int id;
    private String question;
    private ArrayList<Answer> answers;

    /**
     * method for getting id
     * @return
     */
    public int getId() {
        return id;
    }
    
    /**
     * Class constructor
     * @param id
     * @param quest
     * @param q1
     * @param q2
     * @param q3
     * @param q4
     */
    public Question(int id, String quest, String q1, String q2, String q3, String q4)
    {
        this.id = id;
        this.question = quest;
        this.answers = new ArrayList();
        answers.add(new Answer(q1, true));
        answers.add(new Answer(q2));
        answers.add(new Answer(q3));
        answers.add(new Answer(q4));
    }
    
    /**
     * method for getting question
     * @return
     */
    public String GetQuestion(){return question;}

    /**
     * method for setting question
     * @param s
     */
    public void SetQuestion(String s){question = s;}

    /**
     * method for getting answers array list
     * @return answers
     */
    public ArrayList<Answer> GetAnswers(){return answers;}
    
    /**
     * method for shuffling answers
     */
    public void ShuffleAnswers()
    {
        Collections.shuffle(answers);
    }
    
    /**
     * method for getting correct answers
     * @return
     */
    public int GetCorrectAnswer()
    {
        for(Answer a : answers)
        {
            if(a.GetCorrect())
                return answers.indexOf(a);
        }
        return -1;
    }
}
