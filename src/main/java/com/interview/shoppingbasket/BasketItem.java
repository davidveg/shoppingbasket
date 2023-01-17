package com.interview.shoppingbasket;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BasketItem {
    private String productCode;
    private String productName;
    private int quantity;
    private double productRetailPrice;

}
