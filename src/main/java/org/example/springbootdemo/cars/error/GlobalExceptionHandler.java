package org.example.springbootdemo.cars.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleValidationError(MethodArgumentNotValidException ex) {
        String errorMessage=ex.getFieldErrors().stream()
                .map(fieldError ->fieldError.getField()+" "+fieldError.getDefaultMessage() )
                .collect(Collectors.joining(","));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO("invalid-request", errorMessage));
    }

    @ExceptionHandler(InvalidLoginException.class)
    public ResponseEntity<ErrorDTO> handleInvalidLoginException(InvalidLoginException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorDTO("invalid-login", ex.getMessage()));
    }
}
