package com.nwbproj.primes.exceptions.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nwbproj.primes.exceptions.InvalidAlgorithimException;
import com.nwbproj.primes.exceptions.InvalidPrimeInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    Map<Integer, BiFunction<String, String, ErrorResponse>> statusCodeMap = new HashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final ObjectMapper objectMapper;

    @Autowired
    public GlobalExceptionHandler(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler(value = InvalidAlgorithimException.class)
    public final ResponseEntity<ApiError> handleInvalidAlgorithmException(InvalidAlgorithimException exception) throws JsonProcessingException {

        ApiError error = ApiError.builder().
                detail(exception.getLocalizedMessage())
                .title("Invalid Algorithm value entered")
                .httpStatus(exception.getHttpStatus())
                .build();

        logException(InvalidAlgorithimException.class.getSimpleName(), error);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = InvalidPrimeInputException.class)
    public final ResponseEntity<ApiError> handlePrimeInputException(InvalidPrimeInputException exception, WebRequest request) throws JsonProcessingException {

        ApiError error = ApiError.builder().
                detail(exception.getLocalizedMessage())
                .title("Invalid value for prime entered")
                .httpStatus(exception.getHttpStatus())
                .build();

        logException(InvalidAlgorithimException.class.getSimpleName(), error);


        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(value = HttpClientErrorException.class)
    public ResponseEntity<ApiError> handleHttpClientErrorException(HttpClientErrorException ex, WebRequest request) throws JsonProcessingException {

        ApiError error = ApiError.builder().
                detail(ex.getLocalizedMessage())
                .title("HTTP Client Error Exception")
                .httpStatus(ex.getStatusCode().value())
                .build();

        logException(HttpClientErrorException.class.getSimpleName(), error);


        return new ResponseEntity<>(error, ex.getStatusCode());

    }
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ApiError> handleGlobalException(RuntimeException ex, WebRequest request) throws JsonProcessingException {

        ApiError error = ApiError.builder().
                detail(ex.getLocalizedMessage())
                .title("Exception Thrown")
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();

        logException(Exception.class.getSimpleName(), error);


        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex, HttpHeaders httpHeaders, HttpStatusCode statusCode, WebRequest request
    ){
        ApiError error = ApiError.builder()
                .detail(ex.getLocalizedMessage())
                .title("Method argument mismatch")
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .build();

        logException(MethodArgumentTypeMismatchException.class.getSimpleName(), error);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }


    private void  logException(String exceptionName, ApiError errorResponse){
        String json = null;

        try {
            json = objectMapper.writeValueAsString(errorResponse);
        }catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }

        logger.error("Exception: {}\nException details: {}", exceptionName, json);
    }


}
