package com.evatool.common.config;

import com.evatool.application.service.TenancySentinel;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.KeycloakDeployment;
import org.keycloak.adapters.KeycloakDeploymentBuilder;
import org.keycloak.adapters.spi.HttpFacade.Request;
import org.keycloak.representations.adapters.config.AdapterConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class GenericConfigResolver implements KeycloakConfigResolver {

    private static final Logger logger = LoggerFactory.getLogger(GenericConfigResolver.class);

    @SuppressWarnings("unused")
    private static AdapterConfig adapterConfig;

    @Value("${evatool.auth.multi-tenancy.enabled:false}")
    private boolean multiTenancyEnabled;

    @Value("${keycloak.auth-server-url:}")
    private String keycloakUrl;

    @Override
    public KeycloakDeployment resolve(Request request) {
        var realm = TenancySentinel.getCurrentRealmFromRequestHeader();
        logger.info("Request to URI {} to realm {}", request.getURI(), realm);

        // Use the default realm if no realm was provided.
        if (realm == null || realm.equals("")) {
            realm = "evatool-realm";
        }

        var requestAdapterConfig = new AdapterConfig();
        requestAdapterConfig.setRealm(realm);
        requestAdapterConfig.setResource("evatool-app");
        requestAdapterConfig.setPublicClient(true);
        requestAdapterConfig.setAuthServerUrl(keycloakUrl);
        requestAdapterConfig.setSslRequired("external");

        return KeycloakDeploymentBuilder.build(requestAdapterConfig);
    }

    static void setAdapterConfig(AdapterConfig adapterConfig) {
        GenericConfigResolver.adapterConfig = adapterConfig;
    }
}
