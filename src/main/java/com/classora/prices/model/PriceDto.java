package com.classora.prices.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.classora.prices.entity.Brand;
import com.classora.prices.service.RateStrategy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PriceDto {
    private Integer priceList;
    private Brand brand;
    private Long productId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal price;
    private String currency;

    public BigDecimal getFinalPrice() {
        return RateStrategy.getRateStrategy(priceList).apply(price);
    }
}
