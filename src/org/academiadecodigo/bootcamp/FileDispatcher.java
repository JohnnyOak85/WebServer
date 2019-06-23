package org.academiadecodigo.bootcamp;

import java.io.File;

public class FileDispatcher {
    private String fileType;
    private String contentType;
    private File file;

    /**
     * @param request
     */
    public FileDispatcher(String request) {
        buildFile(request);
    }

    /**
     * Interprets the request and sets all variables to deploy the correct file.
     * @param request
     */
    private void buildFile(String request) {
        String filePath = request.split(" ")[1];
        file = new File("www" + filePath);

        if (!file.exists()) {
            file = new File("www/404.html");
            fileType = "error";
            contentType = "html";
            return;
        }
        fileType = "document";

        if (filePath.equals("/") || filePath.equals("")) {
            file = new File ("www/profile.html");
            contentType = "html";
            return;
        }
        contentType = filePath.split("\\.")[1];
    }

    /**
     * @return the file.
     */
    public File getFile() {
        return file;
    }

    /**
     * @return the file type.
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * @return the content type.
     */
    public String getContentType() {
        return contentType;
    }
}
