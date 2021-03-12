package com.evatool.impact.domain.entity;

import org.junit.jupiter.api.Test;

import static com.evatool.impact.common.TestDataGenerator.createDummyStakeholder;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ImpactStakeholderTest {

    @Test
    void testConstructor_NullId_ThrowIllegalArgumentException() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new ImpactStakeholder(null, "name"));
    }

    @Test
    void testSetId_NullValue_ThrowIllegalArgumentException() {
        // given
        var stakeholder = createDummyStakeholder();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> stakeholder.setId(null));
    }

    @Test
    void testSetName_NullValue_ThrowIllegalArgumentException() {
        // given
        var stakeholder = createDummyStakeholder();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> stakeholder.setName(null));
    }
}
