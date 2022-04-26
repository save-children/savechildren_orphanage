package com.supung.orphanage.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> resourceNotFoundException(ResourceNotFoundException ex) {
        log.error(ex.getMessage());
        ApiError apiError = ApiError.builder().status(HttpStatus.NOT_FOUND)
                .message(ex.getLocalizedMessage()).build();
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
}
