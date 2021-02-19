package com.evatool.impact.application.json.mapper;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.evatool.impact.application.json.mapper.DimensionJsonMapper.toJson;
import static com.evatool.impact.common.TestDataGenerator.createDummyDimension;
import static org.assertj.core.api.Assertions.assertThat;

class DimensionJsonMapperTest {

    @Test
    void testToJson_NewDimension_EqualsDimensionJson() {
        // given
        var dimension = createDummyDimension();
        dimension.setId(UUID.randomUUID());

        // when
        var dimensionJson = toJson(dimension);

        // then
        assertThat(dimensionJson.getId()).isEqualTo(dimension.getId().toString());
        assertThat(dimensionJson.getName()).isEqualTo(dimension.getName());
        assertThat(dimensionJson.getType()).isEqualTo(dimension.getType().toString());
        assertThat(dimensionJson.getDescription()).isEqualTo(dimension.getDescription());
    }
}