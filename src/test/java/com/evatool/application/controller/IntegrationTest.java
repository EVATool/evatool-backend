package com.evatool.application.controller;

import com.evatool.application.dto.AuthTokenDto;
import com.evatool.common.util.UriUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public abstract class IntegrationTest {

    @Autowired
    protected TestRestTemplate rest;

    protected String token;
    protected String refreshToken;

    protected void loginAsUser() {
        login("user", "user");
    }

    protected void loginAsAdmin() {
        login("admin", "admin");
    }

    private void login(String username, String password) {
        login(username, password, "evatool-realm");
    }

    private void login(String username, String password, String realm) {
        var response = rest.postForEntity(UriUtil.AUTH_LOGIN
                + "?username=" + username
                + "&password=" + password
                + "&realm=" + realm, null, AuthTokenDto.class);
        var authTokenDto = response.getBody();
        assert authTokenDto != null;
        token = authTokenDto.getToken();
        refreshToken = authTokenDto.getRefreshToken();
    }
}
