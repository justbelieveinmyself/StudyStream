package com.justbelieveinmyself.authservice.exceptions.adviser;

import com.justbelieveinmyself.library.exception.ResponseError;
import com.justbelieveinmyself.authservice.exceptions.UsernameOrEmailAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(value = UsernameOrEmailAlreadyExistsException.class)
    public ResponseEntity<ResponseError> handleUsernameOrEmailAlreadyExistsException(UsernameOrEmailAlreadyExistsException e) {
        ResponseError responseError = new ResponseError(e.getMessage(), Instant.now(), 409);
        return new ResponseEntity<>(responseError, HttpStatus.CONFLICT);
    }
}
