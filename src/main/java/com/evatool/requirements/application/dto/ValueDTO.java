package com.evatool.requirements.application.dto;

import com.evatool.requirements.application.controller.RequirementsController;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.hateoas.EntityModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class ValueDTO {

    @ApiModelProperty(example = "0f1ab1fe-9dff-11eb-a8b3-0242ac130003", required = true)
    private UUID entityId;
    @ApiModelProperty(example = "Title of the value")
    private String valueTitle;

    public ValueDTO() {
    }

    public ValueDTO(UUID entityId, String valueTitle) {
        this.entityId = entityId;
        this.valueTitle = valueTitle;
    }

    public UUID getEntityId() {
        return entityId;
    }

    public void setEntityId(UUID entityId) {
        this.entityId = entityId;
    }

    public String getValueTitle() {
        return valueTitle;
    }

    public void setValueTitle(String valuesTitle) {
        this.valueTitle = valuesTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValueDTO that = (ValueDTO) o;
        return Objects.equals(entityId, that.entityId) &&
                Objects.equals(valueTitle, that.valueTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityId, valueTitle);
    }

    public static EntityModel<ValueDTO> generateLinks(ValueDTO valueDTO){
        EntityModel<ValueDTO> requirementDTOEntityModel = EntityModel.of(valueDTO);
        requirementDTOEntityModel.add(linkTo(RequirementsController.class).slash("/values").slash(valueDTO.getEntityId()).withSelfRel());
        return requirementDTOEntityModel;
    }

    public static List<EntityModel<ValueDTO>> generateLinks(List<ValueDTO> valueDTOList){
        List<EntityModel<ValueDTO>> returnList = new ArrayList<>();
        valueDTOList.stream().forEach(e->returnList.add(generateLinks(e)));
        return returnList;
    }
}
