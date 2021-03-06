package com.evatool.application.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@ApiModel(value = "VariantDto")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class VariantDto extends PrefixIdDto implements AnalysisChildDto {

    @ApiModelProperty(required = true)
    @NotNull
    private String name;

    @ApiModelProperty(required = true)
    @NotNull
    private String description;

    @ApiModelProperty(required = true)
    @NotNull
    private Boolean archived;

    @ApiModelProperty(required = true)
    @NotNull
    private UUID analysisId;

    // TODO what is the purpose of having subVariants? Can this be removed?
    //@ApiModelProperty(required = false)
    //private UUID[] subVariantIds;

    public VariantDto(String name, String description, Boolean archived, UUID analysisId) {
        this.name = name;
        this.description = description;
        this.archived = archived;
        this.analysisId = analysisId;
    }
}
