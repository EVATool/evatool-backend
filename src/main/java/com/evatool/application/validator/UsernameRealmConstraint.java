package com.evatool.application.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import static com.evatool.common.validation.UsernameRealmValidation.validateUsernameOrRealm;

public @interface UsernameRealmConstraint {
    String message() default "Invalid Value";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class UsernameRealmValidator implements ConstraintValidator<UsernameRealmConstraint, String> {

        @Override
        public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
            var error = validateUsernameOrRealm(value);
            if (error != null) {
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate(error).addConstraintViolation();
                return false;
            }

            return true;
        }
    }
}
