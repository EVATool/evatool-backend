package com.evatool.application.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;

@ApiModel(value = "AuthRegisterRealmDto")
@EqualsAndHashCode
@ToString
@Getter
@Setter
@NoArgsConstructor
public class AuthRegisterRealmDto {

    private String realm;

    public AuthRegisterRealmDto(String realm) {
        this.realm = realm;
    }
}
