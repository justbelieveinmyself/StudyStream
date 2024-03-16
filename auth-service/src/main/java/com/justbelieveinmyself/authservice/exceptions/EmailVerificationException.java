package com.justbelieveinmyself.authservice.exceptions;

public class EmailVerificationException extends RuntimeException {
    public EmailVerificationException(String message) {
        super(message);
    }
}
