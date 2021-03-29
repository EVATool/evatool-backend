package com.evatool.requirements.application.service;

import com.evatool.requirements.application.dto.RequirementDTO;
import com.evatool.requirements.application.dto.VariantsDTO;
import com.evatool.requirements.domain.entity.*;
import com.evatool.requirements.common.exceptions.EntityNotFoundException;
import com.evatool.requirements.domain.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.EntityModel;

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

        Map<UUID, EntityModel<VariantsDTO>> variantsTitle = new HashMap<>();
        variantsTitle.put(requirementsVariant.getId(),VariantsDTO.generateLinks(new VariantsDTO(requirementsVariant.getId(),requirementsVariant.getTitle())));

        RequirementDTO requirementDTO = getRequirementDTO(requirementsAnalysis.getAnalysisId(),variantsTitle);

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



}