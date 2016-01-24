/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab_3_sd;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static lab_3_sd.ClientStart.nParticiones;
import static lab_3_sd.ClientStart.puerto_cache;
import static lab_3_sd.ClientStart.puerto_front;
import static lab_3_sd.ClientStart.puerto_index;
import static lab_3_sd.ClientStart.stopwords;

/**
 *
 * @author Nelson
 */
class MultiThreadServer implements Runnable {

    Socket csocket;
    int particiones;
    private String fromClient;
    
    MultiThreadServer(Socket sock, int nParticiones) {
        this.csocket = sock;  
        this.particiones = nParticiones;
    }

    @Override
    public void run() {
        
        try {
            
            FileReader fr = new FileReader("FrontConf.ini");
            BufferedReader bf = new BufferedReader(fr);
            String parametro;
            int counter = 0;
            
            while( (parametro = bf.readLine() ) != null ){
                if(parametro.charAt(0) != '/'){
                    switch (counter) {
                        case 0:
                            puerto_front = Integer.parseInt(parametro);
                            break;
                        case 1:
                            nParticiones = Integer.parseInt(parametro);
                            break;
                        case 2:
                            puerto_index = Integer.parseInt(parametro);
                            break;
                        case 3:
                            puerto_cache = Integer.parseInt(parametro);
                            break;
                        case 4:
                            stopwords = parametro;
                            break;
                        default:
                            break;
                    }
                    counter++;
                }
            }
            try {
                fr.close();
                bf.close();
            } catch (IOException ex) {
                Logger.getLogger(MultiThreadServer.class.getName()).log(Level.SEVERE, null, ex);
            }  
            
            
            try{
                
                //Buffer para leer al cliente
                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(csocket.getInputStream()));
                //Buffer para enviar al cliente
                DataOutputStream outToClient = new DataOutputStream(csocket.getOutputStream());
                
                //Recibimos el dato del cliente y lo mostramos en el server
                fromClient =inFromClient.readLine();
                System.out.println("===== ===== ===== ===== =====");
                
                String[] tokens = fromClient.split(" ");
                String parametros = tokens[1];
                
                String http_method = tokens[0];
                
                String[] tokens_parametros = parametros.split("/");
                
                String resource = tokens_parametros.length > 1 ? tokens_parametros[1] : "";
                String id = tokens_parametros.length > 2 ? tokens_parametros[2] : "";
                
                //String meta_data = tokens.length > 2 ? tokens[2] : "";
                String meta_data = "";
                if(tokens.length > 2){
                    for (int i = 2; i < tokens.length; i++) {
                        id = id + " " +tokens[i];
                    }
                }
                
                System.out.println("CONSULTA:       " + fromClient);
                System.out.println("HTTP METHOD:    " + http_method);
                System.out.println("Resource:       " + resource);
                System.out.println("ID:             " + id);
                System.out.println("META DATA:      " + meta_data);
                
                // Determinamos la particion a acceder con una funcion hash
//            int ParticionDestino = hash(id, particiones.size());
//            System.out.println("Particion destino: " + ParticionDestino);

switch (http_method) {
    case "GET":
        // CACHE
        System.out.println("Enviamos la consulta(stopwords filtradas) al cache: " + id);
        id = "GET /consulta/" + id;
        String fromServer;            
        
        //Socket para el cliente (host, puerto)
        Socket clientSocket = new Socket("localhost", puerto_cache);
        //Buffer para enviar el dato al server
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
        //Buffer para recibir dato del servidor
        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        //Leemos del cliente y lo mandamos al servidor
        outToServer.writeBytes( id + '\n');
        //Recibimos del servidor
        fromServer = inFromServer.readLine();
        System.out.println("Cache response: " + fromServer);
        //Enviamos miss al cliente
        if(fromServer.equals("MISS")){
            outToClient.writeBytes("MISS\n");
        }
        else{
            outToClient.writeBytes(fromServer+"\n");
        }
        //Cerramos el socket
        clientSocket.close();
        // INDEX
        break;
    default:
        break;
}

            }catch (IOException ex) {
                Logger.getLogger(MultiThreadServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(MultiThreadServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
