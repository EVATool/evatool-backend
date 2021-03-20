package com.evatool.requirements.application.controller;

import com.evatool.requirements.application.dto.RequirementDTO;
import com.evatool.requirements.domain.entity.*;
import com.evatool.requirements.domain.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        RequirementPoint requirementPoint = new RequirementPoint(requirementsImpact,1);
        requirementPointController.newRequirementPoint(Arrays.asList(requirementPoint));
        requirement.getRequirementPointCollection().add(requirementPoint);
        requirementRepository.save(requirement);

        RequirementPoint requirementPoint1 = ((List<RequirementPoint>)requirement.getRequirementPointCollection()).get(0);
        assertThat(requirementPoint1.getId()).isEqualTo(requirementPoint.getId());

        int newPoint = -1;
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

        Map<UUID,Integer> requirementImpactPoints = new HashMap<>();
        requirementImpactPoints.put(requirementsImpact.getId(),1);

        Map<UUID,String> variantsTitle = new HashMap<>();
        variantsTitle.put(requirementsVariant.getId(),requirementsVariant.getTitle());

        RequirementDTO requirementDTO = getRequirementDTO(requirementImpactPoints,impactTitles,requirementsAnalysis.getAnalysisId(),variantsTitle);
        RequirementDTO requirementDTOObj = requirementsController.newRequirement(requirementDTO).getBody().getContent();

        Requirement requirement = getRequirement(requirementsAnalysis,requirementsVariants);
        requirementRepository.save(requirement);

        assert requirementDTOObj != null;
        Requirement requirement1 = requirementPointController.createPoints(requirement,requirementDTOObj);

        RequirementPoint requirementPoint1 = ((List<RequirementPoint>)requirement.getRequirementPointCollection()).get(0);

        assertThat(requirementPoint1).isNotNull();
        assertThat(requirementPoint1.getPoints()).isEqualTo(requirementDTO.getRequirementImpactPoints().get(requirementPoint1.getRequirementsImpact().getId()));


        Map<UUID,Integer> requirementImpactPoints2 = new HashMap<>();
        int newPoint = -1;
        requirementImpactPoints2.put(requirementsImpact.getId(),newPoint);

        requirementDTOObj.setRequirementImpactPoints(requirementImpactPoints2);
        requirement1 = requirementPointController.updatePoints(requirement1,requirementDTOObj);

        RequirementPoint requirementPointUpdated = ((List<RequirementPoint>)requirement1.getRequirementPointCollection()).get(0);;

        assertThat(requirementPointUpdated).isNotNull();
        assertThat(requirementPointUpdated.getPoints()).isEqualTo(requirementDTOObj.getRequirementImpactPoints().get(requirementPointUpdated.getRequirementsImpact().getId()));
        assertThat(requirementPointUpdated.getPoints()).isEqualTo(newPoint);

        requirementPointController.deletePointsForRequirement(requirement1);
        assertThat(requirement1.getRequirementPointCollection().size()).isEqualTo(0);

    }

}
