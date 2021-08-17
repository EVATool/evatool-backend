package com.evatool.application.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@ApiModel(value = "ImpactDto")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class ImpactDto extends PrefixIdDto implements AnalysisChildDto {

    @ApiModelProperty(required = true, allowableValues = "range[-1.0,1.0]")
    @DecimalMin("-1.0")
    @DecimalMax("1.0")
    @NotNull
    @ImportExportInclude
    private Float merit;

    @ApiModelProperty(required = true)
    @NotNull
    @ImportExportInclude
    private String description;

    @ApiModelProperty
    private Boolean isGoal;

    @ApiModelProperty(required = true)
    @NotNull
    @ImportExportInclude
    private UUID valueId;

    @ApiModelProperty(required = true)
    @NotNull
    @ImportExportInclude
    private UUID stakeholderId;

    @ApiModelProperty(required = true)
    @NotNull
    private UUID analysisId;

    public ImpactDto(Float merit, String description, Boolean isGoal, UUID valueId, UUID stakeholderId, UUID analysisId) {
        this.merit = merit;
        this.description = description;
        this.isGoal = isGoal;
        this.valueId = valueId;
        this.stakeholderId = stakeholderId;
        this.analysisId = analysisId;
    }
}
