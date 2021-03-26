package com.evatool.requirements.domain.event;

import com.evatool.global.event.variants.VariantCreatedEvent;
import com.evatool.requirements.entity.RequirementsVariant;
import com.evatool.requirements.error.exceptions.EventEntityAlreadyExistsException;
import com.evatool.requirements.error.exceptions.InvalidEventPayloadException;
import com.evatool.requirements.events.listener.RequirementEventListener;
import com.evatool.requirements.repository.RequirementsVariantsRepository;
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
class RequirementsVariantsCreateEventListenerTest {

    @Autowired
    private RequirementsVariantsRepository requirementsVariantsRepository;

    @Autowired
    private RequirementEventListener requirementEventListener;

    @Test
    void testOnApplicationEvent_PublishEvent_VariantsCreated() {

        // given
        UUID id = UUID.randomUUID();
        String title = "title";
        String description = "description";
        String json = String.format("{\"id\":\"%s\",\"title\":\"%s\",\"description\":\"%s\"}", id.toString(),title,description);

        // when
        VariantCreatedEvent variantCreatedEvent = new VariantCreatedEvent(json);
        requirementEventListener.variantsCreated(variantCreatedEvent);

        // then
        Optional<RequirementsVariant> createdByEvent = requirementsVariantsRepository.findById(id);
        assertThat(createdByEvent).isPresent();
        assertThat(createdByEvent.get().getId()).isEqualTo(id);
    }


    @Test
    void testOnApplicationEvent_VariantsAlreadyExists_ThrowEventEntityAlreadyExistsException() {

        // given
        UUID id = UUID.randomUUID();
        String title = "title";
        String description = "description";
        String json = String.format("{\"id\":\"%s\",\"title\":\"%s\",\"description\":\"%s\"}", id.toString(),title,description);

        RequirementsVariant requirementsVariant;

        try {
            JSONObject jsonObject = new JSONObject(json);
            requirementsVariant = new RequirementsVariant();
            requirementsVariant.setId(UUID.fromString(jsonObject.getString("id")));
            requirementsVariant.setDescription(jsonObject.getString("description"));
            requirementsVariant.setTitle(jsonObject.getString("title"));
        } catch (JSONException jex) {
            throw new InvalidEventPayloadException(json, jex);
        }

        requirementsVariantsRepository.save(requirementsVariant);

        // when
        VariantCreatedEvent variantCreatedEvent = new VariantCreatedEvent(json);

        // then
        assertThatExceptionOfType(EventEntityAlreadyExistsException.class).isThrownBy(() -> requirementEventListener.variantsCreated(variantCreatedEvent));

    }
}
