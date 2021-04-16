package com.evatool.global.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.client.LinkDiscoverer;
import org.springframework.hateoas.client.LinkDiscoverers;
import org.springframework.hateoas.mediatype.collectionjson.CollectionJsonLinkDiscoverer;
import org.springframework.plugin.core.SimplePluginRegistry;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;

import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


// http://localhost:8080/swagger-ui.html#/
@Configuration
@EnableSwagger2
public class SwaggerConfig {


      @Bean public LinkDiscoverers discoverers() {
      List<LinkDiscoverer> plugins = new ArrayList<>();
      plugins.add(new CollectionJsonLinkDiscoverer());
      return new LinkDiscoverers(SimplePluginRegistry.create(plugins));
      }


    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(RequestHandlerSelectors
                        .basePackage("com.evatool"))
                .build().apiInfo(getAPIMetaData());
    }

    private ApiInfo getAPIMetaData() {
        String archiLabUrl = "https://archi-lab.io/";
        Contact contact = new Contact("Stefan Bente", archiLabUrl, "stefan.bente@th-koeln.de");
        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder()
                .description("The backend of the Eva-tool")
                .title("EVA-tool")
                .version("1.0")
                .contact(contact);
        return apiInfoBuilder.build();
        
    }
}
