package com.evatool.application.controller;

import com.evatool.application.controller.impl.ImpactControllerImpl;
import com.evatool.application.dto.ImpactDto;
import com.evatool.domain.entity.Impact;
import org.springframework.beans.factory.annotation.Autowired;

public class ImpactControllerTest extends CrudControllerTest<Impact, ImpactDto> implements FindByAnalysisControllerTest {

    @Autowired
    private ImpactControllerImpl controller;

}
