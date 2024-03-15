package com.justbelieveinmyself.library.exception;

import java.time.Instant;

public class ResponseError {
    private Integer statusCode;
    private String message;
    private Instant timestamp;

    public ResponseError(String message, Instant timestamp, Integer statusCode) {
        this.statusCode = statusCode;
        this.message = message;
        this.timestamp = timestamp;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
