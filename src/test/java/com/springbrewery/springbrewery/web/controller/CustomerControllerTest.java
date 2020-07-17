package com.springbrewery.springbrewery.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbrewery.springbrewery.web.model.CustomerDto;
import com.springbrewery.springbrewery.web.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;

    private CustomerDto customerDto;

    @BeforeEach
    void setUp() {
        customerDto = CustomerDto.builder().id(UUID.randomUUID()).name("John").build();
    }

    @Test
    void getCustomer() throws Exception {
        when(customerService.getCustomerById(any(UUID.class))).thenReturn(customerDto);
        mockMvc.perform(get("/api/v1/customer/" + UUID.randomUUID().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(customerDto.getId().toString())))
                .andExpect(jsonPath("$.name", is("John")));
    }

    @Test
    void createNewCustomer() throws Exception {
        String customerToJson = objectMapper.writeValueAsString(customerDto);

        when(customerService.saveCustomer(any())).thenReturn(customerDto);

        mockMvc.perform(post("/api/v1/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerToJson))
                .andExpect(status().isCreated());

    }

    @Test
    void updateCustomer() throws Exception {
        String customerToJson = objectMapper.writeValueAsString(customerDto);
        mockMvc.perform(put("/api/v1/customer/" + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerToJson))
                .andExpect(status().isNoContent());
        verify(customerService).updateCustomer(any(), any());
    }
}