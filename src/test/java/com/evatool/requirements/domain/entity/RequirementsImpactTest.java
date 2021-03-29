package com.evatool.requirements.domain.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.evatool.requirements.common.TestDataGenerator.getRequirementValue;
import static com.evatool.requirements.common.TestDataGenerator.getRequirementsImpacts;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class RequirementsImpactTest {

    @Test
    void testSetDescription_NullValue_ThrowException() {
        // given
        RequirementValue requirementValue = getRequirementValue();
        RequirementsImpact requirementsImpact = getRequirementsImpacts(requirementValue);

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> requirementsImpact.setDescription(null));
    }


    @ParameterizedTest
    @ValueSource(doubles = {3,2,-4})
    void testSetValue_NullValue_ThrowException(Double value) {
        // given
        RequirementValue requirementValue = getRequirementValue();
        RequirementsImpact requirementsImpact = getRequirementsImpacts(requirementValue);

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> requirementsImpact.setValue(value));
    }

    @Test
    void testSetRequirementValue_NullValue_ThrowException() {
        // given
        RequirementValue requirementValue = getRequirementValue();
        RequirementsImpact requirementsImpact = getRequirementsImpacts(requirementValue);
        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> requirementsImpact.setRequirementValue(null));
    }

}
