package com.evatool.application.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;

@ApiModel(value = "AuthRegisterUserDto")
@EqualsAndHashCode
@ToString
@Getter
@Setter
@NoArgsConstructor
public class AuthRegisterUserDto {

    private String username;
    private String email;

    public AuthRegisterUserDto(String username, String email) {
        this.username = username;
        this.email = email;
    }
}
