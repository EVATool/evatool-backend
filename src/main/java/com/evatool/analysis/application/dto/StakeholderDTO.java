package com.evatool.analysis.application.dto;

import com.evatool.analysis.domain.enums.StakeholderLevel;
import com.evatool.analysis.domain.model.AnalysisImpact;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
public class StakeholderDTO {
    private UUID rootEntityID;
    private String stakeholderName;
    private StakeholderLevel stakeholderLevel;
    private Integer priority;
    private Collection<AnalysisImpact> impactList = new ArrayList<>();
    private String guiId;

    @NotNull
    private UUID analysisId;

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
