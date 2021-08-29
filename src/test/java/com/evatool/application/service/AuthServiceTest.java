package com.evatool.application.service;

import com.evatool.application.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(classes = {RestTemplate.class, AuthServiceImpl.class})
class AuthServiceTest {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AuthServiceImpl authService;

    //private MockRestServiceServer mockServer;

    @BeforeEach
    public void setUp() {
        //mockServer = MockRestServiceServer.createServer(restTemplate);

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

//        mockServer.expect(requestTo(authService.getKeycloakLoginUrl(realm)))
//                .andExpect(method(HttpMethod.GET))
//                .andRespond(withSuccess("resultSuccess", MediaType.TEXT_PLAIN));
//
//        // when
//        authService.login(username, password, realm);
//        mockServer.verify(); // ??


        // then

        //assertThat(result, allOf(containsString("SUCCESS"),                containsString("resultSuccess")));

    }
}
