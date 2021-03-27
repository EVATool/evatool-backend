package com.evatool.impact.application.dto;

import com.evatool.impact.domain.entity.Value;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

@ApiModel(value = "Value", description = "Value of an Values")
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
    private Value value;

    @ApiModelProperty(required = true)
    @Getter
    @Setter
    @NotNull
    private String description;

    @Override
    public String toString() {
        return "DimensionDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + value + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValueDto that = (ValueDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && value == that.value && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, value, description);
    }
}
