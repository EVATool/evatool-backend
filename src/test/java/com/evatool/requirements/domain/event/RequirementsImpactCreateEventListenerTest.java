package com.evatool.requirements.domain.event;

import com.evatool.global.event.impact.ImpactCreatedEvent;
import com.evatool.requirements.domain.entity.RequirementsImpact;
import com.evatool.requirements.common.exceptions.EventEntityAlreadyExistsException;
import com.evatool.requirements.common.exceptions.InvalidEventPayloadException;
import com.evatool.requirements.domain.events.listener.RequirementEventListener;
import com.evatool.requirements.domain.repository.RequirementsImpactsRepository;
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
class RequirementsImpactCreateEventListenerTest {

    @Autowired
    private RequirementsImpactsRepository requirementsImpactsRepository;

    @Autowired
    private RequirementEventListener requirementEventListener;

    @Test
    void testOnApplicationEvent_PublishEvent_ImpactCreated() {
        // given
        UUID id = UUID.randomUUID();
        String description = "description";
        String json = String.format("{\"id\":\"%s\",\"description\":\"%s\"}", id.toString(), description);

        // when
        ImpactCreatedEvent impactCreatedEvent = new ImpactCreatedEvent(requirementEventListener, json);
        requirementEventListener.impactCreated(impactCreatedEvent);

        // then
        Optional<RequirementsImpact> createdByEvent = requirementsImpactsRepository.findById(id);
        assertThat(createdByEvent).isPresent();
        assertThat(createdByEvent.get().getId()).isEqualTo(id);
    }


    @Test
    void testOnApplicationEvent_ImpactAlreadyExists_ThrowEventEntityAlreadyExistsException() {
        // given
        UUID id = UUID.randomUUID();
        String description = "description";
        String json = String.format("{\"id\":\"%s\",\"description\":\"%s\"}", id.toString(), description);

        RequirementsImpact requirementsImpact;

        try {
            JSONObject jsonObject = new JSONObject(json);
            requirementsImpact = new RequirementsImpact();
            requirementsImpact.setDescription(jsonObject.getString("description"));
            requirementsImpact.setId(UUID.fromString(jsonObject.getString("id")));
        } catch (JSONException jex) {
            throw new InvalidEventPayloadException(json, jex);
        }

        requirementsImpactsRepository.save(requirementsImpact);

        // when
        ImpactCreatedEvent impactCreatedEvent = new ImpactCreatedEvent(requirementEventListener, json);

        // then
        assertThatExceptionOfType(EventEntityAlreadyExistsException.class).isThrownBy(() -> requirementEventListener.impactCreated(impactCreatedEvent));
    }


}
