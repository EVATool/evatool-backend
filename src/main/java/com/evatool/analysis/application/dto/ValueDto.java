package com.evatool.analysis.application.dto;

import com.evatool.analysis.domain.enums.ValueType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.UUID;

@ApiModel(value = "Value", description = "ImpactValue of an Values")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ValueDto {

    @ApiModelProperty(example ="f33c6fa8-1697-11ea-8d71-362b9e155667")
    private UUID id;

    @ApiModelProperty(example = " Care ", required = true)
    private String name;

    @ApiModelProperty(example = " Social ",required = true)
    private ValueType type;

    @ApiModelProperty(example = " Lorem ipsum dolor sit amet, consectetur adipisici elit ",required = true)
    private String description;

    @ApiModelProperty(required = true)
    private AnalysisDTO analysis;

    @ApiModelProperty(example = " TRUE ",required = true)
    private Boolean archived ;


}
