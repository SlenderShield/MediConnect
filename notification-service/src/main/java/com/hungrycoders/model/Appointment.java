package com.hungrycoders.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.kafka.common.serialization.Deserializer;

import java.time.LocalDateTime;

/**
 * Represents an appointment entity with details about doctor, patient, and status.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Appointment {

    private String id;
    private Patient patient;
    private Doctor doctor;
    private LocalDateTime appointmentTime;
    private AppointmentStatus status; // PENDING, CONFIRMED, REJECTED, COMPLETED
    private String notes;
    private String doctorComments;
}