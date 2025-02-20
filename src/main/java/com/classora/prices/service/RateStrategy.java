package com.classora.prices.service;

import com.classora.prices.service.impl.DiscountRate;
import com.classora.prices.service.impl.PlainRate;

public class RateStrategy {
    private static final Rate DEFAULT_RATE_STRATEGY = new PlainRate();
    private static final Rate DISCOUNT_RATE_STRATEGY = new DiscountRate();
    public static final int RATE_ID_DEMO = 555;

    private RateStrategy() {
    }

    /*
     * It returns the rate strategy for a given rateId
     * from original documentation: PRICE_LIST: Identificador de la tarifa
     */
    public static Rate getRateStrategy(int rateId) {
        /*
         * en relacion a PRICE_LIST: Identificador de la tarifa de precios aplicable,
         * es posible obtener un Rate especifico y obtener el precio final de venta
         * aplicando la tarifa correspondiente.
         */

        switch (rateId) {
            case RATE_ID_DEMO:
                return DISCOUNT_RATE_STRATEGY;
            default:
                return DEFAULT_RATE_STRATEGY;
        }

    }
}
