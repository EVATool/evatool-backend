package com.evatool.common.config;

import org.keycloak.adapters.AdapterDeploymentContext;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class MultiTenancyAuthEntryPoint extends KeycloakAuthenticationEntryPoint {

    public MultiTenancyAuthEntryPoint(AdapterDeploymentContext adapterDeploymentContext) {
        super(adapterDeploymentContext);
    }

    public MultiTenancyAuthEntryPoint(AdapterDeploymentContext adapterDeploymentContext, RequestMatcher apiRequestMatcher) {
        super(adapterDeploymentContext, apiRequestMatcher);
    }

//    @Override
//    protected void commenceLoginRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
//
//        String path = request.getRequestURI();
//        int multitenantIndex = path.indexOf("tenant/");
//        if (multitenantIndex == -1) {
//            throw new IllegalStateException("Not able to resolve realm from the request path!");
//        }
//
//        String realm = path.substring(path.indexOf("tenant/")).split("/")[1];
//        if (realm.contains("?")) {
//            realm = realm.split("\\?")[0];
//        }
//
//        String contextAwareLoginUri = request.getContextPath() + "/tenant/" + realm + DEFAULT_LOGIN_URI;
//        response.sendRedirect(contextAwareLoginUri);
//    }
}
