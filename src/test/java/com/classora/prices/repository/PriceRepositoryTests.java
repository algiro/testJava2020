package com.classora.prices.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import com.classora.prices.entity.Price;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.test.database.replace=none",
        "spring.datasource.url=jdbc:tc:postgresql:16-alpine:///db"
})
class ProductRepositoryTest {

    @Autowired
    PriceRepository priceRepository;

    @Test
    @Sql("classpath:/sql/data-test-init.sql")
    void findAll_getStandardDataAndTestData_Success() {
        List<Price> prices = priceRepository.findAll();
        assertEquals(5, prices.size());
    }

    @Test
    void findById_getStandardData_noDataTestCheck() {
        Optional<Price> price101 = priceRepository.findById(101L);
        assertTrue(price101.isPresent());

        Optional<Price> dataTestPrice = priceRepository.findById(201L);
        assertFalse(dataTestPrice.isPresent());
    }

    @Test
    @Sql("classpath:/sql/data-test-init.sql")
    void findById_getStandardDataAndTestData_Success() {
        Optional<Price> price101 = priceRepository.findById(101L);
        assertTrue(price101.isPresent());

        Optional<Price> dataTestPrice = priceRepository.findById(201L);
        assertTrue(dataTestPrice.isPresent());
    }

    @Test
    void findByProductIdAndBrandIdAndDate_getOnlyASingleItem_Success() {
        List<Price> prices = priceRepository.findByProductIdAndBrandIdAndDate(1L, 35455L,
                LocalDateTime.parse("2020-06-14T10:00:00"));

        assertEquals(1, prices.size());
    }

    @Test
    void findByProductIdAndBrandIdAndDate_getTwoItems_Success() {
        List<Price> prices = priceRepository.findByProductIdAndBrandIdAndDate(1L, 35455L,
                LocalDateTime.parse("2020-06-14T16:00:00"));

        assertEquals(2, prices.size());
    }
}
