package com.evatool.application.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.UUID;

public interface AnalysisChildDto {

    @ApiModelProperty(required = true)
    UUID getAnalysisId();

}
