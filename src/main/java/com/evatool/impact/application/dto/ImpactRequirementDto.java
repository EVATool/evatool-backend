package com.evatool.impact.application.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@ApiModel(value = "ImpactRequirement", description = "Requirement of an impact")
public class ImpactRequirementDto {

    @ApiModelProperty
    @Getter
    @Setter
    private UUID id;

    @ApiModelProperty(required = true)
    @Getter
    @Setter
    @NotNull
    private String title;

    @ApiModelProperty(required = true)
    @Getter
    @Setter
    private String description;
}
