package com.evatool.analysis.application.dto;

import com.evatool.analysis.domain.enums.StakeholderLevel;
import com.evatool.analysis.domain.model.AnalysisImpact;
import com.google.gson.Gson;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Getter
public class StakeholderDTO {
    private UUID rootEntityID;
    private String stakeholderName;
    private StakeholderLevel stakeholderLevel;
    private Integer priority;
    private Collection<AnalysisImpact> impactList = new ArrayList<>();
    private String guiId;

    @NotNull
    private UUID analysisId;

    public void setRootEntityID(UUID rootEntityID) {
        if (this.getRootEntityID() == null){
            throw new IllegalArgumentException("Id cannot be null");
        }
        this.rootEntityID = rootEntityID;
    }

    public void setStakeholderName(String stakeholderName) {
        if (stakeholderName == null) {
            throw new IllegalArgumentException("stakeholder Name cannot be null.");
        }
        this.stakeholderName = stakeholderName;
    }

    public void setAnalysisId(UUID analysisId) {
        if (analysisId == null) {
            throw new IllegalArgumentException("analysisId cannot be null.");
        }
        this.analysisId = analysisId;
    }

    public void setStakeholderLevel(StakeholderLevel stakeholderLevel) {
        if (stakeholderLevel == null) {
            throw new IllegalArgumentException("stakeholder Level description cannot be null.");
        }
        this.stakeholderLevel = stakeholderLevel;
    }

    public void setPriority(Integer priority) {
        if (priority == null) {
            throw new IllegalArgumentException("Priority cannot be null.");
        }
        this.priority = priority;
    }

    public void setGuiId(String guiId) {
        if (guiId == null) {
            throw new IllegalArgumentException("GuiId cannot be null.");
        }
        this.guiId = guiId;
    }

    public void setImpactList(Collection<AnalysisImpact> impactList) {
        if (this.getImpactList() == null){
            throw new IllegalArgumentException("Impact list cannot be null.");
        }
        this.impactList = impactList;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
