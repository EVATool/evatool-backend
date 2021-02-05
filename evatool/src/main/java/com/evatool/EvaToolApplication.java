package com.evatool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EvaToolApplication {
    public static void main(String[] args) {
        SpringApplication.run(EvaToolApplication.class, args);
        System.out.println("EVATool started successfully.");
    }
}
