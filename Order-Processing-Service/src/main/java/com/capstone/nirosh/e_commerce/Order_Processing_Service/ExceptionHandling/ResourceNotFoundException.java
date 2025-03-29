package com.capstone.nirosh.e_commerce.Order_Processing_Service.ExceptionHandling;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
