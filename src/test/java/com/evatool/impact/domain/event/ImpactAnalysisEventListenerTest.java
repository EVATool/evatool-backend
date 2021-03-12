package com.evatool.impact.domain.event;

import com.evatool.global.event.analysis.AnalysisCreatedEvent;
import com.evatool.global.event.analysis.AnalysisDeletedEvent;
import com.evatool.global.event.analysis.AnalysisUpdatedEvent;
import com.evatool.impact.common.exception.EventEntityAlreadyExistsException;
import com.evatool.impact.common.exception.EventEntityDoesNotExistException;
import com.evatool.impact.domain.entity.ImpactAnalysis;
import com.evatool.impact.domain.repository.ImpactAnalysisRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@ActiveProfiles(profiles = "non-async")
public class ImpactAnalysisEventListenerTest {

    @Autowired
    private ImpactAnalysisRepository analysisRepository;

    @Autowired
    private ImpactAnalysisEventListener impactAnalysisEventListener;

    @BeforeEach
    void clearData() {
        analysisRepository.deleteAll();
    }

    @Nested
    class Created {

        @Test
        void testOnAnalysisCreatedEvent_PublishEvent_AnalysisCreated() {
            // given
            var id = UUID.randomUUID();
            var json = String.format("{\"id\":\"%s\"}", id.toString());

            // when
            var analysisCreatedEvent = new AnalysisCreatedEvent(json);
            impactAnalysisEventListener.onAnalysisCreatedEvent(analysisCreatedEvent);

            // then
            var createdByEvent = analysisRepository.findById(id);
            assertThat(createdByEvent).isPresent();
            assertThat(createdByEvent.get().getId()).isEqualTo(id);
        }

        @Test
        void testOnAnalysisCreatedEvent_AnalysisAlreadyExists_ThrowEventEntityAlreadyExistsException() {
            // given
            var id = UUID.randomUUID();
            var json = String.format("{\"id\":\"%s\"}", id.toString());

            var analysis = new ImpactAnalysis(id);
            analysisRepository.save(analysis);

            // when
            var analysisCreatedEvent = new AnalysisCreatedEvent(json);

            // then
            assertThatExceptionOfType(EventEntityAlreadyExistsException.class).isThrownBy(() -> impactAnalysisEventListener.onAnalysisCreatedEvent(analysisCreatedEvent));
        }
    }

    @Nested
    class Deleted {

        @Test
        void testOnAnalysisDeletedEvent_PublishEvent_AnalysisDeleted() {
            // given
            var id = UUID.randomUUID();
            // TODO [hbuhl] See actual jsonPayload published by the analysis domain
            var json = String.format("{\"id\":\"%s\"}", id.toString());

            var analysis = new ImpactAnalysis(id);
            analysisRepository.save(analysis);

            // when
            AnalysisDeletedEvent analysisDeletedEvent = new AnalysisDeletedEvent(json);
            impactAnalysisEventListener.onAnalysisDeletedEvent(analysisDeletedEvent);

            // then
            var deletedByEventAnalysis = analysisRepository.findById(id);
            assertThat(deletedByEventAnalysis).isNotPresent();
        }

        @Test
        void testOnAnalysisDeletedEvent_AnalysisDoesNotExist_ThrowEventEntityDoesNotExistException() {
            // given
            var id = UUID.randomUUID();
            // TODO [hbuhl] See actual jsonPayload published by the analysis domain
            var json = String.format("{\"id\":\"%s\"}", id.toString());

            // when
            AnalysisDeletedEvent analysisDeletedEvent = new AnalysisDeletedEvent(json);

            // then
            assertThatExceptionOfType(EventEntityDoesNotExistException.class).isThrownBy(() -> impactAnalysisEventListener.onAnalysisDeletedEvent(analysisDeletedEvent));
        }
    }

    @Nested
    class Updated {

        @Test
        void testOnAnalysisUpdatedEvent_PublishEvent_AnalysisUpdated() {
            // given
            var id = UUID.randomUUID();
            var json = String.format("{\"id\":\"%s\"}", id.toString());

            var analysis = new ImpactAnalysis(id);
            analysisRepository.save(analysis);

            // when
            AnalysisUpdatedEvent analysisUpdatedEvent =  new AnalysisUpdatedEvent(json);
            impactAnalysisEventListener.onAnalysisUpdatedEvent(analysisUpdatedEvent);

            // then
            var updatedByEventAnalysis = analysisRepository.findById(id);
            assertThat(updatedByEventAnalysis).isPresent();
            assertThat(updatedByEventAnalysis.get().getId()).isEqualTo(id);
        }

        @Test
        void testOnAnalysisUpdatedEvent_AnalysisDoesNotExists_ThrowEventEntityDoesNotExistException() {
            // given
            var id = UUID.randomUUID();
            var json = String.format("{\"id\":\"%s\"}", id.toString());

            // when
            AnalysisUpdatedEvent analysisUpdatedEvent = new AnalysisUpdatedEvent(json);

            // then
            assertThatExceptionOfType(EventEntityDoesNotExistException.class).isThrownBy(() -> impactAnalysisEventListener.onAnalysisUpdatedEvent(analysisUpdatedEvent));
        }
    }
}
