package com.evatool.requirements.domain.event;

import com.evatool.analysis.common.error.execptions.EventEntityDoesNotExistException;
import com.evatool.global.event.value.ValueDeletedEvent;
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
class RequirementsDimensionDeletedEventListenerTest {

    @Autowired
    private RequirementValueRepository requirementDimensionRepository;

    @Autowired
    private RequirementEventListener requirementEventListener;


    @Test
    void testOnApplicationEvent_PublishEvent_DimensionDeleted() {
        // given
        RequirementValue requirementValue = new RequirementValue("Title");
        requirementDimensionRepository.save(requirementValue);

        String json = String.format("{\"id\":\"%s\",\"title\":\"%s\"}", requirementValue.getId().toString(), "Title");
        UUID tempId = requirementValue.getId();

        // when
        ValueDeletedEvent dimensionDeletedEvent = new ValueDeletedEvent(requirementEventListener, json);
        requirementEventListener.valueDeleted(dimensionDeletedEvent);

        // then
        Optional<RequirementValue> optionalRequirementDimension = requirementDimensionRepository.findById(tempId);
        assertThat(optionalRequirementDimension).isNotPresent();
    }

    @Test
    void testOnApplicationEvent_ValueDoesNotExist_ThrowEventEntityDoesNotExistException() {
        // given
        UUID id = UUID.randomUUID();
        String title = "title";
        String json = String.format("{\"id\":\"%s\",\"title\":\"%s\"}", id.toString(), title);

        // when
        ValueDeletedEvent dimensionDeletedEvent = new ValueDeletedEvent(requirementEventListener, json);

        // then
        assertThatExceptionOfType(EventEntityDoesNotExistException.class).isThrownBy(() -> requirementEventListener.valueDeleted(dimensionDeletedEvent));
    }

}
