package com.evatool.application.validator;

import com.evatool.common.exception.functional.http400.EmailInvalidException;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

import static com.evatool.common.validation.EmailValidation.validateEmail;

@Documented
@Constraint(validatedBy = Email.EmailValidator.class)
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Email {
    String message() default "Invalid Email";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class EmailValidator implements ConstraintValidator<Email, String> {

        @Override
        public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
            var error = validateEmail(email);
            if (error != null) {
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate(error).addConstraintViolation();
               throw new EmailInvalidException(error, email);
            }

            return true;
        }
    }
}
