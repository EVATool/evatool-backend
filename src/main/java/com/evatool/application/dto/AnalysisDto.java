package com.evatool.application.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@ApiModel(value = "AnalysisDto")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class AnalysisDto extends PrefixIdDto {

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
    private Boolean isTemplate;

    @ApiModelProperty
    @ImportExportInclude
    private String imageUrl;

    @ApiModelProperty
    @EqualsAndHashCode.Exclude
    private Long lastUpdated;

    @ApiModelProperty
    @EqualsAndHashCode.Exclude
    private String lastUpdatedPreformatted;

    public AnalysisDto(String name, String description, Boolean isTemplate, String imageUrl, Long lastUpdated, String lastUpdatedPreformatted) {
        this.name = name;
        this.description = description;
        this.isTemplate = isTemplate;
        this.imageUrl = imageUrl;
        this.lastUpdated = lastUpdated;
        this.lastUpdatedPreformatted = lastUpdatedPreformatted;
    }
}
