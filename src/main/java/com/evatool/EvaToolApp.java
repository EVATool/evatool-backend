package com.evatool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
public class EvaToolApp {
    public static void main(String[] args) {
        SpringApplication.run(EvaToolApp.class, args);
    }
}
