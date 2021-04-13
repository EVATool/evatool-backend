package com.evatool.analysis.application.dto.mapper;

import com.evatool.analysis.application.dto.AnalysisDTO;
import com.evatool.analysis.application.dto.StakeholderDTO;
import com.evatool.analysis.application.dto.ValueDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ValueDTOTest {

    @Test
    void testSetId_NullValue_ThrowException() {
        // given
        var valueDto = new ValueDto();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> valueDto.setId(null));
    }

    @Test
    void testSetValueName_NullValue_ThrowException() {
        // given
        var valueDto = new ValueDto();
        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> valueDto.setName(null));
    }

    @Test
    void testSetValueType_NullValue_ThrowException() {
        // given
        var valueDto = new ValueDto();
        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> valueDto.setType(null));
    }

    @Test
    void testSetValueDescription_NullValue_ThrowException() {
        // given
        var valueDto = new ValueDto();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> valueDto.setDescription(null));

    }

    @Test
    void testSetValueAnalysisDTO_NullValue_ThrowException() {
        // given
        var valueDto = new ValueDto();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> valueDto.setAnalysis(null));

    }

    @Test
    void testSetValuesArchived_NullValue_ThrowException() {
        // given
        var valueDto = new ValueDto();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> valueDto.setArchived(null));
    }
}
