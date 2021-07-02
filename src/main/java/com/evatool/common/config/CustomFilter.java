package com.evatool.common.config;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.adapters.spi.KeycloakAccount;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomFilter extends OncePerRequestFilter {

    //@Value("${evatool.auth.enabled}")
    private boolean securityEnabled = false;

    //@Value("${grantedRoles}")
    private String[] grantedRoles = {"reader", "writer"};

    @Override
    public void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
                                 FilterChain chain) throws IOException, ServletException {
        if (!securityEnabled) {
            // Read roles from application.yml
            Set<String> roles = Arrays.stream(grantedRoles).collect(Collectors.toCollection(HashSet::new));

            System.out.println(roles);

            // Dummy Keycloak-Account.
            RefreshableKeycloakSecurityContext session = new RefreshableKeycloakSecurityContext(
                    null, null, null, null, null, null, null);
            final KeycloakPrincipal<RefreshableKeycloakSecurityContext> principal = new KeycloakPrincipal<>("Dummy_Principal", session);
            final KeycloakAccount account = new SimpleKeycloakAccount(principal, roles, principal.getKeycloakSecurityContext());

            // Dummy Security Context.
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(new KeycloakAuthenticationToken(account, false));
            SecurityContextHolder.setContext(context);

            // Skip the rest of the filters.
            req.getRequestDispatcher(req.getServletPath()).forward(req, res);
        }

        chain.doFilter(req, res);
    }
}
