package ru.otus.java.safarov.homework23.processors;

import ru.otus.java.safarov.homework23.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class HelloWorldProcessor implements RequestProcessor{
    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        String responce = "" +
                          "HTTP/1.1 200 OK\r\n" +
                          "Content-Type: text/html\r\n" +
                          "\r\n" +
                          "<!DOCTYPE html><html lang=\"en\">" +
                          "<head><title>HTTP_server</title></head>" +
                          "<body><h1>Hello World.</h1></body> </html>";
        output.write(responce.getBytes(StandardCharsets.UTF_8));
    }
}
