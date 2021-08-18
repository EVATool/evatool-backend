package com.evatool.application.service;

import com.evatool.common.exception.CrossRealmAccessException;
import com.evatool.domain.entity.SuperEntity;
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

    public static String getCurrentRealm() {
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

        var realm = getCurrentRealm();
        if (!entity.getRealm().equals(realm)) {
            throw new CrossRealmAccessException();
        }
    }

    public static <S extends SuperEntity> Iterable<S> handleFind(Iterable<S> entities) {
        if (!multiTenancyEnabled) {
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
        if (!multiTenancyEnabled) {
            return;
        }

        var realm = getCurrentRealm();
        entity.setRealm(realm);
    }

    public static <S extends SuperEntity> void handleUpdate(S entity) {
        if (!multiTenancyEnabled) {
            return;
        }

        var realm = getCurrentRealm();
        if (!entity.getRealm().equals(realm)) {
            throw new CrossRealmAccessException();
        }
    }

    public static <S extends SuperEntity> void handleDelete(S entity) {
        if (!multiTenancyEnabled) {
            return;
        }

        var realm = getCurrentRealm();
        if (!entity.getRealm().equals(realm)) {
            throw new CrossRealmAccessException();
        }
    }
}
