package com.evatool.impact.domain.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ImpactAnalysisTest {

    @Test
    void testConstructor_NullId_ThrowIllegalArgumentException() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new ImpactAnalysis(null));
    }
}
