package com.evatool.requirements.dto;
import com.google.gson.Gson;

import java.util.*;

public class RequirementDTO {

    private Map<UUID,String> impactDescription = new HashMap<>();
    private UUID rootEntityId;
    private UUID projectID;
    private String requirementTitle;
    private String requirementDescription;
    private Set<String> dimensions = new HashSet<>();
    private Map<UUID, Float> requirementImpactPoints = new HashMap<>();
    private Map<UUID,String> variantsTitle = new HashMap<>();

    public UUID getRootEntityId() {
        return rootEntityId;
    }

    public void setRootEntityId(UUID rootEntityId) {
        if (rootEntityId == null) {
            throw new IllegalArgumentException("RootEntityId cannot be null.");
        }
        this.rootEntityId = rootEntityId;
    }

    public String getRequirementTitle() {
        return requirementTitle;
    }

    public void setRequirementTitle(String requirementTitle) {
        if (requirementTitle == null) {
            throw new IllegalArgumentException("Requirement title cannot be null.");
        }
        this.requirementTitle = requirementTitle;
    }

    public String getRequirementDescription() {
        return requirementDescription;
    }

    public void setRequirementDescription(String requirementDescription) {
        if (requirementDescription == null) {
            throw new IllegalArgumentException("Requirement description cannot be null.");
        }
        this.requirementDescription = requirementDescription;
    }

    public Map<UUID,String> getImpactDescription() {
        return impactDescription;
    }

    public void setImpactDescription(Map<UUID,String> impactDescription) {
        if (impactDescription == null) {
            throw new IllegalArgumentException("Impact description cannot be null.");
        }
        this.impactDescription = impactDescription;
    }

    public Set<String> getDimensions() {
        return dimensions;
    }

    public void setDimensions(Set<String> dimensions) {
        if (dimensions == null) {
            throw new IllegalArgumentException("Dimensions cannot be null.");
        }
        this.dimensions = dimensions;
    }

    public Map<UUID, String> getVariantsTitle() {
        return variantsTitle;
    }

    public void setVariantsTitle(Map<UUID, String> variantsTitle) {
        if (variantsTitle == null) {
            throw new IllegalArgumentException("Variants title cannot be null.");
        }
        this.variantsTitle = variantsTitle;
    }

    public Map<UUID, Float> getRequirementImpactPoints() {
        return requirementImpactPoints;
    }

    public void setRequirementImpactPoints(Map<UUID, Float> requirementImpactPoints) {
        if (requirementImpactPoints == null) {
            throw new IllegalArgumentException("Requirement Impact Points cannot be null.");
        }
        this.requirementImpactPoints = requirementImpactPoints;
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
