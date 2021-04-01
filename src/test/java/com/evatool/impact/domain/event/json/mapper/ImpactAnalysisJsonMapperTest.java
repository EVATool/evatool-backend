package com.evatool.impact.domain.event.json.mapper;

import com.evatool.impact.common.exception.EventPayloadInvalidException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.evatool.impact.common.TestDataGenerator.createDummyAnalysis;
import static com.evatool.impact.domain.event.json.mapper.ImpactAnalysisJsonMapper.fromJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ImpactAnalysisJsonMapperTest {

    @Test
    void testFromJsonString_ImpactAnalysisFromJson_EqualsImpactAnalysis() {
        // given
        var analysis = createDummyAnalysis();
        var json = String.format("{\"analysisId\":\"%s\"}", analysis.getId());

        // when
        var eventAnalysis = fromJson(json);

        // then
        assertThat(eventAnalysis).isEqualTo(analysis);
    }
}
