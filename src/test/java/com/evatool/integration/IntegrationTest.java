package com.evatool.integration;

import com.evatool.application.dto.AuthTokenDto;
import com.evatool.common.util.UriUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
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

    @Test
    void testGetAnalyses_NotLoggedIn_Forbidden() {

    }

    @Nested
    class LoggedIn {

        private String token;
        private String refreshToken;

        @BeforeEach
        void login() {
            var username = "admin";
            var password = "admin";
            var realm = "evatool-realm";
            var response = rest.postForEntity(UriUtil.AUTH_LOGIN
                    + "?username=" + username
                    + "&password=" + password
                    + "&realm=" + realm, null, AuthTokenDto.class);
            var authTokenDto = response.getBody();
            assert authTokenDto != null;
            token = authTokenDto.getToken();
            refreshToken = authTokenDto.getRefreshToken();
        }

        @Test
        void testGetAnalyses() {

        }

        @Test
        void test() {

        }
    }
}
