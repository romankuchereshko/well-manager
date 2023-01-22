package com.simulation.wellmanager.rest.validator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = FrameValueConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface FrameValueConstraint {

    String message() default "Wrong values in request";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
