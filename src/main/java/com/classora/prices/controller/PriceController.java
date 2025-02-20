package com.classora.prices.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import com.classora.prices.model.PriceDto;
import com.classora.prices.service.PriceService;

@RestController
@Validated
@RequestMapping("price")
public class PriceController {
    private static Logger logger = LoggerFactory.getLogger(PriceController.class);
    private static final String DATE_FORMAT = "yyyyMMdd-HH.mm";
    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @Operation(summary = "Product price lookup", description = "Lookup for a product price", tags = { "price" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PriceDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data supplied"),
            @ApiResponse(responseCode = "404", description = "Price not found") })

    @GetMapping(value = "/get-price/brandid/{brandid}/productid/{productid}/querydate/{querydate}", produces = {
            "application/json" })
    public ResponseEntity<PriceDto> getPrice(
            @PathVariable("querydate") @NotNull(message = "Query date is required") @Pattern(regexp = "^\\d{8}-\\d{2}\\.\\d{2}$", message = "Invalid querydate date format yyyyMMdd-HH.mm") String queryDateStr,

            @PathVariable("productid") @NotNull(message = "Product ID is required") @Positive(message = "Product ID must be positive") Long productid,

            @PathVariable("brandid") @NotNull(message = "Brand ID is required") @Positive(message = "Brand ID must be positive") Long brandid) {

        try {
            logger.info("getPrice: brandid={}, productid={}, querydate={}", brandid, productid, queryDateStr);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
            LocalDateTime queryDate = LocalDateTime.parse(queryDateStr, formatter);

            var price = priceService.getPrice(brandid, productid, queryDate);
            if (price.isPresent()) {
                return new ResponseEntity<>(price.get(), HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("getPrice", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}