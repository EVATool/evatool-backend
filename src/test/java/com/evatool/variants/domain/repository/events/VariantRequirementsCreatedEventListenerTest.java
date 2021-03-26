package com.evatool.variants.domain.repository.events;

import com.evatool.global.event.requirements.RequirementCreatedEvent;
import com.evatool.variants.common.error.exceptions.EventEntityAlreadyExistsException;
import com.evatool.variants.common.error.exceptions.IllegalEventPayloadException;
import com.evatool.variants.domain.entities.VariantsRequirements;
import com.evatool.variants.domain.events.VariantEventListener;
import com.evatool.variants.domain.repositories.VariantRequirementsRepository;
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
class VariantRequirementsCreatedEventListenerTest {


    @Autowired
    VariantEventListener variantEventListener;

    @Autowired
    VariantRequirementsRepository variantRequirementsRepository;

    @Test
    void testOnApplicationEvent_PublishEvent_RequirementCreated() {

        // given
        UUID id = UUID.randomUUID();
        String json = String.format("{\"requirementId\":\"%s\"}", id.toString());

        // when
        RequirementCreatedEvent requirementCreatedEvent = new RequirementCreatedEvent(json);
        variantEventListener.requirementCreated(requirementCreatedEvent);

        // then
        Optional<VariantsRequirements> createdByEvent = variantRequirementsRepository.findById(id);
        assertThat(createdByEvent).isPresent();
        assertThat(createdByEvent.get().getRequirementId()).isEqualTo(id);
    }


    @Test
    void testOnApplicationEvent_RequirementAlreadyExists_ThrowEventEntityAlreadyExistsException() {

        // given
        UUID id = UUID.randomUUID();
        String json = String.format("{\"requirementId\":\"%s\"}", id.toString());

        VariantsRequirements variantsRequirements;

        try {
            JSONObject jsonObject = new JSONObject(json);
            variantsRequirements = new VariantsRequirements();
            variantsRequirements.setRequirementId(UUID.fromString(jsonObject.getString("requirementId")));
        } catch (JSONException jex) {
            throw new IllegalEventPayloadException(json);
        }

        variantRequirementsRepository.save(variantsRequirements);

        // when
        RequirementCreatedEvent requirementCreatedEvent = new RequirementCreatedEvent(json);

        // then
        assertThatExceptionOfType(EventEntityAlreadyExistsException.class).isThrownBy(() -> variantEventListener.requirementCreated(requirementCreatedEvent));
    }
}
