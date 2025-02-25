package com.hungrycoders.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hungrycoders.model.DoctorStatus;
import com.hungrycoders.utils.ValidEnum;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class Doctor implements Serializable {

    @NotBlank
    @Size(max = 15, message = "must be 15 characters or less")
    private String firstName;

    @NotBlank
    @Size(max = 15, message = "must be 15 characters or less")
    private String lastName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String phone;

    @NotBlank
    @Size(max = 200, message = "must be 500 chars or less")
    private String speciality;

    @NotNull(message = "must be provided")
    @Min(value = 0L, message = "must be positive")
    private Integer yearsOfExperience;

    @NotNull(message = "must be provided")
    @ValidEnum(message = "must be valid", enumClass = DoctorStatus.class)
    private String status;
}
