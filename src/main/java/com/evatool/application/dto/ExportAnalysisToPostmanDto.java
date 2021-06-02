package com.evatool.application.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@ApiModel(value = "ExportAnalysisToPostmanDto")
@EqualsAndHashCode
@ToString
@Getter
@Setter
@NoArgsConstructor
public class ExportAnalysisToPostmanDto {

    @ApiModelProperty
    @NotNull
    private String evatoolVersion;

    @ApiModelProperty
    @NotNull
    private String postmanCollectionJson;

    @ApiModelProperty
    @NotNull
    private String postmanEnvironmentJson;

    public ExportAnalysisToPostmanDto(String evatoolVersion, String postmanCollectionJson, String postmanEnvironmentJson) {
        this.evatoolVersion = evatoolVersion;
        this.postmanCollectionJson = postmanCollectionJson;
        this.postmanEnvironmentJson = postmanEnvironmentJson;
    }
}
