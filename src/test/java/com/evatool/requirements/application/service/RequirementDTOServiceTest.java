package com.evatool.requirements.application.service;

import com.evatool.requirements.dto.RequirementDTO;
import com.evatool.requirements.entity.*;
import com.evatool.requirements.error.exceptions.EntityNotFoundException;
import com.evatool.requirements.repository.*;
import com.evatool.requirements.service.RequirementDTOService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static com.evatool.requirements.common.TestDataGenerator.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
class RequirementDTOServiceTest {

    @Autowired
    private RequirementAnalysisRepository requirementAnalysisRepository;

    @Autowired
    private RequirementsVariantsRepository requirementsVariantsRepository;

    @Autowired
    RequirementsImpactsRepository requirementsImpactsRepository;

    @Autowired
    RequirementDimensionRepository requirementDimensionRepository;

    @Autowired
    RequirementPointRepository requirementPointRepository;

    @Autowired
    RequirementRepository requirementRepository;

    @Autowired
    private RequirementDTOService requirementDTOService;


    @Test
    void testRequirementDTOService_ThrowException() {
        RequirementDimension requirementDimension = getRequirementDimension();
        requirementDimensionRepository.save(requirementDimension);

        RequirementsImpact requirementsImpact = getRequirementsImpacts(requirementDimension);
        requirementsImpactsRepository.save(requirementsImpact);

        Map<UUID,String> impactTitles = new HashMap<>();
        impactTitles.put(requirementsImpact.getId(),requirementsImpact.getDescription());

        RequirementsAnalysis requirementsAnalysis = getRequirementsAnalysis();
        requirementAnalysisRepository.save(requirementsAnalysis);

        RequirementsVariant requirementsVariant = getRequirementsVariant();
        requirementsVariantsRepository.save(requirementsVariant);
        Collection<RequirementsVariant> requirementsVariants = new ArrayList<>();
        requirementsVariants.add(requirementsVariant);

        Map<UUID,Integer> requirementImpactPoints = new HashMap<>();
        requirementImpactPoints.put(requirementsImpact.getId(),1);

        Map<UUID,String> variantsTitle = new HashMap<>();
        variantsTitle.put(requirementsVariant.getId(),requirementsVariant.getTitle());

        RequirementDTO requirementDTO = getRequirementDTO(impactTitles,requirementsAnalysis.getId(),variantsTitle);

        //create
        UUID uuidRequirement = requirementDTOService.create(requirementDTO);

        RequirementDTO requirementDTO2 = requirementDTOService.findById(uuidRequirement);
        assertThat(requirementDTO2).isNotNull();
        assertThat(requirementDTO2.getRootEntityId()).isEqualTo(uuidRequirement);

        //update
        String newTitle = "newTitle";
        requirementDTO2.setRequirementTitle(newTitle);
        requirementDTOService.update(requirementDTO2);

        RequirementDTO requirementDTO3 = requirementDTOService.findById(requirementDTO2.getRootEntityId());
        assertThat(requirementDTO3).isNotNull();
        assertThat(requirementDTO3.getRequirementTitle()).isEqualTo(newTitle);

        List<RequirementDTO> requirementDTOList = requirementDTOService.findAllForAnalysis(requirementDTO3.getProjectID());
        assertThat(requirementDTOList).isNotNull().isNotEmpty();
        assertThat(requirementDTOList.get(0).getProjectID()).isEqualTo(requirementDTO3.getProjectID());

        requirementDTOService.deleteRequirement(requirementDTOList.get(0).getRootEntityId());

        UUID uuidRootId = requirementDTOList.get(0).getRootEntityId();

        assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> requirementDTOService.findById(uuidRootId));
    }

    @Test
    void testRequirementDTOService_checkDto_ThrowException() {
        RequirementDTO requirementDTO = new RequirementDTO();

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> requirementDTOService.checkDto(requirementDTO));
        requirementDTO.setProjectID(UUID.randomUUID());
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> requirementDTOService.checkDto(requirementDTO));

        Map<UUID, Float> requirementImpactPoints = new HashMap<>();
        requirementDTO.setRequirementImpactPoints(requirementImpactPoints);
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> requirementDTOService.checkDto(requirementDTO));

    }
}
