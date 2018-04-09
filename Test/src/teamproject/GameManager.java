/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teamproject;

import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author c0692516
 */
public class GameManager 
{
    private LinkedList<Question> listAllQuestions;
    private LinkedList<Integer> listQuestionIndexes;
    
    public GameManager()
    {
        listAllQuestions = new LinkedList();
        listQuestionIndexes = new LinkedList();
    }
    
    public void GenerateQuestions()
    {
        //this class will call the database class and populate it there
        //for now, generate it with two questions
        listAllQuestions.add(new Question(0,"What is the best leaf?","2","3","4","blue"));
        listAllQuestions.add(new Question(1,"Who is not one of the best coders?",
            "Daffy Duck", "Bradley Coleman", "Aeri Jung", "Michael Patoine"));
    }
    
    //a function to create the list of random questions for the set
    public void RandomizeQuestions()
    {
        listQuestionIndexes.clear();
        int length = listAllQuestions.size() >= 10 ? 10 : listAllQuestions.size();
        for(int i = 0; i < length; i++)
        {
            Random rnd = new Random();
            int temp = rnd.nextInt(length);
            //make sure the questions are different
            for(int j = 0; j < listQuestionIndexes.size(); j++)
            {
                if(listQuestionIndexes.get(j) == temp)
                {
                    j = -1;
                    temp = rnd.nextInt(length);
                }
            }
            listQuestionIndexes.add(temp);
        }
    }
    
    public Question GetCurrentQuestion()
    {
        //TODO: insert a custom try-catch error catcher to check if the stored index
        //is out of bounds
        if(listQuestionIndexes.size() > 0)
            return listAllQuestions.get(listQuestionIndexes.get(0));
        return listAllQuestions.getFirst();
    }
    
    public void IncrementQuestion()
    {
        if(listQuestionIndexes.size() > 0)
            listQuestionIndexes.removeFirst();
    }
}
