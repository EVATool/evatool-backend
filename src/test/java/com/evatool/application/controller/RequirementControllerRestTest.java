package com.evatool.application.controller;

import com.evatool.application.controller.impl.RequirementControllerImpl;
import com.evatool.application.dto.RequirementDto;
import com.evatool.domain.entity.Requirement;
import org.springframework.beans.factory.annotation.Autowired;

public class RequirementControllerRestTest extends CrudControllerRestTest<Requirement, RequirementDto> implements FindByAnalysisControllerRestTest {

    @Autowired
    private RequirementControllerImpl controller;

}
