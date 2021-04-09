package com.evatool.impact.application.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@ApiModel(value = "ImpactAnalysis", description = "Analysis an impact belongs to")
@EqualsAndHashCode
@ToString
public class ImpactAnalysisDto {

    @ApiModelProperty(example = "a550faa0-c4c9-48e5-8748-16b916212bac")
    @Getter
    @Setter
    private UUID id;
}
