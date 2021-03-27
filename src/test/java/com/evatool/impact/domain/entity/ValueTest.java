package com.evatool.impact.domain.entity;

import org.junit.jupiter.api.Test;

import static com.evatool.impact.common.TestDataGenerator.createDummyDimension;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ValueTest {

    @Test
    void testSetName_NullValue_ThrowIllegalArgumentException() {
        // given
        var dimension = createDummyDimension();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> dimension.setName(null));
    }

    @Test
    void testSetTypeString_NullValue_ThrowIllegalArgumentException() {
        // given
        var dimension = createDummyDimension();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> dimension.setType(null));
    }

    @Test
    void testSetDescription_NullValue_ThrowIllegalArgumentException() {
        // given
        var dimension = createDummyDimension();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> dimension.setDescription(null));
    }
}
