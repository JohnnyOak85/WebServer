package org.academiadecodigo.bootcamp;

import java.io.File;

public class FileDispatcher {
    private String fileType;
    private String contentType;

    public File getFile(String request) {
        String filePath = request.split(" ")[1];
        File file = new File("www" + filePath);

        if (!file.exists()) {
            file = new File("www/404.html");
            fileType = "error";
            contentType = "html";
            return file;
        }

        fileType = "document";

        if (filePath.equals("/") || filePath.equals("")) {
            file = new File ("www/index.html");
            contentType = "html";
            return file;
        }

        contentType = filePath.split("\\.")[1];

        System.out.println(filePath);
        System.out.println(fileType);
        System.out.println(contentType);
        return file;
    }

    public String getFileType() {
        return fileType;
    }

    public String getContentType() {
        return contentType;
    }
}
