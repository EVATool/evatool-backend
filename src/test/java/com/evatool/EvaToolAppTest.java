package com.evatool;

import com.evatool.analysis.enums.StakeholderLevel;
import com.evatool.analysis.events.AnalysisEventPublisher;
import com.evatool.analysis.model.Analysis;
import com.evatool.analysis.model.Stakeholder;
import com.evatool.global.event.analysis.AnalysisCreatedEvent;
import com.evatool.global.event.analysis.AnalysisDeletedEvent;
import com.evatool.global.event.stakeholder.StakeholderCreatedEvent;
import com.evatool.global.event.stakeholder.StakeholderDeletedEvent;
import com.evatool.global.event.stakeholder.StakeholderUpdatedEvent;
import com.evatool.global.event.variants.VariantCreatedEvent;
import com.evatool.global.event.variants.VariantDeletedEvent;
import com.evatool.global.event.variants.VariantUpdatedEvent;
import com.evatool.impact.domain.repository.ImpactAnalysisRepository;
import com.evatool.impact.domain.repository.ImpactStakeholderRepository;
import com.evatool.requirements.repository.RequirementAnalysisRepository;
import com.evatool.requirements.repository.RequirementsVariantsRepository;
import com.evatool.variants.domain.entities.Variant;
import com.evatool.variants.domain.events.VariantsEventPublisher;
import com.evatool.variants.domain.repositories.VariantRepository;
import com.evatool.variants.domain.repositories.VariantsAnalysisRepository;
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

        @Autowired
        AnalysisEventPublisher analysisEventPublisher;

        @Autowired
        ImpactStakeholderRepository impactStakeholderRepository;

        @BeforeEach
        @AfterAll
        void clearDatabase() {
            impactStakeholderRepository.deleteAll();
        }

        Stakeholder createDummyStakeholder() {
            return new Stakeholder("Patient", 1, StakeholderLevel.NATURAL_PERSON);
        }

        // Received by: Impact
        @Test
        void testCreatedEvent_ModulesReceive_ModulesPersist() {
            // given
            var stakeholder = createDummyStakeholder();
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
            var stakeholder = createDummyStakeholder();
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
            var stakeholder = createDummyStakeholder();
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

        @Autowired
        AnalysisEventPublisher analysisEventPublisher;

        @Autowired
        ImpactAnalysisRepository impactAnalysisRepository;

        @Autowired
        RequirementAnalysisRepository requirementAnalysisRepository;

        @Autowired
        VariantsAnalysisRepository variantsAnalysisRepository;

        @BeforeEach
        @AfterAll
        void clearDatabase() {
            impactAnalysisRepository.deleteAll();
            requirementAnalysisRepository.deleteAll();
            variantsAnalysisRepository.deleteAll();
        }

        Analysis createDummyAnalysis() {
            return new Analysis("Name", "Description");
        }

        // Received by: Impact, Requirement, Variant
        @Test
        void testCreatedEvent_ModulesReceive_ModulesPersist() {
            // given
            var analysis = createDummyAnalysis();
            var analysisCreatedEvent = new AnalysisCreatedEvent(analysis.toJson());

            // when
            analysisEventPublisher.publishEvent(analysisCreatedEvent);
            var impactAnalysis = impactAnalysisRepository.findById(analysis.getAnalysisId()).orElse(null);
            var requirementAnalysis = requirementAnalysisRepository.findById(analysis.getAnalysisId()).orElse(null);
            var variantAnalysis = variantsAnalysisRepository.findById(analysis.getAnalysisId()).orElse(null);

            // then
            assertThat(impactAnalysis).isNotNull();
            assertThat(requirementAnalysis).isNotNull();
            assertThat(variantAnalysis).isNotNull();

            assertThat(impactAnalysis.getId()).isEqualTo(analysis.getAnalysisId());
            assertThat(requirementAnalysis.getAnalysisId()).isEqualTo(analysis.getAnalysisId());
            assertThat(variantAnalysis.getAnalysisId()).isEqualTo(analysis.getAnalysisId());
        }

        // Received by: Impact, Requirement, Variant
        @Test
        void testDeletedEvent_ModulesReceive_ModulesPersist() {
            // given
            var analysis = createDummyAnalysis();
            var analysisCreatedEvent = new AnalysisCreatedEvent(analysis.toJson());
            analysisEventPublisher.publishEvent(analysisCreatedEvent);

            // when
            var analysisDeletedEvent = new AnalysisDeletedEvent(analysis.toJson());
            analysisEventPublisher.publishEvent(analysisDeletedEvent);
            var impactAnalysis = impactAnalysisRepository.findById(analysis.getAnalysisId()).orElse(null);
            var requirementAnalysis = requirementAnalysisRepository.findById(analysis.getAnalysisId()).orElse(null);
            var variantAnalysis = variantsAnalysisRepository.findById(analysis.getAnalysisId()).orElse(null);

            // then
            assertThat(impactAnalysis).isNull();
            assertThat(requirementAnalysis).isNull();
            assertThat(variantAnalysis).isNull();
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

        @Autowired
        VariantsEventPublisher variantsEventPublisher;

        @Autowired
        RequirementsVariantsRepository requirementsVariantsRepository;

        @Autowired
        VariantRepository variantRepository;

        @BeforeEach
        @AfterAll
        void clearDatabase() {
            requirementsVariantsRepository.deleteAll();
        }

        Variant createDummyVariant() {
            var variant = new Variant();
            variant.setTitle("Title");
            return variantRepository.save(variant);
        }

        // Received by: Requirement
        @Test
        void testCreatedEvent_ModulesReceive_ModulesPersist() {
            // given
            var variant = createDummyVariant();
            var variantCreatedEvent = new VariantCreatedEvent(variant.toJson());

            // when
            variantsEventPublisher.publishEvent(variantCreatedEvent);
            var requirementsVariant = requirementsVariantsRepository.findById(variant.getId()).orElse(null);

            // then
            assertThat(requirementsVariant).isNotNull();
            assertThat(requirementsVariant.getId()).isEqualTo(variant.getId());
            assertThat(requirementsVariant.getTitle()).isEqualTo(variant.getTitle());
            assertThat(requirementsVariant.getDescription()).isEqualTo(variant.getDescription());
        }

        // Received by: Requirement
        @Test
        void testUpdatedEvent_ModulesReceive_ModulesPersist() {
            // given
            var variant = createDummyVariant();
            var variantCreatedEvent = new VariantCreatedEvent(variant.toJson());
            variantsEventPublisher.publishEvent(variantCreatedEvent);

            // when
            variant.setTitle("New_Title");
            var variantUpdatedEvent = new VariantUpdatedEvent(variant.toJson());
            variantsEventPublisher.publishEvent(variantUpdatedEvent);
            var requirementsVariant = requirementsVariantsRepository.findById(variant.getId()).orElse(null);

            // then
            assertThat(requirementsVariant).isNotNull();
            assertThat(requirementsVariant.getId()).isEqualTo(variant.getId());
            assertThat(requirementsVariant.getTitle()).isEqualTo(variant.getTitle());
            assertThat(requirementsVariant.getDescription()).isEqualTo(variant.getDescription());
        }

        // Received by: Requirement
        @Test
        void testDeletedEvent_ModulesReceive_ModulesPersist() {
            // given
            var variant = createDummyVariant();
            var variantCreatedEvent = new VariantCreatedEvent(variant.toJson());
            variantsEventPublisher.publishEvent(variantCreatedEvent);

            // when
            var variantDeletedEvent = new VariantDeletedEvent(variant.toJson());
            variantsEventPublisher.publishEvent(variantDeletedEvent);
            var requirementsVariant = requirementsVariantsRepository.findById(variant.getId()).orElse(null);

            // then
            assertThat(requirementsVariant).isNull();
        }
    }
}
