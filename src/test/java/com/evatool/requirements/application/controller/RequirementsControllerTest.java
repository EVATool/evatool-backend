package com.evatool.requirements.application.controller;


import com.evatool.requirements.application.dto.RequirementDTO;
import com.evatool.requirements.application.dto.RequirementPointDTO;
import com.evatool.requirements.application.dto.VariantsDTO;
import com.evatool.requirements.domain.entity.*;
import com.evatool.requirements.common.exceptions.EntityNotFoundException;
import com.evatool.requirements.domain.repository.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.EntityModel;

import java.util.*;

import static com.evatool.requirements.common.TestDataGenerator.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class RequirementsControllerTest {

    @Autowired
    private RequirementsController requirementsController;

    @Autowired
    private RequirementAnalysisRepository requirementAnalysisRepository;

    @Autowired
    private RequirementsVariantsRepository requirementsVariantsRepository;

    @Autowired
    RequirementsImpactsRepository requirementsImpactsRepository;

    @Autowired
    RequirementValueRepository requirementValueRepository;

    @Autowired
    RequirementPointRepository requirementPointRepository;

    @Autowired
    RequirementRepository requirementRepository;

    @Test
    void testRequirementController_ThrowException() {

        RequirementValue requirementValue = getRequirementValue();
        requirementValueRepository.save(requirementValue);

        RequirementsImpact requirementsImpact = getRequirementsImpacts(requirementValue);
        requirementsImpactsRepository.save(requirementsImpact);

        Map<UUID,String> impactTitles = new HashMap<>();
        impactTitles.put(requirementsImpact.getId(),requirementsImpact.getDescription());

        RequirementsAnalysis requirementsAnalysis = getRequirementsAnalysis();
        requirementAnalysisRepository.save(requirementsAnalysis);

        RequirementsVariant requirementsVariant = getRequirementsVariant();
        requirementsVariantsRepository.save(requirementsVariant);
        Collection<RequirementsVariant> requirementsVariants = new ArrayList<>();
        requirementsVariants.add(requirementsVariant);

        Set<EntityModel<RequirementPointDTO>> requirementImpactPoints = new HashSet<>();
        requirementImpactPoints.add(RequirementPointDTO.generateLinks(new RequirementPointDTO(requirementsImpact.getId(),requirementsImpact.getDescription(),1d)));

        Set<EntityModel<VariantsDTO>> variantsTitle = new HashSet<>();
        variantsTitle.add(VariantsDTO.generateLinks(new VariantsDTO(requirementsVariant.getId(),requirementsVariant.getTitle(),new Boolean(false))));

        RequirementDTO requirementDTO = getRequirementDTO(requirementImpactPoints,requirementsAnalysis.getAnalysisId(),variantsTitle);
        //RequirementDTO requirementDTO = getRequirementDTO(impactTitles,requirementsAnalysis.getId(),variantsTitle);

        //create requirement
        RequirementDTO requirementDTOObj = requirementsController.newRequirement(requirementDTO).getBody().getContent();

        //check is requirement created
        assertThat(requirementsController.getRequirementById(requirementDTOObj.getRootEntityId())).isNotNull();

        //change requirement title
        String testTitle = "REQnull";
        requirementDTOObj.setUniqueString(testTitle);
        requirementsController.updateRequirement(requirementDTOObj);

        //check is requirement title changed
        RequirementDTO requirementAfterUpdate = requirementsController.getRequirementById(requirementDTOObj.getRootEntityId()).getContent();

        assertThat(requirementAfterUpdate.getUniqueString()).isEqualTo(testTitle);

        //delete requirement
        UUID idRequirement = requirementDTOObj.getRootEntityId();
        requirementsController.deleteRequirement(idRequirement);

        //check is requirement deleted


        UUID uuidRootEntity = requirementDTOObj.getRootEntityId();
        assertThrows(EntityNotFoundException.class, ()-> requirementsController.getRequirementById(uuidRootEntity));

    }

}
