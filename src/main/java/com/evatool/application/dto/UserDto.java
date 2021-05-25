package com.evatool.application.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@ApiModel(value = "UserDto")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class UserDto extends SuperDto {

    @ApiModelProperty(required = true)
    @NotNull
    private String externalUserId;

    public UserDto(String externalUserId) {
        this.externalUserId = externalUserId;
    }
}
