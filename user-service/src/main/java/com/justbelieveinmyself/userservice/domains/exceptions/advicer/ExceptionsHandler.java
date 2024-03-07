package com.justbelieveinmyself.userservice.domains.exceptions.advicer;

import com.justbelieveinmyself.library.exception.InvalidRequestException;
import com.justbelieveinmyself.library.exception.ResponseError;
import com.justbelieveinmyself.library.exception.UsernameOrEmailAlreadyExistsException;
import com.justbelieveinmyself.userservice.domains.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<ResponseError> handleUserNotFoundException(UserNotFoundException e) {
        ResponseError response = new ResponseError(e.getMessage(), Instant.now(), 404);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({UsernameOrEmailAlreadyExistsException.class})
    public ResponseEntity<ResponseError> handleUsernameOrEmailAlreadyExistsException(UsernameOrEmailAlreadyExistsException e) {
        ResponseError response = new ResponseError(e.getMessage(), Instant.now(), 409);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({InvalidRequestException.class})
    public ResponseEntity<ResponseError> handleInvalidRequestException(InvalidRequestException e) {
        ResponseError response = new ResponseError(e.getMessage(), Instant.now(), 403);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

}
