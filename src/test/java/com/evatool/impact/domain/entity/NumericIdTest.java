package com.evatool.impact.domain.entity;

import org.junit.jupiter.api.Test;

import static com.evatool.impact.common.TestDataGenerator.createDummyNumericId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class NumericIdTest {

    @Test
    void testCreate_CreatedNumericId_NumericIdIsNull() {
        assertThat(createDummyNumericId().getNumericId()).isNull();
    }

    @Test
    void testCreate_CreatedNumericId_ReadableIdIsNull() {
        assertThat(createDummyNumericId()._getReadableId()).isNull();
    }

    @Test
    void testSetNumericId_ExistingId_ThrowNewIllegalArgumentException() {
        // given
        var numericId = createDummyNumericId();

        // when
        numericId.setNumericId(1);

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> numericId.setNumericId(2));
    }
}
