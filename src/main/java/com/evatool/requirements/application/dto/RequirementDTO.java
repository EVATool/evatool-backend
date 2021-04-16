package com.evatool.requirements.application.dto;
import com.google.gson.Gson;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.hateoas.EntityModel;

import java.util.*;

public class RequirementDTO {

    @ApiModelProperty(example = "f33c6fa8-1697-11ea-8d71-362b9e155667")
    private UUID rootEntityId;
    @ApiModelProperty(example = "57be65dc-9dfe-11eb-a8b3-0242ac130003", required = true)
    private UUID projectID;
    @ApiModelProperty(example = "REQ1")
    private String uniqueString;
    @ApiModelProperty(example = "Simple description for a requirement.")
    private String requirementDescription;
    private Set<EntityModel<ValueDTO>> values = new HashSet<>();
    private Set<EntityModel<RequirementPointDTO>> requirementPointDTOMap = new HashSet<>();
    private Set<EntityModel<VariantsDTO>> variantsTitle = new HashSet<>();

    public UUID getRootEntityId() {
        return rootEntityId;
    }

    public void setRootEntityId(UUID rootEntityId) {
        if (rootEntityId == null) {
            throw new IllegalArgumentException("RootEntityId cannot be null.");
        }
        this.rootEntityId = rootEntityId;
    }

    public String getUniqueString() {
        return uniqueString;
    }

    public void setUniqueString(String uniqueString) {
        if (uniqueString == null) {
            throw new IllegalArgumentException("Requirement title cannot be null.");
        }
        this.uniqueString = uniqueString;
    }

    public String getRequirementDescription() {
        return requirementDescription;
    }

    public void setRequirementDescription(String requirementDescription) {
        this.requirementDescription = requirementDescription;
    }


    public Set<EntityModel<ValueDTO>> getValues() {
        return values;
    }

    public void setValues(Set<EntityModel<ValueDTO>> values) {
        if (values == null) {
            throw new IllegalArgumentException("ImpactValue cannot be null.");
        }
        this.values = values;
    }

    public Set<EntityModel<VariantsDTO>> getVariantsTitle() {
        return variantsTitle;
    }

    public void setVariantsTitle(Set<EntityModel<VariantsDTO>> variantsTitle) {
        if (variantsTitle == null) {
            throw new IllegalArgumentException("Variants title cannot be null.");
        }
        this.variantsTitle = variantsTitle;
    }

    public Set<EntityModel<RequirementPointDTO>> getRequirementImpactPoints() {
        return requirementPointDTOMap;
    }

    public void setRequirementImpactPoints(Set<EntityModel<RequirementPointDTO>> requirementPointDTOMap) {
        if (requirementPointDTOMap == null) {
            throw new IllegalArgumentException("Requirement Impact Points cannot be null.");
        }
        this.requirementPointDTOMap = requirementPointDTOMap;
    }

    public UUID getProjectID() {
        return projectID;
    }

    public void setProjectID(UUID projectID) {
        if (projectID == null) {
            throw new IllegalArgumentException("ProjectID cannot be null.");
        }
        this.projectID = projectID;
    }

    @Override
    public String toString()  {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
