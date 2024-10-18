package ru.otus.java.safarov.homework24;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static ru.otus.java.safarov.homework24.Main.logger;


public class HttpServer {
    private final int port;
    private Dispatcher dispatcher;
    ExecutorService executor;

    public HttpServer(int port) {
        this.port = port;
        dispatcher = new Dispatcher();
        executor = Executors.newFixedThreadPool(4);
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Сервер запущен на порту " + port);
            while (true) {
                executor.execute(() -> {
                    try (Socket socket = serverSocket.accept()) {
                        byte[] buffer = new byte[8192];
                        int n = 0;
                        n = socket.getInputStream().read(buffer);
                        String rawRequest = new String(buffer, 0, n);
                        HttpRequest request = new HttpRequest(rawRequest);
                        request.info(false);
                        dispatcher.execute(request, socket.getOutputStream());
                    } catch (IOException e) {
                        logger.error(e.getMessage());
                    }
                });
                executor.awaitTermination(100, TimeUnit.MILLISECONDS);
            }
        } catch (IOException e) {
            logger.error(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
