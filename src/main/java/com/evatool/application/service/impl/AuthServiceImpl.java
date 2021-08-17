package com.evatool.application.service.impl;

import com.evatool.application.dto.AuthRegisterRealmDto;
import com.evatool.application.dto.AuthRegisterUserDto;
import com.evatool.application.dto.AuthTokenDto;
import com.evatool.application.service.api.AuthService;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @SneakyThrows
    @Override
    public AuthTokenDto login(String username, String password, String realm) {
        var rest = new RestTemplate();

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        var request = getLoginRequest(username, password, "evatool-app");
        var entity = new HttpEntity<>(request, headers);

        var response = rest.postForEntity(getKeycloakLoginUrl(realm), entity, String.class);
        var httpStatus = response.getStatusCode();

        // TODO
        // Error handling.
        if (httpStatus == HttpStatus.NOT_FOUND) {

        } else if (httpStatus == HttpStatus.BAD_REQUEST) {

        } else {
            // Unhandled error.

        }

        var authTokenDto = getAuthTokenDtoFromKeycloakResponse(response.getBody());
        return authTokenDto;
    }

    @Override
    public AuthTokenDto refreshLogin(String refreshToken, String realm) {
        return null;
    }

    @Override
    public AuthRegisterUserDto registerUser(String username, String email, String password) {
        return null;
    }

    @Override
    public AuthRegisterRealmDto registerRealm(String authAdminUsername, String authAdminPassword, String realm) {
        return null;
    }

    private String getLoginRequest(String username, String password, String clientId) {
        return "grant_type=password" +
                "&scope=openid" +
                "&client_id=" + clientId +
                "&username=" + username +
                "&password=" + password;
    }

    private String getRefreshLoginRequest(String refreshToken) {
        return "grant_type=refresh_token" +
                "&scope=openid" +
                "&client_id=evatool-app" +
                "&refresh_token=" + refreshToken;
    }

    @SneakyThrows
    private AuthTokenDto getAuthTokenDtoFromKeycloakResponse(String keycloakResponse) {
        var responseJson = new JSONObject(keycloakResponse);
        var authTokenDto = new AuthTokenDto(
                responseJson.getString("access_token"),
                responseJson.getInt("expires_in"),
                responseJson.getString("refresh_token"),
                responseJson.getInt("refresh_expires_in"));
        return authTokenDto;
    }


    // Dynamic keycloak urls. Move this to service impl?
    @Value("${keycloak.auth-server-url:LOL}")
    private String keycloakBaseUrl;

    private String getKeycloakAdminLoginUrl() {
        return getKeycloakLoginUrl("master");
    }

    public String getKeycloakLoginUrl(String realm) {
        return keycloakBaseUrl + "realms/" + realm + "/protocol/openid-connect/token";
    }

    private String getKeycloakRefreshUrl(String realm) {
        return getKeycloakLoginUrl(realm);
    }

    private String getKeycloakRegisterUserUrl() {
        return keycloakBaseUrl + ""; // TODO
    }

    private String getKeycloakRegisterRealmUrl() {
        return keycloakBaseUrl + "admin/realms";
    }
}
