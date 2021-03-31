package com.evatool.impact.application.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@ApiModel(value = "Impact")
@EqualsAndHashCode
@ToString
public class ImpactDto {

    @ApiModelProperty
    @Getter
    @Setter
    private UUID id;

    @ApiModelProperty
    @Getter
    @Setter
    private String uniqueString;

    @ApiModelProperty(required = true, allowableValues = "range[-1.0,1.0]")
    @Getter
    @Setter
    @DecimalMin("-1.0")
    @DecimalMax("1.0")
    @NotNull
    private Double value;

    @ApiModelProperty(required = true)
    @Getter
    @Setter
    @NotNull
    private String description;

    @ApiModelProperty(required = true)
    @Getter
    @Setter
    @NotNull
    private DimensionDto dimension;

    @ApiModelProperty(required = true)
    @Getter
    @Setter
    @NotNull
    private ImpactStakeholderDto stakeholder;

    @ApiModelProperty(required = true)
    @Getter
    @Setter
    @NotNull
    private ImpactAnalysisDto analysis;
}
