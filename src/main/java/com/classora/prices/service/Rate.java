package com.classora.prices.service;

import java.math.BigDecimal;

public interface Rate {
    /*
     * It applies a rate to sourcePrice
     * (note from the original documentation: "la tarifa de precios aplicable")
     * 
     * @param sourcePrice the price to apply the rate
     * 
     * @return the price after applying the rate
     */

    BigDecimal apply(BigDecimal sourcePrice);
}
