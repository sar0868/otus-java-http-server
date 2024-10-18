package ru.otus.java.safarov.homework24.processors;

import ru.otus.java.safarov.homework24.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class DefaultInternalServerErrorProcessor implements RequestProcessor {
    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        String responce = "" +
                          "HTTP/1.1 500 Internal Server Error\r\n" +
                          "Content-Type: text/html\r\n" +
                          "\r\n" +
                          "<!DOCTYPE html><html lang=\"en\">" +
                          "<head><title>HTTP_server</title></head>" +
                          "<body><h1>Internal Server Error</h1></body> </html>";
        output.write(responce.getBytes(StandardCharsets.UTF_8));
    }
}
