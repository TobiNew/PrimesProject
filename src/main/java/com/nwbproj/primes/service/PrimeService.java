package com.nwbproj.primes.service;

import com.nwbproj.primes.enums.AlgorithmsEnum;
import com.nwbproj.primes.model.PrimesResponse;
import org.springframework.http.ResponseEntity;

public interface PrimeService {

    public ResponseEntity<PrimesResponse> calculatePrimeList(Integer number, AlgorithmsEnum algorithm) throws Exception;
}
