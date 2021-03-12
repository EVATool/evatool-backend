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
        var json = String.format("{\"id\":\"%s\"}", analysis.getId());

        // when
        var eventAnalysis = fromJson(json);

        // then
        assertThat(eventAnalysis).isEqualTo(analysis);
    }

    @Test
    void testFromJsonString_JsonStringEmpty_ThrowEventPayloadInvalidException() {
        // given

        // when

        // then
        assertThatExceptionOfType(EventPayloadInvalidException.class).isThrownBy(() -> fromJson(""));
    }

    @Test
    void testFromJsonString_JsonStringNull_ThrowEventPayloadInvalidException() {
        // given

        // when

        // then
        assertThatExceptionOfType(EventPayloadInvalidException.class).isThrownBy(() -> fromJson(null));
    }

    @Test
    void testFromJsonString_JsonStringMissingColon_ThrowEventPayloadInvalidException() {
        // given
        var id = UUID.randomUUID().toString();

        // when
        var json = String.format("{\"id\"\"%s\"}", id);

        // then
        assertThatExceptionOfType(EventPayloadInvalidException.class).isThrownBy(() -> fromJson(json));
    }

    @Test
    void testFromJsonString_JsonHasNotRequiredFields_EqualsImpactAnalysis() {
        // given
        var analysis = createDummyAnalysis();
        var json = String.format("{\"id\":\"%s\",\"not required\":\"useless\"}", analysis.getId());

        // when
        var eventAnalysis = fromJson(json);

        // then
        assertThat(eventAnalysis).isEqualTo(analysis);
    }

    @Test
    void testFromJsonString_JsonStringMissingIdField_ThrowEventPayloadInvalidException() {
        // given

        // when
        var json = "{}";

        // then
        assertThatExceptionOfType(EventPayloadInvalidException.class).isThrownBy(() -> fromJson(json));
    }

    @Test
    void testFromJsonString_JsonStringNullIdField_ThrowEventPayloadInvalidException() {
        // given

        // when
        var json = "{\"id\":null}";

        // then
        assertThatExceptionOfType(EventPayloadInvalidException.class).isThrownBy(() -> fromJson(json));
    }
}
