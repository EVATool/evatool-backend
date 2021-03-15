package com.evatool.requirements.domain.event;

import com.evatool.global.event.variants.VariantUpdatedEvent;
import com.evatool.requirements.entity.RequirementsVariant;
import com.evatool.requirements.error.exceptions.EventEntityDoesNotExistException;
import com.evatool.requirements.events.listener.RequirementEventListener;
import com.evatool.requirements.repository.RequirementsVariantsRepository;
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
class RequirementsVariantsUpdateEventListenerTest {

    @Autowired
    private RequirementsVariantsRepository requirementsVariantsRepository;

    @Autowired
    private RequirementEventListener requirementEventListener;

    @Test
    void testOnApplicationEvent_PublishEvent_VariantsUpdated() {
        // given
        RequirementsVariant requirementsVariant = new RequirementsVariant("Title","Description");
        requirementsVariantsRepository.save(requirementsVariant);
        String newTitle = "newTitle";
        String newDescription = "newDescription";
        String json = String.format("{\"id\":\"%s\",\"title\":\"%s\",\"description\":\"%s\"}", requirementsVariant.getId().toString(),newTitle,newDescription);
        UUID tempId = requirementsVariant.getId();

        // when
        VariantUpdatedEvent variantUpdatedEvent = new VariantUpdatedEvent(json);
        requirementEventListener.variantsUpdated(variantUpdatedEvent);

        // then
        Optional<RequirementsVariant> optionalRequirementsVariant = requirementsVariantsRepository.findById(tempId);
        assertThat(optionalRequirementsVariant).isPresent();
        assertThat(optionalRequirementsVariant.get().getId()).isEqualTo(tempId);
        assertThat(optionalRequirementsVariant.get().getTitle()).isEqualTo(newTitle);
        assertThat(optionalRequirementsVariant.get().getDescription()).isEqualTo(newDescription);
    }

    @Test
    void testOnApplicationEvent_VariantsDoesNotExists_ThrowEventEntityDoesNotExistException() {

        // given
        UUID id = UUID.randomUUID();
        String newTitle = "newTitle";
        String newDescription = "newDescription";
        String json = String.format("{\"id\":\"%s\",\"title\":\"%s\",\"description\":\"%s\"}", id.toString(),newTitle,newDescription);

        // when
        VariantUpdatedEvent variantUpdatedEvent = new VariantUpdatedEvent(json);

        // then
        assertThatExceptionOfType(EventEntityDoesNotExistException.class).isThrownBy(() -> requirementEventListener.variantsUpdated(variantUpdatedEvent));

    }

}
