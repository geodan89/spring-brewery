package com.springbrewery.springbrewery.web.service;

import com.springbrewery.springbrewery.web.model.BeerDto;

import java.util.UUID;

public interface BeerService {
    BeerDto getBeerById(UUID beerId);
}
