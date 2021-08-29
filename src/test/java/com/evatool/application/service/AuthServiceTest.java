package com.evatool.application.service;

import com.evatool.application.dto.AuthTokenDto;
import com.evatool.application.service.impl.AuthServiceImpl;
import com.evatool.common.exception.InternalServerErrorException;
import com.evatool.common.exception.functional.http401.InvalidCredentialsException;
import com.evatool.common.exception.functional.http404.RealmNotFoundException;
import com.evatool.common.exception.functional.http404.UsernameNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AuthServiceImpl authService;

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void testLogin() {
        // given
        var username = "username";
        var password = "password";
        var realm = "realm";

        var expectedAuthTokenDto = getDummyAuthTokenDto();
        mockServer.expect(requestTo("/" + authService.getKeycloakLoginUrl(realm)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(getKeycloakLoginResponse(expectedAuthTokenDto), MediaType.TEXT_PLAIN));

        // when
        var authTokenDto = authService.login(username, password, realm);

        // then
        mockServer.verify();
        assertThat(authTokenDto).isEqualTo(expectedAuthTokenDto);
    }

    @Test
    void testLogin_KeycloakRespondsWithNotFoundIfRealmAndUsernameAreEqual_Throws() {
        // given
        var username = "username";
        var password = "password";
        var realm = "username";

        mockServer.expect(requestTo("/" + authService.getKeycloakLoginUrl(realm)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));

        // when

        // then
        assertThatExceptionOfType(UsernameNotFoundException.class).isThrownBy(() -> authService.login(username, password, realm));
    }

    @Test
    void testLogin_KeycloakRespondsWithNotFoundIfRealmAndUsernameAreNotEqual_Throws() {
        // given
        var username = "username";
        var password = "password";
        var realm = "realm";

        mockServer.expect(requestTo("/" + authService.getKeycloakLoginUrl(realm)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.NOT_FOUND));

        // when

        // then
        assertThatExceptionOfType(RealmNotFoundException.class).isThrownBy(() -> authService.login(username, password, realm));
    }

    @Test
    void testLogin_KeycloakRespondsWithUnauthorized_Throws() {
        // given
        var username = "username";
        var password = "password";
        var realm = "realm";

        mockServer.expect(requestTo("/" + authService.getKeycloakLoginUrl(realm)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.UNAUTHORIZED));

        // when

        // then
        assertThatExceptionOfType(InvalidCredentialsException.class).isThrownBy(() -> authService.login(username, password, realm));
    }

    @Test
    void testLogin_KeycloakRespondsWithUnhandledHttpStatus_Throws() {
        // given
        var username = "username";
        var password = "password";
        var realm = "realm";

        mockServer.expect(requestTo("/" + authService.getKeycloakLoginUrl(realm)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));

        // when

        // then
        assertThatExceptionOfType(InternalServerErrorException.class).isThrownBy(() -> authService.login(username, password, realm));
    }

    private AuthTokenDto getDummyAuthTokenDto() {
        return new AuthTokenDto("token", 1800, "refreshToken", 30000);
    }

    private String getKeycloakLoginResponse(AuthTokenDto authTokenDto) {
        return "{access_token: \"" + authTokenDto.getToken() + "\"," +
                "expires_in: \"" + authTokenDto.getTokenExpiresIn() + "\"," +
                "refresh_token: \"" + authTokenDto.getRefreshToken() + "\"," +
                "refresh_expires_in: \"" + authTokenDto.getRefreshTokenExpiresIn() + "\"}";
    }
}
