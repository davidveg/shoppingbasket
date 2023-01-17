package com.interview.shoppingbasket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Basket {
    private List<BasketItem> items = new ArrayList<>();

    public void add(String productCode, String productName, int quantity) {
        BasketItem basketItem = BasketItem.builder()
                .productCode(productCode)
                .productName(productName)
                .quantity(quantity)
                .build();

        items.add(basketItem);
    }

    public List<BasketItem> getItems() {
        return items;
    }

    public void consolidateItems() {
        // Exercise - implement this function
        Map<String, BasketItem> itemMap = new HashMap<>();
        items.forEach(item -> {
            if (itemMap.containsKey(item.getProductCode())) {
                BasketItem existingItem = itemMap.get(item.getProductCode());
                existingItem.setQuantity(existingItem.getQuantity() + item.getQuantity());
            } else {
                itemMap.put(item.getProductCode(), item);
            }
        });
        items.clear();
        items.addAll(itemMap.values());
    }
}
