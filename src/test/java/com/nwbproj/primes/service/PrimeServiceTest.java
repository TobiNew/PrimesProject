package com.nwbproj.primes.service;


import com.nwbproj.primes.algorithms.Algorithms;
import com.nwbproj.primes.enums.AlgorithmsEnum;
import com.nwbproj.primes.exceptions.InvalidAlgorithimException;
import com.nwbproj.primes.exceptions.InvalidPrimeInputException;
import com.nwbproj.primes.model.PrimesResponse;
import com.nwbproj.primes.service.impl.PrimeServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class PrimeServiceTest {
    //The Prime service class is not meant to check if the output is correct or not, its just to do basic error checking and pass input to correct algorithm

    @Mock
    private Algorithms algorithimsService;

    @InjectMocks
    private PrimeServiceImpl primeService;


    @ParameterizedTest
    @DisplayName("Should return a Response Entity list of values from Algorithm service")
    @EnumSource(AlgorithmsEnum.class)
    public void GivenValidInput_ShouldReturnResponseEntity(AlgorithmsEnum algorithmsEnum) throws Exception {

        switch (algorithmsEnum){
            case DEFAULT -> when(algorithimsService.defaultAlgorithm(any())).thenReturn(new ArrayList<Integer>());
            case SIEVE_CONCURRENCY -> when(algorithimsService.sieveConcurrency(any())).thenReturn(new ArrayList<Integer>());
            case SIEVE_OF_ERATHOSTENES -> when(algorithimsService.sieveOfEratosthenes(any())).thenReturn(new ArrayList<Integer>());
        }

        ResponseEntity<PrimesResponse> response = primeService.calculatePrimeList(100, algorithmsEnum);

        assertAll(
                () -> assertNotNull(response),
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(ArrayList.class, response.getBody().getPrimeNumbers().getClass())
        );

    }

    @Test
    @DisplayName("Should throw InvalidAlgorithmException if null algorithm is entered")
    public void GivenInvalidAlgorithm_ShouldReturnResponseEntity() throws Exception {

        InvalidAlgorithimException exception = assertThrows(InvalidAlgorithimException.class, () -> {
            primeService.calculatePrimeList(100, null);
        });

        assertEquals("Algorithm value cannot be null", exception.getLocalizedMessage());

    }

    @Test
    @DisplayName("Should throw InvalidPrimeInputException if number below 0 is entered")
    public void GivenInvalidNumberInput_ShouldReturnResponseEntity() throws Exception {

        InvalidPrimeInputException exception = assertThrows(InvalidPrimeInputException.class, () -> {
            primeService.calculatePrimeList(-20, AlgorithmsEnum.DEFAULT);
        });

        assertEquals("Values below 0 are invalid", exception.getLocalizedMessage());

    }



}
