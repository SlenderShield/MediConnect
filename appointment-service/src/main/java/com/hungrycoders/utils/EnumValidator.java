package com.hungrycoders.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

/**
 * Custom validator to check if a string value matches any constant of a specified enum class.
 */
public class EnumValidator implements ConstraintValidator<ValidEnum, String> {

    /**
     * The enum class to validate against.
     */
    private Class<? extends Enum<?>> enumClass;

    /**
     * Initializes the validator with the enum class provided in the annotation.
     *
     * @param constraintAnnotation the annotation instance containing the enum class.
     */
    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        this.enumClass = constraintAnnotation.enumClass();
    }

    /**
     * Validates if the provided value matches any constant in the specified enum class.
     *
     * @param value   the value to validate.
     * @param context the constraint validation context.
     * @return true if the value is valid, otherwise false.
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Null or empty values are handled elsewhere (e.g., @NotNull or @NotBlank)
        if (value == null || value.isEmpty()) {
            return true;
        }

        // Check if the value matches any of the enum constants
        return Arrays.stream(enumClass.getEnumConstants())
                .anyMatch(e -> e.name().equalsIgnoreCase(value));
    }
}
