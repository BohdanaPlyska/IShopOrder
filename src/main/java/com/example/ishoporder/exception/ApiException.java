package com.example.ishoporder.exception;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class ApiException {

    private int statusCode;
    private String errorMessage;
    private ZonedDateTime zonedDateTime;

    public ApiException(Integer statusCode, String errorMessage) {
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }

    public ApiException() {

    }

    public ApiException(int statusCode, String errorMessage, ZonedDateTime zonedDateTime) {
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
        this.zonedDateTime = zonedDateTime;
    }
}