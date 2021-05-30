package com.evatool.application.service;

import com.evatool.application.dto.AnalysisDto;
import com.evatool.application.service.impl.AnalysisServiceImpl;
import com.evatool.domain.entity.Analysis;
import org.springframework.beans.factory.annotation.Autowired;

class AnalysisServiceTest extends CrudServiceTest<Analysis, AnalysisDto> {

    @Autowired
    private AnalysisServiceImpl service;

}
