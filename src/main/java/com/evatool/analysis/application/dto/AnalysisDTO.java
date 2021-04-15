package com.evatool.analysis.application.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class AnalysisDTO {

    @ApiModelProperty(example = "f33c6fa8-1697-11ea-8d71-362b9e155667")
    private UUID rootEntityID;

    @ApiModelProperty(example = "TestAnalysis")
    private String analysisName;

    @ApiModelProperty(example = "Lorem ipsum dolor sit amet, consectetur adipisici elit")
    private String analysisDescription;

    @ApiModelProperty(example = "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD.jpg")
    private String image;

    @ApiModelProperty(example = "31.12.9999")
    private Date lastUpdate;

    @ApiModelProperty(example = "True")
    private Boolean isTemplate;

    @ApiModelProperty(example = "ANA01")
    private String uniqueString;
}
