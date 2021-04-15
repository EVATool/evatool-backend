package com.evatool.impact.domain.entity;

import org.junit.jupiter.api.Test;

import static com.evatool.impact.common.TestDataGenerator.createDummyRequirement;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ImpactRequirementTest {

    @Test
    void testConstructor_NullId_ThrowIllegalArgumentException() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new ImpactRequirement(null, "title", "desc"));
    }

    @Test
    void testSetId_NullValue_ThrowIllegalArgumentException() {
        // given
        var requirement = createDummyRequirement();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> requirement.setId(null));
    }

    @Test
    void testSetTitle_NullValue_ThrowIllegalArgumentException() {
        // given
        var requirement = createDummyRequirement();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> requirement.setTitle(null));
    }

    @Test
    void testSetDescription_NullValue_ThrowIllegalArgumentException() {
        // given
        var requirement = createDummyRequirement();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> requirement.setDescription(null));
    }
}
