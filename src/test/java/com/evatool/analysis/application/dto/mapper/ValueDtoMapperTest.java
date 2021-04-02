package com.evatool.analysis.application.dto.mapper;

import com.evatool.analysis.application.dto.AnalysisDTO;
import com.evatool.analysis.application.dto.ValueDto;
import com.evatool.analysis.application.dto.ValueDtoMapper;
import com.evatool.analysis.domain.enums.ValueType;
import com.evatool.analysis.domain.model.Analysis;
import com.evatool.analysis.domain.model.Value;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ValueDtoMapperTest {

    @Test
    void testToDto_RecreatedValue_EqualsValue() {
        // given
        var value = new Value("name", ValueType.ECONOMIC, "desc");
        value.setId(UUID.randomUUID());
        var analysis = new Analysis("name", "desc");
        analysis.setAnalysisId(UUID.randomUUID());
        value.setAnalysis(analysis);

        // when
        var valueDto = ValueDtoMapper.toDto(value);
        var recreatedValue = ValueDtoMapper.fromDto(valueDto);

        // then
        assertThat(value).isEqualTo(recreatedValue);
    }

    @Test
    void testFromDto_RecreatedValue_EqualsValue() {
        // given
        var analysisDto = new AnalysisDTO();
        analysisDto.setRootEntityID(UUID.randomUUID());
        analysisDto.setAnalysisName("aName");
        analysisDto.setAnalysisDescription("aDesc");

        var valueDto = new ValueDto();
        valueDto.setId(UUID.randomUUID());
        valueDto.setName("vName");
        valueDto.setDescription("vDesc");
        valueDto.setType(ValueType.ECONOMIC);
        valueDto.setAnalysis(analysisDto);

        // when
        var value = ValueDtoMapper.fromDto(valueDto);
        var recreatedValueDto = ValueDtoMapper.toDto(value);

        // then
        assertThat(valueDto).isEqualTo(recreatedValueDto);
    }
}
