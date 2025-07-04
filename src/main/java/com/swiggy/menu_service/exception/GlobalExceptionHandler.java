package com.swiggy.menu_service.exception;

import com.swiggy.menu_service.enums.ValidationError;
import jakarta.validation.ConstraintViolationException;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handle malformed JSON requests
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        List<String> details = new ArrayList<>();
        details.add("Request body is not readable: " + ex.getMessage());

        ApiError apiError = buildApiErrorFromValidationError(
                ValidationError.REQUEST_BODY_NOT_READABLE, details);

        log.error("Message not readable: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles argument type mismatch exceptions.
     * Handle validation errors from @Valid annotations
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        ApiError apiError = buildApiErrorFromValidationError(
                ValidationError.INVALID_FIELD_FORMAT, details);

        log.error("Validation Error: {}. Details: {}", ex.getMessage(), details);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles validation exceptions from Bean Validation
     * Handle constraint violations (e.g., from @NotNull at method parameters)
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> details = ex.getConstraintViolations()
                .stream()
                .map(violation -> violation.getPropertyPath() + ": " + violation.getMessage())
                .collect(Collectors.toList());

        ApiError apiError = buildApiErrorFromValidationError(
                ValidationError.INVALID_FIELD_FORMAT, details);

        log.error("Constraint violation: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle custom business exceptions and request exceptions like -
     * New Password and Confirmation do not match,
     * or new password is same as old password
     */
    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApiError> handleApplicationException(ApplicationException ex) {
        ApiError apiError = buildApiErrorFromValidationError(
                ex.getValidationError(), ex.getDetails());

        log.error("Application exception: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(apiError, HttpStatus.valueOf(ex.getValidationError().errorStatus));
    }

    /**
     * Fallback handler for all other exceptions
     * Handles all other unexpected exceptions
     * */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add("Unexpected error: " + ex.getMessage());

        ApiError apiError = buildApiErrorFromValidationError(
                ValidationError.INTERNAL_SERVER_ERROR, details);

        log.error("Unexpected error occurred: {}", ex.getMessage(), ex);
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // ========== Helper methods ==========

    /**
     * Helper method to build ApiError from ValidationErrors enum
     */
    private ApiError buildApiErrorFromValidationError(ValidationError error, List<String> details) {
        return ApiError.builder()
                .errorStatus(error.errorStatus)
                .errorCode(error.errorCode)
                .errorMessage(error.errorMessage)
                .errorType(error.errorType)
                .details(details)
                .build();
    }


}
