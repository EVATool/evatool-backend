package com.evatool.requirements.domain.event;

import com.evatool.global.event.value.ValueUpdatedEvent;
import com.evatool.requirements.common.exceptions.EventEntityDoesNotExistException;
import com.evatool.requirements.domain.entity.RequirementValue;
import com.evatool.requirements.domain.events.listener.RequirementEventListener;
import com.evatool.requirements.domain.repository.RequirementValueRepository;
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
    private RequirementValueRepository requirementValueRepository;

    @Autowired
    private RequirementEventListener requirementEventListener;


    @Test
    void testOnApplicationEvent_PublishEvent_ValueUpdated() {
        // given
        RequirementValue requirementValue = new RequirementValue("Name");
        requirementValueRepository.save(requirementValue);
        String newName = "newName";
        String json = String.format("{\"id\":\"%s\",\"name\":\"%s\"}", requirementValue.getId().toString(), newName);
        UUID tempId = requirementValue.getId();

        // when
        ValueUpdatedEvent valueUpdatedEvent = new ValueUpdatedEvent(requirementEventListener, json);
        requirementEventListener.valueUpdated(valueUpdatedEvent);

        // then
        Optional<RequirementValue> optionalRequirementValue = requirementValueRepository.findById(tempId);
        assertThat(optionalRequirementValue).isPresent();
        assertThat(optionalRequirementValue.get().getId()).isEqualTo(tempId);
        assertThat(optionalRequirementValue.get().getName()).isEqualTo(newName);
    }

    @Test
    void testOnApplicationEvent_ValueDoesNotExists_ThrowEventEntityDoesNotExistException() {

        // given
        UUID id = UUID.randomUUID();
        String name = "name";
        String json = String.format("{\"id\":\"%s\",\"title\":\"%s\"}", id.toString(), name);

        // when
        ValueUpdatedEvent valueUpdatedEvent = new ValueUpdatedEvent(requirementEventListener, json);

        // then
        assertThatExceptionOfType(EventEntityDoesNotExistException.class).isThrownBy(() -> requirementEventListener.valueUpdated(valueUpdatedEvent));

    }

}
