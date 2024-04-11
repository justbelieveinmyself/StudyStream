package com.justbelieveinmyself.userservice.domains.exceptions.advicer;

import com.justbelieveinmyself.library.dto.ResponseError;
import com.justbelieveinmyself.library.exception.ConflictException;
import com.justbelieveinmyself.library.exception.ForbiddenException;
import com.justbelieveinmyself.library.exception.NotFoundException;
import com.justbelieveinmyself.library.exception.UnprocessableEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<ResponseError> handleNotFoundException(NotFoundException e) {
        ResponseError response = new ResponseError(e.getMessage(), Instant.now(), 404);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ConflictException.class})
    public ResponseEntity<ResponseError> handleConflictException(ConflictException e) {
        ResponseError response = new ResponseError(e.getMessage(), Instant.now(), 409);
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({UnprocessableEntityException.class})
    public ResponseEntity<ResponseError> handleUnprocessableEntityException(UnprocessableEntityException e) {
        ResponseError response = new ResponseError(e.getMessage(), Instant.now(), 422);
        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({ForbiddenException.class})
    public ResponseEntity<ResponseError> handleForbiddenException(ForbiddenException e) {
        ResponseError response = new ResponseError(e.getMessage(), Instant.now(), 403);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

}
