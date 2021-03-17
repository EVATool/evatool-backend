package com.evatool.requirements.domain.event;

import com.evatool.global.event.analysis.AnalysisDeletedEvent;
import com.evatool.requirements.entity.RequirementsAnalysis;
import com.evatool.requirements.error.exceptions.EventEntityDoesNotExistException;
import com.evatool.requirements.events.listener.RequirementEventListener;
import com.evatool.requirements.repository.RequirementAnalysisRepository;
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
class RequirementsAnalysisDeletedEventListenerTest {

    @Autowired
    private RequirementAnalysisRepository requirementAnalysisRepository;

    @Autowired
    private RequirementEventListener requirementEventListener;

    @Test
    @Disabled
    void testOnApplicationEvent_PublishEvent_AnalysisDeleted() {
        // given
        RequirementsAnalysis requirementsAnalysis = new RequirementsAnalysis();
        requirementAnalysisRepository.save(requirementsAnalysis);

        String json = String.format("{\"id\":\"%s\"}", requirementsAnalysis.getAnalysisId().toString());
        UUID tempId = requirementsAnalysis.getAnalysisId();

        // when
        AnalysisDeletedEvent analysisDeletedEvent = new AnalysisDeletedEvent(json);
        requirementEventListener.analyseDeleted(analysisDeletedEvent);

        // then
        Optional<RequirementsAnalysis> optionalRequirementsAnalysis = requirementAnalysisRepository.findById(tempId);
        assertThat(optionalRequirementsAnalysis).isNotPresent();
    }

    @Test
    void testOnApplicationEvent_AnalysisDoesNotExist_ThrowEventEntityDoesNotExistException() {
        // given
        UUID id = UUID.randomUUID();
        String json = String.format("{\"id\":\"%s\"}", id.toString());

        // when
        AnalysisDeletedEvent analysisDeletedEvent = new AnalysisDeletedEvent(json);

        // then
        assertThatExceptionOfType(EventEntityDoesNotExistException.class).isThrownBy(() -> requirementEventListener.analyseDeleted(analysisDeletedEvent));
    }
}
