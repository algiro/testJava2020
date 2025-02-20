package com.classora.prices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.classora.prices.entity.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {
}
