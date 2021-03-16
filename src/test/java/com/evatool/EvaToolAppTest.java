package com.evatool;

import com.evatool.analysis.events.AnalysisEventPublisher;
import com.evatool.analysis.model.Stakeholder;
import com.evatool.global.event.stakeholder.StakeholderCreatedEvent;
import com.evatool.global.event.stakeholder.StakeholderDeletedEvent;
import com.evatool.global.event.stakeholder.StakeholderUpdatedEvent;
import com.evatool.impact.domain.repository.ImpactStakeholderRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("non-async")
class EvaToolAppTest {


    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class StakeholderEvent {

        @BeforeEach
        @AfterAll
        void clearDatabase() {
            impactStakeholderRepository.deleteAll();
        }

        @Autowired
        AnalysisEventPublisher analysisEventPublisher;

        @Autowired
        ImpactStakeholderRepository impactStakeholderRepository;

        // Received by: Impact
        @Test
        void testCreatedEvent_ModulesReceive_ModulesPersist() {
            // given
            var stakeholder = new Stakeholder();
            stakeholder.setStakeholderName("Patient");
            var stakeholderCreatedEvent = new StakeholderCreatedEvent(this, stakeholder.toJson());

            // when
            analysisEventPublisher.publishEvent(stakeholderCreatedEvent);
            var impactStakeholder = impactStakeholderRepository.findById(stakeholder.getStakeholderId()).orElse(null);

            // then
            assertThat(impactStakeholder).isNotNull();
            assertThat(impactStakeholder.getId()).isEqualTo(stakeholder.getStakeholderId());
            assertThat(impactStakeholder.getName()).isEqualTo(stakeholder.getStakeholderName());
        }

        // Received by: Impact
        @Test
        void testUpdatedEvent_ModulesReceive_ModulesPersist() {
            // given
            var stakeholder = new Stakeholder();
            stakeholder.setStakeholderName("Patient");
            var stakeholderCreatedEvent = new StakeholderCreatedEvent(this, stakeholder.toJson());
            analysisEventPublisher.publishEvent(stakeholderCreatedEvent);

            // when
            stakeholder.setStakeholderName("Family");
            var stakeholderUpdatedEvent = new StakeholderUpdatedEvent(this, stakeholder.toJson());
            analysisEventPublisher.publishEvent(stakeholderUpdatedEvent);
            var impactStakeholder = impactStakeholderRepository.findById(stakeholder.getStakeholderId()).orElse(null);

            // then
            assertThat(impactStakeholder).isNotNull();
            assertThat(impactStakeholder.getId()).isEqualTo(stakeholder.getStakeholderId());
            assertThat(impactStakeholder.getName()).isEqualTo(stakeholder.getStakeholderName());
        }

        // Received by: Impact
        @Test
        void testDeletedEvent_ModulesReceive_ModulesPersist() {
            // given
            var stakeholder = new Stakeholder();
            stakeholder.setStakeholderName("Patient");
            var stakeholderCreatedEvent = new StakeholderCreatedEvent(this, stakeholder.toJson());
            analysisEventPublisher.publishEvent(stakeholderCreatedEvent);

            // when
            var stakeholderDeletedEvent = new StakeholderDeletedEvent(this, stakeholder.toJson());
            analysisEventPublisher.publishEvent(stakeholderDeletedEvent);
            var impactStakeholder = impactStakeholderRepository.findById(stakeholder.getStakeholderId()).orElse(null);

            // then
            assertThat(impactStakeholder).isNull();
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class AnalysisEvent {

        @BeforeEach
        @AfterAll
        void clearDatabase() {

        }

        // Received by: ?
        @Test
        void testCreatedEvent_ModulesReceive_ModulesPersist() {
            // given

            // when

            // then

        }

        // Received by: ?
        @Test
        void testUpdatedEvent_ModulesReceive_ModulesPersist() {
            // given

            // when

            // then

        }

        // Received by: ?
        @Test
        void testDeletedEvent_ModulesReceive_ModulesPersist() {
            // given

            // when

            // then

        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class DimensionEvent {

        @BeforeEach
        @AfterAll
        void clearDatabase() {

        }

        // Received by: ?
        @Test
        void testCreatedEvent_ModulesReceive_ModulesPersist() {
            // given

            // when

            // then

        }

        // Received by: ?
        @Test
        void testUpdatedEvent_ModulesReceive_ModulesPersist() {
            // given

            // when

            // then

        }

        // Received by: ?
        @Test
        void testDeletedEvent_ModulesReceive_ModulesPersist() {
            // given

            // when

            // then

        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class ImpactEvent {

        @BeforeEach
        @AfterAll
        void clearDatabase() {

        }

        // Received by: ?
        @Test
        void testCreatedEvent_ModulesReceive_ModulesPersist() {
            // given

            // when

            // then

        }

        // Received by: ?
        @Test
        void testUpdatedEvent_ModulesReceive_ModulesPersist() {
            // given

            // when

            // then

        }

        // Received by: ?
        @Test
        void testDeletedEvent_ModulesReceive_ModulesPersist() {
            // given

            // when

            // then

        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class VariantEvent {

        @BeforeEach
        @AfterAll
        void clearDatabase() {

        }

        // Received by: ?
        @Test
        void testCreatedEvent_ModulesReceive_ModulesPersist() {
            // given

            // when

            // then

        }

        // Received by: ?
        @Test
        void testUpdatedEvent_ModulesReceive_ModulesPersist() {
            // given

            // when

            // then

        }

        // Received by: ?
        @Test
        void testDeletedEvent_ModulesReceive_ModulesPersist() {
            // given

            // when

            // then

        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class RequirementEvent {

        @BeforeEach
        @AfterAll
        void clearDatabase() {

        }

        // Received by: ?
        @Test
        void testCreatedEvent_ModulesReceive_ModulesPersist() {
            // given

            // when

            // then

        }

        // Received by: ?
        @Test
        void testUpdatedEvent_ModulesReceive_ModulesPersist() {
            // given

            // when

            // then

        }

        // Received by: ?
        @Test
        void testDeletedEvent_ModulesReceive_ModulesPersist() {
            // given

            // when

            // then

        }
    }
}
