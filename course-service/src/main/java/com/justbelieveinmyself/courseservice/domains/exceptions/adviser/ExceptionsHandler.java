package com.justbelieveinmyself.courseservice.domains.exceptions.adviser;

import com.justbelieveinmyself.library.exception.ForbiddenException;
import com.justbelieveinmyself.library.exception.NotFoundException;
import com.justbelieveinmyself.library.dto.ResponseError;
import com.justbelieveinmyself.library.exception.UnprocessableEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<ResponseError> handleNotFoundException(NotFoundException ex) {
        ResponseError responseError = new ResponseError(ex.getMessage(), Instant.now(), 404);
        return new ResponseEntity<>(responseError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {ForbiddenException.class})
    public ResponseEntity<ResponseError> handleForbiddenException(ForbiddenException ex) {
        ResponseError responseError = new ResponseError(ex.getMessage(), Instant.now(), 403);
        return new ResponseEntity<>(responseError, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {UnprocessableEntityException.class})
    public ResponseEntity<ResponseError> handleUnprocessableEntityException(UnprocessableEntityException e) {
        ResponseError responseError = new ResponseError(e.getMessage(), Instant.now(), 422);
        return new ResponseEntity<>(responseError, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
