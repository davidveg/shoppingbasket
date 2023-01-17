package com.interview.shoppingbasket;

public class Promotion {
    // Not yet implemented
    private String productCode;
    private PromotionType type;

    public Promotion(String productCode, PromotionType type) {
        this.productCode = productCode;
        this.type = type;
    }

    public String getProductCode() {
        return productCode;
    }

    public PromotionType getType() {
        return type;
    }
}
