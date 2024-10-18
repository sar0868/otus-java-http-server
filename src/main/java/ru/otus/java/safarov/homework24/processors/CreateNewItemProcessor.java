package ru.otus.java.safarov.homework24.processors;

import com.google.gson.Gson;
import ru.otus.java.safarov.homework24.HttpRequest;
import ru.otus.java.safarov.homework24.app.Item;
import ru.otus.java.safarov.homework24.app.ItemsRepository;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class CreateNewItemProcessor implements RequestProcessor{
    private ItemsRepository itemsRepository;

    public CreateNewItemProcessor(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    @Override
    public void execute(HttpRequest request, OutputStream output) throws IOException {
        Gson gson = new Gson();
        Item item = itemsRepository.save(gson.fromJson(request.getBody(), Item.class));

        String response = "" +
                "HTTP/1.1 201 Created\r\n" +
                "Content-Type: application/json\r\n" +
                "\r\n" +
                gson.toJson(item);
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
