package com.evatool.requirements.application.service;

import com.evatool.requirements.application.dto.RequirementDTO;
import com.evatool.requirements.domain.entity.*;
import com.evatool.requirements.domain.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.evatool.requirements.common.TestDataGenerator.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RequirementMapperTest {

    @Autowired
    private RequirementAnalysisRepository requirementAnalysisRepository;

    @Autowired
    private RequirementsVariantsRepository requirementsVariantsRepository;

    @Autowired
    RequirementsImpactsRepository requirementsImpactsRepository;

    @Autowired
    RequirementValueRepository requirementValueRepository;

    @Autowired
    RequirementRepository requirementRepository;

    @Autowired
    RequirementMapper requirementMapper;


    @Test
    void testOnApplicationMapper_RequirementMapperTest_map()
    {
        //create
        RequirementsAnalysis requirementsAnalysis = getRequirementsAnalysis();
        requirementAnalysisRepository.save(requirementsAnalysis);

        RequirementsVariant requirementsVariant1 = getRequirementsVariant();
        requirementsVariantsRepository.save(requirementsVariant1);

        RequirementsVariant requirementsVariant2 = getRequirementsVariant();
        requirementsVariantsRepository.save(requirementsVariant2);

        ArrayList<RequirementsVariant> requirementsVariants = new ArrayList<>();
        requirementsVariants.add(requirementsVariant1);
        requirementsVariants.add(requirementsVariant2);

        Requirement requirement = getRequirement(requirementsAnalysis,requirementsVariants);
        requirementRepository.save(requirement);

        RequirementValue requirementValue = getRequirementValue();
        requirementValueRepository.save(requirementValue);

        RequirementsImpact requirementsImpact = getRequirementsImpacts(requirementValue);
        requirementsImpactsRepository.save(requirementsImpact);

        RequirementDTO requirementDTO = requirementMapper.map(requirement);

        assertThat(requirementDTO).isNotNull();
        assertThat(requirementDTO.getRootEntityId()).isEqualTo(requirement.getId());

    }

    @Test
    void testOnApplicationMapper_RequirementMapperTest_mapList()
    {
        //create
        RequirementsAnalysis requirementsAnalysis = getRequirementsAnalysis();
        requirementAnalysisRepository.save(requirementsAnalysis);

        RequirementsVariant requirementsVariant1 = getRequirementsVariant();
        requirementsVariantsRepository.save(requirementsVariant1);

        RequirementsVariant requirementsVariant2 = getRequirementsVariant();
        requirementsVariantsRepository.save(requirementsVariant2);

        ArrayList<RequirementsVariant> requirementsVariants = new ArrayList<>();
        requirementsVariants.add(requirementsVariant1);
        requirementsVariants.add(requirementsVariant2);

        Requirement requirement = getRequirement(requirementsAnalysis,requirementsVariants);
        requirementRepository.save(requirement);

        RequirementValue requirementValue = getRequirementValue();
        requirementValueRepository.save(requirementValue);

        RequirementsImpact requirementsImpact = getRequirementsImpacts(requirementValue);
        requirementsImpactsRepository.save(requirementsImpact);

        ArrayList<Requirement> requirements = new ArrayList<>();
        requirements.add(requirement);
        List<RequirementDTO> requirementDTOs = requirementMapper.mapList(requirements);

        assertThat(requirementDTOs).isNotNull().isNotEmpty();
        assertThat(requirementDTOs.get(0).getRootEntityId()).isEqualTo(requirement.getId());

    }
}
