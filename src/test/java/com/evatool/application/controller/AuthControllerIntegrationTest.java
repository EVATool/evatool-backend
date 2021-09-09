package com.evatool.application.controller;

import com.evatool.application.dto.AuthRegisterRealmDto;
import com.evatool.application.dto.AuthRegisterUserDto;
import com.evatool.application.dto.AuthTokenDto;
import com.evatool.application.service.impl.AuthServiceImpl;
import com.evatool.common.util.UriUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class AuthControllerIntegrationTest extends IntegrationTest {

    @Autowired
    private AuthServiceImpl authService;

    // These variables represent entities that might have been created in a test and need to be deleted.
    private String testUser = "username";
    private String testRealm = "realm";

    @Value("${keycloak.auth-server-url:}")
    private String keycloakBaseUrl;

    @BeforeEach
    void resetKeycloak() {
        // Authorize.
        loginAsKeycloakAdmin();

        // Delete test user.
        //var keycloakUsers = rest.getForEntity(keycloakBaseUrl + "evatool-realm/users", String.class);
        //System.out.println(keycloakUsers);
        // for each username == testUserId then id -> testUserId
        //rest.delete("/evatool-realm/users/" + testUserId);

        // Delete test realm.
        //rest.delete(keycloakBaseUrl + testRealm);
        System.out.println(keycloakBaseUrl + "admin/realms/" + testRealm);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        var httpEntity = new HttpEntity<>(null, headers);
        var s = rest.exchange(keycloakBaseUrl + "admin/realms/" + testRealm, HttpMethod.DELETE, httpEntity, Void.class);
        System.out.println(s);
    }

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
        loginAsEvatoolAdmin();
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
        // given
        var username = testUser;
        var email = "email@email.de";
        var password = "password";

        // when
        var response = rest.postForEntity(UriUtil.AUTH_REGISTER_USER
                + "?username=" + username
                + "&email=" + email
                + "&password=" + password, null, AuthRegisterUserDto.class);
        var authRegisterUserDto = response.getBody();

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(authRegisterUserDto).isNotNull();
        assertThat(authRegisterUserDto.getUsername()).isEqualTo(username);
        assertThat(authRegisterUserDto.getEmail()).isEqualTo(email);
    }

    @Test
    void testRegisterRealm() {
        // given
        var authAdminUsername = "admin";
        var authAdminPassword = "admin";
        var realm = testRealm;

        // when
        var response = rest.postForEntity(UriUtil.AUTH_REGISTER_REALM
                + "?authAdminUsername=" + authAdminUsername
                + "&authAdminPassword=" + authAdminPassword
                + "&realm=" + realm, null, AuthRegisterRealmDto.class);
        var authRegisterRealmDto = response.getBody();

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(authRegisterRealmDto).isNotNull();
        assertThat(authRegisterRealmDto.getRealm()).isEqualTo(realm);
    }
}
