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
    private static LinkedList<Question> listAllQuestions;
    private static LinkedList<Integer> listQuestionIndexes;
    private static int iCurrentScore = 0;
    
    public GameManager()
    {
        listAllQuestions = new LinkedList();
        listQuestionIndexes = new LinkedList();
        //iCurrentScore = 0;
    }
    
    //get a specific question from the question list
    public static Question GetSpecificQuestion(int index)
    {
        try
        {
            if(index >= listAllQuestions.size())
                throw new IndexOutOfBoundsException();
        } catch(IndexOutOfBoundsException ex)
        {
            System.err.println(ex.getMessage());
        }
        return listAllQuestions.get(index);
    }
    
    public int GetScore(){return iCurrentScore;}
    public void SetScore(int score){iCurrentScore = score;}
    public void ResetScore(){iCurrentScore = 0;}
    
    public void GenerateQuestions()
    {
        //this class will call the database class and populate it there
        //for now, generate it with two questions
        DBManager.GetQuestions(listAllQuestions);
    }
    
    //a function to create the list of random questions for the set
    public void RandomizeQuestions()
    {
        listQuestionIndexes.clear();
        int length = listAllQuestions.size() >= 10 ? 10 : listAllQuestions.size();
        for(int i = 0; i < length; i++)
        {
            Random rnd = new Random();
            int temp = rnd.nextInt(listAllQuestions.size());
            //make sure the questions are different
            for(int j = 0; j < listQuestionIndexes.size(); j++)
            {
                if(listQuestionIndexes.get(j) == temp)
                {
                    j = -1;
                    temp = rnd.nextInt(listAllQuestions.size());
                }
            }
            listQuestionIndexes.add(temp);
        }
    }
    
    public Question GetCurrentQuestion()
    {
        //TODO: insert a custom try-catch error catcher to check if the stored index
        //is out of bounds
        try
        {
            if(listAllQuestions.size() <= 0)
                throw new NoQuestionsException();
            return listAllQuestions.get(listQuestionIndexes.get(0));
        } catch(NoQuestionsException ex)
        {
            System.err.println(ex.getMessage());
            return null;//new Question(-1,"","","","","");
        }
        
    }
    
    public void IncrementQuestion()
    {
       
        try
        {
            if(listAllQuestions.size() <= 0)
                throw new NoQuestionsException();
            listQuestionIndexes.removeFirst();
        } catch(NoQuestionsException ex)
        {
            System.err.println(ex.getMessage());
        }
    }
    
    public int GetNumQuestionsRemaining()
    {
        return listQuestionIndexes.size();
    }
    
    public void CheckAnswer(int index, Question q)
    {
        try
        {
            //make sure index is within range
            if(index < 0 || index > 3)
                throw new IndexOutOfBoundsException("No answer at that index.");

            //check if the answer is correct
            if(q.GetAnswers().get(index).GetCorrect())
            {
                iCurrentScore += 100;
            }
        } catch(IndexOutOfBoundsException ex)
        {
            System.err.println(ex.getMessage());
        }
    }
}
