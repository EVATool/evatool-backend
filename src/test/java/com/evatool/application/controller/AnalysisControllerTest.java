package com.evatool.application.controller;

import com.evatool.application.controller.impl.AnalysisControllerImpl;
import com.evatool.application.dto.AnalysisDto;
import com.evatool.domain.entity.Analysis;
import org.springframework.beans.factory.annotation.Autowired;

class AnalysisControllerTest extends CrudControllerTest<Analysis, AnalysisDto> {

    @Autowired
    private AnalysisControllerImpl controller;

}
