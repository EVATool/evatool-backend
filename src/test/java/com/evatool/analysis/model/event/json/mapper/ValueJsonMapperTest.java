package com.evatool.analysis.model.event.json.mapper;

import com.evatool.analysis.domain.events.json.ValueJson;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import static com.evatool.analysis.common.TestDataGenerator.*;
import static com.evatool.analysis.domain.events.json.ValueJsonMapper.toJson;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValueJsonMapperTest {

    @Test
    void testToJson_ValueJson_EqualsValue() {
        // given
        var value = createDummyValue();
        value.setId(UUID.randomUUID());


        // when
        var valueJson = new Gson().fromJson(toJson(value), ValueJson.class);

        // then
        assertTrue(valueJson.equalsEntity(value));
    }
}
