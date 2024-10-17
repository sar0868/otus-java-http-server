package ru.otus.java.safarov.homework23.processors;

import ru.otus.java.safarov.homework23.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;

public interface RequestProcessor {
    void execute(HttpRequest request, OutputStream output) throws IOException;
}
