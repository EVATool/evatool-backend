package com.evatool.common.config;

import com.evatool.common.exception.CrossRealmAccessException;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.OIDCHttpFacade;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.adapters.config.AdapterConfig;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class GenericConfigResolver implements KeycloakConfigResolver {

    @SuppressWarnings("unused")
    private static AdapterConfig adapterConfig;

    private String keycloakUrl = "http://localhost:8081/auth/"; // TODO get from env variables

    public static String getCurrentRealm() {
        var request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        System.out.println(request.getRequestURI());
        System.out.println(request.getHeader("Authorization"));
        if (request.getUserPrincipal() == null) {
            return null;
        }
        KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) request.getUserPrincipal();
        System.out.println(token);
        if (token == null) { // Request not authenticated.
            return null;
        }
        KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
        KeycloakSecurityContext session = principal.getKeycloakSecurityContext();
        AccessToken accessToken = session.getToken();
        var issuer = accessToken.getIssuer();
        var realm = issuer.substring(issuer.lastIndexOf("/") + 1);
        return realm;
    }

    @Override
    public KeycloakDeployment resolve(OIDCHttpFacade.Request request) {

        var uri = request.getURI();
        var realmName = getCurrentRealm();

        System.out.println(realmName);

        if (realmName == null) {
            realmName = "DUMMY-DOES-NOT-EXIST-PLACEHOLDER-REALM";
            //throw new CrossRealmAccessException(); // TODO CHANGE
        }

        var adapterConfig = new AdapterConfig();
        adapterConfig.setRealm(realmName);
        adapterConfig.setResource("evatool-app");
        adapterConfig.setPublicClient(true);
        adapterConfig.setAuthServerUrl(keycloakUrl);
        adapterConfig.setSslRequired("external");

        var keycloakDeployment = KeycloakDeploymentBuilder.build(adapterConfig);

        return keycloakDeployment;
    }

    static void setAdapterConfig(AdapterConfig adapterConfig) {
        GenericConfigResolver.adapterConfig = adapterConfig;
    }
}
