package com.evatool.application.validator;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import java.lang.annotation.*;

import static com.evatool.common.validation.EmailValidation.validateEmail;

@Documented
@Constraint(validatedBy = EmailConstraint.EmailValidator.class)
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailConstraint {
    String message() default "Invalid Email";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class EmailValidator implements ConstraintValidator<EmailConstraint, String> {

        @Override
        public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
            var error = validateEmail(email);
            if (error != null) {
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate(error).addConstraintViolation();
                return false;
            }

            return true;
        }
    }
}
