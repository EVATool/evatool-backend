package com.evatool.requirements.domain.event;

import com.evatool.global.event.value.ValueDeletedEvent;
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
class RequirementsValueDeletedEventListenerTest {

    @Autowired
    private RequirementValueRepository requirementValueRepository;

    @Autowired
    private RequirementEventListener requirementEventListener;


    @Test
    void testOnApplicationEvent_PublishEvent_ValueDeleted() {
        // given
        RequirementValue requirementValue = new RequirementValue("Title");
        requirementValueRepository.save(requirementValue);

        String json = String.format("{\"id\":\"%s\",\"title\":\"%s\"}", requirementValue.getId().toString(), "Title");
        UUID tempId = requirementValue.getId();

        // when
        ValueDeletedEvent valueDeletedEvent = new ValueDeletedEvent(requirementEventListener, json);
        requirementEventListener.valueDeleted(valueDeletedEvent);

        // then
        Optional<RequirementValue> optionalRequirementValue = requirementValueRepository.findById(tempId);
        assertThat(optionalRequirementValue).isNotPresent();
    }

    @Test
    void testOnApplicationEvent_ValueDoesNotExist_ThrowEventEntityDoesNotExistException() {
        // given
        UUID id = UUID.randomUUID();
        String title = "title";
        String json = String.format("{\"id\":\"%s\",\"title\":\"%s\"}", id.toString(), title);

        // when
        ValueDeletedEvent valueDeletedEvent = new ValueDeletedEvent(requirementEventListener, json);

        // then
        assertThatExceptionOfType(EventEntityDoesNotExistException.class).isThrownBy(() -> requirementEventListener.valueDeleted(valueDeletedEvent));
    }

}
