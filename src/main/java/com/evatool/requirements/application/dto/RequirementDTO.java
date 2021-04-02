package com.evatool.requirements.application.dto;
import com.google.gson.Gson;
import org.springframework.hateoas.EntityModel;

import java.util.*;

public class RequirementDTO {

    private UUID rootEntityId;
    private UUID projectID;
    private String uniqueString;
    private String requirementDescription;
    private Set<EntityModel<ValueDTO>> values = new HashSet<>();
    private Map<UUID, EntityModel<RequirementPointDTO>> requirementPointDTOMap = new HashMap<>();
    private Map<UUID,EntityModel<VariantsDTO>> variantsTitle = new HashMap<>();

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

    public Map<UUID, EntityModel<VariantsDTO>> getVariantsTitle() {
        return variantsTitle;
    }

    public void setVariantsTitle(Map<UUID, EntityModel<VariantsDTO>> variantsTitle) {
        if (variantsTitle == null) {
            throw new IllegalArgumentException("Variants title cannot be null.");
        }
        this.variantsTitle = variantsTitle;
    }

    public Map<UUID, EntityModel<RequirementPointDTO>> getRequirementImpactPoints() {
        return requirementPointDTOMap;
    }

    public void setRequirementImpactPoints(Map<UUID, EntityModel<RequirementPointDTO>> requirementPointDTOMap) {
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
