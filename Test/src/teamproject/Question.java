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
 * @author c0692516
 */
public class Question implements Serializable
{
    private int id;
    private String question;
    private ArrayList<Answer> answers;

    public int getId() {
        return id;
    }
    
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
    
    public String GetQuestion(){return question;}
    public void SetQuestion(String s){question = s;}
    public ArrayList<Answer> GetAnswers(){return answers;}
    
    public void ShuffleAnswers()
    {
        Collections.shuffle(answers);
    }
    
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
