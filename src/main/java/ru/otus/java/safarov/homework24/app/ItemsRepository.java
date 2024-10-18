package ru.otus.java.safarov.homework24.app;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;


public class ItemsRepository {
    private List<Item> items;

    public ItemsRepository() {
        this.items = new ArrayList<>(Arrays.asList(
                new Item(1L, "Milk", BigDecimal.valueOf(80.0)),
                new Item(2L, "Bread", BigDecimal.valueOf(32)),
                new Item(3L, "Cheese", BigDecimal.valueOf(3200))
        ));
    }

    public List<Item> getItems() {
        return Collections.unmodifiableList(items);
    }

    public Item save(Item item) {
        Long newID = items.stream().mapToLong(Item::getId).max().orElse(0) + 1L;
        item.setId(newID);
        items.add(item);
        return item;
    }
}
