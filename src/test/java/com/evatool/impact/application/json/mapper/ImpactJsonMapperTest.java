package com.evatool.impact.application.json.mapper;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.evatool.impact.application.json.mapper.ImpactJsonMapper.toJson;
import static com.evatool.impact.common.TestDataGenerator.createDummyImpact;
import static org.assertj.core.api.Assertions.assertThat;

class ImpactJsonMapperTest {

    @Test
    void testToJson_NewImpact_EqualsImpactJson() {
        // given
        var impact = createDummyImpact();
        impact.setId(UUID.randomUUID());
        impact.getDimension().setId(UUID.randomUUID());
        impact.getStakeholder().setId(UUID.randomUUID());

        // when
        var impactJson = toJson(impact);

        // then
        assertThat(impactJson.getId()).isEqualTo(impact.getId().toString());
        assertThat(impactJson.getValue()).isEqualTo(impact.getValue());
        assertThat(impactJson.getDescription()).isEqualTo(impact.getDescription());
        assertThat(impactJson.getDimensionId()).isEqualTo(impact.getDimension().getId().toString());
        assertThat(impactJson.getStakeholderId()).isEqualTo(impact.getStakeholder().getId().toString());
    }
}