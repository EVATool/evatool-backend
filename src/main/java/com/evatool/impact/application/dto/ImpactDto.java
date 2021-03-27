package com.evatool.impact.application.dto;

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

    @ApiModelProperty
    @Getter
    @Setter
    private String uniqueString;

    @ApiModelProperty(required = true, allowableValues = "range[-1.0,1.0]")
    @Getter
    @Setter
    @DecimalMin("-1.0")
    @DecimalMax("1.0")
    @NotNull
    private Double value;

    @ApiModelProperty(required = true)
    @Getter
    @Setter
    @NotNull
    private String description;

    @ApiModelProperty(required = true)
    @Getter
    @Setter
    @NotNull
    private ImpactValueDto impactValueDto;

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
                ", uniqueString=" + uniqueString +
                ", value=" + value +
                ", description='" + description + '\'' +
                ", dimension=" + impactValueDto +
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
                && Objects.equals(uniqueString, that.uniqueString)
                && Objects.equals(description, that.description)
                && Objects.equals(stakeholder, that.stakeholder)
                && Objects.equals(impactValueDto, that.impactValueDto)
                && Objects.equals(analysis, that.analysis);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, uniqueString, value, description, stakeholder, impactValueDto, analysis);
    }
}
