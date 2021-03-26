package com.evatool.requirements.application.dto;

import com.evatool.requirements.application.controller.RequirementsController;
import org.springframework.hateoas.EntityModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class RequirementPointDTO {


    private UUID entityId;
    private String impactDescription;
    private Double points;

    public RequirementPointDTO() {
    }

    public RequirementPointDTO(UUID entityId, String impactDescription, Double points) {
        this.entityId = entityId;
        this.impactDescription = impactDescription;
        this.points = points;
    }

    public UUID getEntityId() {
        return entityId;
    }

    public void setEntityId(UUID entityId) {
        this.entityId = entityId;
    }

    public String getImpactDescription() {
        return impactDescription;
    }

    public void setImpactDescription(String impactDescription) {
        this.impactDescription = impactDescription;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequirementPointDTO impactDTO = (RequirementPointDTO) o;
        return Objects.equals(entityId, impactDTO.entityId) &&
                Objects.equals(impactDescription, impactDTO.impactDescription) &&
                Objects.equals(points, impactDTO.points);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityId, impactDescription, points);
    }

    public static EntityModel<RequirementPointDTO> generateLinks(RequirementPointDTO requirementPointDTO){
        EntityModel<RequirementPointDTO> requirementDTOEntityModel = EntityModel.of(requirementPointDTO);
        requirementDTOEntityModel.add(linkTo(RequirementsController.class).slash("/impact").slash(requirementPointDTO.getEntityId()).withSelfRel());
        return requirementDTOEntityModel;
    }

    public static List<EntityModel<RequirementPointDTO>> generateLinks(List<RequirementPointDTO> requirementPointDTOS){
        List<EntityModel<RequirementPointDTO>> returnList = new ArrayList<>();
        requirementPointDTOS.stream().forEach(e->returnList.add(generateLinks(e)));
        return returnList;
    }
}
