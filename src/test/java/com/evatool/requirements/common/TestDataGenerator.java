package com.evatool.requirements.common;

import com.evatool.requirements.application.dto.RequirementDTO;
import com.evatool.requirements.application.dto.RequirementPointDTO;
import com.evatool.requirements.application.dto.VariantsDTO;
import com.evatool.requirements.domain.entity.*;
import org.springframework.hateoas.EntityModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class TestDataGenerator {

    public static RequirementValue getRequirementDimension(){
        return new RequirementValue("DimensionTitle");
    }

    public static Requirement getRequirement(RequirementsAnalysis requirementsAnalysis, Collection<RequirementsVariant> variants) {
        return new Requirement("requirementTitle","descriptionTitle",requirementsAnalysis,variants);
    }

    public static RequirementsImpact getRequirementsImpacts(RequirementValue requirementValue) {
        return new RequirementsImpact("Description",10d, requirementValue);
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

        return new RequirementPoint(requirementsImpact, 3d);
    }

    public static RequirementPoint getRequirementGR(RequirementValue requirementValue, RequirementsAnalysis requirementsAnalysis, Collection<RequirementsVariant> variants) {

        return new RequirementPoint(getRequirementsImpacts(requirementValue), 3d);
    }

    public static RequirementDTO getRequirementDTO(UUID projectID,Map<UUID, EntityModel<VariantsDTO>> variantsTitle) {
        var requirementDTO = new RequirementDTO();

        requirementDTO.setProjectID(projectID);

        requirementDTO.setRequirementTitle("Title");
        requirementDTO.setRequirementDescription("Description");
        requirementDTO.setVariantsTitle(variantsTitle);

        return requirementDTO;
    }

    public static RequirementDTO getRequirementDTO(Map<UUID, EntityModel<RequirementPointDTO>> requirementImpactPoints, UUID projectID, Map<UUID,EntityModel<VariantsDTO>> variantsTitle) {
        var requirementDTO = new RequirementDTO();

        requirementDTO.setProjectID(projectID);

        requirementDTO.setRequirementTitle("Title");
        requirementDTO.setRequirementDescription("Description");
        requirementDTO.setVariantsTitle(variantsTitle);
        requirementDTO.setRequirementImpactPoints(requirementImpactPoints);

        return requirementDTO;
    }
}
