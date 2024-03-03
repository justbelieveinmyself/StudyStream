package com.justbelieveinmyself.library.exception;

import java.time.Instant;
public class ResponseError {
    private String message;
    private Instant timestamp;
    private Integer statusCode;
    public ResponseError(String message, Instant timestamp, Integer statusCode) {
        this.message = message;
        this.timestamp = timestamp;
        this.statusCode = statusCode;
    }
}
