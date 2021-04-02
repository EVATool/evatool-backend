package com.evatool.impact.domain.entity;

import org.junit.jupiter.api.Test;

import static com.evatool.impact.common.TestDataGenerator.createDummyAnalysis;
import static com.evatool.impact.common.TestDataGenerator.createDummyValue;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ImpactValueTest {

    @Test
    void testConstructor_NullId_ThrowIllegalArgumentException() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new ImpactValue(null, "name", "SOCIAL", "desc", createDummyAnalysis()));
    }

    @Test
    void testSetId_NullValue_ThrowIllegalArgumentException() {
        // given
        var value = createDummyValue();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> value.setId(null));
    }

    @Test
    void testSetName_NullValue_ThrowIllegalArgumentException() {
        // given
        var value = createDummyValue();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> value.setName(null));
    }

    @Test
    void testSetTypeString_NullValue_ThrowIllegalArgumentException() {
        // given
        var value = createDummyValue();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> value.setType(null));
    }

    @Test
    void testSetDescription_NullValue_ThrowIllegalArgumentException() {
        // given
        var value = createDummyValue();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> value.setDescription(null));
    }

    @Test
    void testSetAnalysis_ExistingAnalysis_ThrowIllegalArgumentException() {
        // given
        var value = createDummyValue();
        var analysis = createDummyAnalysis();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> value.setAnalysis(analysis));
    }
}
