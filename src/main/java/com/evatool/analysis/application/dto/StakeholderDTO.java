package com.evatool.analysis.application.dto;

import com.evatool.analysis.domain.enums.StakeholderLevel;
import com.evatool.analysis.domain.model.AnalysisImpact;
import com.google.gson.Gson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
public class StakeholderDTO {

    @ApiModelProperty(example = "f33c6fa8-1697-11ea-8d71-362b9e155667")
    private UUID rootEntityID;

    @ApiModelProperty(example = "Marvin")
    private String stakeholderName;

    @ApiModelProperty(example = "Orga")
    private StakeholderLevel stakeholderLevel;

    @ApiModelProperty(example = "2")
    private Integer priority;

    @ApiModelProperty()
    private Collection<AnalysisImpact> impactList = new ArrayList<>();

    @ApiModelProperty(example = "ST1")
    private String guiId;

    @NotNull
    @ApiModelProperty(example = "f33c6e3a6-7894-11ea-8d71-362478955667")
    private UUID analysisId;

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
