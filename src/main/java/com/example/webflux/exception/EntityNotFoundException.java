package com.example.webflux.exception;

public class EntityNotFoundException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Entity not found";
    }
}
