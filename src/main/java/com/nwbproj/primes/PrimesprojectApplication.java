package com.nwbproj.primes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PrimesprojectApplication {
    public static void main(String[] args) {
        SpringApplication.run(PrimesprojectApplication.class, args);

    }
}