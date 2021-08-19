package com.evatool.application.service.impl;

import com.evatool.application.dto.AuthRegisterRealmDto;
import com.evatool.application.dto.AuthRegisterUserDto;
import com.evatool.application.dto.AuthTokenDto;
import com.evatool.application.service.api.AuthService;
import com.evatool.common.exception.ConflictException;
import com.evatool.common.exception.InternalServerErrorException;
import com.evatool.common.exception.NotFoundException;
import com.evatool.common.exception.UnauthorizedException;
import com.evatool.common.exception.handle.RestTemplateResponseErrorHandlerIgnore;
import com.evatool.common.util.AuthUtil;
import com.evatool.common.util.UUIDUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Value("${evatool.auth.registration.enabled:false}")
    private boolean registrationEnabled;

    @Value("${evatool.auth.admin-user:}")
    private String authAdminUser;

    @Value("${evatool.auth.admin-password:}")
    private String authAdminPassword;

    public AuthTokenDto login(String username, String password, String realm) {
        var rest = getRestTemplate();
        var clientId = getClientId(realm);
        var request = getLoginRequest(username, password, clientId);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        var httpEntity = new HttpEntity<>(request, headers);
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
        var clientId = getClientId(realm);
        var request = getRefreshLoginRequest(refreshToken, clientId);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        var httpEntity = new HttpEntity<>(request, headers);
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
        var adminToken = login(authAdminUser, authAdminPassword, "master").getToken();
        var rest = getRestTemplate();

        // Create user.
        var request = getKeycloakCreateUserJson(username, email, password);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(adminToken);
        var httpEntity = new HttpEntity<>(request, headers);
        var response = rest.postForEntity(getKeycloakCreateUserUrl(), httpEntity, String.class);
        var httpStatus = response.getStatusCode();

        // Error handling.
        if (httpStatus == HttpStatus.CONFLICT) {
            throw new ConflictException(response.getBody());
        } else if (httpStatus != HttpStatus.CREATED) {
            throw new InternalServerErrorException("Unhandled Exception from create user rest call to keycloak (Status: " + httpStatus + ", Body: " + response.getBody() + ")");
        }

        // Save user id for later.
        var location = response.getHeaders().getLocation();
        if (location == null) {
            throw new InternalServerErrorException("Keycloak must return ");
        }
        var locationStr = location.toString();
        var userId = locationStr.substring(locationStr.lastIndexOf("/") + 1);


        // Get available realm roles.
        httpEntity = new HttpEntity<>(null, headers);
        response = rest.exchange(getKeycloakGetRealmRolesUrl(), HttpMethod.GET, httpEntity, String.class);
        httpStatus = response.getStatusCode();
        var realmRolesJson = response.getBody();

        // Error handling.
        if (httpStatus != HttpStatus.OK) {
            throw new InternalServerErrorException("Unhandled Exception from get realm roles rest call to keycloak (Status: " + httpStatus + ", Body: " + response.getBody() + ")");
        }


        // Assign realm roles to user.
        request = getKeycloakUpdateUserRealmRolesJson(realmRolesJson);
        httpEntity = new HttpEntity<>(request, headers);
        response = rest.postForEntity(getKeycloakSetUserRolesUrl(userId), httpEntity, String.class);
        httpStatus = response.getStatusCode();

        // Error handling.
        if (httpStatus != HttpStatus.NO_CONTENT) {
            throw new InternalServerErrorException("Unhandled Exception from set user roles rest call to keycloak (Status: " + httpStatus + ", Body: " + response.getBody() + ")");
        }

        return new AuthRegisterUserDto(username, email);
    }

    private String getKeycloakCreateUserJson(String username, String email, String password) {
        return "{" +
                //"\"firstName\":\"xyz\", " +
                //"\"lastName\":\"xyz\", " +
                "\"email\":\"" + email + "\", " +
                "\"enabled\":\"true\", " +
                "\"username\":\"" + username + "\", " +
                //"\"realmRoles\": " + getRealmRolesJsonArray() + ", " +
                "\"credentials\": [{\"type\":\"password\", \"value\":\"" + password + "\", \"temporary\":false}]" +
                "}";
    }

    @SneakyThrows
    private String getKeycloakUpdateUserRealmRolesJson(String realmRolesJson) {
        var roleList = new ArrayList<String>();

        var realmRoles = new JSONArray(realmRolesJson);
        for (int i = 0; i < realmRoles.length(); i++) {
            var realmRole = realmRoles.getJSONObject(i);
            var roleId = realmRole.getString("id");
            var roleName = realmRole.getString("name");

            for (var role : AuthUtil.ALL_ROLES) {
                if(role.equals(roleName)){
                roleList.add("{\"id\": \""+roleId+"\" ,\"name\": \"" + roleName + "\"}");
                }
            }
        }

        return "[" + String.join(", ", roleList) + "]";
    }

    @Override
    public AuthRegisterRealmDto registerRealm(String authAdminUsername, String authAdminPassword, String realm) {
        var adminToken = login(authAdminUsername, authAdminPassword, "master").getToken();
        var rest = getRestTemplate();
        var request = getKeycloakRealmImportJson(realm);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(adminToken);
        var httpEntity = new HttpEntity<>(request, headers);
        var response = rest.postForEntity(getKeycloakRegisterRealmUrl(), httpEntity, String.class);
        var httpStatus = response.getStatusCode();

        // Error handling.
        if (httpStatus == HttpStatus.CONFLICT) {
            throw new ConflictException("Realm \"" + realm + "\" does already exist" + response.getBody());
        } else if (httpStatus != HttpStatus.CREATED) {
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

        // Convert json to JSONObject to definitely get a json with new lines per attribute.
        var jsonObject = new JSONObject(realmImportJson);
        realmImportJson = jsonObject.toString(4);

        // Replace realm name.
        realmImportJson = realmImportJson.replace("evatool-realm", realm);

        // Re-assign ids.
        var lines = realmImportJson.split("\\r?\\n");
        for (var line : lines) {

            // Check if an id is in the line.
            if (line.toLowerCase().contains("id\": ")) {

                // Retrieve id from line.
                var oldId = line.split(":")[1].trim().replace("\"", "").replace(",", "");

                // Check if id is UUID.
                if (UUIDUtil.isValidUUID(oldId)) {

                    // Change oldId to newId in whole json.
                    var newId = UUID.randomUUID().toString();
                    realmImportJson = realmImportJson.replace(oldId, newId);
                }
            }
        }

        return realmImportJson;
    }

    private RestTemplate getRestTemplate() {
        return new RestTemplateBuilder()
                .errorHandler(new RestTemplateResponseErrorHandlerIgnore())
                .build();
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

    private String getClientId(String realm) {
        return realm.equals("master") ? "admin-cli" : "evatool-app";
    }

    // Dynamic keycloak URLs.
    @Value("${keycloak.auth-server-url:}")
    private String keycloakBaseUrl;

    public String getKeycloakLoginUrl(String realm) {
        return keycloakBaseUrl + "realms/" + realm + "/protocol/openid-connect/token";
    }

    private String getKeycloakCreateUserUrl() {
        return keycloakBaseUrl + "admin/realms/evatool-realm/users";
    }

    private String getKeycloakGetRealmRolesUrl() {
        return keycloakBaseUrl + "admin/realms/evatool-realm/roles";
    }

    private String getKeycloakSetUserRolesUrl(String userId) {
        return keycloakBaseUrl + "admin/realms/evatool-realm/users/" + userId + "/role-mappings/realm";
    }

    private String getKeycloakRegisterRealmUrl() {
        return keycloakBaseUrl + "admin/realms";
    }
}
