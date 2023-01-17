package com.interview.shoppingbasket.promotions;

import com.interview.shoppingbasket.BasketItem;

public class HalfOffPromotion {
    public double apply(BasketItem item, double retailPrice) {
        return item.getQuantity() * retailPrice * 0.5;
    }
}
