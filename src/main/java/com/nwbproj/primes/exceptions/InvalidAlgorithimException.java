package com.nwbproj.primes.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidAlgorithimException extends BaseException{
    public InvalidAlgorithimException(String message, Integer httpStatus) {
        super(message, httpStatus);
    }
}
