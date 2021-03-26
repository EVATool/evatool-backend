package com.evatool.requirements.domain.repository;

import com.evatool.requirements.domain.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import static com.evatool.requirements.common.TestDataGenerator.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RequirementPointRepositoryTest {

    @Autowired
    private RequirementPointRepository requirementPointRepository;

    @Autowired
    private RequirementRepository requirementRepository;

    @Autowired
    private RequirementsImpactsRepository requirementsImpactsRepository;

    @Autowired
    private RequirementValueRepository requirementDimensionRepository;

    @Autowired
    private RequirementAnalysisRepository requirementAnalysisRepository;

    @Autowired
    private RequirementsVariantsRepository requirementsVariantsRepository;

    @Test
    void testFindById_ExistingRequirementGR_ReturnRequirement() {
        // given
        RequirementValue requirementValue = getRequirementDimension();
        requirementDimensionRepository.save(requirementValue);

        RequirementsAnalysis requirementsAnalysis = getRequirementsAnalysis();
        requirementAnalysisRepository.save(requirementsAnalysis);

        RequirementsVariant requirementsVariant = getRequirementsVariant();
        RequirementsVariant requirementsVariant1 = getRequirementsVariant();

        requirementsVariantsRepository.save(requirementsVariant);
        requirementsVariantsRepository.save(requirementsVariant1);

        Collection<RequirementsVariant> requirementsVariants = new ArrayList<>();
        requirementsVariants.add(requirementsVariant);
        requirementsVariants.add(requirementsVariant1);


        RequirementsImpact requirementsImpact = getRequirementsImpacts(requirementValue);
        Requirement requirement = getRequirement(requirementsAnalysis,requirementsVariants);
        requirementRepository.save(requirement);
        requirementsImpactsRepository.save(requirementsImpact);

        RequirementPoint requirementPoint = getRequirementGR(requirement, requirementsImpact);
        requirementPointRepository.save(requirementPoint);

        // when
        RequirementPoint requirementPointFound = requirementPointRepository.findById(requirementPoint.getId()).orElse(null);

        // then
        assertThat(requirementPointFound.getId()).isEqualTo(requirementPoint.getId());
    }

    @Test
    void testSave_InsertedRequirementGR_IdIsNotNull() {

        RequirementValue requirementValue = getRequirementDimension();
        requirementDimensionRepository.save(requirementValue);

        RequirementsAnalysis requirementsAnalysis = getRequirementsAnalysis();
        requirementAnalysisRepository.save(requirementsAnalysis);

        RequirementsVariant requirementsVariant = getRequirementsVariant();
        RequirementsVariant requirementsVariant1 = getRequirementsVariant();

        requirementsVariantsRepository.save(requirementsVariant);
        requirementsVariantsRepository.save(requirementsVariant1);

        Collection<RequirementsVariant> requirementsVariants = new ArrayList<>();
        requirementsVariants.add(requirementsVariant);
        requirementsVariants.add(requirementsVariant1);


        RequirementsImpact requirementsImpact = getRequirementsImpacts(requirementValue);
        Requirement requirement = getRequirement(requirementsAnalysis,requirementsVariants);
        requirementRepository.save(requirement);
        requirementsImpactsRepository.save(requirementsImpact);
        // given
        RequirementPoint requirementPoint = getRequirementGR(requirement, requirementsImpact);

        // when
        requirementPointRepository.save(requirementPoint);

        // then
        assertThat(requirementPoint.getId()).isNotNull();
    }

    @Test
    void testSave_InsertedRequirementGR_Id_IsUuid() {

        RequirementValue requirementValue = getRequirementDimension();
        requirementDimensionRepository.save(requirementValue);

        RequirementsAnalysis requirementsAnalysis = getRequirementsAnalysis();
        requirementAnalysisRepository.save(requirementsAnalysis);

        RequirementsVariant requirementsVariant = getRequirementsVariant();
        RequirementsVariant requirementsVariant1 = getRequirementsVariant();

        requirementsVariantsRepository.save(requirementsVariant);
        requirementsVariantsRepository.save(requirementsVariant1);

        Collection<RequirementsVariant> requirementsVariants = new ArrayList<>();
        requirementsVariants.add(requirementsVariant);
        requirementsVariants.add(requirementsVariant1);

        RequirementsImpact requirementsImpact = getRequirementsImpacts(requirementValue);
        Requirement requirement = getRequirement(requirementsAnalysis,requirementsVariants);
        requirementRepository.save(requirement);
        requirementsImpactsRepository.save(requirementsImpact);
        // given
        RequirementPoint requirementPoint = getRequirementGR(requirement, requirementsImpact);

        // when
        requirementPointRepository.save(requirementPoint);

        // then
        UUID.fromString(requirementPoint.getId().toString());

        assertThat(requirementsImpact.getId()).isNotNull();
    }

    @Test
    void testDelete_DeletedRequirementGR_ReturnNull() {

        RequirementValue requirementValue = getRequirementDimension();
        requirementDimensionRepository.save(requirementValue);

        RequirementsAnalysis requirementsAnalysis = getRequirementsAnalysis();
        requirementAnalysisRepository.save(requirementsAnalysis);

        RequirementsVariant requirementsVariant = getRequirementsVariant();
        RequirementsVariant requirementsVariant1 = getRequirementsVariant();

        requirementsVariantsRepository.save(requirementsVariant);
        requirementsVariantsRepository.save(requirementsVariant1);

        Collection<RequirementsVariant> requirementsVariants = new ArrayList<>();
        requirementsVariants.add(requirementsVariant);
        requirementsVariants.add(requirementsVariant1);

        RequirementsImpact requirementsImpact = getRequirementsImpacts(requirementValue);
        Requirement requirement = getRequirement(requirementsAnalysis,requirementsVariants);
        requirementRepository.save(requirement);
        requirementsImpactsRepository.save(requirementsImpact);
        // given
        RequirementPoint requirementPoint = getRequirementGR(requirement, requirementsImpact);
        requirementPointRepository.save(requirementPoint);

        // when
        requirementPointRepository.delete(requirementPoint);
        RequirementPoint requirementPointFound = requirementPointRepository.findById(requirementPoint.getId()).orElse(null);

        // then
        assertThat(requirementPointFound).isNull();
    }
}
