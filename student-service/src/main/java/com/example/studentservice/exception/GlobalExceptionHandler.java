package com.example.studentservice.exception;

import com.example.studentservice.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Centralised error handling — converts exceptions to structured JSON.
 *
 * All errors use the ApiResponse envelope so callers always get:
 * { "success": false, "message": "...", "data": null }
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** 404 — student not found */
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(StudentNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error(ex.getMessage()));
    }

    /** 400 — Bean Validation failures (@Valid) */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidation(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String field   = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(field, message);
        });

        ApiResponse<Map<String, String>> body = new ApiResponse<>();
        // manually set fields — ApiResponse.error() doesn't carry data
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(buildValidationResponse(errors));
    }

    /** 409 — duplicate email or other data conflict */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiResponse.error(ex.getMessage()));
    }

    /** 500 — anything else */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGeneral(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("An unexpected error occurred: " + ex.getMessage()));
    }

    // ─── helpers ────────────────────────────────────────────────────────────

    private ApiResponse<Map<String, String>> buildValidationResponse(Map<String, String> errors) {
        ApiResponse<Map<String, String>> r = new ApiResponse<>();
        // reflection-free workaround — use a subclass to set fields
        return ApiResponse.ok("Validation failed", errors);
    }
}

