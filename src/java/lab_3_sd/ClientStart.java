/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab_3_sd;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author Nelson
 */
public class ClientStart {
    
    public static int puerto_index;
    public static int puerto_cache;
    public static int puerto_front;
    public static int nParticiones;
    public static String stopwords;
    public static ArrayList<String> palabras = new ArrayList<>();
    
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException, Exception {
        
        // Lectura de parametros
        
        FileReader fr = new FileReader("FrontConf.ini");
        
        if(fr == null){
            System.out.println("Archivo erroneo");
            System.exit(1);
        }
        BufferedReader bf = new BufferedReader(fr);
        String parametro;
        int counter = 0;
        
        while( (parametro = bf.readLine() ) != null ){
            if(parametro.charAt(0) != '/'){
                switch (counter) {
                    case 0:
                        puerto_front = Integer.parseInt(parametro);
                        System.out.println("parametro " + counter + ": " + puerto_front);
                        break;
                    case 1:
                        nParticiones = Integer.parseInt(parametro);
                        System.out.println("parametro " + counter + ": " + nParticiones);
                        break;
                    case 2:
                        puerto_index = Integer.parseInt(parametro);
                        System.out.println("parametro " + counter + ": " + puerto_index);
                        break;
                    case 3:
                        puerto_cache = Integer.parseInt(parametro);
                        System.out.println("parametro " + counter + ": " + puerto_cache);
                        break;
                    case 4:
                        stopwords = parametro;
                        System.out.println("parametro " + counter + ": " + stopwords);
                        break;
                    default:
                        System.out.println("parametro " + counter + ": " + parametro + "no es usado");
                        break;
                }
                counter++;            
            }
        }
        fr.close();
        bf.close();        
        
        ServerSocket ssock = new ServerSocket(puerto_front);
        System.out.println("Listening...");
        while (true) {
           Socket sock = ssock.accept();
           System.out.println("Connected");
           new Thread(new MultiThreadServer(sock, nParticiones)).start();
        }
    }
    
//    public static String GET(String sentence) throws IOException{
//        String fromServer;
//        
////        FileReader fr = new FileReader("FrontConf.ini");        
////        BufferedReader bf = new BufferedReader(fr);
////        String parametro;
////        parametro = bf.readLine();
////        parametro = bf.readLine();
////        int port = Integer.parseInt(parametro);
//        
//        System.out.println("el puerto es: " + 1324);
//        
//        // estructura improvisada
//        sentence = "GET /consulta/" + sentence;
//        String[] requests = {sentence};
//        
//        for (String request : requests) {
//            //Buffer para enviar el dato al server
//            try ( //Socket para el cliente (host, puerto)
//                
//                Socket clientSocket = new Socket("localhost", 1324)) {
//                //Buffer para enviar el dato al server
//                DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
//                //Buffer para recibir dato del servidor
//                BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//                //Leemos del cliente y lo mandamos al servidor
//                outToServer.writeBytes(request + '\n');
//                //Recibimos del servidor
//                fromServer = inFromServer.readLine();
//                System.out.println("Server response: " + fromServer);
//                //Cerramos el socket
//            }
//        }
//        return sentence;
//    }
    
    public static String filtrarSW(String cadena) throws FileNotFoundException, IOException {
        
        try (FileReader xr = new FileReader("C:\\Users\\Nelson\\Documents\\NetBeansProjects\\LAB3_SD\\stop-words-spanish.txt")) {
//        try (FileReader xr = new FileReader("C:\\Users\\Amaranta Saball\\Documents\\NetBeansProjects\\LAB3_SD\\stop-words-spanish.txt")) {
            if(xr == null){
                System.out.println("Archivo erroneo");
                System.exit(1);
            }
            try (BufferedReader xf = new BufferedReader(xr)) {
                palabras = new ArrayList();
                String aux1;
                
                while( (aux1 = xf.readLine() ) != null ){
                    palabras.add(aux1);
                }
                xf.close();
            }
            xr.close();  
            System.out.println("lista de " + palabras.size() + " stopwords");
        }
              
        // reemplazamos caracteres especiales        
        cadena = cadena.replaceAll("[Ñ]","N");
        cadena = cadena.replaceAll("[ñ]","n");
        
        cadena = cadena.replaceAll("[èéêë]","e");
        cadena = cadena.replaceAll("[ùúûü]","u");
        cadena = cadena.replaceAll("[ìíîï]","i");
        cadena = cadena.replaceAll("[àáâä]","a");
        cadena = cadena.replaceAll("[òóôö]","o");

        cadena = cadena.replaceAll("[ÈÉÊË]","E");
        cadena = cadena.replaceAll("[ÙÚÛÜ]","U");
        cadena = cadena.replaceAll("[ÌÍÎÏ]","I");
        cadena = cadena.replaceAll("[ÀÁÂÄ]","A");
        cadena = cadena.replaceAll("[ÒÓÔÖ]","O");
        
        cadena = cadena.replaceAll("[|.,<>=/:;]"," ");        
        cadena = cadena.replaceAll("[^a-z \\nA-Z]","");
        cadena = cadena.replaceAll("\\s+", " ");
        
        cadena = cadena.toLowerCase();
        cadena = cadena.trim();
        
        // eliminamos las stopwords, los espacios aseguran no eliminar parte de palabras
        for (int i = 0; i < palabras.size(); i++) {
            String aux = "\\b"+palabras.get(i)+"\\b";            
            cadena = cadena.replaceAll( aux , "");
        }
        
        return cadena;
    }
}
