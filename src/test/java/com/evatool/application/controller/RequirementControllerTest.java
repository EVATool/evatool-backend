package com.evatool.application.controller;

import com.evatool.application.controller.impl.RequirementControllerImpl;
import com.evatool.application.dto.RequirementDto;
import com.evatool.domain.entity.Requirement;
import org.springframework.beans.factory.annotation.Autowired;

public class RequirementControllerTest extends CrudControllerTest<Requirement, RequirementDto> implements FindByAnalysisControllerTest {

    @Autowired
    private RequirementControllerImpl controller;

}
