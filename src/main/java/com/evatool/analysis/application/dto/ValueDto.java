package com.evatool.analysis.application.dto;

import com.evatool.analysis.domain.enums.ValueType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@ApiModel(value = "Value", description = "ImpactValue of an Values")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ValueDto {

    @ApiModelProperty
    private UUID id;

    @ApiModelProperty(required = true)
    private String name;

    @ApiModelProperty(required = true)
    private ValueType type;

    @ApiModelProperty(required = true)
    private String description;

    @ApiModelProperty(required = true)
    private AnalysisDTO analysis;

    @ApiModelProperty(required = true)
    private Boolean archived ;


}
