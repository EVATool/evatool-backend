package com.evatool.application.service;

import com.evatool.application.dto.ValueTypeDto;
import com.evatool.application.service.impl.ValueTypeServiceImpl;
import com.evatool.domain.entity.ValueType;
import org.springframework.beans.factory.annotation.Autowired;

public class ValueTypeServiceTest extends CrudServiceTest<ValueType, ValueTypeDto> implements FindByAnalysisServiceTest {

    @Autowired
    private ValueTypeServiceImpl service;

}
