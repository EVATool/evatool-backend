package com.evatool.requirements.domain.entity;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static com.evatool.requirements.common.TestDataGenerator.*;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class RequirementTest {
    @Test
    void testSetDescription_NullValue_ThrowException() {
        // given
        RequirementsAnalysis requirementsAnalysis = getRequirementsAnalysis();
        Collection<RequirementsVariant> requirementsVariant = getRequirementsVariants();
        Requirement requirement = getRequirement(requirementsAnalysis,requirementsVariant);

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> requirement.setDescription(null));
    }

    @Test
    void testSetTitle_NullValue_ThrowException() {
        // given
        RequirementsAnalysis requirementsAnalysis = getRequirementsAnalysis();
        Collection<RequirementsVariant> requirementsVariant = getRequirementsVariants();
        Requirement requirement = getRequirement(requirementsAnalysis,requirementsVariant);

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> requirement.setTitle(null));
    }

    @Test
    void testSetVariants_NullValue_ThrowException() {
        // given
        RequirementsAnalysis requirementsAnalysis = getRequirementsAnalysis();
        Collection<RequirementsVariant> requirementsVariant = getRequirementsVariants();
        Requirement requirement = getRequirement(requirementsAnalysis,requirementsVariant);

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> requirement.setVariants(null));
    }



    @Test
    void testSetRequirementsAnalysis_NullValue_ThrowException() {
        // given
        RequirementsAnalysis requirementsAnalysis = getRequirementsAnalysis();
        Collection<RequirementsVariant> requirementsVariant = getRequirementsVariants();
        Requirement requirement = getRequirement(requirementsAnalysis,requirementsVariant);

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> requirement.setRequirementsAnalysis(null));
    }
}
