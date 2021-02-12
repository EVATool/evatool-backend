package com.evatool.impact.domain.entity;

import com.evatool.impact.common.exception.PropertyViolationException;
import org.junit.jupiter.api.Test;

import static com.evatool.impact.common.TestDataGenerator.createDummyDimension;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class DimensionTest {

    @Test
    void testSetName_NullValue_ThrowPropertyViolationException() {
        // given
        var dimension = createDummyDimension();

        // when

        // then
        assertThatExceptionOfType(PropertyViolationException.class).isThrownBy(() -> dimension.setName(null));
    }

    @Test
    void testSetDescription_NullValue_ThrowException() {
        // given
        var dimension = createDummyDimension();

        // when

        // then
        assertThatExceptionOfType(PropertyViolationException.class).isThrownBy(() -> dimension.setDescription(null));
    }
}
