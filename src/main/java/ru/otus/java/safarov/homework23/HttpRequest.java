package ru.otus.java.safarov.homework23;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private String rawRequest;
    private String uri;
    private HttpMethod method;
    private Map<String, String> parameters;
    private Exception exception;

    public HttpRequest(String rawRequest) {
        this.rawRequest = rawRequest;
        this.parse();
    }

    private void parse() {
        int startIndex = rawRequest.indexOf(' ');
        int endIndex = rawRequest.indexOf(' ', startIndex + 1);
        uri = rawRequest.substring(startIndex + 1, endIndex);
        method = HttpMethod.valueOf(rawRequest.substring(0, startIndex));
        parameters = new HashMap<>();
        if(uri.contains("?")){
            String[] elements = uri.split("[?]");
            uri = elements[0];
            String[] keysValues = elements[1].split("[&]");
            for(String el: keysValues){
                String[] keyValue = el.split("=");
                parameters.put(keyValue[0], keyValue[1]);
            }
        }
    }

    public void info(boolean debug) {
        if (debug){
            System.out.println(rawRequest);
        }
        System.out.println("Method: " + method);
        System.out.println("URI: " + uri);
        System.out.println("Parameters: " + parameters);
    }

    public String getUri() {
        return uri;
    }

    public String getParameter(String key) {
        return parameters.get(key);
    }

    public boolean containsParameter(String key){
        return parameters.containsKey(key);
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
