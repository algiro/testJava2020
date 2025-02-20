package com.classora.prices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.classora.prices.entity.Price;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

    /**
     * Find price by params.
     *
     * @param queryDate The query date.
     * @param productId The product id.
     * @param brandId   The brand id.
     * @return The list of matching prices.
     */
    @Query("SELECT p FROM prices p WHERE p.productId=:productId AND p.brand.id=:brandId AND (p.startDate<=:queryDate AND p.endDate>=:queryDate) ")
    List<Price> findByProductIdAndBrandIdAndDate(@Param("brandId") Long brandId, @Param("productId") Long productId,
            @Param("queryDate") LocalDateTime queryDate);
}
