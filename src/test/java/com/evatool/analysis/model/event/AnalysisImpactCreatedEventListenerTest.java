package com.evatool.analysis.model.event;

import com.evatool.analysis.common.error.execptions.EventEntityAlreadyExistsException;
import com.evatool.analysis.common.error.execptions.InvalidEventPayloadException;
import com.evatool.analysis.domain.events.listener.AnalysisEventListener;
import com.evatool.analysis.domain.model.AnalysisImpacts;
import com.evatool.analysis.domain.repository.AnalysisImpactRepository;
import com.evatool.global.event.impact.ImpactCreatedEvent;
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
class AnalysisImpactCreatedEventListenerTest {

    @Autowired
    private AnalysisImpactRepository analysisImpactRepository;

    @Autowired
    private AnalysisEventListener analysisEventListener;

    @Test
    void testOnApplicationEvent_PublishEvent_ImpactCreated() {
        // given
        UUID id = UUID.randomUUID();
        String title = "name";
        String json = String.format("{\"id\":\"%s\",\"name\":\"%s\"}", id.toString(), title);

        // when
        ImpactCreatedEvent impactCreatedEvent = new ImpactCreatedEvent(this, json);
        analysisEventListener.impactCreated(impactCreatedEvent);

        // then
        Optional<AnalysisImpacts> createdByEvent = analysisImpactRepository.findById(id);
        assertThat(createdByEvent).isPresent();
        assertThat(createdByEvent.get().getId()).isEqualTo(id);
    }


    @Test
    void testOnApplicationEvent_ImpactAlreadyExists_ThrowEventEntityAlreadyExistsException() {
        // given
        UUID id = UUID.randomUUID();
        String title = "name";
        String json = String.format("{\"id\":\"%s\",\"title\":\"%s\"}", id.toString(), title);

        AnalysisImpacts analysisImpacts;

        try {
            var jsonObject = new JSONObject(json);
            analysisImpacts = new AnalysisImpacts();
            analysisImpacts.setTitle(jsonObject.getString("title"));
            analysisImpacts.setId(UUID.fromString(jsonObject.getString("id")));
        } catch (JSONException jex) {
            throw new InvalidEventPayloadException(json, jex);
        }

        analysisImpactRepository.save(analysisImpacts);

        // when
        ImpactCreatedEvent impactCreatedEvent = new ImpactCreatedEvent(this, json);

        // then
        assertThatExceptionOfType(EventEntityAlreadyExistsException.class).isThrownBy(() -> analysisEventListener.impactCreated(impactCreatedEvent));
    }
}
