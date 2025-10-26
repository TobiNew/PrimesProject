package com.nwbproj.primes.service;


import com.nwbproj.primes.algorithms.impl.AlgorithmsImpl;
import com.nwbproj.primes.utils.TestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class AlgorithmServiceTest {

    @InjectMocks
    private AlgorithmsImpl algorithimsService;


    @ParameterizedTest
    @DisplayName("Default Algorithm: values 1 and 0 should return empty responses")
    @ValueSource(ints = {0,1})
    public void Given0And1_ShouldReturnEmptyDefault(int input){
        ArrayList<Integer> zeroResponse = algorithimsService.defaultAlgorithm(input);
        ArrayList<Integer> oneResponse = algorithimsService.defaultAlgorithm(input);

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

    @ParameterizedTest
    @DisplayName("SoE Algorithm: values 1 and 0 should return empty responses")
    @ValueSource(ints = {0,1})
    public void Given0And1_ShouldReturnEmptySoE(int input){
        ArrayList<Integer> response = algorithimsService.sieveOfEratosthenes(input);
        assertTrue(response.isEmpty());
    }

    @Test
    @DisplayName("SoE Algorithm: Should return list of primes up to 100")
    public void GivenInput100_ShouldReturnListOfPrimesSoE() throws IOException {
        ArrayList<Integer> response = algorithimsService.sieveOfEratosthenes(100);

        ArrayList<Integer> expected = TestUtils.loadFromJson("src/test/java/resources/PrimesList.json");
        assertEquals(expected, response);

    }


    @ParameterizedTest
    @DisplayName("SoE Concurrency Algorithm: values 1 and 0 should return empty responses")
    @ValueSource(ints = {0,1})
    public void Given0And1_ShouldReturnEmptySoEConcurrency(int input) throws InterruptedException {
        ArrayList<Integer> zeroResponse = algorithimsService.sieveConcurrency(input);
        ArrayList<Integer> oneResponse = algorithimsService.sieveConcurrency(input);

        assertTrue(zeroResponse.isEmpty());
        assertTrue(oneResponse.isEmpty());

    }

    @Test
    @DisplayName("SoE Concurrency Algorithm: Should return list of primes up to 100")
    public void GivenInput100_ShouldReturnListOfPrimesSoEConcurrency() throws IOException, InterruptedException {
        ArrayList<Integer> response = algorithimsService.sieveConcurrency(100);

        ArrayList<Integer> expected = TestUtils.loadFromJson("src/test/java/resources/PrimesList.json");
        assertEquals(expected, response);

    }




}
