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
    private String name;

    @ApiModelProperty(required = true)
    @NotNull
    private StakeholderPriority priority;

    @ApiModelProperty(required = true)
    @NotNull
    private StakeholderLevel level;

    @ApiModelProperty(required = true)
    @NotNull
    private UUID analysisId;

    // TODO add impacted (slider in stakeholder table)

    public StakeholderDto(String name, StakeholderPriority priority, StakeholderLevel level, UUID analysisId) {
        this.name = name;
        this.priority = priority;
        this.level = level;
        this.analysisId = analysisId;
    }
}
