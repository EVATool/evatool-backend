package com.evatool.application.validator;

import com.evatool.common.exception.functional.http400.PasswordInvalidException;
import com.evatool.common.exception.functional.http400.PasswordNotSecureEnoughException;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

import static com.evatool.common.validation.PasswordValidation.validatePassword;
import static com.evatool.common.validation.PasswordValidation.validatePasswordSecurity;

@Documented
@Constraint(validatedBy = Password.PasswordValidator.class)
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {
    String message() default "Invalid Password";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    boolean skipSecurity() default false;

    class PasswordValidator implements ConstraintValidator<Password, String> {

        private boolean skipSecurity;

        @Override
        public void initialize(Password constraintAnnotation) {
            this.skipSecurity = constraintAnnotation.skipSecurity();
        }

        @Override
        public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
            var error = validatePassword(password);
            if (error != null) {
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate(error).addConstraintViolation();
                throw new PasswordInvalidException(error, password);
            }

            if (!skipSecurity) {
                error = validatePasswordSecurity(password);
                if (error != null) {
                    constraintValidatorContext.disableDefaultConstraintViolation();
                    constraintValidatorContext.buildConstraintViolationWithTemplate(error).addConstraintViolation();
                    throw new PasswordNotSecureEnoughException(error, password);
                }
            }

            return true;
        }
    }
}
