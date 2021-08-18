package com.evatool.common.config;

import com.evatool.application.service.TenancySentinel;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.OIDCHttpFacade;
import org.keycloak.representations.adapters.config.AdapterConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class GenericConfigResolver implements KeycloakConfigResolver {

    private static final Logger logger = LoggerFactory.getLogger(GenericConfigResolver.class);

    @SuppressWarnings("unused")
    private static AdapterConfig adapterConfig;

    @Value("${keycloak.auth-server-url:}")
    private String keycloakUrl;

    @Override
    public KeycloakDeployment resolve(OIDCHttpFacade.Request request) {
        var realm = TenancySentinel.getCurrentRealmFromRequestHeader();
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
