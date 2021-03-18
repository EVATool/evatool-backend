package com.evatool.requirements.domain.entity;

import com.evatool.requirements.entity.RequirementsVariant;
import org.junit.jupiter.api.Test;

import static com.evatool.requirements.common.TestDataGenerator.getRequirementsVariant;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class RequirementsVariantTest {
    @Test
    void testSetDescription_NullValue_ThrowException() {
        // given
        RequirementsVariant requirementsVariant = getRequirementsVariant();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> requirementsVariant.setDescription(null));
    }

    @Test
    void testSetTitle_NullValue_ThrowException() {
        // given
        RequirementsVariant requirementsVariant = getRequirementsVariant();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> requirementsVariant.setTitle(null));
    }
}
