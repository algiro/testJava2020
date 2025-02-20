package com.classora.prices.service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.classora.prices.model.PriceDto;

public interface PriceService {
    /**
     * Get price by brandId, productId and queryDate
     *
     * @param brandId   brandId to find
     * @param productId productId to find
     * @param queryDate query date
     * @return Price
     */
    Optional<PriceDto> getPrice(Long brandId, Long productId, LocalDateTime queryDate);
}
