package com.evatool.requirements.domain.event;

import com.evatool.global.event.impact.ImpactUpdatedEvent;
import com.evatool.requirements.entity.RequirementsImpact;
import com.evatool.requirements.error.exceptions.EventEntityDoesNotExistException;
import com.evatool.requirements.events.listener.RequirementEventListener;
import com.evatool.requirements.repository.RequirementsImpactsRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@Disabled
@SpringBootTest
@ActiveProfiles(profiles = "non-async")
public class RequirementImpactUpdateEventListener {

    @Autowired
    private RequirementsImpactsRepository requirementsImpactsRepository;

    @Autowired
    private RequirementEventListener requirementEventListener;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Test
    void testOnApplicationEvent_PublishEvent_ImpactUpdated() {
        // given
        UUID id = UUID.randomUUID();
        String  title = "title";
        String json = String.format("{\"id\":\"%s\",\"title\":\"%s\"}", id.toString(), title);

        RequirementsImpact requirementsImpact = new RequirementsImpact("Title","Description",10,null);
        requirementsImpact.setId(id);
        requirementsImpactsRepository.save(requirementsImpact);

        // when
        ImpactUpdatedEvent impactCreatedEvent = new ImpactUpdatedEvent(applicationEventPublisher, json);
        applicationEventPublisher.publishEvent(impactCreatedEvent);

        // then
        Optional<RequirementsImpact> requirementsImpactsRepositoryById = requirementsImpactsRepository.findById(id);
        assertThat(requirementsImpactsRepositoryById).isPresent();
        assertThat(requirementsImpactsRepositoryById.get().getId()).isEqualTo(id);
        assertThat(requirementsImpactsRepositoryById.get().getTitle()).isEqualTo(title);
    }

    @Test
    void testOnApplicationEvent_ImpactDoesNotExists_ThrowEventEntityDoesNotExistException() {

        // given
        UUID id = UUID.randomUUID();
        String  title = "name";
        String json = String.format("{\"id\":\"%s\",\"title\":\"%s\"}", id.toString(), title);

        // when
        ImpactUpdatedEvent impactCreatedEvent = new ImpactUpdatedEvent(applicationEventPublisher, json);

        // then
        assertThatExceptionOfType(EventEntityDoesNotExistException.class).isThrownBy(() -> applicationEventPublisher.publishEvent(impactCreatedEvent));

   }
}
