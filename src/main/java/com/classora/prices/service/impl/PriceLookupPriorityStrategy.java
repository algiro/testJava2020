package com.classora.prices.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import com.classora.prices.entity.Price;

public class PriceLookupPriorityStrategy implements PriceLookupStrategy {

    @Override
    public Optional<Price> getPrice(List<Price> prices) {
        if (prices == null || prices.isEmpty()) {
            return Optional.empty(); // Handle empty or null list
        }
        return prices.stream().max(Comparator.comparingInt(Price::getPriority));
    }

}
