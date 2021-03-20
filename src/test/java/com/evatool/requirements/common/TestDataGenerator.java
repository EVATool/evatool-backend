package com.evatool.requirements.common;

import com.evatool.requirements.application.dto.RequirementDTO;
import com.evatool.requirements.domain.entity.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class TestDataGenerator {

    public static RequirementDimension getRequirementDimension(){
        return new RequirementDimension("DimensionTitle");
    }

    public static Requirement getRequirement(RequirementsAnalysis requirementsAnalysis, Collection<RequirementsVariant> variants) {
        return new Requirement("requirementTitle","descriptionTitle",requirementsAnalysis,variants);
    }

    public static RequirementsImpact getRequirementsImpacts(RequirementDimension requirementDimension) {
        return new RequirementsImpact("Description",10, requirementDimension);
    }

    public static RequirementsVariant getRequirementsVariant() {
        return new RequirementsVariant("Title","Description");
    }

    public static RequirementsAnalysis getRequirementsAnalysis()
    {
        return new RequirementsAnalysis();
    }

    public static Collection<RequirementsVariant> getRequirementsVariants(){
        Collection<RequirementsVariant> requirementsVariants = new ArrayList<>();
        requirementsVariants.add(new RequirementsVariant("Title1","Description1"));
        requirementsVariants.add(new RequirementsVariant("Title2","Description2"));
        requirementsVariants.add(new RequirementsVariant("Title3","Description3"));

        return requirementsVariants;
    }

    public static RequirementPoint getRequirementGR(Requirement requirement, RequirementsImpact requirementsImpact) {

        return new RequirementPoint(requirementsImpact, requirement, 3);
    }

    public static RequirementPoint getRequirementGR(RequirementDimension requirementDimension,RequirementsAnalysis requirementsAnalysis, Collection<RequirementsVariant> variants) {

        return new RequirementPoint(getRequirementsImpacts(requirementDimension), getRequirement(requirementsAnalysis,variants), 3);
    }

    public static RequirementDTO getRequirementDTO(Map<UUID,String> impactTitles,UUID projectID,Map<UUID,String> variantsTitle) {
        var requirementDTO = new RequirementDTO();

        requirementDTO.setImpactDescription(impactTitles);
        requirementDTO.setProjectID(projectID);

        requirementDTO.setRequirementTitle("Title");
        requirementDTO.setRequirementDescription("Description");
        requirementDTO.setVariantsTitle(variantsTitle);

        return requirementDTO;
    }

    public static RequirementDTO getRequirementDTO(Map<UUID,Integer> requirementImpactPoints,Map<UUID,String> impactTitles,UUID projectID,Map<UUID,String> variantsTitle) {
        var requirementDTO = new RequirementDTO();

        requirementDTO.setImpactDescription(impactTitles);
        requirementDTO.setProjectID(projectID);

        requirementDTO.setRequirementTitle("Title");
        requirementDTO.setRequirementDescription("Description");
        requirementDTO.setVariantsTitle(variantsTitle);
        requirementDTO.setRequirementImpactPoints(requirementImpactPoints);

        return requirementDTO;
    }
}
