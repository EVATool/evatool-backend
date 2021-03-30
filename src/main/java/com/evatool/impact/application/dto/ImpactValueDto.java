package com.evatool.impact.application.dto;

import com.evatool.impact.domain.entity.ImpactValue;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

@ApiModel(value = "ImpactValue", description = "ImpactValue of an Values")
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
    private ImpactValue impactValue;

    @ApiModelProperty(required = true)
    @Getter
    @Setter
    @NotNull
    private String description;

    @Override
    public String toString() {
        return "ValueDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + impactValue + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImpactValueDto that = (ImpactValueDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && impactValue == that.impactValue && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, impactValue, description);
    }
}
