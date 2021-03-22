package com.evatool.requirements.domain.event;

import com.evatool.global.event.value.ValueUpdatedEvent;
import com.evatool.requirements.entity.RequirementDimension;
import com.evatool.requirements.error.exceptions.EventEntityDoesNotExistException;
import com.evatool.requirements.events.listener.RequirementEventListener;
import com.evatool.requirements.repository.RequirementDimensionRepository;
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
class RequirementsDimensionUpdateEventListenerTest {

    @Autowired
    private RequirementDimensionRepository requirementDimensionRepository;

    @Autowired
    private RequirementEventListener requirementEventListener;


    @Test
    void testOnApplicationEvent_PublishEvent_DimensionUpdated() {
        // given
        RequirementDimension requirementDimension = new RequirementDimension("Name");
        requirementDimensionRepository.save(requirementDimension);
        String newName = "newName";
        String json = String.format("{\"id\":\"%s\",\"name\":\"%s\"}", requirementDimension.getId().toString(), newName);
        UUID tempId = requirementDimension.getId();

        // when
        ValueUpdatedEvent dimensionUpdatedEvent = new ValueUpdatedEvent(requirementEventListener, json);
        requirementEventListener.dimensionUpdated(dimensionUpdatedEvent);

        // then
        Optional<RequirementDimension> optionalRequirementDimension = requirementDimensionRepository.findById(tempId);
        assertThat(optionalRequirementDimension).isPresent();
        assertThat(optionalRequirementDimension.get().getId()).isEqualTo(tempId);
        assertThat(optionalRequirementDimension.get().getName()).isEqualTo(newName);
    }

    @Test
    void testOnApplicationEvent_DimensionDoesNotExists_ThrowEventEntityDoesNotExistException() {

        // given
        UUID id = UUID.randomUUID();
        String name = "name";
        String json = String.format("{\"id\":\"%s\",\"title\":\"%s\"}", id.toString(), name);

        // when
        ValueUpdatedEvent dimensionUpdatedEvent = new ValueUpdatedEvent(requirementEventListener, json);

        // then
        assertThatExceptionOfType(EventEntityDoesNotExistException.class).isThrownBy(() -> requirementEventListener.dimensionUpdated(dimensionUpdatedEvent));

    }

}
