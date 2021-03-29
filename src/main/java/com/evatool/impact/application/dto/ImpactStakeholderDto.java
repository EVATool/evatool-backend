package com.evatool.impact.application.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@ApiModel(value = "ImpactStakeholder", description = "Stakeholder of an impact")
@EqualsAndHashCode
@ToString
public class ImpactStakeholderDto {

    @ApiModelProperty
    @Getter
    @Setter
    private UUID id;

    @ApiModelProperty(required = true)
    @Getter
    @Setter
    @NotNull
    private String name;
}
