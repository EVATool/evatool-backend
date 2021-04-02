package com.evatool.analysis.model.entity;

import org.junit.jupiter.api.Test;

import static com.evatool.analysis.common.TestDataGenerator.getAnalysis;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class AnalysisTest {

    @Test
    void testSetAnalysisName() {

        // given
        var analysis = getAnalysis();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> analysis.setAnalysisName(null));
    }

    @Test
    void testSetAnalysisDescription() {

        // given
        var analysis = getAnalysis();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> analysis.setDescription(null));
    }
}
