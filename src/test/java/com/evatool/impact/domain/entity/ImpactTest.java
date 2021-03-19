package com.evatool.impact.domain.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.evatool.impact.application.dto.mapper.ImpactDtoMapper.fromDto;
import static com.evatool.impact.common.TestDataGenerator.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ImpactTest {

    @ParameterizedTest
    @ValueSource(doubles = {-1.0, -0.5, 0.0, 0.5, 1.0})
    void testSetValue_LegalValue_DoNotThrowException(double value) {
        // given
        var impact = createDummyImpact();

        // when
        impact.setValue(value);

        // then
        assertThat(impact.getValue()).isEqualTo(value);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-Double.MAX_VALUE, -2.0, -1.5, -1.1, -1.000001, 1.000001, 1.1, 1.5, 2.0, Double.MAX_VALUE})
    void testSetValue_IllegalValue_ThrowIllegalArgumentException(double value) {
        // given
        var impact = createDummyImpact();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> impact.setValue(value));
    }

    @Test
    void testSetDescription_NullValue_ThrowIllegalArgumentException() {
        // given
        var impact = createDummyImpact();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> impact.setDescription(null));
    }

    @Test
    void testSetDimension_NullValue_ThrowIllegalArgumentException() {
        // given
        var impact = createDummyImpact();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> impact.setDimension(null));
    }

    @Test
    void testSetStakeholder_NullValue_ThrowIllegalArgumentException() {
        // given
        var impact = createDummyImpact();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> impact.setStakeholder(null));
    }

    @Test
    void testSetAnalysis_NullValue_ThrowIllegalArgumentException() {
        // given
        var impact = createDummyImpact();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> impact.setAnalysis(null));
    }

    @Test
    void testSetAnalysis_ExistingValue_ThrowIllegalArgumentException() {
        // given
        var impact = createDummyImpact();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> impact.setAnalysis(createDummyAnalysis()));
    }

    @Test
    void testSetNumericId_ExistingNumericId_ThrowIllegalArgumentException() {
        // given
        var impact = createDummyImpact();

        // when
        impact.setNumericId(1337);

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> impact.setNumericId(9));
    }
}
