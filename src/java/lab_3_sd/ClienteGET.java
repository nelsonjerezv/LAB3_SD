
package lab_3_sd;

import java.io.*;
import java.net.*;

public class ClienteGET {
    
    
    
    
    public static String clientGET(String asd) throws Exception{
        
        FileReader fr = new FileReader("C:\\Users\\Nelson\\Documents\\NetBeansProjects\\LAB3_SD\\web\\FrontConf.ini");
        BufferedReader bf = new BufferedReader(fr);
        String parametro;
        parametro = bf.readLine();
        parametro = bf.readLine();
        
        //Variables
        String fromServer = null;

        String sentence = asd;
        
        // estructura improvisada
        sentence = "GET /consulta/" + sentence;
        String[] requests = {sentence};
        
        for (String request : requests) {
            //Socket para el cliente (host, puerto)
            Socket clientSocket = new Socket("localhost", Integer.parseInt(parametro));

            //Buffer para enviar el dato al server
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

            //Buffer para recibir dato del servidor
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            //Leemos del cliente y lo mandamos al servidor
            outToServer.writeBytes(request + '\n');

            //Recibimos del servidor
            fromServer = inFromServer.readLine();
            System.out.println("Server response: " + fromServer);

            //Cerramos el socket
            clientSocket.close();
        }
        return fromServer;
    }    
}
