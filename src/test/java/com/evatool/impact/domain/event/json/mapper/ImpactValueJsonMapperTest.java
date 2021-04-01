package com.evatool.impact.domain.event.json.mapper;

import org.junit.jupiter.api.Test;

import static com.evatool.impact.common.TestDataGenerator.createDummyValue;
import static com.evatool.impact.domain.event.json.mapper.ImpactValueJsonMapper.*;
import static org.assertj.core.api.Assertions.assertThat;

class ImpactValueJsonMapperTest { // TODO change other two domain foreign entities json mapping and tests

    @Test
    void testToAndFrom_ImpactValue_EqualsRecreatedImpactValue() {
        // given
        var value = createDummyValue();
        var json = toJson(value);
        var string = ImpactValueJsonMapper.toString(json);
        var recreatedJson = fromString(string);
        var recreatedValue = fromJson(json);

        // when

        // then
        assertThat(value).isEqualTo(recreatedValue);
    }
}
