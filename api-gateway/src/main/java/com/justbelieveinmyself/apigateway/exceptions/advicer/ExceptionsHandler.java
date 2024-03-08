package com.justbelieveinmyself.apigateway.exceptions.advicer;

import com.justbelieveinmyself.apigateway.exceptions.NotAuthorizedException;
import com.justbelieveinmyself.library.exception.ResponseError;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler({NotAuthorizedException.class})
    public ResponseEntity<ResponseError> handleNotAuthorizedException(NotAuthorizedException ex) {
        ResponseError response = new ResponseError(ex.getMessage(), Instant.now(), 403);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
}
