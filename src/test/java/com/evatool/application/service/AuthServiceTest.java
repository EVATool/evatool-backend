package com.evatool.application.service;

import com.evatool.application.dto.AuthRegisterUserDto;
import com.evatool.application.dto.AuthTokenDto;
import com.evatool.application.service.impl.AuthServiceImpl;
import com.evatool.application.service.impl.LoginAttemptServiceImpl;
import com.evatool.common.exception.InternalServerErrorException;
import com.evatool.common.exception.functional.http401.InvalidCredentialsException;
import com.evatool.common.exception.functional.http403.RemoteIpBlockedException;
import com.evatool.common.exception.functional.http404.RealmNotFoundException;
import com.evatool.common.exception.functional.http404.UsernameNotFoundException;
import com.evatool.common.exception.functional.http409.EmailAlreadyTakenException;
import com.evatool.common.exception.functional.http409.UsernameAlreadyTakenException;
import com.evatool.common.util.AuthUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.UUID;

import static com.evatool.application.service.api.LoginAttemptService.MAX_ATTEMPTS;
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

    @Autowired
    private LoginAttemptServiceImpl loginAttemptService;

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        loginAttemptService.loginSucceeded("127.0.0.1");
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
    void testLogin_KeycloakRespondsWithNotFound_IfRealmAndUsernameAreEqual_Throws() {
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
    void testLogin_KeycloakRespondsWithNotFound_IfRealmAndUsernameAreNotEqual_Throws() {
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
    void testLogin_KeycloakRespondsWithUnauthorized_InvalidCredentials_Throws() {
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

    @Test
    void testLogin_RemainingAttemptsDecreases_UntilRemoteIpIsBlocked() {
        // given
        var username = "username";
        var password = "password";
        var realm = "realm";

        mockServer.expect(ExpectedCount.max(MAX_ATTEMPTS), requestTo("/" + authService.getKeycloakLoginUrl(realm)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.UNAUTHORIZED));

        // when
        for (int i = 0; i < MAX_ATTEMPTS - 1; i++) {
            assertThatExceptionOfType(InvalidCredentialsException.class).isThrownBy(() -> authService.login(username, password, realm));
            assertThat(loginAttemptService.getRemainingAttempts("127.0.0.1")).isEqualTo(MAX_ATTEMPTS - (i + 1));
        }

        // then
        assertThatExceptionOfType(RemoteIpBlockedException.class).isThrownBy(() -> authService.login(username, password, realm));
        assertThatExceptionOfType(RemoteIpBlockedException.class).isThrownBy(() -> authService.login(username, password, realm)); // This is here to verify the ip remains banned.
    }

    @Test
    void testRefreshLogin() {
        // given
        var refreshToken = "refreshToken";
        var realm = "realm";

        var expectedAuthTokenDto = getDummyAuthTokenDto();
        mockServer.expect(requestTo("/" + authService.getKeycloakLoginUrl(realm)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(getKeycloakLoginResponse(expectedAuthTokenDto), MediaType.TEXT_PLAIN));

        // when
        var authTokenDto = authService.refreshLogin(refreshToken, realm);

        // then
        mockServer.verify();
        assertThat(authTokenDto).isEqualTo(expectedAuthTokenDto);
    }

    @Test
    void testRefreshLogin_KeycloakRespondsWithUnhandledHttpStatus_Throws() {
        // given
        var refreshToken = "refreshToken";
        var realm = "realm";

        var expectedAuthTokenDto = getDummyAuthTokenDto();
        mockServer.expect(requestTo("/" + authService.getKeycloakLoginUrl(realm)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));

        // when

        // then
        assertThatExceptionOfType(InternalServerErrorException.class).isThrownBy(() -> authService.refreshLogin(refreshToken, realm));
    }

    @SneakyThrows
    @Test
    void testRegisterUser() {
        // given
        String username = "username";
        String email = "email";
        String password = "password";

        var expectedAuthTokenDto = getDummyAuthTokenDto();
        var expectedAuthRegisterUserDto = getDummyAuthRegisterUserDto();
        mockServer.expect(requestTo("/" + authService.getKeycloakLoginUrl("master")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(getKeycloakLoginResponse(expectedAuthTokenDto), MediaType.TEXT_PLAIN));

        var responseHeaders = new HttpHeaders();
        var userId = UUID.randomUUID().toString();
        responseHeaders.setLocation(new URI("/" + userId));
        mockServer.expect(requestTo("/" + authService.getKeycloakCreateUserUrl()))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.CREATED).headers(responseHeaders));

        mockServer.expect(requestTo("/" + authService.getKeycloakGetRealmRolesUrl()))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(getKeycloakGetRealmRolesResponse(), MediaType.TEXT_PLAIN));

        mockServer.expect(requestTo("/" + authService.getKeycloakSetUserRolesUrl(userId)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.NO_CONTENT));

        // when
        var authRegisterUserDto = authService.registerUser(username, email, password);

        // then
        assertThat(authRegisterUserDto).isEqualTo(expectedAuthRegisterUserDto);
    }

    @SneakyThrows
    @Test
    void testRegisterUser_KeycloakRespondsWithConflict_EmailAlreadyExists_Throws() {
        // given
        String username = "username";
        String email = "email";
        String password = "password";

        var expectedAuthTokenDto = getDummyAuthTokenDto();
        var expectedAuthRegisterUserDto = getDummyAuthRegisterUserDto();
        mockServer.expect(requestTo("/" + authService.getKeycloakLoginUrl("master")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(getKeycloakLoginResponse(expectedAuthTokenDto), MediaType.TEXT_PLAIN));

        mockServer.expect(requestTo("/" + authService.getKeycloakCreateUserUrl()))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.CONFLICT).body("\"errorMessage\":\"User exists with same email\""));

        // when

        // then
        assertThatExceptionOfType(EmailAlreadyTakenException.class).isThrownBy(() -> authService.registerUser(username, email, password));
    }

    @Test
    void testRegisterUser_KeycloakRespondsWithConflict_UsernameAlreadyExists_Throws() {
        // given
        String username = "username";
        String email = "email";
        String password = "password";

        var expectedAuthTokenDto = getDummyAuthTokenDto();
        var expectedAuthRegisterUserDto = getDummyAuthRegisterUserDto();
        mockServer.expect(requestTo("/" + authService.getKeycloakLoginUrl("master")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(getKeycloakLoginResponse(expectedAuthTokenDto), MediaType.TEXT_PLAIN));

        mockServer.expect(requestTo("/" + authService.getKeycloakCreateUserUrl()))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.CONFLICT));

        // when

        // then
        assertThatExceptionOfType(UsernameAlreadyTakenException.class).isThrownBy(() -> authService.registerUser(username, email, password));
    }

    // TODO ...and error cases...
    @Test
    void testRegisterRealm() {

    }

    private AuthTokenDto getDummyAuthTokenDto() {
        return new AuthTokenDto("token", 1800, "refreshToken", 30000);
    }

    private AuthRegisterUserDto getDummyAuthRegisterUserDto() {
        return new AuthRegisterUserDto("username", "email");
    }

    private String getKeycloakLoginResponse(AuthTokenDto authTokenDto) {
        return "{access_token: \"" + authTokenDto.getToken() + "\"," +
                "expires_in: \"" + authTokenDto.getTokenExpiresIn() + "\"," +
                "refresh_token: \"" + authTokenDto.getRefreshToken() + "\"," +
                "refresh_expires_in: \"" + authTokenDto.getRefreshTokenExpiresIn() + "\"}";
    }

    private String getKeycloakGetRealmRolesResponse() {
        var roleResponseList = new ArrayList<String>();

        for (var role : AuthUtil.ALL_ROLES) {
            roleResponseList.add("{id: \"" + UUID.randomUUID() + "\" ,name: \"" + role + "\" }");
        }

        return "[" + String.join(",", roleResponseList) + "]";
    }
}
