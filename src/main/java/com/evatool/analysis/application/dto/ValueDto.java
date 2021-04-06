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

@ApiModel(value = "ImpactValue", description = "ImpactValue of an Values")
@EqualsAndHashCode
@ToString
public class ValueDto {

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
    private ValueType type;

    @ApiModelProperty(required = true)
    @Getter
    @Setter
    @NotNull
    private String description;

    @ApiModelProperty(required = true)
    @Getter
    @Setter
    @NotNull
    private AnalysisDTO analysis;

    @ApiModelProperty(required = true)
    @Getter
    @Setter
    @NotNull
    private Boolean archived = false;
}
