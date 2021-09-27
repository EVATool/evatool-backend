package com.evatool.application.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@ApiModel(value = "ValueDto")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class ValueDto extends SuperDto implements AnalysisChildDto {

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
    private UUID valueTypeId;

    @ApiModelProperty(required = true)
    @NotNull
    @ImportExportInclude
    private Boolean archived;

    @ApiModelProperty(required = true)
    @NotNull
    private UUID analysisId;

    public ValueDto(String name, String description, Boolean archived, UUID valueTypeId, UUID analysisId) {
        this.name = name;
        this.description = description;
        this.archived = archived;
        this.valueTypeId = valueTypeId;
        this.analysisId = analysisId;
    }
}
