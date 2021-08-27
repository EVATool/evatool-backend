package com.evatool.application.validator;

import com.evatool.common.exception.functional.http400.RealmInvalidException;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

import static com.evatool.common.validation.UsernameRealmValidation.validateUsernameOrRealm;

@Documented
@Constraint(validatedBy = Realm.RealmValidator.class)
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Realm {
    String message() default "Invalid Realm";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class RealmValidator implements ConstraintValidator<Realm, String> {

        @Override
        public boolean isValid(String realm, ConstraintValidatorContext constraintValidatorContext) {
            var error = validateUsernameOrRealm(realm);
            if (error != null) {
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate(error).addConstraintViolation();
                throw new RealmInvalidException(error, realm);
            }

            return true;
        }
    }
}
