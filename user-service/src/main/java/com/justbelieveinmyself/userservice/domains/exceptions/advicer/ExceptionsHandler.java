package com.justbelieveinmyself.userservice.domains.exceptions.advicer;

import com.justbelieveinmyself.library.exception.UnprocessableEntityException;
import com.justbelieveinmyself.library.dto.ResponseError;
import com.justbelieveinmyself.library.exception.ConflictException;
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

    @ExceptionHandler({ConflictException.class})
    public ResponseEntity<ResponseError> handleUsernameOrEmailAlreadyExistsException(ConflictException e) {
        ResponseError response = new ResponseError(e.getMessage(), Instant.now(), 409);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({UnprocessableEntityException.class})
    public ResponseEntity<ResponseError> handleInvalidRequestException(UnprocessableEntityException e) {
        ResponseError response = new ResponseError(e.getMessage(), Instant.now(), 403);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

}
