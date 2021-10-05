package com.evatool.domain;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Tag("integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class DatabaseMigrationIntegrationTest {

    @Test
    void migrate() {
        System.out.println("INTEGRATION TEST FLYWAY ---");
    }
}
