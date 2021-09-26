package com.evatool.application.dto;

import com.evatool.common.enums.StakeholderLevel;
import com.evatool.common.enums.StakeholderPriority;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@ApiModel(value = "StakeholderDto")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class StakeholderDto extends PrefixIdDto implements AnalysisChildDto {

    @ApiModelProperty(required = true)
    @NotNull
    @ImportExportInclude
    private String name;

    @ApiModelProperty(required = true)
    @NotNull
    @ImportExportInclude
    private String description;

    @ApiModelProperty(required = true)
    @NotNull
    @ImportExportInclude
    private StakeholderPriority priority;

    @ApiModelProperty(required = true)
    @NotNull
    @ImportExportInclude
    private StakeholderLevel level;

    @ApiModelProperty(required = true)
    @NotNull
    private UUID analysisId;

    @ApiModelProperty
    private Float impacted;

    public StakeholderDto(String name, String description, StakeholderPriority priority, StakeholderLevel level, UUID analysisId, Float impacted) {
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.level = level;
        this.analysisId = analysisId;
        this.impacted = impacted;
    }
}
