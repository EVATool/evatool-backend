package com.evatool.application.dto;

import com.evatool.application.validator.OverwriteMeritConstraint;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.awt.*;
import java.util.UUID;

@ApiModel(value = "RequirementDeltaDto")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@OverwriteMeritConstraint
public class RequirementDeltaDto extends SuperDto implements AnalysisChildDto {

    @ApiModelProperty(required = true, allowableValues = "range[-1.0,1.0]")
    @DecimalMin("-1.0")
    @DecimalMax("1.0")
    @NotNull
    private Float overwriteMerit;

    private Float originalMerit;

    private Color meritColor;

    @ApiModelProperty(required = true)
    @NotNull
    private UUID impactId;

    @ApiModelProperty(required = true)
    @NotNull
    private UUID requirementId;

    @ApiModelProperty(required = true)
    private UUID analysisId;

    public RequirementDeltaDto(Float overwriteMerit, Float originalMerit, Color meritColor, UUID impactId, UUID requirementId, UUID analysisId) {
        this.overwriteMerit = overwriteMerit;
        this.originalMerit = originalMerit;
        this.meritColor = meritColor;
        this.impactId = impactId;
        this.requirementId = requirementId;
        this.analysisId = analysisId;
    }
}
