package com.interview.shoppingbasket;

import com.interview.shoppingbasket.promotions.HalfOffPromotion;
import com.interview.shoppingbasket.promotions.TenPercentOffPromotion;
import com.interview.shoppingbasket.promotions.TwoForOnePromotion;

import java.util.List;
import java.util.Objects;

public class RetailPriceCheckoutStep implements CheckoutStep {
    private PricingService pricingService;
    private double retailTotal;

    public RetailPriceCheckoutStep(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    @Override
    public void execute(CheckoutContext checkoutContext) {
        List<Promotion> promotions = checkoutContext.getPromotions();
        Basket basket = checkoutContext.getBasket();
        basket.getItems().forEach(basketItem -> {
            double partialTotal = 0;
            basketItem.setProductRetailPrice(pricingService.getPrice(basketItem.getProductCode()));
            Promotion promotion = getPromotionByCode(promotions, basketItem.getProductCode());
            if (!Objects.isNull(promotion)) {
                partialTotal = applyPromotion(promotion, basketItem, basketItem.getProductRetailPrice());
            } else {
                partialTotal = basketItem.getQuantity() * basketItem.getProductRetailPrice();
            }
            retailTotal += partialTotal;
        });

        checkoutContext.setRetailPriceTotal(retailTotal);
    }

    public double applyPromotion(Promotion promotion, BasketItem item, double price) {
        /*
         * Implement applyPromotion method
         */
        switch (promotion.getType()) {
            case TWO_FOR_ONE:
                price = new TwoForOnePromotion().apply(item, price);
                break;
            case FIFTY_PERCENT_OFF:
                price = new HalfOffPromotion().apply(item, price);
                break;
            case TEN_PERCENT_OFF:
                price = new TenPercentOffPromotion().apply(item, price);
        }

        return price;
    }

    private Promotion getPromotionByCode(List<Promotion> promotions, String code) {
        for (Promotion promotion : promotions) {
            if (promotion.getProductCode().equals(code)) {
                return promotion;
            }
        }
        return null;
    }

}
