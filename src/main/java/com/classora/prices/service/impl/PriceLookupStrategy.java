package com.classora.prices.service.impl;

import java.util.List;
import java.util.Optional;

import com.classora.prices.entity.Price;

public interface PriceLookupStrategy {
    /**
     * Extract a price from a list of prices
     *
     * @param prices list of prices to search
     * @return Optional<Price> the price found
     */
    Optional<Price> getPrice(List<Price> prices);
}
