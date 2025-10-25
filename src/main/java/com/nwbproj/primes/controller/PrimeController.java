package com.nwbproj.primes.controller;

import com.nwbproj.primes.enums.AlgorithmsEnum;
import com.nwbproj.primes.model.PrimesResponse;
import com.nwbproj.primes.service.PrimeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrimeController {

    @Autowired
    private PrimeService primeService;

    @Operation(
            description = "Calculates and retrieves a list of primes up to and including the value given",
            summary = "getPrimesList",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Primes successfully calculated and displayed",
                    content = @Content(schema = @Schema(implementation = PrimesResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Internal service error", content = @Content)
            }
    )
    @GetMapping(path = "/getPrime/{primeNumber}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<PrimesResponse> getPrimeList(
            @PathVariable(name = "primeNumber") Integer primeNumber,
            @RequestParam(required = false, defaultValue = "DEFAULT") AlgorithmsEnum algorithm
            ) throws Exception {
        return primeService.calculatePrimeList(primeNumber, algorithm);
    }





}
