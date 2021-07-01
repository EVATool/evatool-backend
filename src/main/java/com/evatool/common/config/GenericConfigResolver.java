package com.evatool.common.config;

import com.evatool.application.service.TenancySentinel;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.OIDCHttpFacade;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.adapters.config.AdapterConfig;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class GenericConfigResolver implements KeycloakConfigResolver {

    @SuppressWarnings("unused")
    private static AdapterConfig adapterConfig;

    private String keycloakUrl = "http://localhost:8081/auth/"; // TODO get from env variables

    public static String getBearerTokenHeader() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
    }

    @Override
    public KeycloakDeployment resolve(OIDCHttpFacade.Request request) {

        var uri = request.getURI();
        var realmName = TenancySentinel.getCurrentRealm();

        System.out.println(request.getURI());
        System.out.println(realmName);

        var t = getBearerTokenHeader();

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
