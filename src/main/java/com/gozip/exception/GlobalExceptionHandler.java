package com.gozip.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                               HttpHeaders headers,
                                                               HttpStatus status,
                                                               WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        List<FieldError> allErrors = exception.getBindingResult().getFieldErrors();

        log.error("MethodArgumentNotValidException throw Exception : {}", BAD_REQUEST);

        for (int i = allErrors.size() - 1; i >= 0; i--) {
            errors.put(allErrors.get(i).getField(), allErrors.get(i).getDefaultMessage());
        }

        return ResponseEntity
                .status(BAD_REQUEST)
                .body(errors);
    }

    @ExceptionHandler(value = {CustomException.class})
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException exception) {
        log.error("CustomException throw Exception : {}", exception.getErrorCode());
        return ErrorResponse.toResponseEntity(exception.getErrorCode());
    }
}
