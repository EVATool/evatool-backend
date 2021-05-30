package com.evatool.application.mapper;

import com.evatool.application.dto.RequirementDto;
import com.evatool.domain.entity.Requirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RequirementMapperTest extends SuperMapperTest<Requirement, RequirementDto, RequirementMapper> {

    @Autowired
    private RequirementMapper mapper;

}
