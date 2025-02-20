package com.classora.prices.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.classora.prices.entity.Brand;
import com.classora.prices.entity.Price;
import com.classora.prices.entity.PriceFactory;

@SpringBootTest()
class PriceLookupPriorityStrategyTests {
    private static final Brand DEFAULT_BRAND = new Brand(1L, "ZARA");

    @Test
    void getPrice_FromEmptyList_ReturnsEmpty() {
        // Arrange
        PriceLookupPriorityStrategy strategy = new PriceLookupPriorityStrategy();
        // Act
        Optional<Price> result = strategy.getPrice(Collections.emptyList());

        // Assert
        assertEquals(Optional.empty(), result);
    }

    @Test
    void getPrice_FromSinglePriceList_ReturnTheUniqueElement() {
        Price[] inputPrices = {
                PriceFactory.create(105L, DEFAULT_BRAND, LocalDateTime.parse("2020-06-14T00:00:00"),
                        LocalDateTime.parse("2020-12-31T23:59:59"), 1L, 35455L, 0, new BigDecimal("35.50"), "EUR"),
        };
        // Arrange
        PriceLookupPriorityStrategy strategy = new PriceLookupPriorityStrategy();
        // Act
        Optional<Price> result = strategy.getPrice(List.of(inputPrices));
        // Assert
        assertTrue(result.isPresent());
        assertEquals(result.get().getPrice(), new BigDecimal("35.50"));
    }

    @Test
    void getPrice_FromPricesDifferentPriorities_ReturnsGreaterPriorityPrice() {
        Price[] inputPrices = {
                PriceFactory.create(106L, DEFAULT_BRAND, LocalDateTime.parse("2020-06-14T00:00:00"),
                        LocalDateTime.parse("2020-12-31T23:59:59"), 1L, 35455L, 0, new BigDecimal("35.50"), "EUR"),
                PriceFactory.create(107L, DEFAULT_BRAND, LocalDateTime.parse("2020-06-14T15:00:00"),
                        LocalDateTime.parse("2020-06-14T18:30:00"), 1L, 35455L, 1, new BigDecimal("25.45"), "EUR")
        };
        // Arrange
        PriceLookupPriorityStrategy strategy = new PriceLookupPriorityStrategy();
        // Act
        Optional<Price> result = strategy.getPrice(List.of(inputPrices));
        // Assert
        assertTrue(result.isPresent());
        assertEquals(result.get().getPrice(), new BigDecimal("25.45"));
    }

    @Test
    void getPrice_FromPricesSamePriorities_ReturnsFirstHighPriorityPrice() {
        Price[] inputPrices = {
                PriceFactory.create(108L, DEFAULT_BRAND, LocalDateTime.parse("2020-06-14T00:00:00"),
                        LocalDateTime.parse("2020-12-31T23:59:59"), 1L, 35455L, 0, new BigDecimal("35.50"), "EUR"),
                PriceFactory.create(109L, DEFAULT_BRAND, LocalDateTime.parse("2020-06-14T15:00:00"),
                        LocalDateTime.parse("2020-06-14T18:30:00"), 1L, 35455L, 1, new BigDecimal("25.45"), "EUR"),
                PriceFactory.create(110L, DEFAULT_BRAND, LocalDateTime.parse("2020-06-13T15:00:00"),
                        LocalDateTime.parse("2020-06-16T18:30:00"), 1L, 35455L, 1, new BigDecimal("25.48"), "EUR")
        };
        // Arrange
        PriceLookupPriorityStrategy strategy = new PriceLookupPriorityStrategy();
        // Act
        Optional<Price> result = strategy.getPrice(List.of(inputPrices));
        // Assert
        assertTrue(result.isPresent());
        assertEquals(result.get().getPrice(), new BigDecimal("25.45"));
    }
}
