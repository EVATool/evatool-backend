package com.evatool.requirements.domain.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static com.evatool.requirements.common.TestDataGenerator.getRequirement;

public class RequirementTest {
    @Test
    public void testSetDescription_NullValue_ThrowException() {
        // given
        var requirement = getRequirement();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> requirement.setDescription(null));
    }

    @Test
    public void testSetTitle_NullValue_ThrowException() {
        // given
        var requirement = getRequirement();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> requirement.setTitel(null));
    }

    @Test
    public void testSetVariants_NullValue_ThrowException() {
        // given
        var requirement = getRequirement();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> requirement.setVariants(null));
    }
}
