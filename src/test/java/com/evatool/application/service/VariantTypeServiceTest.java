package com.evatool.application.service;

import com.evatool.application.dto.VariantTypeDto;
import com.evatool.application.service.impl.VariantTypeServiceImpl;
import com.evatool.domain.entity.VariantType;
import org.springframework.beans.factory.annotation.Autowired;

public class VariantTypeServiceTest extends CrudServiceTest<VariantType, VariantTypeDto> implements FindByAnalysisServiceTest {

    @Autowired
    private VariantTypeServiceImpl service;

}
