package com.nwbproj.primes.exceptions;

import lombok.Getter;


@Getter
public class BaseException extends RuntimeException{

    private Integer httpStatus;

    public BaseException(String message, Integer httpStatus){
        super(message);
        this.httpStatus = httpStatus;
    }

    public BaseException(String message){
        super(message);
    }

}
