package com.evatool.application.validator;

import com.evatool.common.exception.functional.http400.UsernameInvalidException;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

import static com.evatool.common.validation.UsernameRealmValidation.validateUsernameOrRealm;

@Documented
@Constraint(validatedBy = UsernameConstraint.UsernameValidator.class)
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameConstraint {
    String message() default "Invalid Username";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class UsernameValidator implements ConstraintValidator<UsernameConstraint, String> {

        @Override
        public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
            var error = validateUsernameOrRealm(username);
            if (error != null) {
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate(error).addConstraintViolation();
                throw new UsernameInvalidException(error, username);
            }

            return true;
        }
    }
}
