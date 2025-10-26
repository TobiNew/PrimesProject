package com.nwbproj.primes.service.impl;

import com.nwbproj.primes.algorithms.Algorithms;
import com.nwbproj.primes.enums.AlgorithmsEnum;
import com.nwbproj.primes.exceptions.InvalidAlgorithimException;
import com.nwbproj.primes.exceptions.InvalidPrimeInputException;
import com.nwbproj.primes.model.PrimesResponse;
import com.nwbproj.primes.service.PrimeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;



@Service
@Slf4j
@AllArgsConstructor
public class PrimeServiceImpl implements PrimeService {


    @Autowired
    private Algorithms algorithms;


    @Override
    @Cacheable("primeCache")
    public ResponseEntity<PrimesResponse> calculatePrimeList(Integer number, AlgorithmsEnum algorithm) throws Exception {
        PrimesResponse response = new PrimesResponse();


        if (number<0){
            throw new InvalidPrimeInputException("Values below 0 are invalid", HttpStatus.BAD_REQUEST.value());
        }
        if (algorithm==null){
            throw new InvalidAlgorithimException("Algorithm value cannot be null", HttpStatus.BAD_REQUEST.value());
        }

        switch (algorithm){
            case DEFAULT -> response.setPrimeNumbers(algorithms.defaultAlgorithm(number));
            case SIEVE_OF_ERATHOSTENES -> response.setPrimeNumbers(algorithms.sieveOfEratosthenes(number));
            case SIEVE_CONCURRENCY -> response.setPrimeNumbers(algorithms.sieveConcurrency(number));
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
