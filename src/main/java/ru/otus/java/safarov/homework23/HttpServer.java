package ru.otus.java.safarov.homework23;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    private final int port;
    private Dispatcher dispatcher;

    public HttpServer(int port) {
        this.port = port;
        dispatcher = new Dispatcher();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket socket = serverSocket.accept();
                byte[] buffer = new byte[8192];
                int n = socket.getInputStream().read(buffer);
                String rawRequest = new String(buffer, 0, n);
                new Thread(() -> {
                    HttpRequest request = new HttpRequest(rawRequest);
                    request.info(true);
                    try {
                        dispatcher.execute(request, socket.getOutputStream());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
