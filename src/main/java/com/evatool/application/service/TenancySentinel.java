package com.evatool.application.service;

import com.evatool.common.exception.CrossRealmAccessException;
import com.evatool.domain.entity.SuperEntity;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Service
public class TenancySentinel {

    private static final Logger logger = LoggerFactory.getLogger(TenancySentinel.class);

    @Value("${multi-tenancy.enabled}")
    public void setMultiTenancyActive(boolean multiTenancyActive) {
        TenancySentinel.multiTenancyActive = multiTenancyActive;
    }
    private static boolean multiTenancyActive;

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

    public static <S extends SuperEntity> void handleFind(S entity) {
        if (!multiTenancyActive) {
            return;
        }

        var realm = getCurrentRealm();
        if (!entity.getRealm().equals(realm)) {
            throw new CrossRealmAccessException();
        }
    }

    public static <S extends SuperEntity> Iterable<S> handleFind(Iterable<S> entities) {
        System.out.println(multiTenancyActive);
        if (!multiTenancyActive) {
            return entities;
        }

        var realm = getCurrentRealm();
        var realmEntities = new ArrayList<S>();

        for (var entity : entities) {
            if (entity.getRealm().equals(realm)) {
                realmEntities.add(entity);
            }
        }

        return realmEntities;
    }

    public static <S extends SuperEntity> void handleCreate(S entity) {
        if (!multiTenancyActive) {
            return;
        }

        var realm = getCurrentRealm();
        entity.setRealm(realm);
    }

    public static <S extends SuperEntity> void handleUpdate(S entity) {
        if (!multiTenancyActive) {
            return;
        }

        var realm = getCurrentRealm();
        if (!entity.getRealm().equals(realm)) {
            throw new CrossRealmAccessException();
        }
    }

    public static <S extends SuperEntity> void handleDelete(S entity) {
        if (!multiTenancyActive) {
            return;
        }

        var realm = getCurrentRealm();
        if (!entity.getRealm().equals(realm)) {
            throw new CrossRealmAccessException();
        }
    }
}
