package com.evatool.application.controller.auth;

import com.evatool.common.util.AuthUtil;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('" + AuthUtil.USER_ROLE + "')" +
        " || hasAuthority('" + AuthUtil.ADMIN_ROLE + "')")
public @interface IsAuthorizedAsUserOrAdmin {
}
