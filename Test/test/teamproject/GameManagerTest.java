/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teamproject;

import java.util.ArrayList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author c0692516
 */
public class GameManagerTest {
    
    public GameManagerTest() {
    }
    
    @Before
    public void setUp() {
        System.out.println("Starting tests");
    }
    
    @After
    public void tearDown() {
        System.out.println("Ending tests");
    }

    /**
     * Test of RandomizeQuestions method, of class GameManager.
     * making sure the question list is random
     */
    @Test
    public void testRandomizeQuestions() {
        System.out.println("RandomizeQuestions");
        GameManager instance = new GameManager();
        instance.GenerateQuestions();
        instance.RandomizeQuestions();
        ArrayList<Integer> list = new ArrayList();
        int limit = instance.GetNumQuestionsRemaining();
        for(int i = 0; i < limit; i++)
        {
            list.add(instance.GetCurrentQuestion().getId());
            instance.IncrementQuestion();
        }
        
        instance.RandomizeQuestions();
        //if random, these ids should be different
        limit = instance.GetNumQuestionsRemaining();
        boolean bSame = true;
        for(int i = 0; i < limit; i++)
        {
            if(list.get(i) != instance.GetCurrentQuestion().getId())
            {
                bSame = false;
                break;
            }
            instance.IncrementQuestion();
        }
        
        assertFalse(bSame);
    }

    

    /**
     * Test of GetCurrentQuestion method, of class GameManager.
     */
    @Test
    public void testGetCurrentQuestion() {
        System.out.println("GetCurrentQuestion");
        GameManager instance = new GameManager();
        assertEquals(instance.GetCurrentQuestion(), null);
        // TODO review the generated test code and remove the default call to fail.
        
    }

    

    

    /**
     * Test of CheckAnswer method, of class GameManager.
     */
    @Test
    public void testCheckAnswerCorrect() {
        System.out.println("CheckAnswer");
        GameManager instance = new GameManager();
        instance.SetScore(0);
        instance.GenerateQuestions();
        //When not shuffled, the correct answer is always 0
        
        
        instance.CheckAnswer(0,GameManager.GetSpecificQuestion(0));
        
        assertEquals(instance.GetScore(), 100);
    }
    /**
     * Test of CheckAnswer method, of class GameManager.
     */
    @Test
    public void testCheckAnswerIncorrect() {
        System.out.println("CheckAnswerWrong");
        GameManager instance = new GameManager();
        instance.SetScore(0);
        instance.GenerateQuestions();
        //When not shuffled, the correct answer is always 0
        
        
        instance.CheckAnswer(1,GameManager.GetSpecificQuestion(0));
        
        assertEquals(instance.GetScore(), 0);
    }
    
    @Test
    public void testCheckAnswerOutOfBounds() {
        System.out.println("CheckAnswerOutOfBounds");
        GameManager instance = new GameManager();
        instance.SetScore(0);
        instance.GenerateQuestions();
        //In the database, the first question's correct answer is at index 2
        
        
        instance.CheckAnswer(5,GameManager.GetSpecificQuestion(0));
        
        assertEquals(instance.GetScore(), 0);
    }
}
