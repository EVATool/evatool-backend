package com.evatool.requirements.domain.event;

import com.evatool.global.event.impact.ImpactUpdatedEvent;
import com.evatool.requirements.domain.entity.RequirementsImpact;
import com.evatool.requirements.common.exceptions.EventEntityDoesNotExistException;
import com.evatool.requirements.domain.events.listener.RequirementEventListener;
import com.evatool.requirements.domain.repository.RequirementsImpactsRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@Disabled
@SpringBootTest
@ActiveProfiles(profiles = "non-async")
class RequirementsImpactUpdateEventListenerTest {

    @Autowired
    private RequirementsImpactsRepository requirementsImpactsRepository;

    @Autowired
    private RequirementEventListener requirementEventListener;

    @Test
    void testOnApplicationEvent_PublishEvent_ImpactUpdated() {
        // given
        RequirementsImpact requirementsImpact = new RequirementsImpact("Description",10d,null);
        requirementsImpactsRepository.save(requirementsImpact);
        String newDescription = "newDescription";

        // when
        String json = String.format("{\"id\":\"%s\",\"description\":\"%s\"}", requirementsImpact.getId().toString(), newDescription);
        ImpactUpdatedEvent impactUpdatedEvent = new ImpactUpdatedEvent(requirementEventListener, json);
        requirementEventListener.impactUpdated(impactUpdatedEvent);

        // then
        Optional<RequirementsImpact> requirementsImpactsRepositoryById = requirementsImpactsRepository.findById(requirementsImpact.getId());
        assertThat(requirementsImpactsRepositoryById).isPresent();
        assertThat(requirementsImpactsRepositoryById.get().getId()).isEqualTo(requirementsImpact.getId());
        assertThat(requirementsImpactsRepositoryById.get().getDescription()).isEqualTo(newDescription);
    }

    @Test
    void testOnApplicationEvent_ImpactDoesNotExists_ThrowEventEntityDoesNotExistException() {

        // given
        UUID id = UUID.randomUUID();
        String description = "description";
        String json = String.format("{\"id\":\"%s\",\"description\":\"%s\"}", id.toString(), description);

        // when
        ImpactUpdatedEvent impactUpdatedEvent = new ImpactUpdatedEvent(requirementEventListener, json);

        // then
        assertThatExceptionOfType(EventEntityDoesNotExistException.class).isThrownBy(() -> requirementEventListener.impactUpdated(impactUpdatedEvent));

   }
}
