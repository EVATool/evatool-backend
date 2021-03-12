package com.evatool.requirements.service;

import com.evatool.requirements.controller.RequirementPointController;
import com.evatool.requirements.dto.RequirementDTO;
import com.evatool.requirements.entity.RequirementsImpact;
import com.evatool.requirements.entity.Requirement;
import com.evatool.requirements.entity.RequirementPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class RequirementMapper {

    @Autowired
    RequirementPointController requirementPointController;

    public List<RequirementDTO> mapList(Collection<Requirement> resultList) {
        List<RequirementDTO> requirementDTOList = new ArrayList<>();
        for(Requirement requirement : resultList){
            requirementDTOList.add(map(requirement));
        }
        return requirementDTOList;
    }

    public RequirementDTO map(Requirement requirement) {
        RequirementDTO requirementDTO = new RequirementDTO();
        requirementDTO.setRequirementTitle(requirement.getTitle());
        requirementDTO.setRootEntityId(requirement.getId());
        requirementDTO.setProjectID(requirement.getRequirementsAnalysis().getId());
        requirementDTO.setRequirementDescription(requirement.getDescription());
        requirement.getVariants().forEach(variants-> requirementDTO.getVariantsTitle().put(variants.getId(),variants.getTitle()));
        Collection<RequirementsImpact> requirementsImpactList = requirementPointController.getRequirementImpactByRequirement(requirement.getId());
        requirementsImpactList.forEach(impact -> {
            requirementDTO.getImpactDescription().put(impact.getId(),impact.getDescription());
            requirementDTO.getDimensions().add(impact.getRequirementDimension().getName());
            RequirementPoint requirementPoint = requirementPointController.getRequirementPointByRequirementAndRequirementsImpact(requirement,impact);
            requirementDTO.getRequirementImpactPoints().put(impact.getId(),requirementPoint.getPoints());
        });

        return requirementDTO;
    }
}
