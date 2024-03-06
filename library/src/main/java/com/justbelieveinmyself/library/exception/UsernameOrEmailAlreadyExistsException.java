package com.justbelieveinmyself.library.exception;

public class UsernameOrEmailAlreadyExistsException extends RuntimeException {
    public UsernameOrEmailAlreadyExistsException(String message) {
        super(message);
    }
}
