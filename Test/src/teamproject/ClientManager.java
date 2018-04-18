/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teamproject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author c0692516 Michael Patoine
 */
public class ClientManager 
{

    /**
     * method for getting question from server
     * @throws UnknownHostException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void GetQuestionFromServer() throws UnknownHostException, IOException, ClassNotFoundException
    {
        //for now, just output the question
        Socket socket1;
        int portNumber = 1777;
        String str = "";
        Question q;

        socket1 = new Socket(InetAddress.getLocalHost(), portNumber);

        ObjectInputStream ois = new ObjectInputStream(socket1.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket1.getOutputStream());
        
        while ((q = (Question) ois.readObject()) != null) {
            System.out.println(q.GetQuestion());
            System.out.println(q.GetAnswers().get(0).GetAnswer());
            System.out.println(q.GetAnswers().get(1).GetAnswer());
            System.out.println(q.GetAnswers().get(2).GetAnswer());
            System.out.println(q.GetAnswers().get(3).GetAnswer());

            //oos.writeObject("bye bye");
            break;
        }
        
        ois.close();
        oos.close();
        socket1.close();
        
    }
}
