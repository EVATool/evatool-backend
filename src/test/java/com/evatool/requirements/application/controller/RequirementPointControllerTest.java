package com.evatool.requirements.application.controller;

import com.evatool.requirements.controller.RequirementPointController;
import com.evatool.requirements.controller.RequirementsController;
import com.evatool.requirements.dto.RequirementDTO;
import com.evatool.requirements.entity.*;
import com.evatool.requirements.repository.*;
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
        RequirementPoint requirementPoint = new RequirementPoint(requirementsImpact,requirement,1);
        ArrayList<RequirementPoint> requirementPoints = new ArrayList<>();
        requirementPoints.add(requirementPoint);
        requirementPointController.newRequirementPoint(requirementPoints);

        RequirementPoint requirementPoint1 = requirementPointController.getRequirementPointByRequirementAndRequirementsImpact(requirement,requirementsImpact);
        assertThat(requirementPoint1.getId()).isEqualTo(requirementPoint.getId());

        int newPoint = -1;
        requirementPoint1.setPoints(newPoint);
        requirementPointController.updateRequirementPoint(Arrays.asList(requirementPoint1));

        RequirementPoint requirementPoint2 = requirementPointController.getRequirementPointByRequirementAndRequirementsImpact(requirement,requirementsImpact);
        assertThat(requirementPoint1.getPoints()).isEqualTo(newPoint);

        ArrayList<RequirementsImpact> requirementsImpact1 = (ArrayList<RequirementsImpact>) requirementPointController.getRequirementImpactByRequirement(requirement.getId());
        assertThat(requirementsImpact.getId()).isEqualTo(requirementsImpact1.get(0).getId());

        //delete
        requirementPointController.deleteRequirementPoint(requirementPoint);
        RequirementPoint requirementPoint3 = requirementPointController.getRequirementPointByRequirementAndRequirementsImpact(requirement,requirementsImpact);
        assertThat(requirementPoint3).isNull();
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

        RequirementDTO requirementDTO = getRequirementDTO(requirementImpactPoints,impactTitles,requirementsAnalysis.getId(),variantsTitle);
        RequirementDTO requirementDTOObj = requirementsController.newRequirement(requirementDTO).getBody().getContent();

        Requirement requirement = getRequirement(requirementsAnalysis,requirementsVariants);
        requirementRepository.save(requirement);

        assert requirementDTOObj != null;
        Requirement requirement1 = requirementPointController.createPoints(requirement,requirementDTOObj);

        RequirementPoint requirementPoint1 = requirementPointController.getRequirementPointByRequirementAndRequirementsImpact(requirement1,requirementsImpact);

        assertThat(requirementPoint1).isNotNull();
        assertThat(requirementPoint1.getPoints()).isEqualTo(requirementDTO.getRequirementImpactPoints().get(requirementPoint1.getRequirementsImpact().getId()));


        Map<UUID,Integer> requirementImpactPoints2 = new HashMap<>();
        int newPoint = -1;
        requirementImpactPoints2.put(requirementsImpact.getId(),newPoint);

        requirementDTOObj.setRequirementImpactPoints(requirementImpactPoints2);
        requirementPointController.updatePoints(requirement1,requirementDTOObj);

        RequirementPoint requirementPointUpdated = requirementPointController.getRequirementPointByRequirementAndRequirementsImpact(requirement1,requirementsImpact);

        assertThat(requirementPointUpdated).isNotNull();
        assertThat(requirementPointUpdated.getPoints()).isEqualTo(requirementDTOObj.getRequirementImpactPoints().get(requirementPointUpdated.getRequirementsImpact().getId()));
        assertThat(requirementPointUpdated.getPoints()).isEqualTo(newPoint);

        requirementPointController.deletePointsForRequirement(requirement1);
        RequirementPoint requirementPoint2 = requirementPointController.getRequirementPointByRequirementAndRequirementsImpact(requirement1,requirementsImpact);
        assertThat(requirementPoint2).isNull();

    }

}
