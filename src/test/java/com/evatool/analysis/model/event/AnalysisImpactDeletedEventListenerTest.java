package com.evatool.analysis.model.event;

import com.evatool.analysis.common.error.execptions.EventEntityDoesNotExistException;
import com.evatool.analysis.domain.events.listener.AnalysisEventListener;
import com.evatool.analysis.domain.model.AnalysisImpacts;
import com.evatool.analysis.domain.repository.AnalysisImpactRepository;
import com.evatool.global.event.impact.ImpactDeletedEvent;
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
class AnalysisImpactDeletedEventListenerTest {

    @Autowired
    private AnalysisImpactRepository analysisImpactRepository;

    @Autowired
    private AnalysisEventListener analysisEventListener;

    @Test
    void testOnApplicationEvent_PublishEvent_ImpactDeleted() {
        // given
        UUID id = UUID.randomUUID();
        String title = "title";
        String json = String.format("{\"id\":\"%s\",\"title\":\"%s\"}", id.toString(), title);

        AnalysisImpacts analysisImpacts = new AnalysisImpacts("Title","Description",10,null);
        analysisImpacts.setId(id);
        analysisImpactRepository.save(analysisImpacts);

        // when
        ImpactDeletedEvent impactDeletedEvent = new ImpactDeletedEvent(this, json);
        analysisEventListener.impactDeleted(impactDeletedEvent);

        // then
        Optional<AnalysisImpacts> optionalAnalysisImpacts = analysisImpactRepository.findById(id);
        assertThat(optionalAnalysisImpacts).isNotPresent();
    }

    @Test
    void testOnApplicationEvent_ImpactDoesNotExist_ThrowEventEntityDoesNotExistException() {
        // given
        UUID id = UUID.randomUUID();
        String title = "title";
        String json = String.format("{\"id\":\"%s\",\"title\":\"%s\"}", id.toString(), title);

        // when
        ImpactDeletedEvent impactDeletedEvent = new ImpactDeletedEvent(this, json);

        // then
        assertThatExceptionOfType(EventEntityDoesNotExistException.class).isThrownBy(() -> analysisEventListener.impactDeleted(impactDeletedEvent));
    }
}
