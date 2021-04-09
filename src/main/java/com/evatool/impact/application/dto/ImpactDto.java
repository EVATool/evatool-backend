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

    @ApiModelProperty(example = "f33c6fa8-1697-11ea-8d71-362b9e155667")
    @Getter
    @Setter
    private UUID id;

    @ApiModelProperty(example = "IMP01")
    @Getter
    @Setter
    private String uniqueString;

    @ApiModelProperty(example = "0.7",required = true, allowableValues = "range[-1.0,1.0]")
    @Getter
    @Setter
    @DecimalMin("-1.0")
    @DecimalMax("1.0")
    @NotNull
    private Double value;

    @ApiModelProperty(example = "If poorly implemented, ILA might contribute to a general loss of privacy by technical support solutions.", required = true)
    @Getter
    @Setter
    @NotNull
    private String description;

    @ApiModelProperty(required = true)
    @Getter
    @Setter
    @NotNull
    private ImpactValueDto valueEntity;

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
