package com.evatool.application.service;

import com.evatool.domain.entity.SuperEntity;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class TenantHandler {

    private static final Logger logger = LoggerFactory.getLogger(TenantHandler.class);

    public static HttpServletRequest getCurrentHttpRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            return request;
        }
        logger.debug("Not called in the context of an HTTP request");
        return null;
    }

    public static String getCurrentRealm() {
        var request = getCurrentHttpRequest();
        KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) request.getUserPrincipal();
        KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
        KeycloakSecurityContext session = principal.getKeycloakSecurityContext();
        AccessToken accessToken = session.getToken();

        var issuer = accessToken.getIssuer();
        var realm = issuer.substring(issuer.lastIndexOf("/") + 1);
        return realm;
    }

    public static void handleGet(SuperEntity entity) {

    }

    public static void handleGet(Iterable<SuperEntity> entities) {

    }

    public static void handlePost(SuperEntity entity) {

    }

    public static void handlePut(SuperEntity entity) {

    }

    public static void handleDelete(SuperEntity entity) {

    }
}
