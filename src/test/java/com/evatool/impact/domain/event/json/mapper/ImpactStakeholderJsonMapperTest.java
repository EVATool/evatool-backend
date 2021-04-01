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
        var json = String.format("{\"stakeholderId\":\"%s\",\"stakeholderName\":\"%s\"}", stakeholder.getId(), stakeholder.getName());

        // when
        var eventStakeholder = fromJson(json);

        // then
        assertThat(eventStakeholder).isEqualTo(stakeholder);
    }
}
