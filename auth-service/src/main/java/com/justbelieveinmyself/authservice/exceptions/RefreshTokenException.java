package com.justbelieveinmyself.authservice.exceptions;

public class RefreshTokenException extends RuntimeException {
    public RefreshTokenException(String message) {
        super(message);
    }

}