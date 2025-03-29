package com.capstone.nirosh.e_commerce.Payment_Service.OrderNotFoundException;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
