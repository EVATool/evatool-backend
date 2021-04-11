package com.evatool.impact.application.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@ApiModel(value = "ImpactValue", description = "Value of an Impact")
@EqualsAndHashCode
@ToString
public class ImpactValueDto {

    @ApiModelProperty(example = "7b715d34-991e-11eb-a8b3-0242ac130003")
    @Getter
    @Setter
    private UUID id;

    @ApiModelProperty(required = true, example = "Safety")
    @Getter
    @Setter
    @NotNull
    private String name;

    @ApiModelProperty(required = true, example = "SOCIAL")
    @Getter
    @Setter
    @NotNull
    private String type;

    @ApiModelProperty(required = true, example = "Safety primarily means protecting.")
    @Getter
    @Setter
    @NotNull
    private String description;

    @ApiModelProperty(required = true)
    @Getter
    @Setter
    @NotNull
    private ImpactAnalysisDto analysis;
}
