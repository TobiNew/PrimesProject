package com.nwbproj.primes.controller;

import com.nwbproj.primes.enums.AlgorithmsEnum;
import com.nwbproj.primes.model.PrimesResponse;
import com.nwbproj.primes.algorithms.impl.AlgorithmsImpl;
import com.nwbproj.primes.service.impl.PrimeServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;




@ExtendWith(MockitoExtension.class)
public class PrimeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PrimeServiceImpl primeService;

    @Mock
    private AlgorithmsImpl algorithimsService;

    @InjectMocks
    private  PrimeController primeController;

    private final static Integer primeNumber = 100;

    private final static String primesPath = "http://localhost:8080/getPrime/";

    private final static AlgorithmsEnum algorithm = AlgorithmsEnum.DEFAULT;

    @BeforeEach
    public void setup(){



        mockMvc = MockMvcBuilders.standaloneSetup(primeController)
                .build();
    }


    @Test
    @DisplayName("Retrieves list of prime numbers(JSON)")
    public void GivenValidParameters_ShouldReturnOkJSON() throws Exception{
        when(primeService.calculatePrimeList(100, AlgorithmsEnum.DEFAULT))
                .thenReturn(new ResponseEntity<PrimesResponse>(HttpStatus.OK));

        mockMvc.perform(get(StringUtils.join(primesPath, primeNumber))
                        .queryParam("algorithm", String.valueOf(AlgorithmsEnum.DEFAULT))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(primeService, times(1)).calculatePrimeList(primeNumber, AlgorithmsEnum.DEFAULT);

    }

    @Test
    @DisplayName("Retrieves list of prime numbers(XML)")
    public void GivenValidParameters_ShouldReturnOkXML() throws Exception{
        when(primeService.calculatePrimeList(100, AlgorithmsEnum.DEFAULT))
                .thenReturn(new ResponseEntity<PrimesResponse>(HttpStatus.OK));

        mockMvc.perform(get(StringUtils.join(primesPath, primeNumber))
                        .queryParam("algorithm", String.valueOf(AlgorithmsEnum.DEFAULT))
                        .contentType(MediaType.APPLICATION_XML))
                .andExpect(status().isOk());

        verify(primeService, times(1)).calculatePrimeList(primeNumber, AlgorithmsEnum.DEFAULT);

    }

    @Test
    @DisplayName("Should return 404 when no prime number input")
    public void GivenMissingPrimeNumberInput_ShouldReturnNotFound() throws Exception{

        mockMvc.perform(get(StringUtils.join(primesPath))
                        .queryParam("algorithm", String.valueOf(AlgorithmsEnum.DEFAULT))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(primeService, times(0)).calculatePrimeList(any(), any());

    }

    @Test
    @DisplayName("Should return 400 when invalid algorithm input")
    public void GivenMissingAlgorithm_ShouldReturnBadRequest() throws Exception{

        mockMvc.perform(get(StringUtils.join(primesPath, primeNumber))
                        .queryParam("algorithm", "INVALID")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(primeService, times(0)).calculatePrimeList(any(), any());

    }

}
