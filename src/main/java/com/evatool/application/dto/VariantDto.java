package com.evatool.application.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@ApiModel(value = "VariantDto")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class VariantDto extends PrefixIdDto implements AnalysisChildDto {

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
    private Boolean archived;

    @ApiModelProperty(required = true)
    @NotNull
    @ImportExportInclude
    private UUID variantTypeId;

    @ApiModelProperty(required = true)
    @NotNull
    private UUID analysisId;

    public VariantDto(String name, String description, Boolean archived, UUID variantTypeId, UUID analysisId) {
        this.name = name;
        this.description = description;
        this.archived = archived;
        this.variantTypeId = variantTypeId;
        this.analysisId = analysisId;
    }
}
