package com.hungrycoders.utils;

import com.hungrycoders.exception.InvalidAppointmentStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Global exception handler to manage application-wide errors and provide meaningful responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation errors for method arguments.
     *
     * @param ex the MethodArgumentNotValidException thrown during validation.
     * @return a ResponseEntity containing error details.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();

        // Extract error messages from binding result
        List<String> errors = bindingResult.getAllErrors().stream()
                .map(objectError -> {
                    if (objectError instanceof FieldError fieldError) {
                        return fieldError.getField() + ": " + fieldError.getDefaultMessage();
                    }
                    return objectError.getDefaultMessage();
                })
                .collect(Collectors.toList());

        // Return the error response
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles invalid enum values during deserialization.
     *
     * @param ex the InvalidAppointmentStatusException thrown for invalid enum values.
     * @return a ResponseEntity containing error details.
     */
    @ExceptionHandler(InvalidAppointmentStatusException.class)
    public ResponseEntity<String> handleInvalidAppointmentStatusException(InvalidAppointmentStatusException ex) {
        return new ResponseEntity<>("Validation failed: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
