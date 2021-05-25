package com.evatool.application.service;

import com.evatool.application.dto.StakeholderDto;
import com.evatool.application.service.impl.StakeholderServiceImpl;
import com.evatool.domain.entity.Stakeholder;
import org.springframework.beans.factory.annotation.Autowired;

public class StakeholderServiceTest extends CrudServiceTest<Stakeholder, StakeholderDto> implements FindByAnalysisServiceTest {

    @Autowired
    private StakeholderServiceImpl service;

}
