package com.example.demo.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(
            BadRequestException ex,
            HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(PessimisticLockingFailureException.class)
    public ResponseEntity<ErrorResponse> handleLockingFailure(
            PessimisticLockingFailureException ex,
            HttpServletRequest request) {
        return buildErrorResponse(
                HttpStatus.CONFLICT,
                "Request conflict due to concurrent update. Please retry.",
                request.getRequestURI());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUsernameNotFound(
            UsernameNotFoundException ex,
            HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage(), request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request) {
        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                request.getRequestURI());
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(
            HttpStatus status,
            String message,
            String path) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(path)
                .build();

        return ResponseEntity.status(status).body(errorResponse);
    }
}
