package com.evatool.requirements.domain.event;

import com.evatool.global.event.impact.ImpactDeletedEvent;
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

@SpringBootTest
@ActiveProfiles(profiles = "non-async")
class RequirementsImpactDeletedEventListenerTest {

    @Autowired
    private RequirementsImpactsRepository requirementsImpactsRepository;

    @Autowired
    private RequirementEventListener requirementEventListener;

    @Test
    void testOnApplicationEvent_PublishEvent_ImpactDeleted() {
        // given
        RequirementsImpact requirementsImpact = new RequirementsImpact("Description",10d,null);
        requirementsImpactsRepository.save(requirementsImpact);
        UUID tempId = requirementsImpact.getId();
        String json = String.format("{\"id\":\"%s\",\"description\":\"%s\"}", requirementsImpact.getId().toString(), requirementsImpact.getDescription());

        // when
        ImpactDeletedEvent impactDeletedEvent = new ImpactDeletedEvent(requirementEventListener, json);
        requirementEventListener.impactDeleted(impactDeletedEvent);

        // then
        Optional<RequirementsImpact> optionalRequirementsImpact = requirementsImpactsRepository.findById(tempId);
        assertThat(optionalRequirementsImpact).isNotPresent();
    }

    @Test
    void testOnApplicationEvent_ImpactDoesNotExist_ThrowEventEntityDoesNotExistException() {
        // given
        UUID id = UUID.randomUUID();
        String description = "description";
        String json = String.format("{\"id\":\"%s\",\"description\":\"%s\"}", id.toString(), description);

        // when
        ImpactDeletedEvent impactDeletedEvent = new ImpactDeletedEvent(requirementEventListener, json);

        // then
        assertThatExceptionOfType(EventEntityDoesNotExistException.class).isThrownBy(() -> requirementEventListener.impactDeleted(impactDeletedEvent));
    }
}
