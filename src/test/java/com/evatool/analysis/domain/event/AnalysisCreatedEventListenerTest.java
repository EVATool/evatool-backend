package com.evatool.analysis.domain.event;

import com.evatool.analysis.domain.events.Listener.AnalysisEventListener;
import com.evatool.analysis.domain.model.Analysis;
import com.evatool.analysis.domain.repository.AnalysisRepository;
import com.evatool.global.event.analysis.AnalysisCreatedEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles(profiles = "non-async")
public class AnalysisCreatedEventListenerTest {

    @Autowired
    private AnalysisRepository analysisRepository;

    @Autowired
    private AnalysisEventListener analysisEventListener;

    @Test
    void testOnApplicationEvent_PublishEvent_AnalysisCreated(){

        // given
        UUID id = UUID.randomUUID();
        String json = String.format("{\"analysisId\":\"%s\"}", id.toString());

        // when
        AnalysisCreatedEvent analysisCreatedEvent = new AnalysisCreatedEvent(json);
        analysisEventListener.analyseCreated(analysisCreatedEvent);

        // then
        Optional<Analysis> createdByEvent = analysisRepository.findById(id);
        assertThat(createdByEvent).isPresent();
        assertThat(createdByEvent.get().getAnalysisId()).isEqualTo(id);
    }
}
