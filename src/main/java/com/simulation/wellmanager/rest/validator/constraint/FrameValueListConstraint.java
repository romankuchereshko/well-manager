package com.simulation.wellmanager.rest.validator.constraint;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.simulation.wellmanager.rest.validator.FrameValueListConstraintValidator;

@Constraint(validatedBy = FrameValueListConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface FrameValueListConstraint {

    String message() default "Wrong values in request";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
