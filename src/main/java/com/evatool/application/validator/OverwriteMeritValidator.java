package com.evatool.application.validator;

import com.evatool.application.dto.RequirementDeltaDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.evatool.common.validation.OverwriteMeritValidation.validateOverwriteMerit;

public class OverwriteMeritValidator implements ConstraintValidator<OverwriteMeritConstraint, RequirementDeltaDto> {

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
