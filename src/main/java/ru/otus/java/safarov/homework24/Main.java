package ru.otus.java.safarov.homework24;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    public static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        new HttpServer(8189).start();
    }
}