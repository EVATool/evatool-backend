package com.evatool.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
    @JsonProperty(value = "name", required = true)
    @NotNull
    private String name;

    @ApiModelProperty(required = true)
    @JsonProperty(value = "description", required = true)
    @NotNull
    private String description;

    @ApiModelProperty(required = true)
    @JsonProperty(value = "isTemplate", required = true)
    @NotNull
    private Boolean isTemplate;

    @ApiModelProperty
    @JsonProperty(value = "imageUrl")
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
