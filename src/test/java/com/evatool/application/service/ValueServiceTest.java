package com.evatool.application.service;

import com.evatool.application.dto.ValueDto;
import com.evatool.application.service.impl.ValueServiceImpl;
import com.evatool.domain.entity.Value;
import org.springframework.beans.factory.annotation.Autowired;

public class ValueServiceTest extends CrudServiceTest<Value, ValueDto> implements FindByAnalysisServiceTest {

    @Autowired
    private ValueServiceImpl service;

}
