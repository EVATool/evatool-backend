package com.evatool.impact.application.dto.mapper;

import org.junit.jupiter.api.Test;

import static com.evatool.impact.application.dto.mapper.ImpactAnalysisDtoMapper.fromDto;
import static com.evatool.impact.application.dto.mapper.ImpactAnalysisDtoMapper.toDto;
import static com.evatool.impact.common.TestDataGenerator.createDummyAnalysis;
import static com.evatool.impact.common.TestDataGenerator.createDummyAnalysisDto;
import static org.assertj.core.api.Assertions.assertThat;

class ImpactAnalysisDtoMapperTest {

    @Test
    void testToDto_RecreatedAnalysis_EqualsAnalysis() {
        // given
        var analysis = createDummyAnalysis();

        // when
        var stakeholderDto = toDto(analysis);
        var recreatedStakeholder = fromDto(stakeholderDto);

        // then
        assertThat(analysis).isEqualTo(recreatedStakeholder);
    }

    @Test
    void testFromDto_RecreatedAnalysisDto_EqualsAnalysisDto() {
        // given
        var analysisDto = createDummyAnalysisDto();

        // when
        var analysis = fromDto(analysisDto);
        var recreatedStakeholderDto = toDto(analysis);

        // then
        assertThat(analysisDto).isEqualTo(recreatedStakeholderDto);
    }
}
