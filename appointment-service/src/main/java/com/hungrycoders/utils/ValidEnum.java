package com.hungrycoders.utils;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation for validating if a value is part of a specified enum.
 */
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidator.class)  // Specify the validator class
public @interface ValidEnum {

    /**
     * Default error message when the value is invalid.
     *
     * @return the error message.
     */
    String message() default "Invalid value for enum";

    /**
     * Used to specify validation groups.
     *
     * @return the groups.
     */
    Class<?>[] groups() default {};

    /**
     * Used to specify custom payload objects.
     *
     * @return the payload.
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * The enum class that the annotation validates against.
     *
     * @return the enum class.
     */
    Class<? extends Enum<?>> enumClass();
}