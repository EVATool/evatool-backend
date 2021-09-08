package com.evatool.application.controller;

import com.evatool.application.dto.AuthTokenDto;
import com.evatool.application.service.impl.AuthServiceImpl;
import com.evatool.common.util.UriUtil;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AuthControllerIntegrationTest extends IntegrationTest {

    @Autowired
    private AuthServiceImpl authService;

    @Test
    void testLogin() {
        // given
        var username = "admin";
        var password = "admin";
        var realm = "evatool-realm";

        // when
        var response = rest.postForEntity(UriUtil.AUTH_LOGIN
                + "?username=" + username
                + "&password=" + password
                + "&realm=" + realm, null, AuthTokenDto.class);
        var authTokenDto = response.getBody();

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(authTokenDto).isNotNull();
        assertThat(authTokenDto.getToken()).isNotNull();
        assertThat(authTokenDto.getTokenExpiresIn()).isNotNull();
        assertThat(authTokenDto.getRefreshToken()).isNotNull();
        assertThat(authTokenDto.getRefreshTokenExpiresIn()).isNotNull();
    }

    @Test
    void testRefreshLogin() {
        // given
        loginAsAdmin();
        var realm = "evatool-realm";

        // when
        var response = rest.postForEntity(UriUtil.AUTH_REFRESH_LOGIN
                + "?refreshToken=" + refreshToken
                + "&realm=" + realm, null, AuthTokenDto.class);
        var authTokenDto = response.getBody();

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(authTokenDto).isNotNull();
        assertThat(authTokenDto.getToken()).isNotNull();
        assertThat(authTokenDto.getTokenExpiresIn()).isNotNull();
        assertThat(authTokenDto.getRefreshToken()).isNotNull();
        assertThat(authTokenDto.getRefreshTokenExpiresIn()).isNotNull();
    }

    @Test
    void testRegisterUser() {

    }

    @Test
    void testRegisterRealm() {

    }
}
