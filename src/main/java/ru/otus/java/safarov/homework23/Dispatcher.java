package ru.otus.java.safarov.homework23;

import ru.otus.java.safarov.homework23.processors.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class Dispatcher {
    private Map<String, RequestProcessor> processors;
    private RequestProcessor defaultNotFoundRorcessor;
    private RequestProcessor defaultInternalServerErrorProcessor;
    private RequestProcessor defaultBadRequestProcessor;

    public Dispatcher() {
        this.processors = new HashMap<>();
        this.processors.put("/", new HelloWorldProcessor());
        this.processors.put("/calculator", new CalculatorProcessor());
        this.defaultNotFoundRorcessor = new DefaultNotFoundProcessor();
        this.defaultInternalServerErrorProcessor = new DefaultInternalServerErrorProcessor();
        this.defaultBadRequestProcessor = new DefaultBadRequestProcessor();
    }
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        try{
            if(!processors.containsKey(request.getUri())){
                defaultNotFoundRorcessor.execute(request, output);
                return;
            }
            processors.get(request.getUri()).execute(request, output);
        } catch (BadRequestException e) {
            request.setException(e);
            defaultBadRequestProcessor.execute(request, output);

        } catch (Exception e){
            e.printStackTrace();
            defaultInternalServerErrorProcessor.execute(request, output);
        }
    }

}
