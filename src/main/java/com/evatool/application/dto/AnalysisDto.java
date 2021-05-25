package com.evatool.application.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.Date;

@ApiModel(value = "AnalysisDto")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class AnalysisDto extends PrefixIdDto {

    @ApiModelProperty(required = true)
    @NotNull
    private String name;

    @ApiModelProperty(required = true)
    @NotNull
    private String description;

    @ApiModelProperty(required = true)
    @NotNull
    private Boolean isTemplate;

    @ApiModelProperty(required = false)
    private String imageUrl;

    @ApiModelProperty(required = false)
    @EqualsAndHashCode.Exclude
    private Date lastUpdated; // TODO change to Long?

    public AnalysisDto(String name, String description, Boolean isTemplate, String imageUrl, Date lastUpdated) {
        this.name = name;
        this.description = description;
        this.isTemplate = isTemplate;
        this.imageUrl = imageUrl;
        this.lastUpdated = lastUpdated;
    }
}
