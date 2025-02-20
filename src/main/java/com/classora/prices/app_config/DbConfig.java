package com.classora.prices.app_config;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.classora.prices.entity.Brand;
import com.classora.prices.entity.Price;
import com.classora.prices.entity.PriceFactory;
import com.classora.prices.repository.BrandRepository;
import com.classora.prices.repository.PriceRepository;

@Component
public class DbConfig {
    private static Logger logger = LoggerFactory.getLogger(DbConfig.class);

    private final PriceRepository priceRepository;
    private final BrandRepository brandRepository;

    private static final Long DEFAULT_PRODUCT_ID = 35455L;
    private static final String DEFAULT_CURRENCY = "EUR";
    private static final Brand DEFAULT_BRAND = new Brand(1L, "ZARA");

    private static final Price[] SAMPLE_PRICES = {
            PriceFactory.create(101L, DEFAULT_BRAND, LocalDateTime.parse("2020-06-14T00:00:00"),
                    LocalDateTime.parse("2020-12-31T23:59:59"), 1L, DEFAULT_PRODUCT_ID, 0, new BigDecimal("35.50"),
                    DEFAULT_CURRENCY),
            PriceFactory.create(102L, DEFAULT_BRAND, LocalDateTime.parse("2020-06-14T15:00:00"),
                    LocalDateTime.parse("2020-06-14T18:30:00"), 2L, DEFAULT_PRODUCT_ID, 1, new BigDecimal("25.45"),
                    DEFAULT_CURRENCY),
            PriceFactory.create(103L, DEFAULT_BRAND, LocalDateTime.parse("2020-06-15T00:00:00"),
                    LocalDateTime.parse("2020-06-15T11:00:00"), 3L, DEFAULT_PRODUCT_ID, 1, new BigDecimal("30.50"),
                    DEFAULT_CURRENCY),
            PriceFactory.create(104L, DEFAULT_BRAND, LocalDateTime.parse("2020-06-15T16:00:00"),
                    LocalDateTime.parse("2020-12-31T23:59:59"), 4L, DEFAULT_PRODUCT_ID, 1, new BigDecimal("38.95"),
                    DEFAULT_CURRENCY)
    };

    public DbConfig(PriceRepository priceRepository, BrandRepository brandRepository) {
        this.priceRepository = priceRepository;
        this.brandRepository = brandRepository;
    }

    public void init() {
        logger.info("Data initialization...");
        brandRepository.save(DEFAULT_BRAND);

        for (Price price : SAMPLE_PRICES) {
            priceRepository.save(price);
        }
        logger.debug("Inserted prices:");
        priceRepository.findAll().forEach(p -> logger.debug(p.toString()));
    }
}
