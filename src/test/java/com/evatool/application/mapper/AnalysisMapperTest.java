package com.evatool.application.mapper;

import com.evatool.application.dto.AnalysisDto;
import com.evatool.domain.entity.Analysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AnalysisMapperTest extends SuperMapperTest<Analysis, AnalysisDto, AnalysisDtoMapper> {

    @Autowired
    private AnalysisDtoMapper mapper;

}
