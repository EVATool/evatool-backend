package com.evatool.application.service;

import com.evatool.application.dto.RequirementDto;
import com.evatool.application.service.impl.RequirementServiceImpl;
import com.evatool.domain.entity.Requirement;
import org.springframework.beans.factory.annotation.Autowired;

class RequirementServiceTest extends CrudServiceTest<Requirement, RequirementDto> implements FindByAnalysisServiceTest {

    @Autowired
    private RequirementServiceImpl service;

}
