package com.interview.shoppingbasket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BasketTest {
    @Test
    void emptyBasket() {
        Basket basket = new Basket();
        List<BasketItem> basketSize = basket.getItems();

        assertEquals(basketSize.size(), 0);
    }

    @Test
    void createBasketFullConstructor() {
        Basket basket = new Basket();
        basket.add("productCode", "myProduct", 10);
        List<BasketItem> basketSize = basket.getItems();

        assertEquals(basketSize.size(), 1);
        assertEquals(basketSize.get(0).getProductCode(), "productCode");
        assertEquals(basketSize.get(0).getProductName(), "myProduct");
        assertEquals(basketSize.get(0).getQuantity(), 10);
    }

    @Test
    void createBasketWithMultipleProducts() {
        Basket basket = new Basket();
        basket.add("productCode", "myProduct", 10);
        basket.add("productCode2", "myProduct2", 10);
        basket.add("productCode3", "myProduct3", 10);

        List<BasketItem> basketSize = basket.getItems();

        assertEquals(basketSize.size(),3);
        assertEquals(basketSize.get(0).getProductCode(), "productCode");
        assertEquals(basketSize.get(0).getProductName(), "myProduct");
        assertEquals(basketSize.get(0).getQuantity(), 10);
        assertEquals(basketSize.get(1).getProductCode(), "productCode2");
        assertEquals(basketSize.get(1).getProductName(), "myProduct2");
        assertEquals(basketSize.get(1).getQuantity(), 10);
        assertEquals(basketSize.get(2).getProductCode(), "productCode3");
        assertEquals(basketSize.get(2).getProductName(), "myProduct3");
        assertEquals(basketSize.get(2).getQuantity(), 10);
    }

    @Test
    void consolidateBasketTest() {
        // Exercise - implement the unit test for consolidate items

        Basket basket = new Basket();
        BasketItem item1 = BasketItem.builder()
                .productCode("code1")
                .productName("item 1")
                .quantity(2)
                .build();

        BasketItem item2 = BasketItem.builder()
                .productCode("code2")
                .productName("item 2")
                .quantity(3)
                .build();

        // Must consolidate with Item1
        BasketItem item3 = BasketItem.builder()
                .productCode("code1")
                .productName("item 1")
                .quantity(5)
                .build();

        // Must consolidate with Item1
        BasketItem item4 = BasketItem.builder()
                .productCode("code3")
                .productName("item 3")
                .quantity(20)
                .build();

        // Add items to basket
        basket.add(item1.getProductCode(), item1.getProductName(), item1.getQuantity());
        basket.add(item2.getProductCode(), item2.getProductName(), item2.getQuantity());
        basket.add(item3.getProductCode(), item3.getProductName(), item3.getQuantity());
        basket.add(item4.getProductCode(), item4.getProductName(), item4.getQuantity());

        // EXECUTE
        basket.consolidateItems();

        assertEquals(3, basket.getItems().size()); // 3 items (1 consolidated)
        assertEquals(7, basket.getItems().stream()
                .filter(item -> item.getProductCode()
                        .equalsIgnoreCase("code1")).map(BasketItem::getQuantity).findFirst().get()); // total items consolidated
    }
}
