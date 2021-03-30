package com.evatool.impact.domain.event.json.mapper;

import com.evatool.impact.common.exception.EventPayloadInvalidException;
import org.junit.jupiter.api.Test;

import static com.evatool.impact.common.TestDataGenerator.createDummyValue;
import static com.evatool.impact.domain.event.json.mapper.ImpactValueJsonMapper.fromJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ImpactValueJsonMapperTest {

    @Test
    void testFromJsonString_ImpactValueFromJson_EqualsImpactValue() {
        // given
        var value = createDummyValue();
        var json = String.format("{\"id\":\"%s\",\"name\":\"%s\",\"type\":\"%s\",\"description\":\"%s\"}", value.getId(), value.getName(), value.getType(), value.getDescription());

        // when
        var eventValue = fromJson(json);

        // then
        assertThat(eventValue).isEqualTo(value);
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
        var value = createDummyValue();

        // when
        var json = String.format("{\"id\"\"%s\",\"name\":\"%s\",\"type\":\"%s\",\"description\":\"%s\"}", value.getId(), value.getName(), value.getType(), value.getDescription());

        // then
        assertThatExceptionOfType(EventPayloadInvalidException.class).isThrownBy(() -> fromJson(json));
    }

    @Test
    void testFromJsonString_JsonHasNotRequiredFields_EqualsImpactValue() {
        // given
        var value = createDummyValue();
        var json = String.format("{\"id\":\"%s\",\"name\":\"%s\",\"type\":\"%s\",\"description\":\"%s\",\"not required\":\"useless\"}", value.getId(), value.getName(), value.getType(), value.getDescription());

        // when
        var eventValue = fromJson(json);

        // then
        assertThat(eventValue).isEqualTo(value);
    }

    @Test
    void testFromJsonString_JsonStringMissingIdField_ThrowEventPayloadInvalidException() {
        // given
        var value = createDummyValue();

        // when
        var json = String.format("{\"name\":\"%s\",\"type\":\"%s\",\"description\":\"%s\"}", value.getName(), value.getType(), value.getDescription());

        // then
        assertThatExceptionOfType(EventPayloadInvalidException.class).isThrownBy(() -> fromJson(json));
    }

    @Test
    void testFromJsonString_JsonStringMissingNameField_ThrowEventPayloadInvalidException() {
        // given
        var value = createDummyValue();

        // when
        var json = String.format("{\"id\":\"%s\",\"type\":\"%s\",\"description\":\"%s\"}", value.getId(), value.getType(), value.getDescription());

        // then
        assertThatExceptionOfType(EventPayloadInvalidException.class).isThrownBy(() -> fromJson(json));
    }

    @Test
    void testFromJsonString_JsonStringNullIdField_ThrowEventPayloadInvalidException() {
        // given
        var value = createDummyValue();

        // when
        var json = String.format("{\"id\":null,\"name\":\"%s\",\"type\":\"%s\",\"description\":\"%s\"}", value.getName(), value.getType(), value.getDescription());

        // then
        assertThatExceptionOfType(EventPayloadInvalidException.class).isThrownBy(() -> fromJson(json));
    }

    @Test
    void testFromJsonString_JsonStringNullNameField_ThrowEventPayloadInvalidException() {
        // given
        var value = createDummyValue();

        // when
        var json = String.format("{\"id\":\"%s\",\"name\":null,\"type\":\"%s\",\"description\":\"%s\"}", value.getId(), value.getType(), value.getDescription());

        // then
        assertThatExceptionOfType(EventPayloadInvalidException.class).isThrownBy(() -> fromJson(json));
    }

    // TODO add missing json and null json tests for tow missing fields
    // TODO ForeignEntity base class
}
