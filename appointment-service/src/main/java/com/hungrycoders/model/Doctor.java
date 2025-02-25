package com.hungrycoders.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a doctor entity with personal and professional details.
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Doctor {

    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String speciality;
    private Integer yearsOfExperience;
    private String status; // ACTIVE, INACTIVE
}

