package com.classora.prices.service.impl;

import java.math.BigDecimal;

import com.classora.prices.service.Rate;

public class DiscountRate implements Rate {

    private static final BigDecimal DISCOUNT_RATE = new BigDecimal("0.9");

    @Override
    public BigDecimal apply(BigDecimal sourcePrice) {
        return sourcePrice.multiply(DISCOUNT_RATE);
    }
}
