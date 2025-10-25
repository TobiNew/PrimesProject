package com.nwbproj.primes.service;


import com.nwbproj.primes.algorithms.impl.AlgorithmsImpl;
import com.nwbproj.primes.utils.TestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
public class AlgorithmServiceTest {

    @InjectMocks
    private AlgorithmsImpl algorithimsService;


    @Test
    @DisplayName("Default Algorithm: values 1 and 0 should return empty responses")
    public void Given0And1_ShouldReturnEmptyDefault(){
        ArrayList<Integer> zeroResponse = algorithimsService.defaultAlgorithm(0);

        ArrayList<Integer> oneResponse = algorithimsService.defaultAlgorithm(1);

        assertTrue(zeroResponse.isEmpty());
        assertTrue(oneResponse.isEmpty());

    }

    @Test
    @DisplayName("Default Algorithm: Should return list of primes up to 100")
    public void GivenInput100_ShouldReturnListOfPrimesDefault() throws IOException {
        ArrayList<Integer> response = algorithimsService.defaultAlgorithm(100);

        ArrayList<Integer> expected = TestUtils.loadFromJson("src/test/java/resources/PrimesList.json");
        assertEquals(expected, response);

    }

    @Test
    @DisplayName("SoE Algorithm: values 1 and 0 should return empty responses")
    public void Given0And1_ShouldReturnEmptySoE(){
        ArrayList<Integer> zeroResponse = algorithimsService.sieveOfEratosthenes(0);

        ArrayList<Integer> oneResponse = algorithimsService.sieveOfEratosthenes(1);

        assertTrue(zeroResponse.isEmpty());
        assertTrue(oneResponse.isEmpty());

    }

    @Test
    @DisplayName("SoE Algorithm: Should return list of primes up to 100")
    public void GivenInput100_ShouldReturnListOfPrimesSoE() throws IOException {
        ArrayList<Integer> response = algorithimsService.sieveOfEratosthenes(100);

        ArrayList<Integer> expected = TestUtils.loadFromJson("src/test/java/resources/PrimesList.json");
        assertEquals(expected, response);

    }




}
