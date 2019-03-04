package org.academiadecodigo.bootcamp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {

    private Socket browserSocket;
    private BufferedReader input;

    public static void main(String[] args){
        try {
            WebServer webServer = new WebServer();
            webServer.startConnection();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void startConnection() throws IOException{
        final int PORT = 8080;
        ServerSocket serverSocket = new ServerSocket(PORT);
        while (true) {

            browserSocket = serverSocket.accept();

            Thread thread = new Thread(new Client(browserSocket));
            thread.start();
        }
    }
}