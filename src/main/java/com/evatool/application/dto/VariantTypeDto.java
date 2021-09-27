package com.evatool.application.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@ApiModel(value = "VariantTypeDto")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class VariantTypeDto extends SuperDto implements AnalysisChildDto {

    @ApiModelProperty(required = true)
    @NotNull
    @ImportExportInclude
    private String name;

    @ApiModelProperty(required = true)
    @NotNull
    @ImportExportInclude
    private String description;

    @ApiModelProperty(required = true)
    @NotNull
    private UUID analysisId;

    public VariantTypeDto(String name, String description, UUID analysisId) {
        this.name = name;
        this.description = description;
        this.analysisId = analysisId;
    }
}
