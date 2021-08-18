package com.evatool.application.service;

import com.evatool.common.exception.CrossRealmAccessException;
import com.evatool.domain.entity.SuperEntity;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
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


    private static boolean authEnabled;

    @Value("${evatool.auth.enabled:false}")
    public void setAuthEnabled(boolean authEnabled) {
        TenancySentinel.authEnabled = authEnabled;
    }


    private static boolean multiTenancyEnabled;

    @Value("${evatool.auth.multi-tenancy.enabled:false}")
    public void setMultiTenancyActive(boolean multiTenancyEnabled) {
        TenancySentinel.multiTenancyEnabled = multiTenancyEnabled;
    }


    private static boolean registrationEnabled;

    @Value("${evatool.auth.registration.enabled:false}")
    public void setRegistrationEnabled(boolean registrationEnabled) {
        TenancySentinel.registrationEnabled = registrationEnabled;
    }

    public static String getCurrentRealmFromRequestToken() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

            var token = (KeycloakAuthenticationToken) request.getUserPrincipal();
            var principal = (KeycloakPrincipal) token.getPrincipal();
            var session = principal.getKeycloakSecurityContext();
            var accessToken = session.getToken();
            var issuer = accessToken.getIssuer();
            return issuer.substring(issuer.lastIndexOf("/") + 1);
        }
        throw new IllegalStateException("Cannot get realm if not in request context");
    }

    public static String getCurrentRealmFromRequestHeader() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            return request.getHeader("Realm");
        }
        throw new IllegalStateException("Cannot get realm if not in request context");
    }

    public static <S extends SuperEntity> void handleFind(S entity) {
        if (!multiTenancyEnabled) {
            return;
        }

        var realm = getCurrentRealmFromRequestToken();
        if (!entity.getRealm().equals(realm)) {
            throw new CrossRealmAccessException();
        }
    }

    public static <S extends SuperEntity> Iterable<S> handleFind(Iterable<S> entities) {
        if (!multiTenancyEnabled) {
            return entities;
        }

        var realm = getCurrentRealmFromRequestToken();
        var realmEntities = new ArrayList<S>();

        for (var entity : entities) {
            if (entity.getRealm().equals(realm)) {
                realmEntities.add(entity);
            }
        }

        return realmEntities;
    }

    public static <S extends SuperEntity> void handleCreate(S entity) {
        if (!multiTenancyEnabled) {
            return;
        }

        var realm = getCurrentRealmFromRequestToken();
        entity.setRealm(realm);
    }

    public static <S extends SuperEntity> void handleUpdate(S entity) {
        if (!multiTenancyEnabled) {
            return;
        }

        var realm = getCurrentRealmFromRequestToken();
        if (!entity.getRealm().equals(realm)) {
            throw new CrossRealmAccessException();
        }
    }

    public static <S extends SuperEntity> void handleDelete(S entity) {
        if (!multiTenancyEnabled) {
            return;
        }

        var realm = getCurrentRealmFromRequestToken();
        if (!entity.getRealm().equals(realm)) {
            throw new CrossRealmAccessException();
        }
    }
}
