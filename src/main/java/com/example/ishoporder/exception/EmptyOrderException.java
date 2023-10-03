package com.example.ishoporder.exception;

public class EmptyOrderException extends RuntimeException{

    private final String message;

    public EmptyOrderException(String message) {
        super(message);
        this.message = message;
    }
}
