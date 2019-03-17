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
        FileDispatcher fileDispatcher = new FileDispatcher();
        File file = fileDispatcher.getFile(getRequest());

        Header header = new Header(file.length());
        header.setContentType(fileDispatcher.getContentType());
        header.setStatusCode(fileDispatcher.getFileType());

        output.writeBytes(header.getHeader());

        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] buffer = new byte[1024];

        int numBytes;
        while ((numBytes = fileInputStream.read(buffer)) != -1) {
            output.write(buffer, 0, numBytes);
        }
        fileInputStream.close();
    }

    private String getRequest() throws IOException{
        return input.readLine();
    }

    private void closeConnection() throws IOException {
        System.out.println("Connection has been terminated.");
        socket.close();
        input.close();
        output.close();
    }
}
