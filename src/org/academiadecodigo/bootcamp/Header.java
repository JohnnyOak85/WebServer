package org.academiadecodigo.bootcamp;

public class Header {
    private String contentType;
    private String statusCode;
    private long length;

    public Header(long length) {
        this.length = length;
    }
        public void setContentType(String fileType) {
            switch (fileType) {
                case "html":
                    contentType = "text/html;";
                    break;
                case "jpeg":
                    contentType = "image/jpeg;";
                    break;
            }
        }

        public void setStatusCode(String type) {
            switch (type) {
                case "document":
                    statusCode = "200 Document Follows";
                    break;
                case "error":
                    statusCode = "404 Not Found";
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
