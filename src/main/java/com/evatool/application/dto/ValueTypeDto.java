package com.evatool.application.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@ApiModel(value = "ValueTypeDto")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class ValueTypeDto extends SuperDto implements AnalysisChildDto {

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

    public ValueTypeDto(String name, String description, UUID analysisId) {
        this.name = name;
        this.description = description;
        this.analysisId = analysisId;
    }
}
