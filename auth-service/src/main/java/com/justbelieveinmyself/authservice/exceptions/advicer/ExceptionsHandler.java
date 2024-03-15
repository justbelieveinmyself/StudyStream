package com.justbelieveinmyself.authservice.exceptions.advicer;

import com.justbelieveinmyself.authservice.exceptions.EmailVerificationException;
import com.justbelieveinmyself.authservice.exceptions.UnauthorizedException;
import com.justbelieveinmyself.library.exception.InvalidRequestException;
import com.justbelieveinmyself.authservice.exceptions.RefreshTokenException;
import com.justbelieveinmyself.library.exception.ResponseError;
import com.justbelieveinmyself.library.exception.UsernameOrEmailAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(value = {UsernameOrEmailAlreadyExistsException.class, EmailVerificationException.class})
    public ResponseEntity<ResponseError> handleUsernameOrEmailAlreadyExistsException(Exception e) {
        ResponseError responseError = new ResponseError(e.getMessage(), Instant.now(), 409);
        return new ResponseEntity<>(responseError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = RefreshTokenException.class)
    public ResponseEntity<ResponseError> handleRefreshTokenException(RefreshTokenException e) {
        ResponseError responseError = new ResponseError(e.getMessage(), Instant.now(), 403);
        return new ResponseEntity<>(responseError, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = InvalidRequestException.class)
    public ResponseEntity<ResponseError> handleInvalidRequestException(InvalidRequestException e) {
        ResponseError responseError = new ResponseError(e.getMessage(), Instant.now(), 403);
        return new ResponseEntity<>(responseError, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    public ResponseEntity<ResponseError> handleUnauthorizedException(UnauthorizedException e) {
        ResponseError responseError = new ResponseError(e.getMessage(), Instant.now(), 402);
        return new ResponseEntity<>(responseError, HttpStatus.UNAUTHORIZED);
    }

}
