package com.evatool.impact.domain.event.json.mapper;

import com.evatool.impact.domain.event.json.ImpactJson;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.evatool.impact.common.TestDataGenerator.createDummyImpact;
import static com.evatool.impact.domain.event.json.mapper.ImpactJsonMapper.toJson;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ImpactJsonMapperTest {

    @Test
    void testToJson_ImpactJson_EqualsImpact() {
        // given
        var impact = createDummyImpact();
        impact.setId(UUID.randomUUID());

        // when
        var impactJson = new Gson().fromJson(toJson(impact), ImpactJson.class);

        // then
        assertTrue(impactJson.equalsEntity(impact));
    }
}
