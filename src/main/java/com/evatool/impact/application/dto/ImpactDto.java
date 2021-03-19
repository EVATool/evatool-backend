package com.evatool.impact.application.dto;

import com.sun.istack.Nullable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

@ApiModel(value = "Impact")
public class ImpactDto {

    @ApiModelProperty
    @Getter
    @Setter
    private UUID id;

    // TODO [philipp] readableId
    @ApiModelProperty
    @Getter
    @Setter
    @Nullable
    private Integer numericId;

    @ApiModelProperty(required = true, allowableValues = "range[-1.0,1.0]")
    @Getter
    @Setter
    @DecimalMin("-1.0")
    @DecimalMax("1.0")
    private double value;

    @ApiModelProperty(required = true)
    @Getter
    @Setter
    @NotNull
    private String description;

    @ApiModelProperty(required = true)
    @Getter
    @Setter
    @NotNull
    private DimensionDto dimension;

    @ApiModelProperty(required = true)
    @Getter
    @Setter
    @NotNull
    private ImpactStakeholderDto stakeholder;

    @ApiModelProperty(required = true)
    @Getter
    @Setter
    @NotNull
    private ImpactAnalysisDto analysis;

    @Override
    public String toString() {
        return "ImpactDto{" +
                "id='" + id + '\'' +
                ", numericId=" + numericId +
                ", value=" + value +
                ", description='" + description + '\'' +
                ", dimension=" + dimension +
                ", stakeholder=" + stakeholder +
                ", analysis=" + analysis +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImpactDto that = (ImpactDto) o;
        return Double.compare(that.value, value) == 0
                && Objects.equals(id, that.id)
                && Objects.equals(numericId, that.numericId)
                && Objects.equals(description, that.description)
                && Objects.equals(stakeholder, that.stakeholder)
                && Objects.equals(dimension, that.dimension)
                && Objects.equals(analysis, that.analysis);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numericId, value, description, stakeholder, dimension, analysis);
    }
}
