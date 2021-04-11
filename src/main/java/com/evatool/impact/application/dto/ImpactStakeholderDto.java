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

    @ApiModelProperty(example = "ede53b7c-cc72-4466-a238-9d9821c420c5")
    @Getter
    @Setter
    private UUID id;

    @ApiModelProperty(required = true, example = "Health Insurance")
    @Getter
    @Setter
    @NotNull
    private String name;

    @ApiModelProperty(required = true, example = "ORGANIZATION")
    @Getter
    @Setter
    @NotNull
    private String level;
}
