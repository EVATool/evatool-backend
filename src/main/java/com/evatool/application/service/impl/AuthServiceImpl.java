package com.evatool.application.service.impl;

import com.evatool.application.dto.AuthRegisterRealmDto;
import com.evatool.application.dto.AuthRegisterUserDto;
import com.evatool.application.dto.AuthTokenDto;
import com.evatool.application.service.api.AuthService;
import com.evatool.common.exception.InternalServerErrorException;
import com.evatool.common.exception.functional.http401.InvalidCredentialsException;
import com.evatool.common.exception.functional.http404.RealmNotFoundException;
import com.evatool.common.exception.functional.http404.UsernameNotFoundException;
import com.evatool.common.exception.functional.http409.EmailAlreadyTakenException;
import com.evatool.common.exception.functional.http409.RealmAlreadyTakenException;
import com.evatool.common.exception.functional.http409.UsernameAlreadyTakenException;
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
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private static final String KEYCLOAK_MASTER_REALM = "master";

    private final RestTemplate restTemplate;

    @Value("${evatool.auth.registration.enabled:false}")
    private boolean registrationEnabled;

    @Value("${evatool.auth.admin-user:}")
    private String authAdminUser;

    @Value("${evatool.auth.admin-password:}")
    private String authAdminPassword;

    public AuthServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandlerIgnore());
    }

    public AuthTokenDto login(String username, String password, String realm) {
        var clientId = getClientId(realm);
        var request = getLoginRequest(username, password, clientId);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        var httpEntity = new HttpEntity<>(request, headers);
        var response = restTemplate.postForEntity(getKeycloakLoginUrl(realm), httpEntity, String.class);
        logKeycloakResponse(response);
        var httpStatus = response.getStatusCode();

        // Error handling.
        if (httpStatus == HttpStatus.NOT_FOUND) {
            if (username.equals(realm)) {
                throw new UsernameNotFoundException(username); // TODO can this even happen?
            } else {
                throw new RealmNotFoundException(realm);
            }
        } else if (httpStatus == HttpStatus.UNAUTHORIZED) {
            throw new InvalidCredentialsException();
        } else if (httpStatus != HttpStatus.OK) {
            throw new InternalServerErrorException("Unhandled response from login rest call to keycloak (Status: " + httpStatus + ", Body: " + response.getBody() + ")");
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
        var clientId = getClientId(realm);
        var request = getRefreshLoginRequest(refreshToken, clientId);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        var httpEntity = new HttpEntity<>(request, headers);
        var response = restTemplate.postForEntity(getKeycloakLoginUrl(realm), httpEntity, String.class);
        logKeycloakResponse(response);
        var httpStatus = response.getStatusCode();

        // Error handling.

        // TODO: com.evatool.common.exception.InternalServerErrorException: Unhandled response from refresh login rest call to keycloak (Status: 400 BAD_REQUEST, Body: {"error":"invalid_grant","error_description":"Session not active"})	at com.evatool.application.service.impl.AuthServiceImpl.refreshLogin(AuthServiceImpl.java:102)	at com.evatool.application.controller.impl.AuthControllerImpl.refreshLogin(AuthControllerImpl.java:41)	at com.evatool.application.controller.impl.AuthControllerImpl$$FastClassBySpringCGLIB$$240e39b1.invoke(<generated>)	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:779)	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:750)	at org.springframework.validation.beanvalidation.MethodValidationInterceptor.invoke(MethodValidationInterceptor.java:123)	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:750)	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:692)	at com.evatool.application.controller.impl.AuthControllerImpl$$EnhancerBySpringCGLIB$$a5585d19.refreshLogin(<generated>)	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)	at java.base/java.lang.reflect.Method.invoke(Method.java:566)	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:197)	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:141)	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:106)	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:894)	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:808)	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1060)	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:962)	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006)	at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:909)	at javax.servlet.http.HttpServlet.service(HttpServlet.java:652)	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883)	at javax.servlet.http.HttpServlet.service(HttpServlet.java:733)	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:231)	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:53)	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)	at org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticatedActionsFilter.doFilter(KeycloakAuthenticatedActionsFilter.java:57)	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)	at org.keycloak.adapters.springsecurity.filter.KeycloakSecurityContextRequestFilter.doFilter(KeycloakSecurityContextRequestFilter.java:61)	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)	at org.keycloak.adapters.springsecurity.filter.KeycloakPreAuthActionsFilter.doFilter(KeycloakPreAuthActionsFilter.java:96)	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)	at org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter.doFilter(AbstractAuthenticationProcessingFilter.java:218)	at org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter.doFilter(AbstractAuthenticationProcessingFilter.java:212)	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:327)	at org.springframework.security.web.access.intercept.FilterSecurityInterceptor.invoke(FilterSecurityInterceptor.java:115)	at org.springframework.security.web.access.intercept.FilterSecurityInterceptor.doFilter(FilterSecurityInterceptor.java:81)	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)	at org.springframework.security.web.access.ExceptionTranslationFilter.doFilter(ExceptionTranslationFilter.java:119)	at org.springframework.security.web.access.ExceptionTranslationFilter.doFilter(ExceptionTranslationFilter.java:113)	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)	at org.springframework.security.web.session.SessionManagementFilter.doFilter(SessionManagementFilter.java:126)	at org.springframework.security.web.session.SessionManagementFilter.doFilter(SessionManagementFilter.java:81)	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)	at org.springframework.security.web.authentication.AnonymousAuthenticationFilter.doFilter(AnonymousAuthenticationFilter.java:105)	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)	at org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticatedActionsFilter.doFilter(KeycloakAuthenticatedActionsFilter.java:74)	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)	at org.keycloak.adapters.springsecurity.filter.KeycloakSecurityContextRequestFilter.doFilter(KeycloakSecurityContextRequestFilter.java:92)	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)	at org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter.doFilter(SecurityContextHolderAwareRequestFilter.java:149)	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)	at org.springframework.security.web.savedrequest.RequestCacheAwareFilter.doFilter(RequestCacheAwareFilter.java:63)	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)	at org.springframework.security.web.authentication.logout.LogoutFilter.doFilter(LogoutFilter.java:103)	at org.springframework.security.web.authentication.logout.LogoutFilter.doFilter(LogoutFilter.java:89)	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)	at org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter.doFilter(AbstractAuthenticationProcessingFilter.java:218)	at org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter.doFilter(AbstractAuthenticationProcessingFilter.java:212)	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)	at org.keycloak.adapters.springsecurity.filter.KeycloakPreAuthActionsFilter.doFilter(KeycloakPreAuthActionsFilter.java:96)	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)	at org.springframework.web.filter.CorsFilter.doFilterInternal(CorsFilter.java:91)	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)	at org.springframework.security.web.header.HeaderWriterFilter.doHeadersAfter(HeaderWriterFilter.java:90)	at org.springframework.security.web.header.HeaderWriterFilter.doFilterInternal(HeaderWriterFilter.java:75)	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)	at org.springframework.security.web.context.SecurityContextPersistenceFilter.doFilter(SecurityContextPersistenceFilter.java:110)	at org.springframework.security.web.context.SecurityContextPersistenceFilter.doFilter(SecurityContextPersistenceFilter.java:80)	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)	at org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter.doFilterInternal(WebAsyncManagerIntegrationFilter.java:55)	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)	at org.springframework.security.web.FilterChainProxy$VirtualFilterChain.doFilter(FilterChainProxy.java:336)	at org.springframework.security.web.FilterChainProxy.doFilterInternal(FilterChainProxy.java:211)	at org.springframework.security.web.FilterChainProxy.doFilter(FilterChainProxy.java:183)	at org.springframework.web.filter.DelegatingFilterProxy.invokeDelegate(DelegatingFilterProxy.java:358)	at org.springframework.web.filter.DelegatingFilterProxy.doFilter(DelegatingFilterProxy.java:271)	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)	at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100)	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)	at org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93)	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201)	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:202)	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:97)	at org.keycloak.adapters.tomcat.AbstractAuthenticatedActionsValve.invoke(AbstractAuthenticatedActionsValve.java:67)	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:542)	at org.keycloak.adapters.tomcat.AbstractKeycloakAuthenticatorValve.invoke(AbstractKeycloakAuthenticatorValve.java:181)	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:143)	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:92)	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:78)	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:343)	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:374)	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:65)	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:888)	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1597)	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)	at java.base/java.lang.Thread.run(Thread.java:829)

        if (httpStatus != HttpStatus.OK) {
            throw new InternalServerErrorException("Unhandled response from refresh login rest call to keycloak (Status: " + httpStatus + ", Body: " + response.getBody() + ")");
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
        var adminToken = login(authAdminUser, authAdminPassword, KEYCLOAK_MASTER_REALM).getToken();

        // Create user.
        var request = getKeycloakCreateUserJson(username, email, password);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(adminToken);
        var httpEntity = new HttpEntity<>(request, headers);
        var response = restTemplate.postForEntity(getKeycloakCreateUserUrl(), httpEntity, String.class);
        logKeycloakResponse(response);
        var httpStatus = response.getStatusCode();

        // Error handling.
        if (httpStatus == HttpStatus.CONFLICT) {
            var responseBody = response.getBody();
            if (responseBody != null && responseBody.contains("\"errorMessage\":\"User exists with same email\"")) {
                throw new EmailAlreadyTakenException(email);
            } else {
                throw new UsernameAlreadyTakenException(username);
            }
        } else if (httpStatus != HttpStatus.CREATED) {
            throw new InternalServerErrorException("Unhandled response from create user rest call to keycloak (Status: " + httpStatus + ", Body: " + response.getBody() + ")");
        }

        // Save user id for later.
        var location = response.getHeaders().getLocation();
        if (location == null) {
            throw new InternalServerErrorException("Keycloak must return the freshly created user id in some way");
        }
        var locationStr = location.toString();
        var userId = locationStr.substring(locationStr.lastIndexOf("/") + 1);


        // Get available realm roles.
        httpEntity = new HttpEntity<>(null, headers);
        response = restTemplate.exchange(getKeycloakGetRealmRolesUrl(), HttpMethod.GET, httpEntity, String.class);
        logKeycloakResponse(response);
        httpStatus = response.getStatusCode();
        var realmRolesJson = response.getBody();

        // Error handling.
        if (httpStatus != HttpStatus.OK) {
            throw new InternalServerErrorException("Unhandled response from get realm roles rest call to keycloak (Status: " + httpStatus + ", Body: " + response.getBody() + ")");
        }


        // Assign realm roles to user.
        request = getKeycloakUpdateUserRealmRolesJson(realmRolesJson);
        httpEntity = new HttpEntity<>(request, headers);
        response = restTemplate.postForEntity(getKeycloakSetUserRolesUrl(userId), httpEntity, String.class);
        logKeycloakResponse(response);
        httpStatus = response.getStatusCode();

        // Error handling.
        if (httpStatus != HttpStatus.NO_CONTENT) {
            throw new InternalServerErrorException("Unhandled response from set user roles rest call to keycloak (Status: " + httpStatus + ", Body: " + response.getBody() + ")");
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
        var adminToken = login(authAdminUsername, authAdminPassword, KEYCLOAK_MASTER_REALM).getToken();
        var request = getKeycloakRealmImportJson(realm);
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(adminToken);
        var httpEntity = new HttpEntity<>(request, headers);
        var response = restTemplate.postForEntity(getKeycloakRegisterRealmUrl(), httpEntity, String.class);
        logKeycloakResponse(response);
        var httpStatus = response.getStatusCode();

        // Error handling.
        if (httpStatus == HttpStatus.CONFLICT) {
            throw new RealmAlreadyTakenException(realm);
        } else if (httpStatus != HttpStatus.CREATED) {
            throw new InternalServerErrorException("Unhandled response from create realm rest call to keycloak (Status: " + httpStatus + ", Body: " + response.getBody() + ")");
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
        return realm.equals(KEYCLOAK_MASTER_REALM) ? "admin-cli" : "evatool-app";
    }

    private void logKeycloakResponse(ResponseEntity<String> response) {
        logger.info("Keycloak responded with status {}: {}", response.getStatusCode(), response.getBody());
    }

    // Dynamic keycloak URLs.
    @Value("${keycloak.auth-server-url:}")
    private String keycloakBaseUrl;

    public String getKeycloakLoginUrl(String realm) {
        return keycloakBaseUrl + "realms/" + realm + "/protocol/openid-connect/token";
    }

    public String getKeycloakCreateUserUrl() {
        return keycloakBaseUrl + "admin/realms/evatool-realm/users";
    }

    public String getKeycloakGetRealmRolesUrl() {
        return keycloakBaseUrl + "admin/realms/evatool-realm/roles";
    }

    public String getKeycloakSetUserRolesUrl(String userId) {
        return keycloakBaseUrl + "admin/realms/evatool-realm/users/" + userId + "/role-mappings/realm";
    }

    public String getKeycloakRegisterRealmUrl() {
        return keycloakBaseUrl + "admin/realms";
    }
}
