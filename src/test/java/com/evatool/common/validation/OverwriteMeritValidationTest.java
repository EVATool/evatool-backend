package com.evatool.common.validation;

import org.junit.jupiter.api.Test;

import static com.evatool.common.validation.OverwriteMeritValidation.validateOverwriteMerit;
import static org.assertj.core.api.Assertions.assertThat;

public class OverwriteMeritValidationTest {

    @Test
    void testSetDelta_DecreaseNegativeImpact_Throw() {
        // given
        var originalMerit = -0.5f;
        var overwriteMerit = -0.6f;

        // when
        var error = validateOverwriteMerit(overwriteMerit, originalMerit);

        // then
        assertThat(error).isNotNull();
    }

    @Test
    void testSetDelta_IncreaseNegativeImpactAboveZero_Throw() {
        // given
        var originalMerit = -0.5f;
        var overwriteMerit = 0.7f;

        // when
        var error = validateOverwriteMerit(overwriteMerit, originalMerit);

        // then
        assertThat(error).isNotNull();
    }

    @Test
    void testSetDelta_IncreasePositiveImpactAboveOriginal_Throw() {
        // given
        var originalMerit = 0.5f;
        var overwriteMerit = 0.6f;

        // when
        var error = validateOverwriteMerit(overwriteMerit, originalMerit);

        // then
        assertThat(error).isNotNull();
    }

    @Test
    void testSetDelta_DecreasePositiveImpactBelowZero_Throw() {
        // given
        var originalMerit = 0.5f;
        var overwriteMerit = -0.6f;

        // when
        var error = validateOverwriteMerit(overwriteMerit, originalMerit);

        // then
        assertThat(error).isNotNull();
    }
}
