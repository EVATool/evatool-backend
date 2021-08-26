package com.evatool.application.validator;

import com.evatool.application.dto.RequirementDeltaDto;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

import static com.evatool.common.validation.OverwriteMeritValidation.validateOverwriteMerit;

@Documented
@Constraint(validatedBy = OverwriteMeritConstraint.OverwriteMeritValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface OverwriteMeritConstraint {
    String message() default "Invalid Overwrite Merit";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class OverwriteMeritValidator implements ConstraintValidator<OverwriteMeritConstraint, RequirementDeltaDto> {

        @Override
        public boolean isValid(RequirementDeltaDto requirementDeltaDto, ConstraintValidatorContext constraintValidatorContext) {
            if (requirementDeltaDto.getOriginalMerit() == null || requirementDeltaDto.getOverwriteMerit() == null) {
                return true;
            }

            var error = validateOverwriteMerit(requirementDeltaDto.getOverwriteMerit(), requirementDeltaDto.getOriginalMerit());
            if (error != null) {
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate(error).addConstraintViolation();
                return false;
            }

            return true;
        }
    }
}
