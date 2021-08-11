package com.evatool.application.mapper;

import com.evatool.application.dto.StakeholderDto;
import com.evatool.domain.entity.Stakeholder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StakeholderMapperTest extends SuperMapperTest<Stakeholder, StakeholderDto, StakeholderDtoMapper> {

    @Autowired
    private StakeholderDtoMapper mapper;

}
