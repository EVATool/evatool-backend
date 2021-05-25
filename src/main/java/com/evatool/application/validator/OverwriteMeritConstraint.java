package com.evatool.application.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = OverwriteMeritValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface OverwriteMeritConstraint {
    String message() default "Invalid Overwrite Merit";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
