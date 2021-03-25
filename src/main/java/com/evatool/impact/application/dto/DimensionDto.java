package com.evatool.impact.application.dto;

import com.evatool.impact.common.DimensionType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@ApiModel(value = "Dimension", description = "Dimension of an impact")
@EqualsAndHashCode
@ToString
public class DimensionDto {

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
    private DimensionType type;

    @ApiModelProperty(required = true)
    @Getter
    @Setter
    @NotNull
    private String description;
}
