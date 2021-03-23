package com.evatool.requirements.application.service;

import com.evatool.requirements.application.controller.RequirementPointController;
import com.evatool.requirements.application.dto.DimensionDTO;
import com.evatool.requirements.application.dto.RequirementDTO;
import com.evatool.requirements.application.dto.RequirementPointDTO;
import com.evatool.requirements.application.dto.VariantsDTO;
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
        requirement.getVariants().forEach(variants-> requirementDTO.getVariantsTitle().put(variants.getId(),VariantsDTO.generateLinks(new VariantsDTO(variants.getId(),variants.getTitle()))));
        requirement.getRequirementPointCollection().forEach(requirementPoint -> {
            requirementDTO.getDimensions().add(DimensionDTO.generateLinks(new DimensionDTO(requirementPoint.getRequirementsImpact().getRequirementDimension().getId(),requirementPoint.getRequirementsImpact().getRequirementDimension().getName())));
            requirementDTO.getRequirementImpactPoints().put(requirementPoint.getRequirementsImpact().getId(),RequirementPointDTO.generateLinks(new RequirementPointDTO(requirementPoint.getRequirementsImpact().getId(),requirementPoint.getRequirementsImpact().getDescription(),requirementPoint.getPoints())));
        });

        return requirementDTO;
    }
}
