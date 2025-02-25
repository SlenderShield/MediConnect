package com.hungrycoders.utils;

import com.hungrycoders.exception.InvalidDoctorStatusException;
import com.hungrycoders.payload.response.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GenericResponse<List<String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
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
        return new ResponseEntity<>(new GenericResponse<>("Validation failed", errors), HttpStatus.BAD_REQUEST);
    }

    // Handle invalid enum value during deserialization
    @ExceptionHandler(InvalidDoctorStatusException.class)
    public ResponseEntity<GenericResponse<String>> handleInvalidDoctorStatusException(InvalidDoctorStatusException ex) {
        return new ResponseEntity<>(new GenericResponse<>("Validation failed: " + ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
