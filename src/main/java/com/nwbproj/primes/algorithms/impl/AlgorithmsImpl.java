package com.nwbproj.primes.algorithms.impl;

import com.nwbproj.primes.algorithms.Algorithms;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
@AllArgsConstructor
public class AlgorithmsImpl implements Algorithms {

    @Override
    public ArrayList<Integer> defaultAlgorithm(Integer n) {
        //Brute force algorithm (O (n sqrt n))
        ArrayList<Integer> result = new ArrayList<>();

        for (int i = 1; i<= n ; i++) {
            if (isPrime(i)){
                result.add(i);
            }
        }
        return result;
    }

    @Override
    public ArrayList<Integer> sieveOfEratosthenes(Integer n) {
        //O(n log log n)
        ArrayList<Integer> result = new ArrayList<>();

        if (n < 2) {
            return new ArrayList<>();
        }
        //Create array of values up to n+1 and make them all true then set 0 and 1 to be not prime.
        //if a value is false it is not a prime
        boolean[] prime = new boolean[n+1];
        Arrays.fill(prime, true);
        prime[0]=false;
        prime[1]=false;

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

    @Override
    public ArrayList<Integer> sieveConcurrency(Integer n) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();
        List<SegmentWorker> workers = new ArrayList<>();

        if (n < 2) return new ArrayList<>();
        //small numbers are given to sequential algorithm, no real performance gain to be gotten
        if (n < 10000) {
            return sieveOfEratosthenes(n);
        }

        int sqrtN = (int) Math.sqrt(n);
        //generating base primes list important step to help optimise
        List<Integer> basePrimeList = sieveOfEratosthenes(sqrtN);

        int numThreads = Runtime.getRuntime().availableProcessors();
        //generate segment size that is either an equally divided up between the number of numbers that
        //need to be generated divided by thread or 1000 numbers to make sure each segment has a significant workload to work on.
        int segmentSize = Math.max((n - sqrtN) / numThreads, 1000);

        // Start from
        for (int i = 0; i < numThreads; i++) {
            //start at first number after processed numbers then add offset for current segment
            //end at an equal number for the end of segment and make sure the segment isn't bigger than n
            int start = sqrtN + 1 + i * segmentSize;
            int end = Math.min(start + segmentSize - 1, n);

            //if the segment start is less than n, begin creating threads and workers
            if (start <= n) {
                SegmentWorker worker = new SegmentWorker(basePrimeList, start, end, n);
                Thread thread = new Thread(worker);
                thread.start();
                threads.add(thread);
                workers.add(worker);
            }
        }

        for (Thread thread : threads) {
            thread.join();
        }

        //when workers finish their tasks, merge into final result arraylist
        ArrayList<Integer> result = new ArrayList<>(basePrimeList);
        for (SegmentWorker worker : workers) {
            result.addAll(worker.getPrimes());
        }

        Collections.sort(result);
        return result;

    }

    private static class SegmentWorker implements Runnable {
        private final List<Integer> basePrimes;
        private final int start, end, n;
        private final ArrayList<Integer> primes;
        private final boolean[] segment; //avoid memory contention by giving each segment its own array

        public SegmentWorker(List<Integer> basePrimes, int start, int end, int n) {
            this.basePrimes = basePrimes;
            this.start = start;
            this.end = end;
            this.n = n;
            this.primes = new ArrayList<>();
            this.segment = new boolean[end - start + 1];
            Arrays.fill(segment, true);
        }

        @Override
        public void run() {
            for (int prime : basePrimes) {
                //finds first multiple of each value in basePrimes
                int firstMultiple = (start / prime) * prime;
                //we do this to make sure the number is after our segment start.
                if (firstMultiple < start){
                    firstMultiple += prime;
                }
                //we do this to make sure we dont accidentally mark a prime number as composite
                if (firstMultiple == prime){
                    firstMultiple += prime;
                }


                //Goes through segment from first multiple marking multiples as false
                for (int j = firstMultiple; j <= end; j += prime) {
                    segment[j - start] = false;
                }
            }

            //collects all primes found
            for (int i = 0; i < segment.length; i++) {
                if (segment[i]) {
                    primes.add(start + i);
                }
            }
        }

        public ArrayList<Integer> getPrimes() {
            return primes;
        }
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
