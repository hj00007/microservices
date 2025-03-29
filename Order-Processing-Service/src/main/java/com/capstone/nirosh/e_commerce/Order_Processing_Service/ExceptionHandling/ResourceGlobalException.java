package com.capstone.nirosh.e_commerce.Order_Processing_Service.ExceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ResourceGlobalException {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResourceErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        ResourceErrorResponse errorResponse = new ResourceErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
