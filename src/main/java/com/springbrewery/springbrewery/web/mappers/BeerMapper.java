package com.springbrewery.springbrewery.web.mappers;

import com.springbrewery.springbrewery.domain.Beer;
import com.springbrewery.springbrewery.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

    BeerDto beerToBeerDto(Beer beer);

    Beer beerDtoToBeer(BeerDto beerDto);
}
