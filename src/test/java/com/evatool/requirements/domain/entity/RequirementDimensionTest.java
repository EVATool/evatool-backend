package com.evatool.requirements.domain.entity;

import com.evatool.requirements.entity.RequirementDimension;
import org.junit.jupiter.api.Test;

import static com.evatool.requirements.common.TestDataGenerator.getRequirementDimension;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class RequirementDimensionTest {

    @Test
    void testSetTitle_NullValue_ThrowException() {
        // given
        RequirementDimension requirementDimension = getRequirementDimension();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> requirementDimension.setName(null));
    }
}
