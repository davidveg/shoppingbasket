package com.interview.shoppingbasket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CheckoutPipelineTest {

    @InjectMocks
    CheckoutPipeline checkoutPipeline;

    @Mock
    Basket basket;

    @Mock
    PromotionsService promotionsService;

    @Mock
    PricingService pricingService;

    @BeforeEach
    void setup() {
        checkoutPipeline = new CheckoutPipeline();
    }

    @Test
    void returnZeroPaymentForEmptyPipeline() {
        PaymentSummary paymentSummary = checkoutPipeline.checkout(basket);

        assertEquals(paymentSummary.getRetailTotal(), 0.0);
    }

    @Test
    void executeAllPassedCheckoutSteps() {
        // Exercise - implement testing passing through all checkout steps

        // GIVEN
        Promotion promotion1 = new Promotion("code1", PromotionType.TWO_FOR_ONE); // Add Two for One Promo for code1 product
        Promotion promotion2 = new Promotion("code2", PromotionType.FIFTY_PERCENT_OFF); // Add FiftyPercentOff for code2 product
        Promotion promotion3 = new Promotion("code3", PromotionType.TEN_PERCENT_OFF); // Ten PercentOff for code3 product

        List<Promotion> promotions = Arrays.asList(promotion1, promotion2, promotion3);
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

        double price1 = 10;
        double price2 = 30;
        double price3 = 10;

        // Add items to basket
        basket.add(item1.getProductCode(), item1.getProductName(), item1.getQuantity());
        basket.add(item2.getProductCode(), item2.getProductName(), item2.getQuantity());
        basket.add(item3.getProductCode(), item3.getProductName(), item3.getQuantity());
        basket.add(item4.getProductCode(), item4.getProductName(), item4.getQuantity());

        // WHEN
        when(promotionsService.getPromotions(basket)).thenReturn(promotions);
        when(pricingService.getPrice("code1")).thenReturn(price1);
        when(pricingService.getPrice("code2")).thenReturn(price2);
        when(pricingService.getPrice("code3")).thenReturn(price3);

        BasketConsolidationCheckoutStep basketConsolidationCheckoutStep = new BasketConsolidationCheckoutStep();
        RetailPriceCheckoutStep retailPriceCheckoutStep = new RetailPriceCheckoutStep(pricingService);
        PromotionCheckoutStep promotionCheckoutStep = new PromotionCheckoutStep(promotionsService);

        double expectedTotal = 265;

        // EXECUTE
        checkoutPipeline.addStep(basketConsolidationCheckoutStep); // Add consolidation Step
        checkoutPipeline.addStep(promotionCheckoutStep); // Add Get Promotions Step
        checkoutPipeline.addStep(retailPriceCheckoutStep); // Add Price Calculation Step
        PaymentSummary paymentSummary = checkoutPipeline.checkout(basket);

        // THEN
        verify(promotionsService, times(1)).getPromotions(basket);
        verify(pricingService, times(1)).getPrice("code1"); // Must call only one time (consolidated)
        verify(pricingService, times(1)).getPrice("code2");
        verify(pricingService, times(1)).getPrice("code3");
        assertEquals(expectedTotal, paymentSummary.getRetailTotal(), 0.0001);
    }

}
