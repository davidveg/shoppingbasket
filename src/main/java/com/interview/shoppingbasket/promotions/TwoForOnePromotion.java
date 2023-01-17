package com.interview.shoppingbasket.promotions;

import com.interview.shoppingbasket.BasketItem;

public class TwoForOnePromotion {
    public double apply(BasketItem item, double retailPrice) {
        int quantity = item.getQuantity();
        if (quantity > 1) {
            int freeItems = quantity / 2;
            retailPrice = (quantity - freeItems)  * retailPrice;
        }
        return retailPrice;
    }
}
