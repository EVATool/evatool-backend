package com.evatool.application.dto;

import com.evatool.common.enums.ValueType;
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
    @ImportExportInclude
    private ValueType type;

    @ApiModelProperty(required = true)
    @NotNull
    @ImportExportInclude
    private Boolean archived;

    @ApiModelProperty(required = true)
    @NotNull
    private UUID analysisId;

    public ValueDto(String name, String description, ValueType type, Boolean archived, UUID analysisId) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.archived = archived;
        this.analysisId = analysisId;
    }
}
