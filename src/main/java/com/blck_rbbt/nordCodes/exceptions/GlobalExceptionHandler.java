package com.blck_rbbt.nordCodes.exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<AppError> catchResourceNotFoundException(ResourceNotFoundException e) {
        return new ResponseEntity<>(new AppError(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler
    public ResponseEntity<UrlValidationError> catchUrlValidationException(UrlValidationException e) {
        return new ResponseEntity<>(new UrlValidationError(e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler
    public ResponseEntity<ShortUrlCreationError> catchShortUrlCreationException(ShortUrlCreationException e) {
        return new ResponseEntity<>(new ShortUrlCreationError(e.getMessage()),
                HttpStatus.NO_CONTENT);
    }
}
