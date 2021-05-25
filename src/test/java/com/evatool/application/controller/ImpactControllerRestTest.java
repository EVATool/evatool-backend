package com.evatool.application.controller;

import com.evatool.application.controller.impl.ImpactControllerImpl;
import com.evatool.application.dto.ImpactDto;
import com.evatool.domain.entity.Impact;
import org.springframework.beans.factory.annotation.Autowired;

public class ImpactControllerRestTest extends CrudControllerRestTest<Impact, ImpactDto> implements FindByAnalysisControllerRestTest {

    @Autowired
    private ImpactControllerImpl controller;

}
