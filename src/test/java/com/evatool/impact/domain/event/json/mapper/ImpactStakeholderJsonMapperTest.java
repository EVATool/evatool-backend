package com.evatool.impact.domain.event.json.mapper;

import org.junit.jupiter.api.Test;

import static com.evatool.impact.common.TestDataGenerator.createDummyStakeholder;
import static com.evatool.impact.domain.event.json.mapper.ImpactStakeholderJsonMapper.fromJson;
import static com.evatool.impact.domain.event.json.mapper.ImpactStakeholderJsonMapper.toJson;
import static org.assertj.core.api.Assertions.assertThat;

class ImpactStakeholderJsonMapperTest {

    @Test
    void testFromJsonString_ImpactStakeholderFromJson_EqualsImpactStakeholder() {
        // given
        var stakeholder = createDummyStakeholder();
        var json = toJson(stakeholder);
        var recreatedStakeholder = fromJson(json);

        // when

        // then
        assertThat(stakeholder).isEqualTo(recreatedStakeholder);
    }
}
