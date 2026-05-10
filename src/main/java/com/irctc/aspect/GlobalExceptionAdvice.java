package com.irctc.aspect;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorMessage> handleNullPointerException(NullPointerException ex) {
        return ResponseEntity.ok(ErrorMessage.builder().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage()).build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleGenericException(Exception ex) {
        return ResponseEntity.ok(ErrorMessage.builder().statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(ex.getMessage()).build());
    }

}
