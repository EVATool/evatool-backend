package com.evatool.analysis.application.dto.mapper;

import com.evatool.analysis.application.dto.AnalysisDTO;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class AnalysisDTOTest {

    @Test
    void testSetRootEntityId_NullValue_ThrowException() {
        // given
        var analysisDTO = new AnalysisDTO();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> analysisDTO.setRootEntityID(null));
    }

    @Test
    void testSetAnalysisName_NullValue_ThrowException() {
        // given
        var analysisDTO = new AnalysisDTO();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> analysisDTO.setAnalysisName(null));
    }

    @Test
    void testSetAnalysisDescription_NullValue_ThrowException() {
        // given
        var analysisDTO = new AnalysisDTO();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> analysisDTO.setAnalysisDescription(null));
    }

    @Test
    void testSetAnalysisImage_NullValue_ThrowException() {
        // given
        var analysisDTO = new AnalysisDTO();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> analysisDTO.setImage(null));
    }

    @Test
    void testSetAnalysisLastUpdate_NullValue_ThrowException() {
        // given
        var analysisDTO = new AnalysisDTO();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> analysisDTO.setLastUpdate(null));

    }

    @Test
    void testSetAnalysisIsTemplate_NullValue_ThrowException() {
        // given
        var analysisDTO = new AnalysisDTO();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> analysisDTO.setTemplate(null));

    }

    @Test
    void testSetAnalysisUniqueString_NullValue_ThrowException() {
        // given
        var analysisDTO = new AnalysisDTO();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> analysisDTO.setUniqueString(null));

    }
}
