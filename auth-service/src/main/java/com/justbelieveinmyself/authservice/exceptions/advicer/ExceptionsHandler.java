package com.justbelieveinmyself.authservice.exceptions.advicer;

import com.justbelieveinmyself.library.exception.ConflictException;
import com.justbelieveinmyself.library.exception.UnprocessableEntityException;
import com.justbelieveinmyself.library.dto.ResponseError;
import com.justbelieveinmyself.library.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(value = {ConflictException.class})
    public ResponseEntity<ResponseError> handleConflictException(ConflictException e) {
        ResponseError responseError = new ResponseError(e.getMessage(), Instant.now(), 409);
        return new ResponseEntity<>(responseError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = {UnprocessableEntityException.class})
    public ResponseEntity<ResponseError> handleInvalidRequestException(UnprocessableEntityException e) {
        ResponseError responseError = new ResponseError(e.getMessage(), Instant.now(), 422);
        return new ResponseEntity<>(responseError, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    public ResponseEntity<ResponseError> handleUnauthorizedException(UnauthorizedException e) {
        ResponseError responseError = new ResponseError(e.getMessage(), Instant.now(), 402);
        return new ResponseEntity<>(responseError, HttpStatus.UNAUTHORIZED);
    }

}
