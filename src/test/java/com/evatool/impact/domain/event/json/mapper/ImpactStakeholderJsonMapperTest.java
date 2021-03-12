package com.evatool.impact.domain.event.json.mapper;

import com.evatool.impact.common.exception.EventPayloadInvalidException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.evatool.impact.common.TestDataGenerator.createDummyStakeholder;
import static com.evatool.impact.domain.event.json.mapper.ImpactStakeholderJsonMapper.fromJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ImpactStakeholderJsonMapperTest {

    @Test
    void testFromJsonString_ImpactStakeholderFromJson_EqualsImpactStakeholder() {
        // given
        var stakeholder = createDummyStakeholder();
        var json = String.format("{\"id\":\"%s\",\"name\":\"%s\"}", stakeholder.getId(), stakeholder.getName());

        // when
        var eventStakeholder = fromJson(json);

        // then
        assertThat(eventStakeholder).isEqualTo(stakeholder);
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
        var name = "name";

        // when
        var json = String.format("{\"id\"\"%s\",\"name\":\"%s\"}", id, name);

        // then
        assertThatExceptionOfType(EventPayloadInvalidException.class).isThrownBy(() -> fromJson(json));
    }

    @Test
    void testFromJsonString_JsonHasNotRequiredFields_EqualsImpactStakeholder() {
        // given
        var stakeholder = createDummyStakeholder();
        var json = String.format("{\"id\":\"%s\",\"name\":\"%s\",\"not required\":\"useless\"}", stakeholder.getId(), stakeholder.getName());

        // when
        var eventStakeholder = fromJson(json);

        // then
        assertThat(eventStakeholder).isEqualTo(stakeholder);
    }

    @Test
    void testFromJsonString_JsonStringMissingIdField_ThrowEventPayloadInvalidException() {
        // given
        var name = "name";

        // when
        var json = String.format("{\"name\":\"%s\"}", name);

        // then
        assertThatExceptionOfType(EventPayloadInvalidException.class).isThrownBy(() -> fromJson(json));
    }

    @Test
    void testFromJsonString_JsonStringMissingNameField_ThrowEventPayloadInvalidException() {
        // given
        var id = UUID.randomUUID().toString();

        // when
        var json = String.format("{\"id\":\"%s\"}", id);

        // then
        assertThatExceptionOfType(EventPayloadInvalidException.class).isThrownBy(() -> fromJson(json));
    }

    @Test
    void testFromJsonString_JsonStringNullIdField_ThrowEventPayloadInvalidException() {
        // given
        var name = "name";

        // when
        var json = String.format("{\"id\":null,\"name\":\"%s\"}", name);

        // then
        assertThatExceptionOfType(EventPayloadInvalidException.class).isThrownBy(() -> fromJson(json));
    }

    @Test
    void testFromJsonString_JsonStringNullNameField_ThrowEventPayloadInvalidException() {
        // given
        var id = UUID.randomUUID().toString();

        // when
        var json = String.format("{\"id\":\"%s\",\"name\":null}", id);

        // then
        assertThatExceptionOfType(EventPayloadInvalidException.class).isThrownBy(() -> fromJson(json));
    }
}
