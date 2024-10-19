package ru.otus.java.safarov.homework24.processors;

import ru.otus.java.safarov.homework24.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;

public interface RequestProcessor {
    void execute(HttpRequest request, OutputStream output) throws IOException;
}
