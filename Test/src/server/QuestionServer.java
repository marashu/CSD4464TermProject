package server;


import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import teamproject.Question;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author c0692516
 */
public class QuestionServer 
{
    public static void main(String args[]) throws Exception {
        ServerSocket servSocket;
        Socket fromClientSocket;
        int cTosPortNumber = 1777;
        String str;

        Question q = new Question(1,"What is the letter a?","b","c","d","e");
        servSocket = new ServerSocket(cTosPortNumber);
        System.out.println("Waiting for a connection on " + cTosPortNumber);

        fromClientSocket = servSocket.accept();

        ObjectOutputStream oos = new ObjectOutputStream(fromClientSocket.getOutputStream());

        ObjectInputStream ois = new ObjectInputStream(fromClientSocket.getInputStream());

        oos.writeObject(q);
          
        oos.close();
        fromClientSocket.close();
    }
}
