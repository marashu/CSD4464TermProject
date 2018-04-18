/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teamproject;

/**
 *
 * @author c0692516 Michael Patoine
 */
public class NoQuestionsException extends Exception {

    /**
     * Class constructor
     */
    public NoQuestionsException()
    {
        super("Error: No questions loaded.");
    }
}
