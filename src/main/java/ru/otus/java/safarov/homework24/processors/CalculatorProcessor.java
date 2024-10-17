package ru.otus.java.safarov.homework24.processors;

import ru.otus.java.safarov.homework24.BadRequestException;
import ru.otus.java.safarov.homework24.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class CalculatorProcessor implements RequestProcessor {
    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        if (!request.containsParameter("a")) {
            throw new BadRequestException("Parameter 'a' is missing");
        }
        if (!request.containsParameter("b")) {
            throw new BadRequestException("Parameter 'b' is missing");
        }
        int a, b;
        try {
            a = Integer.parseInt(request.getParameter("a"));
        } catch (NumberFormatException e) {
            throw new BadRequestException("Parameter 'a' has incorrect type");
        }
        try {
            b = Integer.parseInt(request.getParameter("b"));
        } catch (NumberFormatException e) {
            throw new BadRequestException("Parameter 'b' has incorrect type");
        }
        String mathOperation = a + " + " + b + " = " + (a + b);
        String responce = "" +
                          "HTTP/1.1 200 OK\r\n" +
                          "Content-Type: text/html\r\n" +
                          "\r\n" +
                          "<!DOCTYPE html><html lang=\"en\">" +
                          "<head><title>HTTP_server</title></head>" +
                          "<body><h1>Calculation</h1><div><p>" +
                          mathOperation + "</p></div></body> </html>";
        output.write(responce.getBytes(StandardCharsets.UTF_8));
    }
}
