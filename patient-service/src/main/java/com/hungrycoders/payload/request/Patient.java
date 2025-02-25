package com.hungrycoders.payload.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class Patient implements Serializable {

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

    @NotNull
    @Min(value = 0, message = "age can't be below 0")
    @Max(value = 100, message = "age can't be above 100")
    private Integer age;
}
