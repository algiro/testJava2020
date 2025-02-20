package com.classora.prices.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerTest {
    public static final String DEFAULT_PRODUCT_ID = "35455";
    public static final String DEFAULT_BRAND_ID = "1";
    public static final String URL_ENDPOINT = "/price/get-price/brandid/{brandid}/productid/{productid}/querydate/{querydate}";
    @Autowired
    private MockMvc mockMvc;

    /**
     * from original documentation:
     * Test 1: petición a las 10:00 del día 14 del producto 35455 para la brand 1
     * (ZARA)
     */
    @Test
    void getPrice_test1_day14At1000() throws Exception {
        String queryDate = "20200614-10.00";
        this.mockMvc
                .perform(get(URL_ENDPOINT, DEFAULT_BRAND_ID, DEFAULT_PRODUCT_ID, queryDate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(35.50))
                .andExpect(jsonPath("$.finalPrice").value(35.50));
    }

    /**
     * from original documentation:
     * Test 2: petición a las 16:00 del día 14 del producto 35455 para la brand 1
     * (ZARA)
     */
    @Test
    void getPrice_test2_day14At1600() throws Exception {
        String queryDate = "20200614-16.00";
        this.mockMvc
                .perform(get(URL_ENDPOINT, DEFAULT_BRAND_ID, DEFAULT_PRODUCT_ID, queryDate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(25.45))
                .andExpect(jsonPath("$.finalPrice").value(25.45));
    }

    /**
     * from original documentation:
     * Test 3: petición a las 21:00 del día 14 del producto 35455 para la brand 1
     * (ZARA)
     */
    @Test
    void getPrice_test3_day14At2100() throws Exception {
        String queryDate = "20200614-21.00";
        this.mockMvc
                .perform(get(URL_ENDPOINT, DEFAULT_BRAND_ID, DEFAULT_PRODUCT_ID, queryDate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(35.50))
                .andExpect(jsonPath("$.finalPrice").value(35.50));

    }

    /**
     * from original documentation:
     * Test 4: petición a las 10:00 del día 15 del producto 35455 para la brand 1
     * (ZARA)
     */
    @Test
    void getPrice_test4_day15At1000() throws Exception {
        String queryDate = "20200615-10.00";
        this.mockMvc
                .perform(get(URL_ENDPOINT, DEFAULT_BRAND_ID, DEFAULT_PRODUCT_ID, queryDate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(30.50))
                .andExpect(jsonPath("$.finalPrice").value(30.50));
    }

    /**
     * from original documentation:
     * Test 5: petición a las 21:00 del día 16 del producto 35455 para la brand 1
     * (ZARA)
     */
    @Test
    void getPrice_test5_day16At2100() throws Exception {
        String queryDate = "20200616-21.00";
        this.mockMvc
                .perform(get(URL_ENDPOINT, DEFAULT_BRAND_ID, DEFAULT_PRODUCT_ID, queryDate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(38.95))
                .andExpect(jsonPath("$.finalPrice").value(38.95));
    }

    @Test
    void getPrice_NotMatchingQueryInputs_returns404() throws Exception {
        String queryDate = "20200616-21.00";
        Long unkownProductId = 555L;
        this.mockMvc
                .perform(get(URL_ENDPOINT, DEFAULT_BRAND_ID, unkownProductId, queryDate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getPrice_InvalidQueryDate_returns400() throws Exception {
        String invalidQueryDate = "20200616$21$00";
        this.mockMvc
                .perform(get(URL_ENDPOINT, DEFAULT_BRAND_ID, DEFAULT_PRODUCT_ID, invalidQueryDate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getPrice_InvalidProductId_returns400() throws Exception {
        String queryDate = "20200616-21.00";
        this.mockMvc
                .perform(get(URL_ENDPOINT, DEFAULT_BRAND_ID, -1L, queryDate)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
