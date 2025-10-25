package com.nwbproj.primes.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwbproj.primes.model.PrimesResponse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TestUtils {

    public static ArrayList<Integer> loadFromJson(String filepath) throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        PrimesResponse primeJson = objectMapper.readValue(new File(filepath), PrimesResponse.class);
        return primeJson.getPrimeNumbers();
    }
}
