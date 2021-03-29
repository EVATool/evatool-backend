package com.evatool.requirements.application.dto;

import com.evatool.requirements.application.controller.RequirementsController;
import org.springframework.hateoas.EntityModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class DimensionDTO {

    private UUID entityId;
    private String dimensionTitle;

    public DimensionDTO() {
    }

    public DimensionDTO(UUID entityId, String dimensionTitle) {
        this.entityId = entityId;
        this.dimensionTitle = dimensionTitle;
    }

    public UUID getEntityId() {
        return entityId;
    }

    public void setEntityId(UUID entityId) {
        this.entityId = entityId;
    }

    public String getDimensionTitle() {
        return dimensionTitle;
    }

    public void setDimensionTitle(String dimensionTitle) {
        this.dimensionTitle = dimensionTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DimensionDTO that = (DimensionDTO) o;
        return Objects.equals(entityId, that.entityId) &&
                Objects.equals(dimensionTitle, that.dimensionTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityId, dimensionTitle);
    }

    public static EntityModel<DimensionDTO> generateLinks(DimensionDTO dimensionDTO){
        EntityModel<DimensionDTO> requirementDTOEntityModel = EntityModel.of(dimensionDTO);
        requirementDTOEntityModel.add(linkTo(RequirementsController.class).slash("/dimensions").slash(dimensionDTO.getEntityId()).withSelfRel());
        return requirementDTOEntityModel;
    }

    public static List<EntityModel<DimensionDTO>> generateLinks(List<DimensionDTO> dimensionDTOList){
        List<EntityModel<DimensionDTO>> returnList = new ArrayList<>();
        dimensionDTOList.stream().forEach(e->returnList.add(generateLinks(e)));
        return returnList;
    }
}
