package com.evatool.application.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@ApiModel(value = "RequirementDto")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class RequirementDto extends PrefixIdDto implements AnalysisChildDto {

    @ApiModelProperty(required = true)
    @NotNull
    @ImportExportInclude
    private String description;

    @ApiModelProperty(required = true)
    @NotNull
    private UUID analysisId;

    @ApiModelProperty
    @ImportExportInclude
    private UUID[] variantIds;

    public RequirementDto(String description, UUID analysisId, UUID[] variantIds) {
        this.description = description;
        this.analysisId = analysisId;
        this.variantIds = variantIds;
    }
}
