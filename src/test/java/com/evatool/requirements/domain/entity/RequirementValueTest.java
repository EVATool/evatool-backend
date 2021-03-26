package com.evatool.requirements.domain.entity;

import org.junit.jupiter.api.Test;

import static com.evatool.requirements.common.TestDataGenerator.getRequirementDimension;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class RequirementValueTest {

    @Test
    void testSetTitle_NullValue_ThrowException() {
        // given
        RequirementValue requirementValue = getRequirementDimension();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> requirementValue.setName(null));
    }
}
