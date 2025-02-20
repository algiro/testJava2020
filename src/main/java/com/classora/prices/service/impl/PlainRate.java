package com.classora.prices.service.impl;

import java.math.BigDecimal;

import com.classora.prices.service.Rate;

public class PlainRate implements Rate {

    @Override
    public BigDecimal apply(BigDecimal sourcePrice) {
        return sourcePrice;
    }
}
