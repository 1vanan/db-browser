package com.example.dbbrowser.controller;

import com.example.dbbrowser.dto.ExceptionResponse;
import com.example.dbbrowser.exceptions.PropertyNotFoundException;
import com.example.dbbrowser.exceptions.PropertyVerificationException;
import com.example.dbbrowser.exceptions.UnableConnectionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ExceptionResponse> handleException(RuntimeException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ExceptionResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .description(ex.getMessage())
                    .build());
    }

    @ExceptionHandler(value = {PropertyNotFoundException.class})
    protected ResponseEntity<ExceptionResponse> handlePropertyNotFoundException(RuntimeException ex,
        WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ExceptionResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .description(ex.getMessage())
                    .build());
    }

    @ExceptionHandler(value = {PropertyVerificationException.class})
    protected ResponseEntity<ExceptionResponse> handlePropertyVerificationException(RuntimeException ex,
        WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ExceptionResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .description(ex.getMessage())
                    .build());
    }

    @ExceptionHandler(value = {UnableConnectionException.class})
    protected ResponseEntity<ExceptionResponse> handleUnableConnectionException(RuntimeException ex,
        WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                ExceptionResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .description(ex.getMessage())
                    .build());
    }
}
