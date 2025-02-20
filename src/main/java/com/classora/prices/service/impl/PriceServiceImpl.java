package com.classora.prices.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.classora.prices.model.PriceDto;
import com.classora.prices.repository.PriceRepository;
import com.classora.prices.service.*;

@Service
public class PriceServiceImpl implements PriceService {

    private final PriceRepository priceRepository;
    private final ModelMapper modelMapper;
    private final PriceLookupStrategy priceLookupStrategy = new PriceLookupPriorityStrategy();

    public PriceServiceImpl(ModelMapper modelMapper, PriceRepository priceRepository) {
        this.modelMapper = modelMapper;
        this.priceRepository = priceRepository;
    }

    @Override
    public Optional<PriceDto> getPrice(Long brandId, Long productId, LocalDateTime queryDate) {
        var priceEntities = priceRepository.findByProductIdAndBrandIdAndDate(brandId, productId, queryDate);
        var selectedPrice = priceLookupStrategy.getPrice(priceEntities);
        if (selectedPrice.isPresent()) {
            return Optional.of(modelMapper.map(selectedPrice, PriceDto.class));
        }
        return Optional.empty();

    }

}
