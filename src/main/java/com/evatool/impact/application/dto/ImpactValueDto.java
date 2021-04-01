package com.evatool.impact.application.dto;

import com.evatool.impact.common.ImpactValueType;
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

    @ApiModelProperty
    @Getter
    @Setter
    private UUID id;

    @ApiModelProperty(required = true)
    @Getter
    @Setter
    @NotNull
    private String name;

    @ApiModelProperty(required = true)
    @Getter
    @Setter
    @NotNull
    private ImpactValueType type;

    @ApiModelProperty(required = true)
    @Getter
    @Setter
    @NotNull
    private String description;

//    @ApiModelProperty(required = true)
//    @Getter
//    @Setter
//    @NotNull
//    private UUID analysisId; // TODO add tests
}
