package com.evatool.analysis.application.dto;

import com.evatool.analysis.domain.enums.ValueType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

@ApiModel(value = "ImpactValue", description = "ImpactValue of an Values")
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


    @Override
    public String toString() {
        return "valueDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValueDto that = (ValueDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && type == that.type && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, description);
    }
}
