package com.example.ishoporder.exception;

import io.jsonwebtoken.SignatureException;

public class TokenExpired extends SignatureException {

    private final String message;

    public TokenExpired(String message) {
        super(message);
        this.message = message;
    }
}
