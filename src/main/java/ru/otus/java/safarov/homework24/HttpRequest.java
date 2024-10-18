package ru.otus.java.safarov.homework24;

import java.util.HashMap;
import java.util.Map;

import static ru.otus.java.safarov.homework24.Main.logger;

public class HttpRequest {
    private String rawRequest;
    private String uri;
    private HttpMethod method;
    private Map<String, String> parameters;
    private Exception exception;
    private String body;
    private Map<String, String> headlines;

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
        headlines = new HashMap<>();
        if(uri.contains("?")){
            String[] elements = uri.split("[?]");
            uri = elements[0];
            String[] keysValues = elements[1].split("[&]");
            for(String el: keysValues){
                String[] keyValue = el.split("=");
                parameters.put(keyValue[0], keyValue[1]);
            }
        }
        if (method == HttpMethod.POST) {
            this.body = rawRequest.substring(rawRequest.indexOf("\r\n\r\n") + 4);
        }
        String[] rawHeadlines = rawRequest.split("\r\n");
        for (int i = 1; i < rawHeadlines.length - 2 ; i++) {
            String[] el = rawHeadlines[i].split(":");
            headlines.put(el[0], el[1].trim());
        }
    }

    public void info(boolean debug) {
        if (debug){
            logger.info(rawRequest);
        }
        logger.info("Method: " + method);
        logger.info("URI: " + uri);
        logger.info("Parameters: " + parameters);
        logger.info("Body: " + body);
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

    public String getBody() {
        return body;
    }

    public String getRoutingKey(){
        return method + " " + uri;
    }
}
