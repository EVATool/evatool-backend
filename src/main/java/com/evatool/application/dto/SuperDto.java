package com.evatool.application.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@EqualsAndHashCode
@ToString
public class SuperDto {

    @ApiModelProperty
    @Getter
    @Setter
    @ImportExportInclude
    private UUID id;

}
