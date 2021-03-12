package com.evatool.requirements.domain.event;

import com.evatool.global.event.impact.ImpactDeletedEvent;
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
public class RequirementImpactDeletedEventListener {

    @Autowired
    private RequirementsImpactsRepository requirementsImpactsRepository;

    @Autowired
    private RequirementEventListener requirementEventListener;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Test
    void testOnApplicationEvent_PublishEvent_ImpactDeleted() {
        // given
        UUID id = UUID.randomUUID();
        String title = "title";
        String json = String.format("{\"id\":\"%s\",\"title\":\"%s\"}", id.toString(), title);

        RequirementsImpact requirementsImpact = new RequirementsImpact("Title","Description",10,null);
        requirementsImpact.setId(id);
        requirementsImpactsRepository.save(requirementsImpact);

        // when
        ImpactDeletedEvent impactDeletedEvent = new ImpactDeletedEvent(applicationEventPublisher, json);
        applicationEventPublisher.publishEvent(impactDeletedEvent);

        // then
        Optional<RequirementsImpact> optionalRequirementsImpact = requirementsImpactsRepository.findById(id);
        assertThat(optionalRequirementsImpact).isNotPresent();
    }

    @Test
    void testOnApplicationEvent_ImpactDoesNotExist_ThrowEventEntityDoesNotExistException() {
        // given
        UUID id = UUID.randomUUID();
        String title = "title";
        String json = String.format("{\"id\":\"%s\",\"title\":\"%s\"}", id.toString(), title);

        // when
        ImpactDeletedEvent impactDeletedEvent = new ImpactDeletedEvent(applicationEventPublisher, json);

        // then
        assertThatExceptionOfType(EventEntityDoesNotExistException.class).isThrownBy(() -> applicationEventPublisher.publishEvent(impactDeletedEvent));
    }
}
