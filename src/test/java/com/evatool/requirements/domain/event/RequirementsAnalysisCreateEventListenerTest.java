package com.evatool.requirements.domain.event;

import com.evatool.global.event.analysis.AnalysisCreatedEvent;
import com.evatool.requirements.domain.entity.RequirementsAnalysis;
import com.evatool.requirements.common.exceptions.EventEntityAlreadyExistsException;
import com.evatool.requirements.common.exceptions.InvalidEventPayloadException;
import com.evatool.requirements.domain.events.listener.RequirementEventListener;
import com.evatool.requirements.domain.repository.RequirementAnalysisRepository;
import org.json.JSONException;
import org.json.JSONObject;
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
class RequirementsAnalysisCreateEventListenerTest {

    @Autowired
    private RequirementAnalysisRepository requirementAnalysisRepository;

    @Autowired
    private RequirementEventListener requirementEventListener;

    @Test
    void testOnApplicationEvent_PublishEvent_AnalysisCreated() {
        // given
        UUID id = UUID.randomUUID();
        String json = String.format("{\"analysisId\":\"%s\"}", id.toString());

        // when
        AnalysisCreatedEvent analysisCreatedEvent = new AnalysisCreatedEvent(json);
        requirementEventListener.analyseCreated(analysisCreatedEvent);

        // then
        Optional<RequirementsAnalysis> createdByEvent = requirementAnalysisRepository.findById(id);
        assertThat(createdByEvent).isPresent();
        assertThat(createdByEvent.get().getAnalysisId()).isEqualTo(id);
    }

    @Test
    void testOnApplicationEvent_AnalysisAlreadyExists_ThrowEventEntityAlreadyExistsException() {
        // given
        UUID id = UUID.randomUUID();
        String json = String.format("{\"analysisId\":\"%s\"}", id.toString());

        RequirementsAnalysis requirementsAnalysis;

        try {
            JSONObject jsonObject = new JSONObject(json);
            requirementsAnalysis = new RequirementsAnalysis();
            requirementsAnalysis.setAnalysisId(UUID.fromString(jsonObject.getString("analysisId")));
        } catch (JSONException jex) {
            throw new InvalidEventPayloadException(json, jex);
        }

        requirementAnalysisRepository.save(requirementsAnalysis);

        // when
        AnalysisCreatedEvent analysisCreatedEvent = new AnalysisCreatedEvent(json);

        // then
        assertThatExceptionOfType(EventEntityAlreadyExistsException.class).isThrownBy(() -> requirementEventListener.analyseCreated(analysisCreatedEvent));
    }
}
