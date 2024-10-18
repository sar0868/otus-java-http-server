package ru.otus.java.safarov.homework24;

import ru.otus.java.safarov.homework24.app.ItemsRepository;
import ru.otus.java.safarov.homework24.processors.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import static ru.otus.java.safarov.homework24.Main.logger;

public class Dispatcher {
    private Map<String, RequestProcessor> processors;
    private RequestProcessor defaultNotFoundRorcessor;
    private RequestProcessor defaultInternalServerErrorProcessor;
    private RequestProcessor defaultBadRequestProcessor;
    private ItemsRepository itemsRepository;

    public Dispatcher() {
        this.itemsRepository = new ItemsRepository();
        this.processors = new HashMap<>();
        this.processors.put("GET /", new HelloWorldProcessor());
        this.processors.put("GET /calculator", new CalculatorProcessor());
        this.processors.put("GET /items", new GetAllItemsProcessor(itemsRepository));
        this.processors.put("POST /items", new CreateNewItemProcessor(itemsRepository));
        this.defaultNotFoundRorcessor = new DefaultNotFoundProcessor();
        this.defaultInternalServerErrorProcessor = new DefaultInternalServerErrorProcessor();
        this.defaultBadRequestProcessor = new DefaultBadRequestProcessor();
    }
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        try{
            if(!processors.containsKey(request.getRoutingKey())){
                defaultNotFoundRorcessor.execute(request, output);
                return;
            }
            processors.get(request.getRoutingKey()).execute(request, output);
        } catch (BadRequestException e) {
            request.setException(e);
            defaultBadRequestProcessor.execute(request, output);

        } catch (Exception e){
            logger.error(e.getMessage());
            defaultInternalServerErrorProcessor.execute(request, output);
        }
    }

}
