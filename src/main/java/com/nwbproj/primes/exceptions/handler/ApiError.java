package com.nwbproj.primes.exceptions.handler;


import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ApiError {
    private String title;
    private Integer httpStatus;
    private String detail;
    @Builder.Default
    private Instant timestamp = Instant.now();

}
