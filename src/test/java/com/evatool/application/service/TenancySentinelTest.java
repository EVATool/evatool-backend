package com.evatool.application.service;

import com.evatool.common.enums.ValueType;
import com.evatool.domain.entity.Analysis;
import com.evatool.domain.entity.Value;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;

class TenancySentinelTest { // TODO The TenancySentinel should be tested. How to test a class that reads env vars and requires RequestContext + KeycloakToken

//    static {
//        System.setProperty("evatool.auth.enabled", "true");
//        System.setProperty("evatool.auth.multi-tenancy.enabled", "true");
//    }
//
//    @BeforeAll
//    private void enableTenancy() {
//        TenancySentinel.
//    }
//
//    @AfterAll
//    private void disableTenancy() {
//
//    }
//
//    @Test
//    void test() {
//        System.out.println(System.getProperty("evatool.auth.enabled"));
//        TenancySentinel.handleCreate(new Value("", "", ValueType.SOCIAL, false, new Analysis("", "", false)));
//    }
}
