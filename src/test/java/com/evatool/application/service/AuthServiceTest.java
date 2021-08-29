package com.evatool.application.service;

import com.evatool.application.dto.AuthTokenDto;
import com.evatool.application.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@SpringBootTest(classes = {RestTemplate.class, AuthServiceImpl.class})
class AuthServiceTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AuthServiceImpl authService;

    private MockRestServiceServer mockServer;

    @BeforeEach
    public void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);

        //var gateway = new RestGatewaySupport();
        //gateway.setRestTemplate(restTemplate);
        //mockServer = MockRestServiceServer.createServer(gateway);
    }

    @Test
    void testLogin() {
        // given
        var username = "username";
        var password = "password";
        var realm = "realm";

        var expectedAuthTokenDto = new AuthTokenDto("token", 1800, "refreshToken", 30000);
        mockServer.expect(requestTo("/" + authService.getKeycloakLoginUrl(realm)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess(
                        "{access_token: \"" + expectedAuthTokenDto.getToken() + "\"," +
                                "expires_in: \"" + expectedAuthTokenDto.getTokenExpiresIn() + "\"," +
                                "refresh_token: \"" + expectedAuthTokenDto.getRefreshToken() + "\"," +
                                "refresh_expires_in: \"" + expectedAuthTokenDto.getRefreshTokenExpiresIn() + "\"}",
                        MediaType.TEXT_PLAIN));

        // when
        var authTokenDto = authService.login(username, password, realm);

        // then
        mockServer.verify();
        assertThat(authTokenDto).isEqualTo(expectedAuthTokenDto);
    }
}
