package com.evatool.requirements.domain.event;

import com.evatool.global.event.dimension.DimensionCreatedEvent;
import com.evatool.requirements.entity.RequirementDimension;
import com.evatool.requirements.error.exceptions.EventEntityAlreadyExistsException;
import com.evatool.requirements.error.exceptions.InvalidEventPayloadException;
import com.evatool.requirements.events.listener.RequirementEventListener;
import com.evatool.requirements.repository.RequirementDimensionRepository;
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
    private RequirementDimensionRepository requirementDimensionRepository;

    @Autowired
    private RequirementEventListener requirementEventListener;


    @Test
    void testOnApplicationEvent_PublishEvent_DimensionCreated() {
        // given
        UUID id = UUID.randomUUID();
        String  name = "name";
        String json = String.format("{\"id\":\"%s\",\"name\":\"%s\"}", id.toString(), name);

        // when
        DimensionCreatedEvent dimensionCreatedEvent = new DimensionCreatedEvent(requirementEventListener, json);
        requirementEventListener.dimensionCreated(dimensionCreatedEvent);

        // then
        Optional<RequirementDimension> createdByEvent = requirementDimensionRepository.findById(id);
        assertThat(createdByEvent).isPresent();
        assertThat(createdByEvent.get().getName()).isEqualTo(name);
    }


    @Test
    void testOnApplicationEvent_DimensionAlreadyExists_ThrowEventEntityAlreadyExistsException() {

        // given
        UUID id = UUID.randomUUID();
        String name = "name";
        String json = String.format("{\"id\":\"%s\",\"name\":\"%s\"}", id.toString(), name);

        RequirementDimension requirementDimension;

        try {
            JSONObject jsonObject = new JSONObject(json);
            requirementDimension = new RequirementDimension();
            requirementDimension.setName(jsonObject.getString("name"));
            requirementDimension.setId(UUID.fromString(jsonObject.getString("id")));
        } catch (JSONException jex) {
            throw new InvalidEventPayloadException(json, jex);
        }

        requirementDimensionRepository.save(requirementDimension);

        // when
        DimensionCreatedEvent dimensionCreatedEvent = new DimensionCreatedEvent(requirementEventListener, json);

        // then
        assertThatExceptionOfType(EventEntityAlreadyExistsException.class).isThrownBy(() -> requirementEventListener.dimensionCreated(dimensionCreatedEvent));

    }

}
