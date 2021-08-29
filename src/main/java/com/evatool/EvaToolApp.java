package com.evatool;

import com.evatool.common.config.GenericConfigResolver;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class EvaToolApp {
    public static void main(String[] args) {
        SpringApplication.run(EvaToolApp.class, args);
    }

    @Bean
    @ConditionalOnMissingBean(GenericConfigResolver.class)
    public KeycloakConfigResolver keycloakConfigResolver() {
        return new GenericConfigResolver();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
