package com.springbrewery.springbrewery.web.service;

import com.springbrewery.springbrewery.web.model.CustomerDto;

import java.util.UUID;

public interface CustomerService {

    CustomerDto getCustomerById(UUID customerId);
}