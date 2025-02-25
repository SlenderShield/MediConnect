package com.hungrycoders.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.hungrycoders.exception.InvalidDoctorStatusException;
import jakarta.validation.ValidationException;

public enum DoctorStatus {
    AVAILABLE,
    NOT_AVAILABLE,
    DISABLED;
    @JsonCreator
    public static DoctorStatus fromValue(String value) {
        if(value == null || value.isBlank()) {
            throw new InvalidDoctorStatusException("Status field is required");
        }
        for (DoctorStatus status : DoctorStatus.values()) {
            if (status.name().equalsIgnoreCase(value)) {
                return status;
            }
        }
        // Throw a custom exception for invalid values
        throw new InvalidDoctorStatusException("Invalid doctor status value " + value);
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}
