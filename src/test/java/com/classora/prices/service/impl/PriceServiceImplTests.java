package com.classora.prices.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.classora.prices.model.PriceDto;
import com.classora.prices.service.PriceService;

@SpringBootTest()
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PriceServiceImplTests {
    @Autowired
    private PriceService priceService;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm");

    @Test
    void getPrice_UnknownBrandId_ReturnEmptyPrice() {
        String querydate = "2020-06-14-10:00";
        Long productId = 35455L;
        Long brandId = 2L;
        Optional<PriceDto> priceItem = priceService.getPrice(brandId, productId, parseDate(querydate));
        assertFalse(priceItem.isPresent());
    }

    @Test
    void getPrice_UnknownProductId_ReturnEmptyPrice() {
        String querydate = "2020-06-14-10:00";
        Long productId = 1111L;
        Long brandId = 1L;
        Optional<PriceDto> priceItem = priceService.getPrice(brandId, productId, parseDate(querydate));
        assertFalse(priceItem.isPresent());
    }

    @Test
    void getPrice_fetchinDateOutOfRange_ReturnEmptyPrice() {
        String querydate = "2025-01-01-10:00";
        Long productId = 35455L;
        Long brandId = 1L;
        Optional<PriceDto> priceItem = priceService.getPrice(brandId, productId, parseDate(querydate));
        assertFalse(priceItem.isPresent());
    }

    @Test
    void getPrice_ValidQuery_ReturnRightPrice() {
        String querydate = "2020-06-14-10:00";
        Long productId = 35455L;
        Long brandId = 1L;
        Optional<PriceDto> priceItem = priceService.getPrice(brandId, productId, parseDate(querydate));
        assertTrue(priceItem.isPresent());
        assertEquals(priceItem.get().getPrice(), new BigDecimal("35.50"));
    }

    private LocalDateTime parseDate(String date) {
        return LocalDateTime.parse(date, formatter);
    }
}
