package pack;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(() -> {
                try {
                    handle(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).run();
        }
    }

    private static void handle(Socket socket) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
        final String content = "<!Doctype html><html><head><title>Dummy title</title></head><body>Time is " + new Date() + "</body></html>";
        String output =
                "HTTP/1.1 200 OK\r\n" +
                        "Content-Length: " + content.length() + "\r\n" +
                        "Content-Type: text/html\r\n" +
                        "Connection: Closed\r\n" +
                        "\r\n" +
                        content;
        outputStream.write(output.getBytes(Charset.forName("UTF-8")));
    }
}
