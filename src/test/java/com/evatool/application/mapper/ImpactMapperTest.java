package com.evatool.application.mapper;

import com.evatool.application.dto.ImpactDto;
import com.evatool.domain.entity.Impact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ImpactMapperTest extends SuperMapperTest<Impact, ImpactDto, ImpactMapper> {

    @Autowired
    private ImpactMapper mapper;

}
