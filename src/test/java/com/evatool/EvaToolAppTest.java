package com.evatool;

import com.evatool.analysis.domain.enums.StakeholderLevel;
import com.evatool.analysis.domain.events.AnalysisEventPublisher;
import com.evatool.analysis.domain.model.Analysis;
import com.evatool.analysis.domain.model.Stakeholder;
import com.evatool.global.event.analysis.AnalysisCreatedEvent;
import com.evatool.global.event.analysis.AnalysisDeletedEvent;
import com.evatool.global.event.stakeholder.StakeholderCreatedEvent;
import com.evatool.global.event.stakeholder.StakeholderDeletedEvent;
import com.evatool.global.event.stakeholder.StakeholderUpdatedEvent;
import com.evatool.global.event.variants.VariantCreatedEvent;
import com.evatool.global.event.variants.VariantDeletedEvent;
import com.evatool.global.event.variants.VariantUpdatedEvent;
import com.evatool.impact.common.ImpactValueType;
import com.evatool.impact.domain.entity.Impact;
import com.evatool.impact.domain.entity.ImpactAnalysis;
import com.evatool.impact.domain.entity.ImpactStakeholder;
import com.evatool.impact.domain.entity.ImpactValue;
import com.evatool.impact.domain.event.ImpactEventPublisher;
import com.evatool.impact.domain.event.json.ImpactValueEventPublisher;
import com.evatool.impact.domain.repository.ImpactAnalysisRepository;
import com.evatool.impact.domain.repository.ImpactRepository;
import com.evatool.impact.domain.repository.ImpactStakeholderRepository;
import com.evatool.impact.domain.repository.ImpactValueRepository;
import com.evatool.requirements.domain.repository.RequirementAnalysisRepository;
import com.evatool.requirements.domain.repository.RequirementValueRepository;
import com.evatool.requirements.domain.repository.RequirementsImpactsRepository;
import com.evatool.requirements.domain.repository.RequirementsVariantsRepository;
import com.evatool.variants.domain.entities.Variant;
import com.evatool.variants.domain.events.VariantsEventPublisher;
import com.evatool.variants.domain.repositories.VariantRepository;
import com.evatool.variants.domain.repositories.VariantsAnalysisRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

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
            assertThat(impactAnalysis.getId()).isEqualTo(analysis.getAnalysisId());

            assertThat(requirementAnalysis).isNotNull();
            assertThat(requirementAnalysis.getAnalysisId()).isEqualTo(analysis.getAnalysisId());

            assertThat(variantAnalysis).isNotNull();
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
    class ValueEvent {

        @Autowired
        ImpactValueRepository impactValueRepository;

        @Autowired
        ImpactValueEventPublisher impactValueEventPublisher;

        @Autowired
        RequirementValueRepository requirementValueRepository;

        @BeforeEach
        @AfterAll
        void clearDatabase() {
            requirementValueRepository.deleteAll();
            impactValueRepository.deleteAll();
        }

        ImpactValue createDummyValue() {
            return impactValueRepository.save(new ImpactValue(UUID.randomUUID(), "Name", ImpactValueType.SOCIAL, "Description"));
        }

        // Received by: Requirement
        @Test
        void testCreatedEvent_ModulesReceive_ModulesPersist() {
            // given
            var value = createDummyValue();

            // when
            impactValueEventPublisher.publishValueCreated(value);
            var requirementValue = requirementValueRepository.findById(value.getId()).orElse(null);

            // then
            assertThat(requirementValue).isNotNull();
            assertThat(requirementValue.getId()).isEqualTo(value.getId());
            assertThat(requirementValue.getName()).isEqualTo(value.getName());
        }

        // Received by: Requirement
        @Test
        void testUpdatedEvent_ModulesReceive_ModulesPersist() {
            // given
            var value = createDummyValue();
            impactValueEventPublisher.publishValueCreated(value);

            // when
            value.setName("new_name");
            impactValueEventPublisher.publishValueUpdated(value);
            var requirementValue = requirementValueRepository.findById(value.getId()).orElse(null);

            // then
            assertThat(requirementValue).isNotNull();
            assertThat(requirementValue.getId()).isEqualTo(value.getId());
            assertThat(requirementValue.getName()).isEqualTo(value.getName());
        }

        // Received by: Requirement
        @Test
        void testDeletedEvent_ModulesReceive_ModulesPersist() {
            // given
            var value = createDummyValue();
            impactValueEventPublisher.publishValueCreated(value);

            // when
            impactValueEventPublisher.publishValueDeleted(value);
            var requirementValue = requirementValueRepository.findById(value.getId()).orElse(null);

            // then
            assertThat(requirementValue).isNull();
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class ImpactEvent {

        @Autowired
        ImpactRepository impactRepository;

        @Autowired
        ImpactValueRepository impactValueRepository;

        @Autowired
        ImpactValueEventPublisher impactValueEventPublisher;

        @Autowired
        ImpactStakeholderRepository impactStakeholderRepository;

        @Autowired
        ImpactAnalysisRepository impactAnalysisRepository;

        @Autowired
        ImpactEventPublisher impactEventPublisher;

        @Autowired
        RequirementsImpactsRepository requirementsImpactsRepository;

        @Autowired
        RequirementValueRepository requirementValueRepository;


//        @Autowired
//        Muss neu gebaut werden
//        AnalysisImpactRepository analysisImpactRepository;

        @BeforeEach
        @AfterAll
        void clearDatabase() {
            requirementsImpactsRepository.deleteAll();
            requirementValueRepository.deleteAll();
//            analysisImpactRepository.deleteAll();
            impactRepository.deleteAll();
            impactValueRepository.deleteAll();
            impactStakeholderRepository.deleteAll();
            impactAnalysisRepository.deleteAll();
        }

        Impact createDummyImpact() {
            var value = impactValueRepository.save(new ImpactValue(UUID.randomUUID(), "Name", ImpactValueType.SOCIAL, "Description"));

            impactValueEventPublisher.publishValueCreated(value);
            var stakeholder = impactStakeholderRepository.save(new ImpactStakeholder(UUID.randomUUID(), "Name"));
            var analysis = impactAnalysisRepository.save(new ImpactAnalysis(UUID.randomUUID()));
            return impactRepository.save(new Impact(0.0, "Description", value, stakeholder, analysis));
        }

        // Received by: Requirement, Analysis
        @Test
        void testCreatedEvent_ModulesReceive_ModulesPersist() {
            // given
            var impact = createDummyImpact();

            // when
            impactEventPublisher.publishImpactCreated(impact);
            var requirementImpact = requirementsImpactsRepository.findById(impact.getId()).orElse(null);
            //var analysisImpact = analysisImpactRepository.findById(impact.getId()).orElse(null);

            // then
            assertThat(requirementImpact).isNotNull();
            assertThat(requirementImpact.getId()).isEqualTo(impact.getId());
            //assertThat(requirementImpact.getImpactValue()).isEqualTo(impact.getImpactValue());
            assertThat(requirementImpact.getDescription()).isEqualTo(impact.getDescription());
            //assertThat(requirementImpact.getRequirementDimension().getId()).isEqualTo(impact.getDimension().getId());
            //assertThat(requirementImpact.getRequirementDimension().getName()).isEqualTo(impact.getDimension().getName());

//            assertThat(analysisImpact).isNotNull();
//            assertThat(analysisImpact.getId()).isEqualTo(impact.getId());
//            assertThat(analysisImpact.getImpactValue()).isEqualTo(impact.getImpactValue());
//            assertThat(analysisImpact.getDescription()).isEqualTo(impact.getDescription());
            //assertThat(analysisImpact.getDimension()).isEqualTo(impact.getDimension());
        }

        // Received by: Requirement, Analysis
        @Test
        void testUpdatedEvent_ModulesReceive_ModulesPersist() {
            // given
            var impact = createDummyImpact();
            impactEventPublisher.publishImpactCreated(impact);

            // when
            impact.setValue(1.0);
            impactEventPublisher.publishImpactUpdated(impact);
            var requirementImpact = requirementsImpactsRepository.findById(impact.getId()).orElse(null);
//            var analysisImpact = analysisImpactRepository.findById(impact.getId()).orElse(null);

            // then
            assertThat(requirementImpact).isNotNull();
            //assertThat(requirementImpact.getImpactValue()).isEqualTo(impact.getImpactValue());

//            assertThat(analysisImpact).isNotNull();
//            assertThat(analysisImpact.getImpactValue()).isEqualTo(impact.getImpactValue());
        }

        // Received by: Requirement, Analysis
        @Test
        void testDeletedEvent_ModulesReceive_ModulesPersist() {
            // given
            var impact = createDummyImpact();
            impactEventPublisher.publishImpactCreated(impact);

            // when
            impactEventPublisher.publishImpactDeleted(impact);
            var requirementImpact = requirementsImpactsRepository.findById(impact.getId()).orElse(null);
//            var analysisImpact = analysisImpactRepository.findById(impact.getId()).orElse(null);

            // then
//            assertThat(requirementImpact).isNull();
//            assertThat(analysisImpact).isNull();
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
            variantRepository.deleteAll();
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
