package com.evatool.integration;

import com.evatool.application.dto.AuthTokenDto;
import com.evatool.common.util.UriUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class IntegrationTest {

    @Autowired
    private TestRestTemplate rest;

    @Test
    void testKeycloakLogin() {
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

    @Nested
    class LoggedIn {

        @BeforeEach
        void login() {

        }
    }
}
