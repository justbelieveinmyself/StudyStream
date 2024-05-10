package com.justbelieveinmyself.authservice.exceptions.advicer;

import com.justbelieveinmyself.library.exception.ConflictException;
import com.justbelieveinmyself.library.exception.NotFoundException;
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
    public ResponseEntity<ResponseError> handleUnprocessableEntityException(UnprocessableEntityException e) {
        ResponseError responseError = new ResponseError(e.getMessage(), Instant.now(), 422);
        return new ResponseEntity<>(responseError, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    public ResponseEntity<ResponseError> handleUnauthorizedException(UnauthorizedException e) {
        ResponseError responseError = new ResponseError(e.getMessage(), Instant.now(), 401);
        return new ResponseEntity<>(responseError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ResponseError> handleNotFoundException(NotFoundException e) {
        ResponseError responseError = new ResponseError(e.getMessage(), Instant.now(), 404);
        return new ResponseEntity<>(responseError, HttpStatus.NOT_FOUND);
    }

}
