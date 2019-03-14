package org.academiadecodigo.bootcamp;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable {

    private BufferedReader input;
    private DataOutputStream output;
    private Socket socket;

    public Client(Socket socket) {
        this.socket = socket;

    }

    @Override
    public void run() {
            try {
                openStreams();
                sendData();
                closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private void openStreams() throws IOException{

        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new DataOutputStream(socket.getOutputStream());


    }

    private void sendData() throws IOException {


        String headerRequest = input.readLine();
        System.out.println(headerRequest);
        String filePath = headerRequest.split(" ")[1];

        File file = new File("www" + filePath);
        System.out.println(filePath);

        Header header = new Header(file.length());
        if (!file.exists()) {
            filePath = "www/404.html";
            header.setStatusCode("error");
            header.setContentType("html");
            file = new File(filePath);
        }

        if (filePath.equals("/") || filePath.equals("")) {
            filePath = "www/index.html";
            header.setStatusCode("document");
            header.setContentType("html");
            file = new File(filePath);
        }
        header.setContentType(filePath.split("\\.")[1]);


        output.writeBytes(header.getHeader());

        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] buffer = new byte[1024];

        int numBytes;

        while ((numBytes = fileInputStream.read(buffer)) != -1) {
            output.write(buffer, 0, numBytes);
        }

        fileInputStream.close();

    }

    private void closeConnection() throws IOException {
        System.out.println("Connection has been terminated.");
        socket.close();
        input.close();
        output.close();

    }
}
