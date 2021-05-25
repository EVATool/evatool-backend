package com.evatool.application.mapper;

import com.evatool.application.dto.RequirementDeltaDto;
import com.evatool.domain.entity.RequirementDelta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RequirementDeltaMapperTest extends SuperMapperTest<RequirementDelta, RequirementDeltaDto, RequirementDeltaMapper> {

    @Autowired
    private RequirementDeltaMapper mapper;

}
