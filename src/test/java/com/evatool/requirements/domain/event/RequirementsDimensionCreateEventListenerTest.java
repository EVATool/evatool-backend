package com.evatool.requirements.domain.event;

import com.evatool.global.event.value.ValueCreatedEvent;

import com.evatool.requirements.common.exceptions.EventEntityAlreadyExistsException;
import com.evatool.requirements.common.exceptions.InvalidEventPayloadException;
import com.evatool.requirements.domain.entity.RequirementValue;
import com.evatool.requirements.domain.events.listener.RequirementEventListener;
import com.evatool.requirements.domain.repository.RequirementValueRepository;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@ActiveProfiles(profiles = "non-async")
class RequirementsDimensionCreateEventListenerTest {

    @Autowired
    private RequirementValueRepository requirementDimensionRepository;

    @Autowired
    private RequirementEventListener requirementEventListener;


    @Test
    void testOnApplicationEvent_PublishEvent_DimensionCreated() {
        // given
        UUID id = UUID.randomUUID();
        String  name = "name";
        String json = String.format("{\"id\":\"%s\",\"name\":\"%s\"}", id.toString(), name);

        // when
        ValueCreatedEvent dimensionCreatedEvent = new ValueCreatedEvent(requirementEventListener, json);
        requirementEventListener.valueCreated(dimensionCreatedEvent);

        // then
        Optional<RequirementValue> createdByEvent = requirementDimensionRepository.findById(id);
        assertThat(createdByEvent).isPresent();
        assertThat(createdByEvent.get().getName()).isEqualTo(name);
    }


    @Test
    void testOnApplicationEvent_DimensionAlreadyExists_ThrowEventEntityAlreadyExistsException() {

        // given
        UUID id = UUID.randomUUID();
        String name = "name";
        String json = String.format("{\"id\":\"%s\",\"name\":\"%s\"}", id.toString(), name);

        RequirementValue requirementValue;

        try {
            JSONObject jsonObject = new JSONObject(json);
            requirementValue = new RequirementValue();
            requirementValue.setName(jsonObject.getString("name"));
            requirementValue.setId(UUID.fromString(jsonObject.getString("id")));
        } catch (JSONException jex) {
            throw new InvalidEventPayloadException(json, jex);
        }

        requirementDimensionRepository.save(requirementValue);

        // when
        ValueCreatedEvent dimensionCreatedEvent = new ValueCreatedEvent(requirementEventListener, json);

        // then
        assertThatExceptionOfType(EventEntityAlreadyExistsException.class).isThrownBy(() -> requirementEventListener.valueCreated(dimensionCreatedEvent));

    }

}
