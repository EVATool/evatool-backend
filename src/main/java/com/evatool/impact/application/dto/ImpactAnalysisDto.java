package com.evatool.impact.application.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@ApiModel(value = "ImpactAnalysis", description = "Analysis an impact belongs to")
public class ImpactAnalysisDto {

    @ApiModelProperty
    @Getter
    @Setter
    private UUID id;

    @Override
    public String toString() {
        return "AnalysisDto{" +
                "id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImpactAnalysisDto that = (ImpactAnalysisDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
