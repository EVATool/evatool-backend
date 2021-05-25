package com.evatool.application.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PrefixIdDto extends SuperDto {

    @ApiModelProperty
    @Getter
    @Setter
    private String prefixSequenceId;
}
