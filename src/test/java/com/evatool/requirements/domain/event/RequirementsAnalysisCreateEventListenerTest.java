package com.evatool.requirements.domain.event;

import com.evatool.global.event.analysis.AnalysisCreatedEvent;
import com.evatool.requirements.entity.RequirementsAnalysis;
import com.evatool.requirements.error.exceptions.EventEntityAlreadyExistsException;
import com.evatool.requirements.error.exceptions.InvalidEventPayloadException;
import com.evatool.requirements.events.listener.RequirementEventListener;
import com.evatool.requirements.repository.RequirementAnalysisRepository;
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
        String json = String.format("{\"id\":\"%s\"}", id.toString());

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
        String json = String.format("{\"id\":\"%s\"}", id.toString());

        RequirementsAnalysis requirementsAnalysis;

        try {
            JSONObject jsonObject = new JSONObject(json);
            requirementsAnalysis = new RequirementsAnalysis();
            requirementsAnalysis.setAnalysisId(UUID.fromString(jsonObject.getString("id")));
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
