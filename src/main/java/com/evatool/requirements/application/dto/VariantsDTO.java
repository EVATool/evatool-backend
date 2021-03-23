package com.evatool.requirements.application.dto;

import com.evatool.requirements.application.controller.RequirementsController;
import org.springframework.hateoas.EntityModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class VariantsDTO {


    private UUID entityId;
    private String variantsTitle;

    public VariantsDTO() {
    }

    public VariantsDTO(UUID entityId, String variantsTitle) {
        this.entityId = entityId;
        this.variantsTitle = variantsTitle;
    }

    public UUID getEntityId() {
        return entityId;
    }

    public void setEntityId(UUID entityId) {
        this.entityId = entityId;
    }

    public String getVariantsTitle() {
        return variantsTitle;
    }

    public void setVariantsTitle(String variantsTitle) {
        this.variantsTitle = variantsTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VariantsDTO that = (VariantsDTO) o;
        return Objects.equals(entityId, that.entityId) &&
                Objects.equals(variantsTitle, that.variantsTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityId, variantsTitle);
    }


    public static EntityModel<VariantsDTO> generateLinks(VariantsDTO variantsDTO){
        EntityModel<VariantsDTO> requirementDTOEntityModel = EntityModel.of(variantsDTO);
        requirementDTOEntityModel.add(linkTo(RequirementsController.class).slash("/variants").slash(variantsDTO.getEntityId()).withSelfRel());
        return requirementDTOEntityModel;
    }

    public static List<EntityModel<VariantsDTO>> generateLinks(List<VariantsDTO> variantsDTOList){
        List<EntityModel<VariantsDTO>> returnList = new ArrayList<>();
        variantsDTOList.stream().forEach(e->returnList.add(generateLinks(e)));
        return returnList;
    }
}
