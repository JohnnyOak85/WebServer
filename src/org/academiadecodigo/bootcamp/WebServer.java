package org.academiadecodigo.bootcamp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer {

    public static void main(String[] args){
        try {
            WebServer webServer = new WebServer();
            webServer.startConnection();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Starts the connection.
     * @throws IOException
     */
    private void startConnection() throws IOException{
        final int PORT = 8080;
        Socket browserSocket;
        ServerSocket serverSocket = new ServerSocket(PORT);
        while (true) {

            browserSocket = serverSocket.accept();
            ExecutorService fixedPool = Executors.newFixedThreadPool(50);

            fixedPool.submit(new ClientConnection(browserSocket));
        }
    }
}