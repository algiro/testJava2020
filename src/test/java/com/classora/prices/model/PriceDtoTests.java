package com.classora.prices.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.classora.prices.service.RateStrategy;

@SpringBootTest
class PriceDtoTests {

    @Test
    void getFinalPrice_WithStandardStrategy_ReturnsSourcePrice() {
        // Arrange
        PriceDto priceDto = new PriceDto();
        priceDto.setPriceList(1);
        priceDto.setPrice(new BigDecimal("100.00"));
        // Assert
        assertEquals(priceDto.getFinalPrice(), new BigDecimal("100.00"));
    }

    @Test
    void getFinalPrice_WithDiscount_ReturnsDiscountedPrice() {
        // Arrange
        PriceDto priceDto = new PriceDto();
        priceDto.setPriceList(RateStrategy.RATE_ID_DEMO);
        priceDto.setPrice(new BigDecimal("100.00"));
        // Assert
        assertEquals(priceDto.getFinalPrice(), new BigDecimal("90.000"));
    }

}
