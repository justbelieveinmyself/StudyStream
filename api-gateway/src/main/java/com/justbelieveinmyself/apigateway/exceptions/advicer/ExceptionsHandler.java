package com.justbelieveinmyself.apigateway.exceptions.advicer;

import com.justbelieveinmyself.apigateway.exceptions.UnauthorizedException;
import com.justbelieveinmyself.library.exception.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(value = UnauthorizedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ResponseError> handleUnauthorizedException(UnauthorizedException ex) {
        ResponseError responseError = new ResponseError(ex.getMessage(), Instant.now(), 403);
        return new ResponseEntity<>(responseError, HttpStatus.FORBIDDEN);
    }

}
