package org.academiadecodigo.bootcamp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {

    private final int PORT = 8080;
    private ServerSocket serverSocket;
    private Socket browserSocket;
    private BufferedReader input;
    private DataOutputStream output;
    private File file;
    private String filePath;
    private String fileType;
    private String header;
    private String contentType;
    private String statusCode;


    public static void main(String[] args){
        try {
            WebServer webServer = new WebServer();
            webServer.startConnection();

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void startConnection() throws IOException{
        serverSocket = new ServerSocket(PORT);
        while (true) {
            browserSocket = serverSocket.accept();
            input = new BufferedReader(new InputStreamReader(browserSocket.getInputStream()));

            sendData();
            closeConnection();
        }
    }

    public void sendData() throws IOException{

        filePath = input.readLine().split(" ")[1];
        file = new File("www" + filePath);

        if(!file.exists()){
            filePath = "www/404.html";
            getStatusCode("error");
            fileType = "html";
        }

        if(filePath.equals("/")) {
            filePath = "www/index.html";
            getStatusCode("document");
            fileType = "html";
        }

        file = new File(filePath);
        fileType = filePath.split("\\.")[1];
        output = new DataOutputStream(browserSocket.getOutputStream());
        output.writeBytes(getHeader());

        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] buffer = new byte[1024];

        int numBytes;

        while((numBytes = fileInputStream.read(buffer)) != -1){
            output.write(buffer, 0, numBytes);
        }
    }

    public void closeConnection() throws IOException{
        System.out.println("Connection has been terminated.");
        browserSocket.close();
        input.close();
    }

    public String getContentType() {
        switch (fileType) {
            case "html":
                contentType = "text/html;";
                break;
            case "jpeg":
                contentType = "image/jpeg;";
                break;
        }
        return contentType;
    }

    public String getStatusCode(String type) {
        switch (type) {
            case "document":
                statusCode = "200 Document Follows";
                break;
            case "error":
                statusCode = "404 Not Found";
                break;
        }
        return statusCode;
    }

    public String getHeader() {
        getContentType();

        header = "HTTP/1.0 " + statusCode + "\r\n" +
                "Content-Type: " + contentType + " \r\n" +
                "Content-Length: " + file.length() + "\r\n" +
                "\r\n";

        return header;
    }
}