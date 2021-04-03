package com.evatool.impact.application.controller;

import com.evatool.EvaToolApp;
import com.evatool.global.config.SwaggerConfig;
import com.evatool.impact.application.service.ImpactService;
import com.evatool.impact.application.service.ImpactValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@ContextConfiguration(classes = {SwaggerConfig.class, EvaToolApp.class})
abstract public class ControllerMockTest {

    @Autowired
    protected MockMvc mvc;

    @MockBean
    protected ImpactService impactService;

    @MockBean
    protected ImpactValueService impactValueService;
}
