package com.evatool.application.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;

@ApiModel(value = "AuthTokenDto")
@EqualsAndHashCode
@ToString
@Getter
@Setter
@NoArgsConstructor
public class AuthTokenDto {

    private String token;
    private Integer tokenExpiresIn;

    private String refreshToken;
    private Integer refreshTokenExpiresIn;

    public AuthTokenDto(String token, Integer tokenExpiresIn, String refreshToken, Integer refreshTokenExpiresIn) {
        this.token = token;
        this.tokenExpiresIn = tokenExpiresIn;
        this.refreshToken = refreshToken;
        this.refreshTokenExpiresIn = refreshTokenExpiresIn;
    }
}
