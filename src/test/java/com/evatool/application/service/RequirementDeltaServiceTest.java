package com.evatool.application.service;

import com.evatool.application.dto.RequirementDeltaDto;
import com.evatool.application.service.impl.RequirementDeltaServiceImpl;
import com.evatool.domain.entity.RequirementDelta;
import org.springframework.beans.factory.annotation.Autowired;

class RequirementDeltaServiceTest extends CrudServiceTest<RequirementDelta, RequirementDeltaDto> implements FindByAnalysisServiceTest {

    @Autowired
    private RequirementDeltaServiceImpl service;

}
