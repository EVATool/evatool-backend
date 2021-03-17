package com.evatool.requirements.domain.repository;

import com.evatool.requirements.common.TestDataGenerator;
import com.evatool.requirements.entity.Requirement;
import com.evatool.requirements.entity.RequirementsAnalysis;
import com.evatool.requirements.entity.RequirementsVariant;
import com.evatool.requirements.repository.RequirementAnalysisRepository;
import com.evatool.requirements.repository.RequirementRepository;
import com.evatool.requirements.repository.RequirementsVariantsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import static com.evatool.requirements.common.TestDataGenerator.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RequirementRepositoryTest {
    @Autowired
    private RequirementRepository requirementRepository;

    @Autowired
    private RequirementAnalysisRepository requirementAnalysisRepository;

    @Autowired
    private RequirementsVariantsRepository requirementsVariantsRepository;


    @Test
    void testFindById_ExistingRequirement_ReturnRequirement() {
        // given

        RequirementsAnalysis requirementsAnalysis = getRequirementsAnalysis();
        requirementAnalysisRepository.save(requirementsAnalysis);

        RequirementsVariant requirementsVariant = getRequirementsVariant();
        RequirementsVariant requirementsVariant1 = getRequirementsVariant();

        requirementsVariantsRepository.save(requirementsVariant);
        requirementsVariantsRepository.save(requirementsVariant1);

        Collection<RequirementsVariant> requirementsVariants = new ArrayList<>();
        requirementsVariants.add(requirementsVariant);
        requirementsVariants.add(requirementsVariant1);


        Requirement requirement = TestDataGenerator.getRequirement(requirementsAnalysis,requirementsVariants);
        requirementRepository.save(requirement);

        // when
        Requirement requirementFound = requirementRepository.findById(requirement.getId()).orElse(null);

        // then
        assertThat(requirementFound.getId()).isEqualTo(requirement.getId());
    }

    @Test
    void testSave_InsertedRequirement_IdIsNotNull() {
        // given

        RequirementsAnalysis requirementsAnalysis = getRequirementsAnalysis();
        requirementAnalysisRepository.save(requirementsAnalysis);

        RequirementsVariant requirementsVariant = getRequirementsVariant();
        RequirementsVariant requirementsVariant1 = getRequirementsVariant();

        requirementsVariantsRepository.save(requirementsVariant);
        requirementsVariantsRepository.save(requirementsVariant1);

        Collection<RequirementsVariant> requirementsVariants = new ArrayList<>();
        requirementsVariants.add(requirementsVariant);
        requirementsVariants.add(requirementsVariant1);


        Requirement requirement = TestDataGenerator.getRequirement(requirementsAnalysis,requirementsVariants);
        requirementRepository.save(requirement);

        // when
        requirementRepository.save(requirement);

        // then
        assertThat(requirement.getId()).isNotNull();
    }

    @Test
    void testSave_InsertedRequirement_IdIsUuid() {
        // given
        RequirementsAnalysis requirementsAnalysis = getRequirementsAnalysis();
        requirementAnalysisRepository.save(requirementsAnalysis);

        RequirementsVariant requirementsVariant = getRequirementsVariant();
        RequirementsVariant requirementsVariant1 = getRequirementsVariant();

        requirementsVariantsRepository.save(requirementsVariant);
        requirementsVariantsRepository.save(requirementsVariant1);

        Collection<RequirementsVariant> requirementsVariants = new ArrayList<>();
        requirementsVariants.add(requirementsVariant);
        requirementsVariants.add(requirementsVariant1);


        Requirement requirement = TestDataGenerator.getRequirement(requirementsAnalysis,requirementsVariants);
        requirementRepository.save(requirement);

        // when
        requirementRepository.save(requirement);

        // then
        UUID.fromString(requirement.getId().toString());

        assertThat(requirement.getId()).isNotNull();
    }

    @Test
    void testDelete_DeletedRequirement_ReturnNull() {
        // given
        RequirementsAnalysis requirementsAnalysis = getRequirementsAnalysis();
        requirementAnalysisRepository.save(requirementsAnalysis);

        RequirementsVariant requirementsVariant = getRequirementsVariant();
        RequirementsVariant requirementsVariant1 = getRequirementsVariant();

        requirementsVariantsRepository.save(requirementsVariant);
        requirementsVariantsRepository.save(requirementsVariant1);

        Collection<RequirementsVariant> requirementsVariants = new ArrayList<>();
        requirementsVariants.add(requirementsVariant);
        requirementsVariants.add(requirementsVariant1);


        Requirement requirement = TestDataGenerator.getRequirement(requirementsAnalysis,requirementsVariants);
        requirementRepository.save(requirement);
        requirementRepository.save(requirement);

        // when
        requirementRepository.delete(requirement);
        Requirement requirementFound = requirementRepository.findById(requirement.getId()).orElse(null);

        // then
        assertThat(requirementFound).isNull();
    }
}
