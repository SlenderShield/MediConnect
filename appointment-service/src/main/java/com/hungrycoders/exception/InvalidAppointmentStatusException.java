package com.hungrycoders.exception;

/**
 * Exception thrown when an invalid appointment status is encountered.
 */
public class InvalidAppointmentStatusException extends RuntimeException {

    /**
     * Constructs a new InvalidAppointmentStatusException with the specified detail message.
     *
     * @param message the detail message.
     */
    public InvalidAppointmentStatusException(String message) {
        super(message);
    }
}


