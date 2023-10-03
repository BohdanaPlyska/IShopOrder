package com.example.ishoporder.exception;


import lombok.Data;

@Data
public class AlreadyExistException extends RuntimeException {

    private final String message;

    public AlreadyExistException(String message) {
        super(message);
        this.message = message;
    }
}