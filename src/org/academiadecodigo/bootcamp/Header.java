package org.academiadecodigo.bootcamp;

import java.util.HashMap;
import java.util.Map;

public class Header {
    private Map<String, String> contentTypeMap;
    private String contentType;
    private String statusCode;
    private long length;

    public Header(long length) {
        this.length = length;
        contentTypeMap = new HashMap<>();
        buildMap();
    }

    private void buildMap() {
        contentTypeMap.put("html", "text/html");
        contentTypeMap.put("txt", "text/plain");
        contentTypeMap.put("jpg", "image/jpeg");
        contentTypeMap.put("gif", "image/gif");
        contentTypeMap.put("mp3", "audio/mpeg3");
        contentTypeMap.put("mpg", "video/mpeg");
        contentTypeMap.put("pdf", "application/pdf");
        contentTypeMap.put("zip", "application/zip");
    }

    public void setContentType(String fileType) {
        contentType = contentTypeMap.get(fileType);
    }

    public void setStatusCode(String type) {
        switch (type) {
            case "document":
                statusCode = "200 Document Follows";
                break;
            case "error":
                statusCode = "404 Not Found";
                break;
            case "teapot":
                statusCode = "418 I'm a Teapot";
                break;
        }
    }

    public String getHeader() {
        String header;

        header = "HTTP/1.0 " + statusCode + "\r\n" +
                "Content-Type: " + contentType + " \r\n" +
                "Content-Length: " + length + "\r\n" +
                "\r\n";

        return header;
    }
}
