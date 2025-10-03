package com.cadt.devicemanagementv2.exception;

import com.cadt.devicemanagementv2.utils.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static com.cadt.devicemanagementv2.utils.ApiResponseExtensions.with;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleNotFound(ResourceNotFoundException ex,
                                                                     HttpServletRequest req) {
        return build(HttpStatus.NOT_FOUND, "Not Found", ex.getMessage(), req, null);
    }

    @ExceptionHandler({ ApiException.class, IllegalArgumentException.class })
    public ResponseEntity<ApiResponse<ErrorResponse>> handleBadRequest(RuntimeException ex,
                                                                       HttpServletRequest req) {
        return build(HttpStatus.BAD_REQUEST, "Bad Request", ex.getMessage(), req, null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleValidation(MethodArgumentNotValidException ex,
                                                                       HttpServletRequest req) {
        Map<String, Object> details = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fe ->
                details.put(fe.getField(), fe.getDefaultMessage()));
        return build(HttpStatus.UNPROCESSABLE_ENTITY, "Validation Failed",
                "One or more fields are invalid", req, details);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleConstraint(ConstraintViolationException ex,
                                                                       HttpServletRequest req) {
        Map<String, Object> details = new HashMap<>();
        ex.getConstraintViolations().forEach(cv ->
                details.put(cv.getPropertyPath().toString(), cv.getMessage()));
        return build(HttpStatus.UNPROCESSABLE_ENTITY, "Validation Failed",
                "One or more fields are invalid", req, details);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleUnreadable(HttpMessageNotReadableException ex,
                                                                       HttpServletRequest req) {
        return build(HttpStatus.BAD_REQUEST, "Malformed JSON",
                "Unable to parse request body", req, null);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleSql(DataIntegrityViolationException ex,
                                                                HttpServletRequest req) {
        return build(HttpStatus.CONFLICT, "Data Integrity Violation",
                "Operation violates database constraints", req, null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<ErrorResponse>> handleOther(Exception ex,
                                                                  HttpServletRequest req) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error",
                "Unexpected error occurred", req, null);
    }

    private ResponseEntity<ApiResponse<ErrorResponse>> build(HttpStatus status,
                                                             String error,
                                                             String message,
                                                             HttpServletRequest req,
                                                             Map<String, Object> details) {
        ErrorResponse payload = new ErrorResponse(
                req.getRequestURI(),
                status.value(),
                error,
                message,
                Instant.now(),
                details
        );
        return ResponseEntity.status(status).body(with(ApiResponse.fail(message), payload));
    }
}

// small trick to keep ApiResponse<T> ergonomic without builders
// add this helper as a nested static class or in ApiResponse if you prefer

