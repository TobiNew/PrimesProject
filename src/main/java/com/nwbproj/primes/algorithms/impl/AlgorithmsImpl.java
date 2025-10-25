package com.nwbproj.primes.algorithms.impl;

import com.nwbproj.primes.algorithms.Algorithms;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;

@Component
@Slf4j
@AllArgsConstructor
public class AlgorithmsImpl implements Algorithms {

    public ArrayList<Integer> defaultAlgorithm(Integer n) {
        //Brute force algorithm
        ArrayList<Integer> result = new ArrayList<>();

        for (int i = 1; i<= n ; i++) {
            if (isPrime(i)){
                result.add(i);
            }
        }
        return result;
    }


    public ArrayList<Integer> sieveOfEratosthenes(Integer n) {
        ArrayList<Integer> result = new ArrayList<>();

        //Create array of values up to n+1 and make them all true then set 0 and 1 to be not prime.
        //if a value is false it is not a prime
        boolean[] prime = new boolean[n+1];
        Arrays.fill(prime, true);

        for (int p = 2; p * p <= n; p++) {
            // If prime[p] is not changed, then it is a prime
            if (prime[p]) {
                //Changes all multiples of p that are greater than or equal to the square of p
                //numbers that are multiples of p and are less that p squared have already been marked
                for (int i = p * p; i <= n; i += p) {
                    prime[i] = false;
                }
            }
        }

        for (int p = 2; p <= n; p++) {
            if (prime[p]) {
                result.add(p);
            }
        }
        return result;
    }

    //default algorithm. works by knowing all even numbers are not primes except 2
    private boolean isPrime(int n){
        if (n == 0 || n == 1){
            return false;
        }

        if (n == 2){
            return true;
        }

        for (int i = 2; i * i <= n ; i++) {
            if (n%i==0){
                return false;
            }
        }
        return true;
    }
}
