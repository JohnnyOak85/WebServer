package org.academiadecodigo.bootcamp;

import java.io.*;
import java.net.Socket;

public class ClientConnection implements Runnable {

    private BufferedReader input;
    private DataOutputStream output;
    private Socket socket;

    /**
     * Constructor method that receives a socket.
     * @param socket
     */
    public ClientConnection(Socket socket) {
        this.socket = socket;
    }

    /**
     * Main method that uses all others to communicate between the server and the browser.
     */
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

    /**
     * Opens the streams to receive a request from the browser
     * and send data back.
     * @throws IOException
     */
    private void openStreams() throws IOException{
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new DataOutputStream(socket.getOutputStream());
    }

    /**
     * Sends the data back to the browser.
     * @throws IOException
     */
    private void sendData() throws IOException {
        FileDispatcher fileDispatcher = new FileDispatcher(getRequest());
        File file = fileDispatcher.getFile();

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

    /**
     * @return The request from the browser.
     * @throws IOException
     */
    private String getRequest() throws IOException{
        return input.readLine();
    }

    /**
     * Closes all connections.
     * @throws IOException
     */
    private void closeConnection() throws IOException {
        System.out.println("Connection has been terminated.");
        socket.close();
        input.close();
        output.close();
    }
}
