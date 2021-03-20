package com.evatool.requirements.application.service;

import com.evatool.requirements.application.controller.RequirementPointController;
import com.evatool.requirements.application.dto.RequirementDTO;
import com.evatool.requirements.domain.entity.RequirementsImpact;
import com.evatool.requirements.domain.entity.Requirement;
import com.evatool.requirements.domain.entity.RequirementPoint;
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
        requirementDTO.setProjectID(requirement.getRequirementsAnalysis().getAnalysisId());
        requirementDTO.setRequirementDescription(requirement.getDescription());
        requirement.getVariants().forEach(variants-> requirementDTO.getVariantsTitle().put(variants.getId(),variants.getTitle()));
        requirement.getRequirementPointCollection().forEach(requirementPoint -> {
            requirementDTO.getImpactDescription().put(requirementPoint.getRequirementsImpact().getId(),requirementPoint.getRequirementsImpact().getDescription());
            requirementDTO.getDimensions().add(requirementPoint.getRequirementsImpact().getRequirementDimension().getName());
            requirementDTO.getRequirementImpactPoints().put(requirementPoint.getRequirementsImpact().getId(),requirementPoint.getPoints());
        });

        return requirementDTO;
    }
}
