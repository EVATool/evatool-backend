package com.evatool.application.service.impl;

import com.evatool.application.dto.AuthRegisterRealmDto;
import com.evatool.application.dto.AuthRegisterUserDto;
import com.evatool.application.dto.AuthTokenDto;
import com.evatool.application.service.api.AuthService;
import com.evatool.common.exception.InternalServerErrorException;
import com.evatool.common.exception.NotFoundException;
import com.evatool.common.exception.UnauthorizedException;
import com.evatool.common.exception.handle.RestTemplateResponseErrorHandlerIgnore;
import com.evatool.common.util.UUIDUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    public AuthTokenDto login(String username, String password, String realm, String clientId) {
        var rest = getRestTemplate();
        var request = getLoginRequest(username, password, clientId);
        var httpEntity = getHttpEntityWithKeycloakUrlEncodedHeaders(request);
        var response = rest.postForEntity(getKeycloakLoginUrl(realm), httpEntity, String.class);
        var httpStatus = response.getStatusCode();

        // Error handling.
        if (httpStatus == HttpStatus.NOT_FOUND) {
            throw new NotFoundException("Realm \"" + realm + "\" not found");
        } else if (httpStatus == HttpStatus.UNAUTHORIZED) {
            throw new UnauthorizedException("Invalid credentials");
        } else if (httpStatus != HttpStatus.OK) {
            throw new InternalServerErrorException("Unhandled Exception from login rest call to keycloak (Status: " + httpStatus + ", Body: " + response.getBody() + ")");
        }

        return getAuthTokenDtoFromKeycloakResponse(response.getBody());
    }

    @Override
    public AuthTokenDto login(String username, String password, String realm) {
        return login(username, password, realm, "evatool-app");
    }

    private String getLoginRequest(String username, String password, String clientId) {
        return "grant_type=password" +
                "&scope=openid" +
                "&client_id=" + clientId +
                "&username=" + username +
                "&password=" + password;
    }

    @Override
    public AuthTokenDto refreshLogin(String refreshToken, String realm) {
        var rest = getRestTemplate();
        var request = getRefreshLoginRequest(refreshToken, "evatool-app");
        var httpEntity = getHttpEntityWithKeycloakUrlEncodedHeaders(request);
        var response = rest.postForEntity(getKeycloakLoginUrl(realm), httpEntity, String.class);
        var httpStatus = response.getStatusCode();

        // Error handling.
        if (httpStatus != HttpStatus.OK) {
            throw new InternalServerErrorException("Unhandled Exception from refresh login rest call to keycloak (Status: " + httpStatus + ", Body: " + response.getBody() + ")");
        }

        return getAuthTokenDtoFromKeycloakResponse(response.getBody());
    }

    private String getRefreshLoginRequest(String refreshToken, String clientId) {
        return "grant_type=refresh_token" +
                "&scope=openid" +
                "&client_id=" + clientId +
                "&refresh_token=" + refreshToken;
    }

    @Override
    public AuthRegisterUserDto registerUser(String username, String email, String password) {
        var rest = getRestTemplate();


        return new AuthRegisterUserDto(username, email);
    }

    @Override
    public AuthRegisterRealmDto registerRealm(String authAdminUsername, String authAdminPassword, String realm) {
        var adminToken = login(authAdminUsername, authAdminPassword, "master", "admin-cli").getToken();
        var rest = getRestTemplate();
        var request = getKeycloakRealmImportJson(realm);
        var httpEntity = getHttpEntityWithKeycloakAuthorizationHeaders(request, adminToken);
        var response = rest.postForEntity(getKeycloakRegisterRealmUrl(), httpEntity, String.class);
        var httpStatus = response.getStatusCode();

        // Error handling.
        if (httpStatus != HttpStatus.OK) {
            throw new InternalServerErrorException("Unhandled Exception from create realm rest call to keycloak (Status: " + httpStatus + ", Body: " + response.getBody() + ")");
        }

        return new AuthRegisterRealmDto(realm);
    }

    @SneakyThrows
    private String getKeycloakRealmImportJson(String realm) {
        // Retrieve template keycloak realm from application resources.
        var in = Thread.currentThread().getContextClassLoader().getResourceAsStream("auth/evatool-realm.json");
        var mapper = new ObjectMapper();
        var jsonNode = mapper.readValue(in, JsonNode.class);
        var realmImportJson = mapper.writeValueAsString(jsonNode);

        // Replace realm name.
        realmImportJson = realmImportJson.replace("evatool-realm", realm);

        // Re-assign ids.
        var lines = realmImportJson.split("\n");
        for (var line : lines) {

            // Check if id is in line.
            if (line.toLowerCase().contains("id\" : ")) {

                // Retrieve id from line.
                var oldId = line.split(":")[1].trim().replace("\"", "").replace(",", "");

                // Check if id is UUID
                if (UUIDUtil.isValidUUID(oldId)) {

                    // Change oldId to newId in whole json.
                    var newId = UUID.randomUUID().toString();
                    realmImportJson = realmImportJson.replace(oldId, newId);
                }
            }
        }

        System.out.println(realmImportJson);
        return realmImportJson;
    }

    private RestTemplate getRestTemplate() {
        return new RestTemplateBuilder()
                .errorHandler(new RestTemplateResponseErrorHandlerIgnore())
                .build();
    }

    private <T> HttpEntity<T> getHttpEntityWithKeycloakUrlEncodedHeaders(T request) {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return new HttpEntity<T>(request, headers);
    }

    private <T> HttpEntity<T> getHttpEntityWithKeycloakAuthorizationHeaders(T request, String token) {
        var headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return new HttpEntity<T>(request, headers);
    }

    @SneakyThrows
    private AuthTokenDto getAuthTokenDtoFromKeycloakResponse(String keycloakResponse) {
        var responseJson = new JSONObject(keycloakResponse);
        return new AuthTokenDto(
                responseJson.getString("access_token"),
                responseJson.getInt("expires_in"),
                responseJson.getString("refresh_token"),
                responseJson.getInt("refresh_expires_in"));
    }

    // Dynamic keycloak URLs.
    @Value("${keycloak.auth-server-url}")
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
