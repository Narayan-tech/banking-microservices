package com.example.fundtransferservice.Exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //Handle ResponseStatusException
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String,Object>> handleResponseStatusException(ResponseStatusException ex){
        Map<String,Object> error = new HashMap<>();

        error.put("timestamp", LocalDateTime.now());
        error.put("status",ex.getStatusCode().value());
        error.put("message",ex.getReason());

        return new ResponseEntity<>(error,ex.getStatusCode());
    }

    //Handle validation errors (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> handleValidationException(MethodArgumentNotValidException ex){

        Map<String,Object> errors = new HashMap<>();
        Map<String,String> fieldErrors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(
                error -> fieldErrors.put(error.getField(),error.getDefaultMessage())
        );

        errors.put("timestamp",LocalDateTime.now());
        errors.put("status",HttpStatus.BAD_REQUEST.value());
        errors.put("errors",fieldErrors);

        return new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);

    }

    //Handle all other exceptions(fallback)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> handleException(Exception ex)
    {
        Map<String,Object> error = new HashMap<>();
        error.put("timestamp",LocalDateTime.now());
        error.put("status",HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.put("message","Something went wrong. Please try again!");

        return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);


    }
}
