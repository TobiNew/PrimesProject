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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

    @Test
    @DisplayName("Should return a Response Entity list of values from Algorithm service (Default algorithm")
    public void GivenValidInput_ShouldReturnResponseEntityDefault() throws Exception {

        when(algorithimsService.defaultAlgorithm(any())).thenReturn(new ArrayList<Integer>());

        ResponseEntity<PrimesResponse> response = primeService.calculatePrimeList(100, AlgorithmsEnum.DEFAULT);

        verify(algorithimsService, times(1)).defaultAlgorithm(100);
        assertEquals(ArrayList.class, Objects.requireNonNull(response.getBody()).getPrimeNumbers().getClass());

    }

    @Test
    @DisplayName("Should return a Response Entity list of values from Algorithm service")
    public void GivenValidInput_ShouldReturnResponseEntitySOE() throws Exception {
        when(algorithimsService.sieveOfEratosthenes(any())).thenReturn(new ArrayList<Integer>());

        ResponseEntity<PrimesResponse> response = primeService.calculatePrimeList(100, AlgorithmsEnum.SIEVE_OF_ERATHOSTENES);

        verify(algorithimsService, times(1)).sieveOfEratosthenes(100);
        assertEquals(ArrayList.class, Objects.requireNonNull(response.getBody()).getPrimeNumbers().getClass());

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
