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

    // TODO Fix redirection of backend when using keycloak (is tenancy the problem? There is commented out coded in the AuthEntryPoint)
    //  https://stackoverflow.com/questions/64202799/keycloak-is-it-possible-to-let-the-users-to-choose-a-realm-in-login-page/64225384
    //  https://www.baeldung.com/keycloak-custom-login-page
//    @Override
//    protected void commenceLoginRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        String contextAwareLoginUri = request.getContextPath() + DEFAULT_LOGIN_URI;
//        response.sendRedirect(contextAwareLoginUri);
//    }
}
