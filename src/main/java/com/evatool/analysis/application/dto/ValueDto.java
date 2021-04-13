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

@ApiModel(value = "Value", description = "ImpactValue of an Values")
@Getter
@EqualsAndHashCode
@ToString
public class ValueDto {

    @ApiModelProperty
    private UUID id;

    @ApiModelProperty(required = true)
    private String name;

    @ApiModelProperty(required = true)
    private ValueType type;

    @ApiModelProperty(required = true)
    private String description;

    @ApiModelProperty(required = true)
    private AnalysisDTO analysis;

    @ApiModelProperty(required = true)
    private Boolean archived ;

    public void setId(UUID id) {
        if (this.getId() == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        this.id = id;
    }

    public void setName(String name) {
        if (this.getName() == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        this.name = name;
    }

    public void setType(ValueType type) {
        if (this.getType() == null) {
            throw new IllegalArgumentException("Type cannot be null");
        }
        this.type = type;
    }

    public void setDescription(String description) {
        if (this.getDescription() == null) {
            throw new IllegalArgumentException("Description cannot be null");
        }
        this.description = description;
    }

    public void setAnalysis(AnalysisDTO analysis) {
        if (this.getAnalysis() == null) {
            throw new IllegalArgumentException("Analysis cannot be null");
        }
        this.analysis = analysis;
    }

    public void setArchived(Boolean archived) {
        if (this.archived == null) {
            throw new IllegalArgumentException("Archived cannot be null");
        }
        this.archived = archived;
    }
}
