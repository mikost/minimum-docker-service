package pack;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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
        String input;
        socket.setSoTimeout(1000);
        final InputStream inputStream = socket.getInputStream();
        try {
            byte[] b = new byte[1024];
            inputStream.read(b);
            input = new String(b, StandardCharsets.UTF_8);
        } catch (IOException e) {
            input = null;
        }
        String output;
        try {
            OutputStream outputStream = socket.getOutputStream();
            final String content = "<!Doctype html><html><head><title>Dummy title</title></head><body>Time is " + new Date() + "</body></html>";
            output =
                    "HTTP/1.1 200 OK\r\n" +
                            "Content-Length: " + content.length() + "\r\n" +
                            "Content-Type: text/html\r\n" +
                            "Connection: Closed\r\n" +
                            "\r\n" +
                            content;
            outputStream.write(output.getBytes(Charset.forName("UTF-8")));
        } finally {
            socket.close();
        }
        logToFile(input, output);
    }

    private static void logToFile(String input, String output) {
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("/tmp/qwerty")))) {
            pw.println("input: " + input);
            pw.println("output:" + output);
        } catch (IOException e) {

        }
    }
}
