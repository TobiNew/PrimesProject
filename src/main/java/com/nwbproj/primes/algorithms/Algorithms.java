package com.nwbproj.primes.algorithms;

import java.util.ArrayList;

public interface Algorithms {

    public ArrayList<Integer> defaultAlgorithm(Integer n);

    public ArrayList<Integer> sieveOfEratosthenes(Integer n);

    public ArrayList<Integer> sieveConcurrency(Integer n) throws InterruptedException;

}
