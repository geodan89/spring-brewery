package com.springbrewery.springbrewery.web.mappers;

import com.springbrewery.springbrewery.domain.Customer;
import com.springbrewery.springbrewery.web.model.CustomerDto;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    CustomerDto customerToCustomerDto(Customer customer);

    Customer customerDtoToCustomer(CustomerDto customerDto);
}
