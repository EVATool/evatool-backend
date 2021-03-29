package com.evatool.requirements.application.controller;

import com.evatool.requirements.application.dto.RequirementDTO;
import com.evatool.requirements.application.dto.RequirementPointDTO;
import com.evatool.requirements.application.dto.VariantsDTO;
import com.evatool.requirements.domain.entity.*;
import com.evatool.requirements.domain.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.EntityModel;

import static org.assertj.core.api.Assertions.*;

import java.util.*;

import static com.evatool.requirements.common.TestDataGenerator.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class RequirementPointControllerTest {

    @Autowired
    private RequirementPointController requirementPointController;

    @Autowired
    private RequirementsController requirementsController;

    @Autowired
    private RequirementAnalysisRepository requirementAnalysisRepository;

    @Autowired
    private RequirementsVariantsRepository requirementsVariantsRepository;

    @Autowired
    private RequirementRepository requirementRepository;

    @Autowired
    private RequirementsImpactsRepository requirementsImpactsRepository;

    @Autowired
    private RequirementDimensionRepository requirementDimensionRepository;

    @Test
    void testRequirementPointController_ThrowException() {

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

        RequirementDimension requirementDimension = getRequirementDimension();
        requirementDimensionRepository.save(requirementDimension);

        RequirementsImpact requirementsImpact = getRequirementsImpacts(requirementDimension);
        requirementsImpactsRepository.save(requirementsImpact);

        //update
        RequirementPoint requirementPoint = new RequirementPoint(requirementsImpact,1.0d);
        requirementPointController.newRequirementPoint(Arrays.asList(requirementPoint));
        requirement.getRequirementPointCollection().add(requirementPoint);
        requirementRepository.save(requirement);

        RequirementPoint requirementPoint1 = ((List<RequirementPoint>)requirement.getRequirementPointCollection()).get(0);
        assertThat(requirementPoint1.getId()).isEqualTo(requirementPoint.getId());

        Double newPoint = -1d;
        requirementPoint1.setPoints(newPoint);
        requirementPointController.updateRequirementPoint(Arrays.asList(requirementPoint1));

        RequirementPoint requirementPoint2 = ((List<RequirementPoint>)requirement.getRequirementPointCollection()).get(0);;
        assertThat(requirementPoint1.getPoints()).isEqualTo(newPoint);

        RequirementsImpact requirementsImpact1 = ((List<RequirementPoint>)requirement.getRequirementPointCollection()).get(0).getRequirementsImpact();
        assertThat(requirementsImpact.getId()).isEqualTo(requirementsImpact1.getId());

        //delete
        requirement.getRequirementPointCollection().remove(requirementPoint);
        requirementRepository.save(requirement);
        requirementPointController.deleteRequirementPoint(requirementPoint);
        assertThat(requirement.getRequirementPointCollection().size()).isEqualTo(0);
    }

    @Test
    void testRequirementPointController_createPoints_DTO() {

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

        Map<UUID, EntityModel<RequirementPointDTO>> requirementImpactPoints = new HashMap<>();
        requirementImpactPoints.put(requirementsImpact.getId(),RequirementPointDTO.generateLinks(new RequirementPointDTO(requirementsImpact.getId(), requirementsImpact.getDescription(),1d)));

        Map<UUID, EntityModel<VariantsDTO>> variantsTitle = new HashMap<>();
        variantsTitle.put(requirementsVariant.getId(), VariantsDTO.generateLinks(new VariantsDTO(requirementsVariant.getId(),requirementsVariant.getTitle())));

        RequirementDTO requirementDTO = getRequirementDTO(requirementImpactPoints,requirementsAnalysis.getAnalysisId(),variantsTitle);
        RequirementDTO requirementDTOObj = requirementsController.newRequirement(requirementDTO).getBody().getContent();

        Requirement requirement = getRequirement(requirementsAnalysis,requirementsVariants);
        requirementRepository.save(requirement);

        assert requirementDTOObj != null;
        Requirement requirement1 = requirementPointController.createPoints(requirement,requirementDTOObj);

        RequirementPoint requirementPoint1 = ((List<RequirementPoint>)requirement.getRequirementPointCollection()).get(0);

        assertThat(requirementPoint1).isNotNull();
        assertThat(requirementPoint1.getPoints()).isEqualTo(requirementDTO.getRequirementImpactPoints().get(requirementPoint1.getRequirementsImpact().getId()).getContent().getPoints());


        Map<UUID,EntityModel<RequirementPointDTO>> requirementImpactPoints2 = new HashMap<>();
        EntityModel<RequirementPointDTO> newPoint = RequirementPointDTO.generateLinks(new RequirementPointDTO(requirementsImpact.getId(),requirementsImpact.getDescription(),-1d));
        requirementImpactPoints2.put(requirementsImpact.getId(),newPoint);

        requirementDTOObj.setRequirementImpactPoints(requirementImpactPoints2);
        requirement1 = requirementPointController.updatePoints(requirement1,requirementDTOObj);

        RequirementPoint requirementPointUpdated = ((List<RequirementPoint>)requirement1.getRequirementPointCollection()).get(0);;

        assertThat(requirementPointUpdated).isNotNull();
        assertThat(requirementPointUpdated.getPoints()).isEqualTo(requirementDTOObj.getRequirementImpactPoints().get(requirementPointUpdated.getRequirementsImpact().getId()).getContent().getPoints());
        assertThat(requirementPointUpdated.getPoints()).isEqualTo(newPoint.getContent().getPoints());

        requirementPointController.deletePointsForRequirement(requirement1);
        assertThat(requirement1.getRequirementPointCollection().size()).isEqualTo(0);

    }

}