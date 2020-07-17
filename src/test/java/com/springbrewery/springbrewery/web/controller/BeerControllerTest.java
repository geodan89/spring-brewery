package com.springbrewery.springbrewery.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbrewery.springbrewery.web.model.BeerDto;
import com.springbrewery.springbrewery.web.service.BeerService;
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

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @MockBean
    private BeerService beerService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private BeerDto beerDto;

    @BeforeEach
    void setUp() {
        beerDto = BeerDto.builder().id(UUID.randomUUID())
                .beerName("Beer")
                .beerStyle("IPA")
                .upc(23764783267L)
                .build();
    }

    @Test
    void getBeer() throws Exception {
        when(beerService.getBeerById(any(UUID.class))).thenReturn(beerDto);

        mockMvc.perform(get("/api/v1/beer/" + beerDto.getId().toString()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(beerDto.getId().toString())))
                .andExpect(jsonPath("$.beerName", is("Beer")));
    }

    @Test
    void createNewBeer() throws Exception {
        BeerDto newBeerDto = beerDto;
        newBeerDto.setId(null);
        BeerDto savedBeerDto = BeerDto.builder().id(UUID.randomUUID()).beerName("New Beer").build();
        String beerToJson = objectMapper.writeValueAsString(newBeerDto);

        when(beerService.saveNewBeer(any())).thenReturn(savedBeerDto);

        mockMvc.perform(post("/api/v1/beer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerToJson))
                .andExpect(status().isCreated());
    }

    @Test
    void updateBeer() throws Exception {
        BeerDto newBeerDto = beerDto;
        newBeerDto.setId(null);
        String beerToJson = objectMapper.writeValueAsString(newBeerDto);

        mockMvc.perform(put("/api/v1/beer/" + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerToJson))
                .andExpect(status().isNoContent());
        verify(beerService).updateBeer(any(), any());

    }
}