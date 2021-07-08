package com.evatool.common.config;

import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.OIDCHttpFacade;
import org.keycloak.representations.adapters.config.AdapterConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class GenericConfigResolver implements KeycloakConfigResolver {

    private static final Logger logger = LoggerFactory.getLogger(GenericConfigResolver.class);

    @SuppressWarnings("unused")
    private static AdapterConfig adapterConfig;

    @Value("${keycloak.auth-server-url:}")
    private String keycloakUrl;

    public static String getCurrentRealm() {
//        var request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        System.out.println(request.getRequestURI());
//        System.out.println(request.getHeader("Authorization"));
//        if (request.getUserPrincipal() == null) {
//            return null;
//        }
//        KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) request.getUserPrincipal();
//        System.out.println(token);
//        if (token == null) { // Request not authenticated.
//            return null;
//        }
//        KeycloakPrincipal principal = (KeycloakPrincipal) token.getPrincipal();
//        KeycloakSecurityContext session = principal.getKeycloakSecurityContext();
//        AccessToken accessToken = session.getToken();
//        var issuer = accessToken.getIssuer();
//        var realm = issuer.substring(issuer.lastIndexOf("/") + 1);
//        return realm;

        var request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        // TODO This requires the realm to be send from frontend.
        //  How to get realm from keycloak Token or request?
        //  The method used in TenancySentinel to retrieve the realm does not work here.
        //  Only token retrieval works (get realm from token string?)
        var realm = request.getHeader("Realm");
        return realm;
    }

    @Override
    public KeycloakDeployment resolve(OIDCHttpFacade.Request request) {
        var realm = getCurrentRealm();
        logger.info("Request to URI {} to realm {}", request.getURI(), realm);

        if (realm == null || realm.equals("")) { // TODO Exceptions that are thrown here are ignored by GlobalExceptionHandler (How to return 403 or 404 here?)
            //throw new IllegalStateException("...");
            realm = "evatool-realm"; // This is supposed to cause a 404.
        }

        var adapterConfig = new AdapterConfig();
        adapterConfig.setRealm(realm);
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
