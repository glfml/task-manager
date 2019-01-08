package com.example.microservice.dto;

public class ValidationError {

    private String message;

    public ValidationError(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
