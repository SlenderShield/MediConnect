package com.hungrycoders.payload.request;

import com.hungrycoders.model.AppointmentStatus;
import com.hungrycoders.utils.ValidEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Represents a request payload for creating or updating an appointment.
 * Includes necessary fields like doctor ID, patient ID, appointment time, and status.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppointmentRequest implements Serializable {

    /**
     * Unique identifier for the appointment (optional for creation).
     */
    private String id;

    /**
     * Unique identifier for the doctor associated with the appointment.
     */
    @NotNull(message = "Doctor ID must not be null")
    private UUID doctorId;

    /**
     * Unique identifier for the patient associated with the appointment.
     */
    @NotNull(message = "Patient ID must not be null")
    private UUID patientId;

    /**
     * The scheduled time for the appointment.
     */
    @NotNull(message = "Appointment time must not be null")
    private LocalDateTime appointmentTime;

    /**
     * Status of the appointment (e.g., PENDING, CONFIRMED, REJECTED).
     * Must match a valid value from {@link AppointmentStatus}.
     */
    @NotNull(message = "Status must be provided")
    @ValidEnum(message = "Status must be valid", enumClass = AppointmentStatus.class)
    private String status;

    /**
     * Notes associated with the appointment (max 200 characters).
     */
    @NotBlank(message = "Notes must not be blank")
    @Size(max = 200, message = "Notes must be 200 characters or less")
    private String notes;

    /**
     * Comments provided by the doctor (optional, max 200 characters).
     */
    @Size(max = 200, message = "Doctor comments must be 200 characters or less")
    private String doctorComments;
}
