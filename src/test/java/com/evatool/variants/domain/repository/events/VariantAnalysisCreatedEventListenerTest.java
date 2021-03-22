package com.evatool.variants.domain.repository.events;

import com.evatool.global.event.analysis.AnalysisCreatedEvent;
import com.evatool.variants.common.error.exceptions.EventEntityAlreadyExistsException;
import com.evatool.variants.common.error.exceptions.IllegalEventPayloadException;
import com.evatool.variants.domain.entities.VariantsAnalysis;
import com.evatool.variants.domain.events.VariantEventListener;
import com.evatool.variants.domain.repositories.VariantsAnalysisRepository;
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
class VariantAnalysisCreatedEventListenerTest {


    @Autowired
    VariantEventListener variantEventListener;

    @Autowired
    VariantsAnalysisRepository variantsAnalysisRepository;

    @Test
    void testOnApplicationEvent_PublishEvent_AnalysisCreated() {

        // given
        UUID id = UUID.randomUUID();
        String json = String.format("{\"analysisId\":\"%s\"}", id.toString());

        // when
        AnalysisCreatedEvent analysisCreatedEvent = new AnalysisCreatedEvent(json);
        variantEventListener.analyseCreated(analysisCreatedEvent);

        // then
        Optional<VariantsAnalysis> createdByEvent = variantsAnalysisRepository.findById(id);
        assertThat(createdByEvent).isPresent();
        assertThat(createdByEvent.get().getAnalysisId()).isEqualTo(id);
    }


    @Test
    void testOnApplicationEvent_AnalysisAlreadyExists_ThrowEventEntityAlreadyExistsException() {

        // given
        UUID id = UUID.randomUUID();
        String json = String.format("{\"analysisId\":\"%s\"}", id.toString());

        VariantsAnalysis variantsAnalysis;

        try {
            JSONObject jsonObject = new JSONObject(json);
            variantsAnalysis = new VariantsAnalysis(UUID.fromString(jsonObject.getString("analysisId")));
        } catch (JSONException jex) {
            throw new IllegalEventPayloadException(json);
        }

        variantsAnalysisRepository.save(variantsAnalysis);

        // when
        AnalysisCreatedEvent analysisCreatedEvent = new AnalysisCreatedEvent(json);

        // then
        assertThatExceptionOfType(EventEntityAlreadyExistsException.class).isThrownBy(() -> variantEventListener.analyseCreated(analysisCreatedEvent));
    }
}
