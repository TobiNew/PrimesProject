package com.nwbproj.primes.exceptions;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwbproj.primes.exceptions.handler.ApiError;
import com.nwbproj.primes.exceptions.handler.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class GlobalExceptionHandlerTest {


    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    public void checkShowsExceptionFormat() throws JsonProcessingException{
        when(objectMapper.writeValueAsString(any())).thenReturn("test");

        ResponseEntity<ApiError> apiErrorResponseEntity = globalExceptionHandler.handleInvalidAlgorithmException(new InvalidAlgorithimException("error shown", HttpStatus.BAD_REQUEST.value()));
        assertEquals("error shown", Objects.requireNonNull(apiErrorResponseEntity.getBody()).getDetail());
        assertEquals(HttpStatus.BAD_REQUEST, apiErrorResponseEntity.getStatusCode());




    }


}
