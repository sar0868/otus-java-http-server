package ru.otus.java.safarov.homework23;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private final int port;
    private Dispatcher dispatcher;

    public HttpServer(int port) {
        this.port = port;
        dispatcher = new Dispatcher();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            ExecutorService executorService = Executors.newFixedThreadPool(4);
            while (true) {
                Socket socket = serverSocket.accept();
                byte[] buffer = new byte[8192];
                int n = socket.getInputStream().read(buffer);
                String rawRequest = new String(buffer, 0, n);
                executorService.execute(() -> {
                    HttpRequest request = new HttpRequest(rawRequest);
                    request.info(false);
                    try {
                        dispatcher.execute(request, socket.getOutputStream());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
