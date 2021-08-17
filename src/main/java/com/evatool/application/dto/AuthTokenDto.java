package com.evatool.application.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;

@ApiModel(value = "AuthLoginDto")
@EqualsAndHashCode
@ToString
@Getter
@Setter
@NoArgsConstructor
public class AuthTokenDto {

    String token;
    Integer tokenExpiresIn;

    String refreshToken;
    Integer refreshTokenExpiresIn;

    public AuthTokenDto(String token, Integer tokenExpiresIn, String refreshToken, Integer refreshTokenExpiresIn) {
        this.token = token;
        this.tokenExpiresIn = tokenExpiresIn;
        this.refreshToken = refreshToken;
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
    }
}
