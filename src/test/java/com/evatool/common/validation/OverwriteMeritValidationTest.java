package com.evatool.common.validation;

import org.junit.jupiter.api.Test;

import static com.evatool.common.validation.OverwriteMeritValidation.validateOverwriteMerit;
import static org.assertj.core.api.Assertions.assertThat;

class OverwriteMeritValidationTest {

    @Test
    void testValidateOverwriteMerit_OriginalEqualsOverwriteMerit_OK() {
        // given
        var originalMerit = 0.5f;
        var overwriteMerit = 0.5f;

        // when
        var error = validateOverwriteMerit(overwriteMerit, originalMerit);

        // then
        assertThat(error).isNull();
    }

    @Test
    void testValidateOverwriteMerit_FulfillPositiveImpact_OK() {
        // given
        var originalMerit = 0.5f;
        var overwriteMerit = 0.3f;

        // when
        var error = validateOverwriteMerit(overwriteMerit, originalMerit);

        // then
        assertThat(error).isNull();
    }

    @Test
    void testValidateOverwriteMerit_AmendNegativeImpact_OK() {
        // given
        var originalMerit = -0.5f;
        var overwriteMerit = -0.3f;

        // when
        var error = validateOverwriteMerit(overwriteMerit, originalMerit);

        // then
        assertThat(error).isNull();
    }

    @Test
    void testValidateOverwriteMerit_DecreaseNegativeImpact_Error() {
        // given
        var originalMerit = -0.5f;
        var overwriteMerit = -0.6f;

        // when
        var error = validateOverwriteMerit(overwriteMerit, originalMerit);

        // then
        assertThat(error).isNotNull();
    }

    @Test
    void testValidateOverwriteMerit_IncreaseNegativeImpactAboveZero_Error() {
        // given
        var originalMerit = -0.5f;
        var overwriteMerit = 0.7f;

        // when
        var error = validateOverwriteMerit(overwriteMerit, originalMerit);

        // then
        assertThat(error).isNotNull();
    }

    @Test
    void testValidateOverwriteMerit_IncreasePositiveImpactAboveOriginal_Error() {
        // given
        var originalMerit = 0.5f;
        var overwriteMerit = 0.6f;

        // when
        var error = validateOverwriteMerit(overwriteMerit, originalMerit);

        // then
        assertThat(error).isNotNull();
    }

    @Test
    void testValidateOverwriteMerit_DecreasePositiveImpactBelowZero_Error() {
        // given
        var originalMerit = 0.5f;
        var overwriteMerit = -0.6f;

        // when
        var error = validateOverwriteMerit(overwriteMerit, originalMerit);

        // then
        assertThat(error).isNotNull();
    }
}
