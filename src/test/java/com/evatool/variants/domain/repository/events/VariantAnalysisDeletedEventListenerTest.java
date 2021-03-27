package com.evatool.variants.domain.repository.events;

import com.evatool.global.event.analysis.AnalysisDeletedEvent;
import com.evatool.variants.common.error.exceptions.EventEntityDoesNotExistException;
import com.evatool.variants.domain.entities.VariantsAnalysis;
import com.evatool.variants.domain.events.VariantEventListener;
import com.evatool.variants.domain.repositories.VariantsAnalysisRepository;
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
class VariantAnalysisDeletedEventListenerTest {

    @Autowired
    VariantEventListener variantEventListener;

    @Autowired
    VariantsAnalysisRepository variantsAnalysisRepository;

    @Test
    void testOnApplicationEvent_PublishEvent_AnalysisDeleted() {

        UUID id = UUID.randomUUID();
        // given
        VariantsAnalysis variantsAnalysis = new VariantsAnalysis(id);
        variantsAnalysisRepository.save(variantsAnalysis);

        String json = String.format("{\"analysisId\":\"%s\"}", variantsAnalysis.getAnalysisId().toString());
        UUID tempId = variantsAnalysis.getAnalysisId();

        // when
        AnalysisDeletedEvent analysisDeletedEvent = new AnalysisDeletedEvent(json);
        variantEventListener.analyseDeleted(analysisDeletedEvent);

        // then
        Optional<VariantsAnalysis> optionalRequirementsAnalysis = variantsAnalysisRepository.findById(tempId);
        assertThat(optionalRequirementsAnalysis).isNotPresent();
    }

    @Test
    void testOnApplicationEvent_AnalysisDoesNotExist_ThrowEventEntityDoesNotExistException() {

        // given
        UUID id = UUID.randomUUID();
        String json = String.format("{\"analysisId\":\"%s\"}", id.toString());

        // when
        AnalysisDeletedEvent analysisDeletedEvent = new AnalysisDeletedEvent(json);

        // then
        assertThatExceptionOfType(EventEntityDoesNotExistException.class).isThrownBy(() -> variantEventListener.analyseDeleted(analysisDeletedEvent));
    }
}
