package com.hungrycoders.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents an appointment entity with details about doctor, patient, and status.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "appointments")
public class Appointment {

    @Id
    @Field(targetType = FieldType.STRING)
    private UUID id;

    private Patient patient;
    private Doctor doctor;
    private LocalDateTime appointmentTime;
    private AppointmentStatus status; // PENDING, CONFIRMED, REJECTED, COMPLETED
    private String notes;
    private String doctorComments;

}