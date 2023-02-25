package com.gozip.exception;

import com.gozip.dto.StateDto;
import com.gozip.exception.customException.InvalidDataException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StateDto> IllegalArgumentException(Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new StateDto(e.getMessage()));
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<StateDto> InvalidDataException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new StateDto(e.getMessage()));
    }
}
