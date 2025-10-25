package com.nwbproj.primes.model;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
public class PrimesResponse {
    private ArrayList<Integer> primeNumbers;
}
