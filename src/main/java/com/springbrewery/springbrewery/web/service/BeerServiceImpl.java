package com.springbrewery.springbrewery.web.service;

import com.springbrewery.springbrewery.web.model.BeerDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BeerServiceImpl implements BeerService {

    @Override
    public BeerDto getBeerById(UUID beerId) {
        return BeerDto.builder().id(UUID.randomUUID())
                .beerName("Galaxy Kitty")
                .beerStyle("Indian Ale")
                .build();
    }
}
