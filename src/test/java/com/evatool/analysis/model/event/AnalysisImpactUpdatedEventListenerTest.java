package com.evatool.analysis.model.event;

import com.evatool.analysis.error.exceptions.EventEntityDoesNotExistException;
import com.evatool.analysis.events.listener.AnalysisEventListener;
import com.evatool.analysis.model.AnalysisImpacts;
import com.evatool.analysis.repository.AnalysisImpactRepository;
import com.evatool.global.event.impact.ImpactUpdatedEvent;
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
class AnalysisImpactUpdatedEventListenerTest {

    @Autowired
    private AnalysisImpactRepository analysisImpactRepository;

    @Autowired
    private AnalysisEventListener analysisEventListener;

    @Test
    void testOnApplicationEvent_PublishEvent_ImpactUpdated() {
        // given
        UUID id = UUID.randomUUID();
        String title = "title";
        String json = String.format("{\"id\":\"%s\",\"title\":\"%s\"}", id.toString(), title);

        AnalysisImpacts analysisImpacts = new AnalysisImpacts("Title","Description",10,null);
        analysisImpacts.setId(id);
        analysisImpactRepository.save(analysisImpacts);

        // when
        ImpactUpdatedEvent impactUpdatedEvent = new ImpactUpdatedEvent(this, json);
        analysisEventListener.impactUpdated(impactUpdatedEvent);

        // then
        Optional<AnalysisImpacts> analysisImpactsRepositoryById = analysisImpactRepository.findById(id);
        assertThat(analysisImpactsRepositoryById).isPresent();
        assertThat(analysisImpactsRepositoryById.get().getId()).isEqualTo(id);
        assertThat(analysisImpactsRepositoryById.get().getTitle()).isEqualTo(title);
    }

    @Test
    void testOnApplicationEvent_ImpactDoesNotExists_ThrowEventEntityDoesNotExistException() {

        // given
        UUID id = UUID.randomUUID();
        String title = "name";
        String json = String.format("{\"id\":\"%s\",\"title\":\"%s\"}", id.toString(), title);

        // when
        ImpactUpdatedEvent impactUpdatedEvent = new ImpactUpdatedEvent(this, json);

        // then
        assertThatExceptionOfType(EventEntityDoesNotExistException.class).isThrownBy(() -> analysisEventListener.impactUpdated(impactUpdatedEvent));
    }
}
