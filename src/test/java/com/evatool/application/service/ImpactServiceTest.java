package com.evatool.application.service;

import com.evatool.application.dto.ImpactDto;
import com.evatool.application.service.impl.ImpactServiceImpl;
import com.evatool.domain.entity.Impact;
import org.springframework.beans.factory.annotation.Autowired;

class ImpactServiceTest extends CrudServiceTest<Impact, ImpactDto> implements FindByAnalysisServiceTest {

    @Autowired
    private ImpactServiceImpl service;

}
