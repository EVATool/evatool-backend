package com.evatool.application.service;

import com.evatool.application.dto.VariantDto;
import com.evatool.application.service.impl.VariantServiceImpl;
import com.evatool.domain.entity.Variant;
import org.springframework.beans.factory.annotation.Autowired;

class VariantServiceTest extends CrudServiceTest<Variant, VariantDto> implements FindByAnalysisServiceTest {

    @Autowired
    private VariantServiceImpl service;

}
