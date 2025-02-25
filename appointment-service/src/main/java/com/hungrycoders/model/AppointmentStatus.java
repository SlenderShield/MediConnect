package com.hungrycoders.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.hungrycoders.exception.InvalidAppointmentStatusException;

/**
 * Enum to represent the various statuses an appointment can have.
 */
public enum AppointmentStatus {
    PENDING,
    CONFIRMED,
    REJECTED,
    COMPLETED;

    /**
     * Converts a string value to an AppointmentStatus enum.
     *
     * @param value the string representation of the status.
     * @return the corresponding AppointmentStatus.
     * @throws InvalidAppointmentStatusException if the value is invalid.
     */
    @JsonCreator
    public static AppointmentStatus fromValue(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidAppointmentStatusException("Status field is required");
        }
        for (AppointmentStatus status : AppointmentStatus.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new InvalidAppointmentStatusException("Invalid appointment status value: " + value);
    }

    /**
     * Converts the AppointmentStatus enum to its string representation.
     *
     * @return the string value of the status.
     */
    @JsonValue
    public String toValue() {
        return this.name();
    }
}