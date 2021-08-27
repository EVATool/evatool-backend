package com.evatool.application.controller;

import com.evatool.common.util.UriUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AuthControllerTest {

    @Autowired
    private TestRestTemplate rest;

    @Test
    void testLogin_UsernameNotProvided_ReturnsHttpStatus400() {
        // given
        var password = "password";

        // when
        var response = rest.postForEntity(UriUtil.AUTH_LOGIN + "?password=" + password, null, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testLogin_EmptyUsername_ReturnsHttpStatus400() {
        // given
        var username = "";
        var password = "password";

        // when
        var response = rest.postForEntity(UriUtil.AUTH_LOGIN + "?username=" + username + "&password=" + password, null, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testLogin_InvalidUsername_ReturnsHttpStatus400() {
        // given
        var username = "!";
        var password = "password";

        // when
        var response = rest.postForEntity(UriUtil.AUTH_LOGIN + "?username=" + username + "&password=" + password, null, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testLogin_PasswordNotProvided_ReturnsHttpStatus400() {
        // given
        var username = "username";

        // when
        var response = rest.postForEntity(UriUtil.AUTH_LOGIN + "?username=" + username, null, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testLogin_EmptyPassword_ReturnsHttpStatus400() {
        // given
        var username = "username";
        var password = "";

        // when
        var response = rest.postForEntity(UriUtil.AUTH_LOGIN + "?username=" + username + "&password=" + password, null, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testLogin_InvalidRealm_ReturnsHttpStatus400() {
        // given
        var username = "username";
        var password = "password";
        var realm = "!";

        // when
        var response = rest.postForEntity(UriUtil.AUTH_LOGIN + "?username=" + username + "&password=" + password + "&realm=" + realm, null, String.class);

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
